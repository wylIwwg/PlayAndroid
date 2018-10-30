package com.sjjd.wyl.playandroid.thread;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.SystemClock;

import com.lzy.okgo.OkGo;

/**
 * Created by wyl on 2018/4/27.
 */

public abstract class BaseThread extends Thread {

    protected final String TAG = this.getClass().getSimpleName();

    public static final int LOAD_NETWORK_STATUS = 0x110;
    public static final int LOAD_DATA_SUCCESS = 0x111;
    public static final int LOAD_DATA_ERROR = 0x112;

    //设置休眠的时间
    protected long SLEEP_MS = 1000 * 2;
    //设置断网几次才进行提示
    protected int NETWORK_OFF_COUNTER = 1;
    private int network_off_count = 0;
    //注意:如果加载data为顺序执行。那么在initData中调用 resumeThread()无效
    //异步网络请求可以正常使用 resumeThread()
    protected boolean loadDataAfterPause = true;
    public int requsetTime = -1;
    protected Context mContext;
    protected Handler mHandler;

    private boolean pause = false;
    public boolean flag = true;
    private Object lock = new Object();


    public BaseThread(Context context, Handler handler) {
        mContext = context;
        mHandler = handler;

    }

    public void pauseThread() {
        pause = true;
    }

    public void resumeThread() {
        pause = false;
        synchronized (lock) {
            lock.notify();
        }
    }

    private void onPause() {
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {

        while (flag) {

            if (pause) {
                onPause();
            }

            if (requsetTime == 1) {
                flag = false;
            }

            SystemClock.sleep(SLEEP_MS);

            if (isNetworkConnected()) {
                //清空记录错误的count
                network_off_count = 0;
                initData();
                if (loadDataAfterPause) {
                    pauseThread();
                }
            } else {
                network_off_count++;
                if (network_off_count >= NETWORK_OFF_COUNTER && mHandler != null) {
                    mHandler.sendEmptyMessage(LOAD_NETWORK_STATUS);
                }
                resumeThread();
            }

        }
    }

    protected abstract void initData();

    /**
     * 判断网络是否链接
     *
     * @return
     */
    protected boolean isNetworkConnected() {
        ConnectivityManager connMgr =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public void onDestory() {
        network_off_count = 0;
        pause = true;
        flag = false;
        OkGo.getInstance().cancelTag(this);
    }
}
