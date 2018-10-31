package com.sjjd.wyl.playandroid.base;

import android.support.v4.app.Fragment;

import com.sjjd.wyl.playandroid.view.iview.IMainView;

/**
 * Created by wyl on 2018/10/30.
 */
public class BaseFragment<T> extends Fragment implements IMainView<T> {
    public boolean isMore = false;

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onError(int error) {

    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void onSuccess(String result) {

    }

    @Override
    public void onSuccess(T result) {

    }

    @Override
    public void showMessage(String msg) {

    }

    @Override
    public void showMessage(int resId) {

    }

    @Override
    public boolean isNetworkConnected() {
        return false;
    }

    @Override
    public void hideKeyboard() {

    }
}
