package com.sjjd.wyl.playandroid.presenter;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.sjjd.wyl.playandroid.bean.CollectBean;
import com.sjjd.wyl.playandroid.model.net.ModelCollect;
import com.sjjd.wyl.playandroid.thread.JsonCallBack;
import com.sjjd.wyl.playandroid.view.iview.IMainView;

/**
 * Created by wyl on 2018/5/10.
 */

public class PrestenerCollect<T> {

    ModelCollect mIModelMain;
    IMainView<T> mMainView;

    public PrestenerCollect(IMainView<T> mainView) {
        mMainView = mainView;
        mIModelMain = new ModelCollect();
    }


    public void getCollect(Context context, int page) {
        mIModelMain.getCollect(context, page, new JsonCallBack<CollectBean>(CollectBean.class) {
            @Override
            public void onSuccess(Response<CollectBean> response) {
                CollectBean mBody = response.body();
                if (mBody.getData() != null) {
                    T t = (T) mBody;
                    mMainView.onSuccess(t);
                } else {
                    mMainView.onError(mBody.getErrorMsg());
                }
            }

            @Override
            public void onError(Response<CollectBean> response) {
                super.onError(response);
                if (response.body() != null) {
                    mMainView.onError(response.body().getErrorMsg());
                } else {

                    mMainView.onError("获取数据失败！");
                }
            }
        });
    }

    public void addCollect(Context context, int id) {
        mIModelMain.getCollect(context, id, new JsonCallBack<CollectBean>(CollectBean.class) {
            @Override
            public void onSuccess(Response<CollectBean> response) {
                CollectBean mBody = response.body();
                if (mBody.getData() != null) {
                    T t = (T) mBody;
                    mMainView.onSuccess(t);
                } else {
                    mMainView.onError(mBody.getErrorMsg());
                }
            }

            @Override
            public void onError(Response<CollectBean> response) {
                super.onError(response);
                if (response.body() != null) {
                    mMainView.onError(response.body().getErrorMsg());
                } else {
                    mMainView.onError("收藏失败！");
                }
            }
        });
    }

    public void delArticleCollect(Context context, int id) {
        mIModelMain.getCollect(context, id, new JsonCallBack<CollectBean>(CollectBean.class) {
            @Override
            public void onSuccess(Response<CollectBean> response) {
                CollectBean mBody = response.body();
                if (mBody.getData() != null) {
                    T t = (T) mBody;
                    mMainView.onSuccess(t);
                } else {
                    mMainView.onError(mBody.getErrorMsg());
                }
            }

            @Override
            public void onError(Response<CollectBean> response) {
                super.onError(response);
                if (response.body() != null) {
                    mMainView.onError(response.body().getErrorMsg());
                } else {

                    mMainView.onError("取消收藏失败！");
                }
            }
        });
    }

    public void delCollect(Context context, int id) {
        mIModelMain.getCollect(context, id, new JsonCallBack<CollectBean>(CollectBean.class) {
            @Override
            public void onSuccess(Response<CollectBean> response) {
                CollectBean mBody = response.body();
                if (mBody.getData() != null) {
                    T t = (T) mBody;
                    mMainView.onSuccess(t);
                } else {
                    mMainView.onError(mBody.getErrorMsg());
                }
            }

            @Override
            public void onError(Response<CollectBean> response) {
                super.onError(response);
                if (response.body() != null) {
                    mMainView.onError(response.body().getErrorMsg());
                } else {

                    mMainView.onError("取消收藏失败！");
                }
            }
        });
    }


}
