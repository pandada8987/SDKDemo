package com.htsy.demo;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.huotun.sdk.open.HtApp;

public class DemoApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        HtApp.onCreate(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        HtApp.attachBaseContext(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        HtApp.onConfigurationChanged(newConfig);
    }
}
