package com.sjjd.wyl.playandroid.thread;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.sjjd.wyl.playandroid.bean.ArticleBean;
import com.sjjd.wyl.playandroid.net.L;

/**
 * Created by wyl on 2018/4/27.
 */

public class ArticleListThread extends BaseThread {
    String url = "";

    String key;

    public ArticleListThread(Context context, Handler handler, int page) {
        super(context, handler);
        url = String.format(L.URL_MAIN.Get_List, page);

    }

    public ArticleListThread(Context context, Handler handler, int page, String key) {
        super(context, handler);
        url = String.format(L.URL_MAIN.Get_Search, page);
        this.key = key;

    }

    @Override
    protected void initData() {
        Log.e(TAG, "initData: " + url);
        if (key == null || key.length() < 1) {
            OkGo.<ArticleBean>get(url)
                    .tag(this)
                    .execute(new JsonCallBack<ArticleBean>(ArticleBean.class) {
                        @Override
                        public void onSuccess(Response<ArticleBean> response) {
                            Log.e(TAG, "onSuccess: " + response.body().toString());
                            ArticleBean mBody = response.body();
                            if (mBody == null) {
                                mHandler.sendEmptyMessage(L.CODE.MSG_DATA_FAILED);
                                return;
                            }
                            flag = false;
                            Message msg = Message.obtain();
                            msg.obj = mBody;
                            msg.what = L.CODE.MSG_MAIN_ARTICLE_SUCCESS;
                            mHandler.sendMessage(msg);
                        }

                        @Override
                        public void onError(Response<ArticleBean> response) {
                            super.onError(response);
                            Log.e(TAG, "onError: " + response.getRawResponse().toString());
                            mHandler.sendEmptyMessage(L.CODE.MSG_DATA_FAILED);

                        }
                    });
        } else {
            OkGo.<ArticleBean>post(url)
                    .params("k", key)
                    .tag(this)
                    .execute(new JsonCallBack<ArticleBean>(ArticleBean.class) {
                        @Override
                        public void onSuccess(Response<ArticleBean> response) {
                            Log.e(TAG, "onSuccess: " + response.body().toString());
                            ArticleBean mBody = response.body();
                            if (mBody == null) {
                                mHandler.sendEmptyMessage(L.CODE.MSG_DATA_FAILED);
                                return;
                            }
                            flag = false;
                            Message msg = Message.obtain();
                            msg.obj = mBody;
                            msg.what = L.CODE.MSG_MAIN_ARTICLE_SUCCESS;
                            mHandler.sendMessage(msg);
                        }

                        @Override
                        public void onError(Response<ArticleBean> response) {
                            super.onError(response);
                            Log.e(TAG, "onError: " + response.getRawResponse().toString());
                            mHandler.sendEmptyMessage(L.CODE.MSG_DATA_FAILED);

                        }
                    });
        }
    }
}