package com.huotun.sdk.report;

import com.huotun.sdk.config.ChannelType;
import com.huotun.sdk.config.Constant;
import com.huotun.sdk.entity.ReportBean;
import com.huotun.sdk.open.HtApp;
import com.huotun.sdk.util.LogUtil;
import com.xwan.datasdk.XSDK;

public class ReportXwan implements IReportSDK {
    @Override
    public void init() {
        if (Constant.CHANNEL_TYPE.equals(ChannelType.YYBSDK) && Constant.ChannelInfo.length > 3) {
            XSDK.getInstance().initGDT(HtApp.getContext(), Constant.ChannelInfo[3], Constant.ChannelInfo[4]);
            LogUtil.d("Xwan初始化成功");
        }
    }

    @Override
    public void register(String userType) {
        XSDK.getInstance().register(userType);
    }

    @Override
    public void purchase(ReportBean report) {
        XSDK.getInstance().onPurchase(10.00, report.getUid(), report.getOrderId());
    }
}
