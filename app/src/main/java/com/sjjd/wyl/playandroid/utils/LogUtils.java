package com.sjjd.wyl.playandroid.utils;

import android.util.Log;

/**
 * Created by wyl on 2018/7/7.
 */

public class LogUtils {

    static boolean debug = true;

    public static void e(String tag, String msg) {
        Log.e(tag, msg);
    }
}
