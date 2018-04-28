package com.sjjd.wyl.playandroid.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.Scroller;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sjjd.wyl.playandroid.R;
import com.sjjd.wyl.playandroid.bean.BannerBean;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by wyl on 2018/4/28.
 */

public class BannerPager extends ViewPager {
    Context mContext;

    /**
     * 自动播放的标识符
     */
    final int ACTION_PLAY = 1;
    /**
     * 轮播图片的适配器
     */
    BannerAdapter mAdapter;
    /**
     * 图片轮播间隔时间
     */
    int mDuration = 4000;
    /**
     * 相册的图片下载地址数组
     */
    Timer mTimer;
    Handler mHandler;
    boolean mAutoSwitch = false;

    public BannerPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initHandler();
        setListener();
    }

    private void setListener() {
        setOnTouchListener();
    }

    /**
     * 设置触摸页面的事件监听
     */
    private void setOnTouchListener() {
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN
                        || event.getAction() == MotionEvent.ACTION_MOVE) {
                    mAutoSwitch = false;
                }
                return false;
            }
        });
    }


    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == ACTION_PLAY) {//若是播放操作
                    if (!mAutoSwitch) {//若不是自动播放状态
                        mAutoSwitch = true;//设置为自动播放状态
                    } else {//设置为下一个item
                        //取出当前item的下标
                        int currentItem = BannerPager.this.getCurrentItem();
                        currentItem++;//递增
                        //设置当前item为下一个
                        BannerPager.this.setCurrentItem(currentItem);
                    }
                }
            }
        };
    }

    class BannerAdapter extends PagerAdapter {
        Context mContext;
        List<BannerBean.Data> mList;
        int count;

        public BannerAdapter(Context context, List<BannerBean.Data> list) {
            mContext = context;
            mList = list;
            count = mList.size();
        }

        @Override
        public int getCount() {
            //return mList == null ? 0 : mList.size();
            return Integer.MAX_VALUE;
        }


        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(mContext, R.layout.item_banner, null);
            ImageView img = view.findViewById(R.id.imgCover);
            TextView tv = view.findViewById(R.id.tvTitle);
            final BannerBean.Data mData = mList.get(position % count);

            tv.setText(mData.getTitle());
            Glide.with(mContext)
                    .load(mData.getImagepath())
                    .into(img);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(mBannerClickLisener!=null){
                        mBannerClickLisener.bannerClick(mData.getUrl());
                    }

                }
            });
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    public interface BannerClickLisener {
        void bannerClick(String url);
    }

    BannerClickLisener mBannerClickLisener;

    public void setBannerClickLisener(BannerClickLisener lisener) {
        mBannerClickLisener = lisener;
    }


    /**
     * 开始图片的轮播
     *
     * @param banner
     */
    public void startPlayLoop(List<BannerBean.Data> banner) {
        if (mAdapter == null) {
            mAdapter = new BannerAdapter(mContext, banner);
            this.setAdapter(mAdapter);
            try {
                Field field = ViewPager.class.getDeclaredField("mScroller");
                field.setAccessible(true);
                MyScroller scroller = new MyScroller(mContext, new LinearInterpolator());
                scroller.setDuration(1000);
                scroller.startScroll(0, 0, 0, 0);
                field.set(this, scroller);
            } catch (NoSuchFieldException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (mTimer == null) {
            mTimer = new Timer();
        }
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(ACTION_PLAY);
            }
        }, 1, mDuration);
    }

    /**
     * 停止图片轮播
     */
    public void stopPlayLoop() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    /**
     * ViewPager列表项滚动的距离、时间间隔的设置
     *
     * @author yao
     */
    class MyScroller extends Scroller {
        public MyScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
            // TODO Auto-generated constructor stub
        }

        int duration;//图片移动的时间间隔

        public void setDuration(int duration) {
            this.duration = duration;
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy,
                                int duration) {
            super.startScroll(startX, startY, dx, dy, this.duration);
        }
    }
}
