package com.huotun.sdk.open;

import android.content.Context;
import android.content.res.Configuration;

import com.qihoo360.replugin.RePluginApplication;

public class SDKApp extends RePluginApplication {
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
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
        HtApp.onConfigurationChanged(config);
    }

}
