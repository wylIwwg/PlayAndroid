package com.sjjd.wyl.playandroid.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.lzy.okgo.model.Response;
import com.sjjd.wyl.playandroid.base.App;
import com.sjjd.wyl.playandroid.bean.UserBean;
import com.sjjd.wyl.playandroid.model.net.IModelMain;
import com.sjjd.wyl.playandroid.model.net.MainModel;
import com.sjjd.wyl.playandroid.thread.JsonCallBack;
import com.sjjd.wyl.playandroid.view.iview.IMainView;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by wyl on 2018/5/10.
 */

public class IMainPrestener implements IPrestenerMain {

    IModelMain mIModelMain;
    IMainView<UserBean> mMainView;

    public IMainPrestener(IMainView<UserBean> mainView) {
        mMainView = mainView;
        mIModelMain = new MainModel();
    }

    @Override
    public void login(final Context context, final String name, String password) {
        mIModelMain.login(context, name, password, new JsonCallBack<UserBean>(UserBean.class) {
            @Override
            public void onSuccess(Response<UserBean> response) {
                UserBean mBody = response.body();
                if (mBody.getData() != null) {

                    mMainView.onSuccess(mBody);
                } else {
                    mMainView.onError(mBody.getErrorMsg());
                }
            }

            @Override
            public void onError(Response<UserBean> response) {
                super.onError(response);
                if (response.body() != null) {
                    mMainView.onError(response.body().getErrorMsg());
                } else {

                    mMainView.onError("登录失败！");
                }
            }
        });
    }

    @Override
    public void register(final Context context, final String name, String password, String repassword) {
        mIModelMain.register(context, name, password, repassword, new JsonCallBack<UserBean>(UserBean.class) {
            @Override
            public void onSuccess(Response<UserBean> response) {
                UserBean mBody = response.body();
                if (mBody.getData() != null) {
                    App.getInstance().logined = true;
                    SharedPreferences mUser = context.getSharedPreferences("User", MODE_PRIVATE);
                    mUser.edit().putBoolean("login", true).apply();
                    mUser.edit().putString("nick", name).apply();
                    mMainView.onSuccess(mBody);
                } else {
                    mMainView.onError("注册失败！");
                }
            }

            @Override
            public void onError(Response<UserBean> response) {
                super.onError(response);
                mMainView.onError("注册失败！");
            }
        });
    }


    @Override
    public void release() {
        mIModelMain.release();
    }
}
