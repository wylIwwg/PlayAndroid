package com.sjjd.wyl.playandroid.presenter;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.sjjd.wyl.playandroid.bean.CategoryBean;
import com.sjjd.wyl.playandroid.model.net.ModelCategory;
import com.sjjd.wyl.playandroid.thread.JsonCallBack;
import com.sjjd.wyl.playandroid.view.iview.IMainView;

/**
 * Created by wyl on 2018/5/10.
 */

public class PrestenerCategory<T> {

    ModelCategory mIModelMain;
    IMainView<T> mMainView;

    public PrestenerCategory(IMainView<T> mainView) {
        mMainView = mainView;
        mIModelMain = new ModelCategory();
    }


    public void getCategory(Context context) {
        mIModelMain.getCategory(context, new JsonCallBack<CategoryBean>(CategoryBean.class) {
            @Override
            public void onSuccess(Response<CategoryBean> response) {
                CategoryBean mBody = response.body();
                if (mBody.getData() != null) {
                    T t = (T) mBody;
                    mMainView.onSuccess(t);
                } else {
                    mMainView.onError(mBody.getErrorMsg());
                }
            }

            @Override
            public void onError(Response<CategoryBean> response) {
                super.onError(response);
                if (response.body() != null) {
                    mMainView.onError(response.body().getErrorMsg());
                } else {

                    mMainView.onError("获取数据失败！");
                }
            }
        });
    }


}
