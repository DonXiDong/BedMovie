package com.xb.bedmovie.application;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by sb.wang on 2016/6/17.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //检测内存泄露
        LeakCanary.install(this);
    }
}
