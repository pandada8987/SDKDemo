package com.huotun.sdk.plugin;

public interface EventType {
    //宿主向插件发起
    int INIT = 1000;//初始化
    int LOGIN = 1001;//登录
    int LOGOUT = 1002;//登出
    int RECHARGE = 1003;//充值
    int SWITCH = 1004;//切换账号
    int WECHAT_CODE = 1005;//微信登录Code
    int WECHAT_PAY_SUCCESS = 1006;//微信登录成功
    int WECHAT_PAY_CANCEL = 1007;//微信登录失败
    //上报日志
    int OPEN = 1010;//open日志接口
    int CREATE_ROLE = 1011;//创角
    int SELECT_SERVER = 1012;//选服
    int RENAME_ROLE = 1013;//更名
    int UPGRADE_ROLE = 1014;//升级

    //创蓝
    int AUTH_SUCCESS = 1031;
    int AUTH_FAILED = 1032;//免密授权失败，包括初始化失败，拉起界面失败，授权失败

    //插件返回给宿主
    int REPORT_REGISTER = 1041;//上报注册
    int REPORT_PURCHASE = 1042;//上报充值

    int LOGIN_SUCCESS = 2000;//登录成功
    int LOGIN_FAILED = 2001;//登录失败
    int PAY_SUCCESS = 2002;//支付成功
    int PAY_FAILED = 2003;//支付失败
}
