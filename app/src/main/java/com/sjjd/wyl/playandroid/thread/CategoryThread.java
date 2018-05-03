package com.sjjd.wyl.playandroid.thread;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.sjjd.wyl.playandroid.bean.CategoryBean;
import com.sjjd.wyl.playandroid.net.L;

/**
 * Created by wyl on 2018/4/27.
 */

public class CategoryThread extends BaseThread {
    public CategoryThread(Context context, Handler handler) {
        super(context, handler);

        Log.e(TAG, "CategoryThread: ");
    }

    @Override
    protected void initData() {
        Log.e(TAG, "initData: " + "CategoryThread");
        OkGo.<CategoryBean>get(L.CATEGORY.Get_Category)
                .tag(this)
                .execute(new JsonCallBack<CategoryBean>(CategoryBean.class) {
                             @Override
                             public void onSuccess(Response<CategoryBean> response) {
                                 Log.e(TAG, "onSuccess: " + response.body().toString());
                                 CategoryBean mBody = response.body();
                                 if (mBody == null) {
                                     mHandler.sendEmptyMessage(L.CODE.MSG_DATA_FAILED);
                                     return;
                                 }
                                 flag = false;
                                 Message msg = Message.obtain();
                                 msg.obj = mBody.getData();
                                 msg.what = L.CODE.MSG_CATEGORY_SUCCESS;
                                 mHandler.sendMessage(msg);

                             }

                             @Override
                             public void onError(Response<CategoryBean> response) {
                                 super.onError(response);
                                 Log.e(TAG, "onError: ");
                                 mHandler.sendEmptyMessage(L.CODE.MSG_DATA_FAILED);

                             }
                         }
                );
    }
}
