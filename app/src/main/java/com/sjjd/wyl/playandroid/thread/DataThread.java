package com.sjjd.wyl.playandroid.thread;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.exception.HttpException;
import com.lzy.okgo.model.Response;
import com.sjjd.wyl.playandroid.utils.LogUtils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.HashMap;

/**
 * Created by wyl on 2018/4/24.
 */

public class DataThread<T> extends BaseThread {


    private Class<T> clazz;
    private String url = "";
    private int what = 0;//自定义的what


    public DataThread(Handler handler, Context context, Class<T> clazz, HashMap<String, Object> parms) {
        super(context, handler);
        this.clazz = clazz;
        url = (String) parms.get("url");
        what = (int) parms.get("what");
    }


    @Override
    protected void initData() {
        LogUtils.e(TAG + clazz.getName(), "initData: " + url);
        if (clazz.getName().equals(String.class.getName())) {
            OkGo.<String>get(url)
                    .tag(this)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            String t = response.body();
                            if (t != null) {
                                Message msg = Message.obtain();
                                msg.what = what > 0 ? what : I.LOAD_DATA_SUCCESS;
                                msg.obj = t;
                                mHandler.sendMessage(msg);
                            } else {
                                mHandler.sendEmptyMessage(I.LOAD_DATA_FAILD);
                            }
                            // sleep_time = 1000 * 5;
                        }

                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                        }
                    });
        } else {
            OkGo.<T>get(url)
                    .tag(this)
                    .execute(new JsonCallBack<T>(clazz) {
                        @Override
                        public void onSuccess(Response<T> response) {
                            T t = response.body();
                            if (t != null) {
                                Message msg = Message.obtain();

                                msg.what = what > 0 ? what : I.LOAD_DATA_SUCCESS;
                                msg.obj = t;
                                mHandler.sendMessage(msg);
                            } else {
                                mHandler.sendEmptyMessage(I.LOAD_DATA_FAILD);
                            }

                        }

                        @Override
                        public void onError(Response<T> response) {
                            super.onError(response);
                            Throwable mException = response.getException();
                            if (mException != null) {
                                mException.printStackTrace();
                            }
                            Message error = Message.obtain();
                            if (mException instanceof SocketTimeoutException) {
                                error.what = I.TIMEOUT;
                                error.obj = mException.getMessage();
                            } else if (mException instanceof UnknownHostException || mException instanceof ConnectException) {
                                error.what = I.NET_ERROR;
                                error.obj = mException.getMessage();
                            } else if (mException instanceof HttpException) {
                                error.what = I.SERVER_ERROR;
                                error.obj = "服务器异常！";
                            } else {
                                error.what = I.UNKNOWN_ERROR;
                                error.obj = "未知错误！";
                            }
                            mHandler.sendMessage(error);

                        }
                    });
        }

    }
}
