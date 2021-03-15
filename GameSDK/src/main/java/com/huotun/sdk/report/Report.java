package com.huotun.sdk.report;

import com.huotun.sdk.config.ChannelType;
import com.huotun.sdk.config.Constant;
import com.huotun.sdk.entity.ReportBean;
import com.huotun.sdk.util.LogUtil;

//第三方SDK上报，目前只有头条和广点通
public class Report {
    private static Report Instance;
    private IReportSDK mReport;

    public synchronized static Report getInstance() {
        if (Instance == null) {
            Instance = new Report();
        }
        return Instance;
    }

    public void init() {
        if (Constant.ChannelInfo != null && Constant.ChannelInfo.length >= 3) {
            switch (Constant.ChannelInfo[2]) {
                case ChannelType.TOUTIAO:
                    setReportSDK(new ReportTouTiao());
                    LogUtil.d("当前上报SDK——头条");
                    break;
                case ChannelType.YYBSDK:
                    setReportSDK(new ReportGdt());
                    LogUtil.d("当前上报SDK——广点通");
                    break;
            }
        }
    }

    public void setReportSDK(IReportSDK reportSDK) {
        mReport = reportSDK;
        mReport.init();
    }

    public void register(String userType) {
        if (mReport != null) {
            mReport.register(userType);
        }
    }

    public void purchase(ReportBean bean) {
        if (mReport != null) {
            mReport.purchase(bean);
        }
    }
}
