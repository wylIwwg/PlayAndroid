package com.sjjd.wyl.playandroid;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;

import com.scwang.smartrefresh.header.StoreHouseHeader;
import com.sjjd.wyl.playandroid.adapter.BannerAdapter;
import com.sjjd.wyl.playandroid.base.BaseActivity;
import com.sjjd.wyl.playandroid.bean.BannerBean;
import com.sjjd.wyl.playandroid.net.L;
import com.sjjd.wyl.playandroid.thread.BannerThread;

import java.lang.ref.WeakReference;

public class MainActivity extends BaseActivity {
    ViewPager mVpBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        rootLayout.setRefreshHeader(new StoreHouseHeader(mContext).initWithString("PLAY ANDROID").setTextColor(Color.WHITE));
        NetHander mNetHander = new NetHander(this);
        mThread = new BannerThread(mContext, mNetHander);
        mThread.start();
    }


    private void initView() {

        mVpBanner = findViewById(R.id.vpBanner);
    }


    class NetHander extends Handler {

        private WeakReference<Activity> mReference;

        public NetHander(Activity reference) {
            mReference = new WeakReference<Activity>(reference);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case L.CODE.MSG_MAIN_BANNER_SUCCESS:
                    BannerBean banner = (BannerBean) msg.obj;
                    initBanner(banner);
                    break;
                case L.CODE.MSG_DATA_FAILED:
                    break;
            }
        }
    }

    private void initBanner(BannerBean banner) {
        BannerAdapter mAdapter = new BannerAdapter(mContext, banner.getData());
        mVpBanner.setAdapter(mAdapter);
    }
}
