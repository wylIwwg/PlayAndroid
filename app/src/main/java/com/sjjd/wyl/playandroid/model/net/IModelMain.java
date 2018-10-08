package com.sjjd.wyl.playandroid.model.net;

import android.content.Context;

import com.sjjd.wyl.playandroid.bean.UserBean;
import com.sjjd.wyl.playandroid.thread.JsonCallBack;

/**
 * Created by wyl on 2018/5/10.
 */

public interface IModelMain extends IBaseModel {
    void login(Context context, String name, String password, JsonCallBack<UserBean> listener);

    void register(Context context, String name, String password, String repassword, JsonCallBack<UserBean> listener);

}
