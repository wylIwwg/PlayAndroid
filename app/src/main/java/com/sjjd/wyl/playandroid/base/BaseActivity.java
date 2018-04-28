package com.sjjd.wyl.playandroid.base;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lzy.okgo.OkGo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.sjjd.wyl.playandroid.R;
import com.sjjd.wyl.playandroid.thread.BaseThread;

public class BaseActivity extends AppCompatActivity {

    public SmartRefreshLayout rootLayout;
    public Context mContext;
    public BaseThread mThread;
    public Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);

        mContext = this;
    }

    @Override
    public void setContentView(int layoutResID) {
        setContentView(View.inflate(this, layoutResID, null));
    }

    @Override
    public void setContentView(View view) {

        rootLayout = findViewById(R.id.refreshLayout);

        if (rootLayout == null) return;
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rootLayout.addView(view, lp);
    }

    @Override
    protected void onDestroy() {


        if (null != mThread) {
            mThread.onDestory();
        }
        if (null != mHandler) {
            mHandler.removeCallbacksAndMessages(null);
        }
        OkGo.getInstance().cancelAll();

        super.onDestroy();
    }
}
