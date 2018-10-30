package com.sjjd.wyl.playandroid.presenter;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.sjjd.wyl.playandroid.bean.ArticleBean;
import com.sjjd.wyl.playandroid.model.net.ModelArticle;
import com.sjjd.wyl.playandroid.thread.JsonCallBack;
import com.sjjd.wyl.playandroid.view.iview.IMainView;

/**
 * Created by wyl on 2018/5/10.
 */

public class PrestenerArticle<T> {

    ModelArticle mModelArticle;
    IMainView<T> mMainView;

    public PrestenerArticle(IMainView<T> mainView) {
        mMainView = mainView;
        mModelArticle = new ModelArticle();
    }


    public void getArticle(Context context, String url, int page, int cid, String key) {
        mModelArticle.getArticle(context, url, page, cid, key, new JsonCallBack<ArticleBean>(ArticleBean.class) {
            @Override
            public void onSuccess(Response<ArticleBean> response) {
                ArticleBean mBody = response.body();
                if (mBody.getData() != null) {
                    T t = (T) mBody;
                    mMainView.onSuccess(t);
                } else {
                    mMainView.onError(mBody.getErrorMsg());
                }
            }

            @Override
            public void onError(Response<ArticleBean> response) {
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
