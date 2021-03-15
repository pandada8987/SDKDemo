package com.huotun.sdk.plugin;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Messenger;

import com.qihoo360.replugin.RePlugin;
import com.qihoo360.replugin.component.service.PluginServiceClient;

public abstract class BaseConnect {
    private Messenger mService;
    private Messenger mMessenger = new Messenger(new HostHandler());
    private ConnectCallBack mCallBack;

    public BaseConnect(Context context, ConnectCallBack callBack) {
        mCallBack = callBack;
        PluginServiceClient.bindService(context, RePlugin.createIntent(getPluginName(), getClassName()), mConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = new Messenger(service);
            mCallBack.connectSuccess(mService, mMessenger);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }
    };

    abstract String getPluginName();//插件名

    abstract String getClassName();//通讯的Service全包名

    public interface ConnectCallBack {
        void connectSuccess(Messenger connectionService, Messenger replyService);
    }
}
