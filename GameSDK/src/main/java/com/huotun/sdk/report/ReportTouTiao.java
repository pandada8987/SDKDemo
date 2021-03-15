package com.huotun.sdk.report;

import android.text.TextUtils;
import android.util.Log;

import com.bytedance.applog.AppLog;
import com.bytedance.applog.GameReportHelper;
import com.bytedance.applog.ILogger;
import com.bytedance.applog.InitConfig;
import com.bytedance.applog.util.UriConstants;
import com.huotun.sdk.config.ChannelType;
import com.huotun.sdk.config.Constant;
import com.huotun.sdk.entity.ReportBean;
import com.huotun.sdk.open.AppNoExtend;
import com.huotun.sdk.util.LogUtil;

public class ReportTouTiao implements IReportSDK {
    private boolean isInitSuccess;

    @Override
    public void init() {
        String appId = "";
        if (Constant.ChannelInfo != null && Constant.ChannelInfo.length >= 4) {//优先从渠道信息读取
            if (Constant.ChannelInfo[2].equals(ChannelType.TOUTIAO)) {
                appId = Constant.ChannelInfo[3];
            }
        }

        if (TextUtils.isEmpty(appId)) {//读取不到就读json文件
            appId = Constant.GAME_CONFIG.getToutiaoAppId();
        }

        if (TextUtils.isEmpty(appId)) {
            return;
        }

        if (!TextUtils.isEmpty(appId)) {
            final InitConfig config = new InitConfig(appId, "ht");
            config.setUriConfig(UriConstants.DEFAULT);
            config.setEnablePlay(true);
            if (Constant.GAME_CONFIG != null &&
                    !TextUtils.isEmpty(Constant.GAME_CONFIG.getToutiaoAppName())) {
                config.setAppName(Constant.GAME_CONFIG.getToutiaoAppName());
            }
            config.setLogger(new ILogger() {
                @Override
                public void log(String s, Throwable throwable) {
                    Log.d("AppLog", s);
                }
            });
            AppLog.init(AppNoExtend.getContext(), config);
            isInitSuccess = true;
            LogUtil.d("头条初始化成功：" + appId);
        }
    }

    @Override
    public void register(String userType) {
        if (isInitSuccess) {
            GameReportHelper.onEventRegister(userType, true);
            LogUtil.d("头条上报注册成功");
        }
    }

    @Override
    public void purchase(ReportBean report) {
        if (isInitSuccess) {
            GameReportHelper.onEventPurchase("", report.getProductName(), "", 1, report.getPayType(), report.getCurtype(), true, report.getMoney());
            LogUtil.d("头条上报充值成功");
        }
    }
}
