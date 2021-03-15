package com.huotun.sdk.open;

import android.content.Context;
import android.content.res.Configuration;

import com.qihoo360.replugin.RePluginApplication;

public class SDKApp extends RePluginApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        AppNoExtend.onCreate(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        AppNoExtend.attachBaseContext(this);
    }

    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
        AppNoExtend.onConfigurationChanged(config);
    }

}
