package com.sjjd.wyl.playandroid.presenter;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.sjjd.wyl.playandroid.bean.ProjectBean;
import com.sjjd.wyl.playandroid.model.net.ModelProject;
import com.sjjd.wyl.playandroid.thread.JsonCallBack;
import com.sjjd.wyl.playandroid.view.iview.IMainView;

/**
 * Created by wyl on 2018/5/10.
 */

public class IProjectPrestener<T> {

    ModelProject mIModelMain;
    IMainView<T> mMainView;

    public IProjectPrestener(IMainView<T> mainView) {
        mMainView = mainView;
        mIModelMain = new ModelProject();
    }


    public void getProject(Context context,int page) {
        mIModelMain.getProject(context,page, new JsonCallBack<ProjectBean>(ProjectBean.class) {
            @Override
            public void onSuccess(Response<ProjectBean> response) {
                ProjectBean mBody = response.body();
                if (mBody.getData() != null) {
                    T t = (T) mBody;
                    mMainView.onSuccess(t);
                } else {
                    mMainView.onError(mBody.getErrorMsg());
                }
            }

            @Override
            public void onError(Response<ProjectBean> response) {
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
