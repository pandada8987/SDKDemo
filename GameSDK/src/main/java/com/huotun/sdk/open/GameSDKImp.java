package com.huotun.sdk.open;

import android.app.Activity;
import android.os.Build;
import android.os.Handler;

import com.alibaba.fastjson.JSON;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.huotun.sdk.entity.OrderParams;
import com.huotun.sdk.entity.ReportParams;
import com.huotun.sdk.plugin.EventManager;
import com.huotun.sdk.plugin.EventType;
import com.huotun.sdk.plugin.update.CheckUpdateCallBack;
import com.huotun.sdk.plugin.update.PluginUtil;
import com.huotun.sdk.util.ContextUtil;

import java.util.List;

public class GameSDKImp implements GameSDK {
    private Activity mActivity;

    public GameSDKImp(Activity activity) {
        mActivity = activity;
        ContextUtil.setContext(activity);
        pluginCheckUpdate();
    }

    private void pluginCheckUpdate() {
        new PluginUtil().checkUpdate(new CheckUpdateCallBack() {
            @Override
            public void checkUpdateFinish() {
                HtApp.isCheckUpdateFinish=true;
                initEventManager();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initPermission();
                    }
                }, 300);//CP可能会连续调初始化和登录，延迟一点调获取权限，让获取权限显示在登录窗口上层
            }
        });
    }

    private void initEventManager() {
        EventManager.getInstance().init(mActivity);
        EventManager.getInstance().sendMessage(EventType.INIT);
    }

    private void initPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//6.0以上，在获取权限地方的回调调用open接口、6.0以下直接调用open接口
            XXPermissions.with(mActivity)//CP若连续调初始化和登录，登录界面关闭之前，不会回调。
                    .permission(Permission.READ_PHONE_STATE)
                    .request(new OnPermission() {
                        @Override
                        public void hasPermission(List<String> granted, boolean isAll) {
                            if (isAll) {//isAll为全部授权
                                EventManager.getInstance().sendMessage(EventType.OPEN);
                            }
                        }

                        @Override
                        public void noPermission(List<String> denied, boolean quick) {
                            String permissions = "";
                            if (denied.size() > 0) {
                                StringBuffer sb = new StringBuffer();
                                for (String permission : denied) {
                                    sb.append(permission);
                                    sb.append("，");
                                }
                                permissions = sb.substring(0, sb.length() - 1);
                            }
                            EventManager.getInstance().sendMessage(EventType.OPEN, permissions.length() > 0 ? permissions : "");
                        }
                    });
            return;
        }
        EventManager.getInstance().sendMessage(EventType.OPEN);
    }

    @Override
    public void login() {
        EventManager.getInstance().sendMessage(EventType.LOGIN);
    }

    @Override
    public void logout() {
        EventManager.getInstance().sendMessage(EventType.LOGOUT);
    }

    @Override
    public void pay(OrderParams orderParams) {
        EventManager.getInstance().sendMessage(EventType.RECHARGE, JSON.toJSONString(orderParams));
    }

    @Override
    public void createRoleLog(String serverId, String roleId, String roleName) {
        ReportParams report = new ReportParams();
        report.setServerId(serverId);
        report.setRoleId(roleId);
        report.setRoleName(roleName);
        EventManager.getInstance().sendMessage(EventType.CREATE_ROLE, JSON.toJSONString(report));
    }

    @Override
    public void selectServerLog(String serverId, String roleId) {
        ReportParams report = new ReportParams();
        report.setServerId(serverId);
        report.setRoleId(roleId);
        EventManager.getInstance().sendMessage(EventType.SELECT_SERVER, JSON.toJSONString(report));
    }

    @Override
    public void renameRole(String serverId, String roleId, String newRoleName) {
        ReportParams report = new ReportParams();
        report.setServerId(serverId);
        report.setRoleId(roleId);
        report.setNewRoleName(newRoleName);
        EventManager.getInstance().sendMessage(EventType.RENAME_ROLE, JSON.toJSONString(report));
    }

    @Override
    public void upgradeRole(String serverId, String roleId, int roleLevel) {
        ReportParams report = new ReportParams();
        report.setServerId(serverId);
        report.setRoleId(roleId);
        report.setRoleLevel(roleLevel);
        EventManager.getInstance().sendMessage(EventType.UPGRADE_ROLE, JSON.toJSONString(report));
    }

    @Override
    public void addListener(SDKListener listener) {
        EventManager.getInstance().setSDKListener(listener);
    }
}
