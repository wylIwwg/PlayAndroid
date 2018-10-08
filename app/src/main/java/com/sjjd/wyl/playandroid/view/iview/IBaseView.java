package com.sjjd.wyl.playandroid.view.iview;

import android.support.annotation.StringRes;

/**
 * Created by wyl on 2018/5/10.
 */

public interface IBaseView<T> {

    void showLoading();

    void hideLoading();

    void onError(@StringRes int error);

    void onError(String error);

    void onSuccess(String result);

    void onSuccess(T result);

    void showMessage(String msg);

    void showMessage(@StringRes int resId);

    boolean isNetworkConnected();

    void hideKeyboard();

}
