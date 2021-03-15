package com.huotun.sdk.plugin.update;

public interface OnRequestCallBack {
    void onSuccess(String json);
    void onError(String errorMsg);
}
