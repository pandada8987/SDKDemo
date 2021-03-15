package com.huotun.sdk.open;

import com.huotun.sdk.entity.User;

public interface SDKListener {
    void onLoginSuccess(User user);

    void onLoginFailed(String message);

    void onPaySuccess();

    void onPayFailed(String message);

    void onSwitchAccount();
}
