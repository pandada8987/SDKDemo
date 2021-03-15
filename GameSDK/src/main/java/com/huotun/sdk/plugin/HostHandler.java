package com.huotun.sdk.plugin;

import android.os.Handler;
import android.os.Message;

public class HostHandler extends Handler {
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        EventManager.getInstance().handleMessage(msg);
    }
}
