package com.sjjd.wyl.playandroid.model.net;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.sjjd.wyl.playandroid.bean.UserBean;
import com.sjjd.wyl.playandroid.model.utils.L;
import com.sjjd.wyl.playandroid.thread.JsonCallBack;

/**
 * Created by wyl on 2018/5/10.
 */

public class MainModel implements IModelMain {

    @Override
    public void release() {
        OkGo.getInstance().cancelAll();
    }

    @Override
    public void login(Context context, String name, String password, JsonCallBack<UserBean> listener) {
        OkGo.<UserBean>post(L.URL.Post_Login)
                .params("username", name)
                .params("password", password)
                .tag(this)
                .execute(listener);

    }

    @Override
    public void register(Context context, String name, String password, String repassword, JsonCallBack<UserBean> listener) {
        OkGo.<UserBean>post(L.URL.Post_Register)
                .params("username", name)
                .params("password", password)
                .params("repassword", repassword)
                .tag(this)
                .execute(listener);
    }

}
