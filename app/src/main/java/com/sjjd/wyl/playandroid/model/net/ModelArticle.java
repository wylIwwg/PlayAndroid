package com.sjjd.wyl.playandroid.model.net;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.sjjd.wyl.playandroid.bean.ArticleBean;
import com.sjjd.wyl.playandroid.thread.BaseThread;
import com.sjjd.wyl.playandroid.thread.JsonCallBack;

/**
 * Created by wyl on 2018/10/30.
 */
public class ModelArticle {

    public void getArticle(Context context, String url, int page, int cid, final String key, final JsonCallBack<ArticleBean> listener) {
        BaseThread mThread;
        if (key != null) {
            final String mUrl = String.format(url, page);
            mThread = new BaseThread(context, null) {
                @Override
                protected void initData() {
                    OkGo.<ArticleBean>post(mUrl)
                            .params("k", key)
                            .tag(this)
                            .execute(listener);
                }
            };
        } else {
            final String mUrl;
            if (cid > 0) {
                mUrl = String.format(url, page, cid);
            } else {
                mUrl = String.format(url, page);
            }
            mThread = new BaseThread(context, null) {
                @Override
                protected void initData() {
                    OkGo.<ArticleBean>get(mUrl)
                            .tag(this)
                            .execute(listener);
                }
            };
        }

        mThread.requsetTime = 1;
        mThread.start();


    }

}
