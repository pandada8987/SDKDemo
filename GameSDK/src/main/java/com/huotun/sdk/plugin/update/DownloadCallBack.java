package com.huotun.sdk.plugin.update;

public interface DownloadCallBack {
    void onSuccess(String filePath);
    void onFailed(String errMsg);
}
