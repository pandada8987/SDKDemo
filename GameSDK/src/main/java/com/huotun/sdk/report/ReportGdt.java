package com.huotun.sdk.report;

import com.huotun.sdk.config.ChannelType;
import com.huotun.sdk.config.Constant;
import com.huotun.sdk.entity.ReportBean;
import com.huotun.sdk.open.AppNoExtend;
import com.huotun.sdk.util.LogUtil;
import com.qq.gdt.action.ActionUtils;
import com.qq.gdt.action.GDTAction;

public class ReportGdt implements IReportSDK {
    public static boolean isInitSuccess;

    public  void init() {
        if (Constant.CHANNEL_TYPE.equals(ChannelType.YYBSDK)&&Constant.ChannelInfo.length>3){
            GDTAction.init(AppNoExtend.getContext(), Constant.ChannelInfo[3], Constant.ChannelInfo[4], Constant.ChannelInfo[1]);
            isInitSuccess = true;
            LogUtil.d("广点通初始化成功");
        }
    }

    public void register(String userType) {
        if (isInitSuccess) {
            ActionUtils.onRegister(userType, true);
            LogUtil.d("广点通上报注册成功");
        }
    }

    public void purchase(ReportBean bean) {
        if (isInitSuccess) {
            ActionUtils.onPurchase("", bean.getProductName(), "", 1, bean.getPayType(), bean.getCurtype(), bean.getMoney(), true);
            LogUtil.d("广点通上报充值成功");
        }
    }
}
