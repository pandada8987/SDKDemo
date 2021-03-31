package com.huotun.sdk.plugin.update;

import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSON;
import com.huotun.sdk.config.Constant;
import com.huotun.sdk.entity.PluginUpdateBean;
import com.huotun.sdk.open.HtApp;
import com.huotun.sdk.util.HttpUtils;
import com.huotun.sdk.util.LogUtil;
import com.qihoo360.replugin.RePlugin;
import com.qihoo360.replugin.model.PluginInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PluginUtil {
    private PluginHandler mHandler = new PluginHandler();
    private static CheckUpdateCallBack mCallBack;
    private final static int CHECK_FINISH = 1001;
    private List<PluginUpdateBean.PluginDetail> mHuoTunPlugins = new ArrayList<>();

    public static class PluginHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1001:
                    mCallBack.checkUpdateFinish();
                    break;
                default:
                    break;
            }
        }
    }

    public PluginUtil() {

    }

    public void checkUpdate(CheckUpdateCallBack callBack) {
        mCallBack = callBack;

        String gameId = Constant.GAME_CONFIG.getGameId();
        int sdk_version = RePlugin.getPluginVersion(Constant.HUOTUN);
        String oaid = "df2b4e45-a843-46bf-98fa-32e788719041";//测试机oaid
        String url = "https://api-yw.huotun.com/plugin/config/index?" +
                "game_id=" + gameId
                + "&sdk_version=" + sdk_version
                + "&device_id=" + oaid;
        HttpUtils.get(url, new OnRequestCallBack() {
            @Override
            public void onSuccess(String json) {
                PluginUpdateBean bean = JSON.parseObject(json, PluginUpdateBean.class);
                if (bean.getData() == null || bean.getData().size() == 0) {
                    checkFinish();
                    return;
                }
                pluginUpdate(bean.getData());
            }

            @Override
            public void onError(String errorMsg) {
                checkFinish();
                LogUtil.d(errorMsg);
            }
        });
    }

    private void pluginUpdate(List<PluginUpdateBean.PluginDetail> plugins) {
        for (PluginUpdateBean.PluginDetail detail : plugins) {//目前仅存在一个插件的时候可以这样先用着，有多插件需要修改逻辑
            switch (detail.getPackage_name()) {
                case Constant.HUOTUN:
                    mHuoTunPlugins.add(detail);
                    break;
            }
        }
        checkHuoTun();
    }

    private void checkHuoTun() {
        if (mHuoTunPlugins.size() == 0) {
            checkFinish();
            return;
        }
        Collections.sort(mHuoTunPlugins);//同包名插件可能有多个版本，降序排序，更新最高版本的那个
        checkPlugin(Constant.HUOTUN, mHuoTunPlugins.get(0));
    }

    private void checkPlugin(final String pluginName, final PluginUpdateBean.PluginDetail detail) {
        if (detail.getVersion() > RePlugin.getPluginVersion(pluginName)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    LogUtil.d("插件有更新");
                    doDownload(detail.getUrl(), pluginName);
                }
            }).start();
        } else {
            checkFinish();
        }
    }

    private void doDownload(String url, String pluginName) {
        HttpUtils.downloadFile(HtApp.getContext(), url, pluginName + ".apk", new DownloadCallBack() {
            @Override
            public void onSuccess(String filePath) {
                PluginInfo info = RePlugin.install(filePath);
                if (info != null) {
                    LogUtil.d("插件更新成功");
                    checkFinish();
                }
            }

            @Override
            public void onFailed(String errMsg) {
                checkFinish();
                LogUtil.d(errMsg);
            }
        });
    }

    private void checkFinish() {
        LogUtil.d("检查更新完成");
        Message message = mHandler.obtainMessage();
        message.what = CHECK_FINISH;
        mHandler.sendMessage(message);
    }
}
