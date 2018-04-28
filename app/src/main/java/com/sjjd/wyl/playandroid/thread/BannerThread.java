package com.sjjd.wyl.playandroid.thread;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.sjjd.wyl.playandroid.bean.BannerBean;
import com.sjjd.wyl.playandroid.net.L;

/**
 * Created by wyl on 2018/4/27.
 */

public class BannerThread extends BaseThread {
    public BannerThread(Context context, Handler handler) {
        super(context, handler);

        Log.e(TAG, "BannerThread: ");
    }

    @Override
    protected void initData() {
        Log.e(TAG, "initData: " + "getBanner");
        OkGo.<BannerBean>get(L.URL_MAIN.Get_Banner)
                .tag(this)
                .execute(new JsonCallBack<BannerBean>(BannerBean.class) {
                             @Override
                             public void onSuccess(Response<BannerBean> response) {
                                 Log.e(TAG, "onSuccess: " + response.body().toString());
                                 BannerBean mBody = response.body();
                                 if (mBody == null) {
                                     mHandler.sendEmptyMessage(L.CODE.MSG_DATA_FAILED);
                                     return;
                                 }
                                 SLEEP_MS = 1000 * 60 * 2;//一分钟请求一次
                                 Message msg = Message.obtain();
                                 msg.obj = mBody;
                                 msg.what = L.CODE.MSG_MAIN_BANNER_SUCCESS;
                                 mHandler.sendMessage(msg);

                             }

                             @Override
                             public void onError(Response<BannerBean> response) {
                                 super.onError(response);
                                 Log.e(TAG, "onError: ");
                                 mHandler.sendEmptyMessage(L.CODE.MSG_DATA_FAILED);

                             }
                         }
                );
    }
}
