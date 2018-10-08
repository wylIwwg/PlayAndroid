package com.sjjd.wyl.playandroid.thread;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.sjjd.wyl.playandroid.bean.HotWords;
import com.sjjd.wyl.playandroid.model.utils.L;

/**
 * Created by wyl on 2018/4/27.
 */

public class KeyWorsThread extends BaseThread {
    public KeyWorsThread(Context context, Handler handler) {
        super(context, handler);

        Log.e(TAG, "KeyWorsThread: ");
    }

    @Override
    protected void initData() {
        Log.e(TAG, "initData: " + "KeyWorsThread");
        OkGo.<HotWords>get(L.URL.Get_HotWords)
                .tag(this)
                .execute(new JsonCallBack<HotWords>(HotWords.class) {
                             @Override
                             public void onSuccess(Response<HotWords> response) {
                                 Log.e(TAG, "onSuccess: " + response.body().toString());
                                 HotWords mBody = response.body();
                                 if (mBody == null) {
                                     mHandler.sendEmptyMessage(L.CODE.MSG_DATA_FAILED);
                                     return;
                                 }
                                 flag = false;
                                 Message msg = Message.obtain();
                                 msg.obj = mBody;
                                 msg.what = L.CODE.MSG_MAIN_HOTWORDS_SUCCESS;
                                 mHandler.sendMessage(msg);

                             }

                             @Override
                             public void onError(Response<HotWords> response) {
                                 super.onError(response);
                                 Log.e(TAG, "onError: ");
                                 mHandler.sendEmptyMessage(L.CODE.MSG_DATA_FAILED);

                             }
                         }
                );
    }
}
