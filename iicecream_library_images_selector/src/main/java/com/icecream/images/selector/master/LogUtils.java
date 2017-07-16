package com.icecream.images.selector.master;

import android.util.Log;

/**
 * 打印日志管理
 * Created by HuangJin on 2016/9/13.
 */
public class LogUtils {
    public static boolean isDebug = true;//是否是测试阶段，上线后直接设置了false 即可
    public static final String tag = "cow cow";//统一不会tag

    public static void d(String msg) {
        if (isDebug)
            Log.d(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug)
            Log.d(tag, msg);
    }

    public static void e(String msg) {
        if (isDebug)
            Log.e(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (isDebug)
            Log.e(tag, msg);
    }


    public static void i(String msg) {
        if (isDebug)
            Log.i(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (isDebug)
            Log.i(tag, msg);
    }

    public static void v(String msg) {
        if (isDebug)
            Log.v(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (isDebug)
            Log.v(tag, msg);
    }

    public static void w(String msg) {
        if (isDebug)
            Log.w(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (isDebug)
            Log.w(tag, msg);
    }
}
