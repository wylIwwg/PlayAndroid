package com.sjjd.wyl.playandroid.model.net;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.sjjd.wyl.playandroid.bean.CollectBean;
import com.sjjd.wyl.playandroid.thread.BaseThread;
import com.sjjd.wyl.playandroid.thread.I;
import com.sjjd.wyl.playandroid.thread.JsonCallBack;

/**
 * Created by wyl on 2018/10/30.
 */
public class ModelCollect {
    public void getCollect(Context context, final int page, final JsonCallBack<CollectBean> listener) {
        BaseThread mThread = new BaseThread(context, null) {
            @Override
            protected void initData() {
                OkGo.<CollectBean>get(String.format(I.URL.Get_Collect, page))
                        .tag(this)
                        .execute(listener);
            }
        };
        mThread.requsetTime = 1;
        mThread.start();


    }

    public void addCollect(Context context, final int id, final JsonCallBack<CollectBean> listener) {
        BaseThread mThread = new BaseThread(context, null) {
            @Override
            protected void initData() {
                OkGo.<CollectBean>post(String.format(I.URL.Post_Collect, id))
                        .tag(this)
                        .execute(listener);
            }
        };
        mThread.requsetTime = 1;
        mThread.start();


    }

    public void delArticleCollect(Context context, final int id, final JsonCallBack<CollectBean> listener) {
        BaseThread mThread = new BaseThread(context, null) {
            @Override
            protected void initData() {
                OkGo.<CollectBean>post(String.format(I.URL.Post_UNCollect_ArticleList, id))
                        .tag(this)
                        .execute(listener);
            }
        };
        mThread.requsetTime = 1;
        mThread.start();


    }public void delCollect(Context context, final int id, final JsonCallBack<CollectBean> listener) {
        BaseThread mThread = new BaseThread(context, null) {
            @Override
            protected void initData() {
                OkGo.<CollectBean>post(String.format(I.URL.Post_UNCollect_MyCollect, id))
                        .tag(this)
                        .execute(listener);
            }
        };
        mThread.requsetTime = 1;
        mThread.start();


    }
}
