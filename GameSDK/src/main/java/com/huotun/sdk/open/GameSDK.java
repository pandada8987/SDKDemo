package com.huotun.sdk.open;

import com.huotun.sdk.entity.OrderParams;

public interface GameSDK {
    //登录
    void login();

    //退出登录
    void logout();

    //充值
    void pay(OrderParams orderParams);

    //创建角色
    void createRoleLog(String serverId, String roleId, String roleName);

    //选择服务器
    void selectServerLog(String serverId,String roleId);

    //角色改名
    void renameRole(String serverId, String roleId, String newRoleName);

    //角色升级
    void upgradeRole(String serverId, String roleId, int roleLevel);

    //注册SDK监听事件
    void addListener(SDKListener listener);
}
