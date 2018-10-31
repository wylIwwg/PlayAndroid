package com.sjjd.wyl.playandroid.model.net;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.sjjd.wyl.playandroid.bean.ProjectBean;
import com.sjjd.wyl.playandroid.thread.BaseThread;
import com.sjjd.wyl.playandroid.thread.I;
import com.sjjd.wyl.playandroid.thread.JsonCallBack;

/**
 * Created by wyl on 2018/10/30.
 */
public class ModelProject {
    public void getProject(Context context, final int page, final JsonCallBack<ProjectBean> listener) {
        BaseThread mThread = new BaseThread(context, null) {
            @Override
            protected void initData() {
                OkGo.<ProjectBean>get(String.format(I.URL.Get_New_ProjectList, page))
                        .tag(this)
                        .execute(listener);
            }
        };
        mThread.requsetTime = 1;
        mThread.start();


    }

}
