package com.huotun.sdk.plugin;

import android.content.Context;

public class HuoTunPlugin extends BaseConnect {
    public HuoTunPlugin(Context context, ConnectCallBack callBack) {
        super(context, callBack);
    }

    @Override
    String getPluginName() {
        return "com.huotun.plugin";
    }

    @Override
    String getClassName() {
        return "com.huotun.plugin.service.PluginService";
    }
}
