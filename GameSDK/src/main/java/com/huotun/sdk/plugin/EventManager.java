package com.huotun.sdk.plugin;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.huotun.sdk.config.Constant;
import com.huotun.sdk.entity.ReportBean;
import com.huotun.sdk.entity.User;
import com.huotun.sdk.open.SDKListener;
import com.huotun.sdk.report.Report;
import com.huotun.sdk.util.LogUtil;
import com.xwan.datasdk.XSDK;

public class EventManager {
    private static EventManager Instance;
    private BaseConnect mConnect;
    private Messenger mHostMessenger;
    private Messenger mReplyMessenger;
    private SDKListener mSDKListener;

    public static EventManager getInstance() {
        if (Instance == null) {
            synchronized (EventManager.class) {
                if (Instance == null) {
                    Instance = new EventManager();
                }
            }
        }
        return Instance;
    }

    private EventManager() {
    }

    public void init(Context context) {
        mConnect = new HuoTunPlugin(context, mConnectCallBack);
    }

    private BaseConnect.ConnectCallBack mConnectCallBack = new BaseConnect.ConnectCallBack() {
        @Override
        public void connectSuccess(Messenger connectionService, Messenger replyService) {
            mHostMessenger = connectionService;
            mReplyMessenger = replyService;
        }
    };

    public void setSDKListener(SDKListener sdkListener) {
        mSDKListener = sdkListener;
    }

    public SDKListener getSDKListener() {
        return mSDKListener;
    }

    public void handleMessage(Message message) {
        String content = message.obj == null ? "" : message.obj.toString();
        switch (message.what) {
            case EventType.LOGIN_SUCCESS:
                loginSuccess(content);
                break;
            case EventType.LOGIN_FAILED:
                if (mSDKListener != null) {
                    mSDKListener.onLoginFailed(content);
                }
                break;
            case EventType.PAY_SUCCESS:
                if (mSDKListener != null) {
                    mSDKListener.onPaySuccess();
                }
                break;
            case EventType.PAY_FAILED:
                if (mSDKListener != null) {
                    mSDKListener.onPayFailed(content);
                }
                break;
            case EventType.REPORT_REGISTER:
                Report.getInstance().register(content);
                break;
            case EventType.REPORT_PURCHASE:
                if (!TextUtils.isEmpty(content)) {
                    Report.getInstance().purchase(JSON.parseObject(content, ReportBean.class));
                }
                break;
            case EventType.SWITCH:
                if (mSDKListener != null) {
                    mSDKListener.onSwitchAccount();
                }
                break;
        }
    }

    public void sendMessage(int messageType) {
        Message message = Message.obtain(null, messageType);
        sendMessage(message);
    }

    public void sendMessage(int messageType, String content) {
        Message message = Message.obtain(null, messageType);
        message.obj = content;
        sendMessage(message);
    }

    public synchronized void sendMessage(final Message message) {
        if (mHostMessenger == null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    sendToPlugin(message);
                }
            }, 200);
            return;
        }
        sendToPlugin(message);
    }

    private void sendToPlugin(Message message) {
        message.replyTo = mReplyMessenger;
        try {
            if (mHostMessenger != null) {
                mHostMessenger.send(message);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void loginSuccess(String content) {
        if (!TextUtils.isEmpty(content)) {
            User user = JSON.parseObject(content, User.class);
            if (mSDKListener != null && user != null) {
                Constant.UID = user.getUserId();
                XSDK.getInstance().login(user.getUserId());
                LogUtil.d("上报Xwan登录调用成功");
                mSDKListener.onLoginSuccess(user);
            }
        }
    }
}
