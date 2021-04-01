package com.huotun.sdk.open;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.bytedance.applog.AppLog;
import com.huotun.sdk.config.ChannelType;
import com.huotun.sdk.config.Constant;
import com.huotun.sdk.entity.GameConfig;
import com.huotun.sdk.report.Report;
import com.huotun.sdk.util.AppUtil;
import com.huotun.sdk.util.CrashHandler;
import com.leon.channel.helper.ChannelReaderUtil;
import com.qihoo360.replugin.RePlugin;
import com.qihoo360.replugin.RePluginConfig;
import com.xwan.datasdk.XSDK;

import java.util.Timer;
import java.util.TimerTask;

public class HtApp {
    private static Context mContext;
    public static boolean isCheckUpdateFinish = false;

    public static void onCreate(Context context) {
        mContext = context;
        RePlugin.App.onCreate();
        initConfigJson();
        initChannelInfo();
        Report.getInstance().init();
        CrashHandler.getInstance().init(context);
        initActivityLifecycle((Application) context);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {//需要等插件检查更新完成再初始化
                if (isCheckUpdateFinish) {
                    isCheckUpdateFinish = false;
                    RePlugin.App.onCreate();
                    cancel();
                }
            }
        }, 0, 1000);
    }

    private static void initActivityLifecycle(Application application) {
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                switch (Constant.CHANNEL_TYPE) {
                    case ChannelType.YYBSDK:
                        XSDK.getInstance().init(activity);
                        break;
                }
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                switch (Constant.CHANNEL_TYPE) {
                    case ChannelType.TOUTIAO:
                        AppLog.onResume(activity);
                        break;
                    case ChannelType.YYBSDK:
                        XSDK.getInstance().onResume(activity,Constant.UID);
//                        if (XXPermissions.hasPermission(activity, Permission.READ_PHONE_STATE)) {
//                            GDTAction.logAction(ActionType.START_APP);
//                        }
                        break;
                }
            }

            @Override
            public void onActivityPaused(Activity activity) {
                switch (Constant.CHANNEL_TYPE) {
                    case ChannelType.TOUTIAO:
                        AppLog.onPause(activity);
                        break;
                }
            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    private static void initConfigJson() {
        String config = AppUtil.loadConfigFromAssets(mContext, "GameConfig.json");
        if (!TextUtils.isEmpty(config)) {
            Constant.GAME_CONFIG = JSON.parseObject(config, GameConfig.class);
        }
    }

    private static void initChannelInfo() {
        String channelInfo = ChannelReaderUtil.getChannel(mContext);
        if (!TextUtils.isEmpty(channelInfo)) {
            Constant.ChannelInfo = channelInfo.split("_");
        } else {
            channelInfo = "htchannel_11_none";
            Constant.ChannelInfo = channelInfo.split("_");
        }
        if (Constant.ChannelInfo.length >= 3) {
            Constant.CHANNEL_ID = Constant.ChannelInfo[1];
            Constant.CHANNEL_TYPE = Constant.ChannelInfo[2];
        }
    }

    public static void attachBaseContext(final Application application) {
        RePlugin.App.attachBaseContext(application);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (isCheckUpdateFinish) {
                    isCheckUpdateFinish = false;
                    RePluginConfig config = new RePluginConfig();
                    config.setUseHostClassIfNotFound(true);
                    config.setVerifySign(true);
                    RePlugin.addCertSignature("FF267146F43E70F5150CA63BAE79451C");
                    RePlugin.App.attachBaseContext(application, config);
                    cancel();
                }
            }
        }, 0, 1000);
    }

    public static void onConfigurationChanged(final Configuration config) {
        RePlugin.App.onConfigurationChanged(config);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (isCheckUpdateFinish) {
                    isCheckUpdateFinish = false;
                    RePlugin.App.onConfigurationChanged(config);
                    cancel();
                }
            }
        }, 0, 1000);
    }

    public static Context getContext() {
        return mContext;
    }
}
