package com.sjjd.wyl.playandroid.model.net;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.sjjd.wyl.playandroid.bean.CategoryBean;
import com.sjjd.wyl.playandroid.thread.BaseThread;
import com.sjjd.wyl.playandroid.thread.I;
import com.sjjd.wyl.playandroid.thread.JsonCallBack;

/**
 * Created by wyl on 2018/10/30.
 */
public class ModelCategory {

    public void getCategory(Context context, final JsonCallBack<CategoryBean> listener) {
        BaseThread mThread = new BaseThread(context, null) {
            @Override
            protected void initData() {
                OkGo.<CategoryBean>get(I.CATEGORY.Get_Category)
                        .tag(this)
                        .execute(listener);
            }
        };
        mThread.requsetTime = 1;
        mThread.start();


    }

    public void getCategoryList(Context context, final JsonCallBack<CategoryBean> listener) {
        BaseThread mThread = new BaseThread(context, null) {
            @Override
            protected void initData() {
                OkGo.<CategoryBean>get(I.CATEGORY.Get_Category_ArticleList)
                        .tag(this)
                        .execute(listener);
            }
        };
        mThread.requsetTime = 1;
        mThread.start();


    }
}
