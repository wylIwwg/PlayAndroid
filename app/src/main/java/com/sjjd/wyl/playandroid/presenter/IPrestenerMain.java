package com.sjjd.wyl.playandroid.presenter;

import android.content.Context;

/**
 * Created by wyl on 2018/5/10.
 */

public interface IPrestenerMain extends IPrestenerBase {

    void login(Context context, String name, String password);

    void register(Context context, String name, String password, String repassword);

}
