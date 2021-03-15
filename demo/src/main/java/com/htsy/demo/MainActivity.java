package com.htsy.demo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.huotun.sdk.entity.OrderParams;
import com.huotun.sdk.entity.User;
import com.huotun.sdk.open.GameSDK;
import com.huotun.sdk.open.GameSDKImp;
import com.huotun.sdk.open.SDKListener;
import com.huotun.sdk.util.AppUtil;
import com.huotun.sdk.util.ContextUtil;
import com.huotun.sdk.util.LogUtil;
import com.qihoo360.replugin.RePlugin;

public class MainActivity extends Activity {
    private static Context mContext;
    private TextView mTvDeviceInfo;
    private Button mBtnLogin, mBtnPay, mBtnLogout;
    private Button mBtnSelectServer, mBtnCreateRole, mBtnRenameRole, mBtnUpgradeRole;
    private GameSDK mGameSDK;


    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        initView();
        initSDK();
        initWebView();
        mGameSDK.login();
//        initData();
    }

    private void initSDK() {
        mGameSDK = new GameSDKImp(MainActivity.this);
        mGameSDK.addListener(new SDKListener() {
            @Override
            public void onLoginSuccess(User user) {
                Toast.makeText(mContext, user.toString(), Toast.LENGTH_LONG).show();
//                mGameSDK.selectServerLog("105292", "105290000000447");
            }

            @Override
            public void onLoginFailed(String message) {
                Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPaySuccess() {
                Toast.makeText(mContext, "支付成功", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPayFailed(String message) {
                Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSwitchAccount() {
                Toast.makeText(mContext, "切换账号成功", Toast.LENGTH_LONG).show();
            }
        });
        mGameSDK.login();
    }

    @Override
    protected void onResume() {
        super.onResume();
        StringBuffer sb = new StringBuffer();
        sb.append("设备厂商：" + android.os.Build.MANUFACTURER);
        sb.append("\t设备型号：" + android.os.Build.MODEL);
        try {
            sb.append("\n程序版本：" + getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        sb.append("\t插件版本：" + RePlugin.getPluginInfo("com.huotun.plugin").getVersion());
        mTvDeviceInfo.setText(sb.toString());
    }

    private void initView() {
        mTvDeviceInfo = findViewById(R.id.tv_device_info);
        mBtnLogin = findViewById(R.id.btn_login);
        mBtnPay = findViewById(R.id.btn_pay);
        mBtnLogout = findViewById(R.id.btn_logout);
        mBtnSelectServer = findViewById(R.id.btn_select_server);
        mBtnCreateRole = findViewById(R.id.btn_create_role);
        mBtnRenameRole = findViewById(R.id.btn_rename_role);
        mBtnUpgradeRole = findViewById(R.id.btn_upgrade);

        mBtnLogin.setOnClickListener(mClickListener);
        mBtnPay.setOnClickListener(mClickListener);
        mBtnLogout.setOnClickListener(mClickListener);
        mBtnSelectServer.setOnClickListener(mClickListener);
        mBtnCreateRole.setOnClickListener(mClickListener);
        mBtnRenameRole.setOnClickListener(mClickListener);
        mBtnUpgradeRole.setOnClickListener(mClickListener);
    }

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_login:
                    mGameSDK.login();
                    break;
                case R.id.btn_pay:
                    pay();
                    break;
                case R.id.btn_logout:
                    mGameSDK.logout();
                    break;
                case R.id.btn_select_server:
                    mGameSDK.selectServerLog("105292", "105290000000447");
                    break;
                case R.id.btn_create_role:
                    mGameSDK.createRoleLog("105291", "105291000000439", "柳花楹");
                    break;
                case R.id.btn_rename_role:
                    mGameSDK.renameRole("1", "1", "德玛西亚");
                    break;
                case R.id.btn_upgrade:
                    mGameSDK.upgradeRole("90171", "90171000000522", 487);
                    break;
            }
        }
    };

    private void pay() {
        OrderParams orderParams = new OrderParams();
        orderParams.setRoleId("100");
        orderParams.setMoney("1");
        orderParams.setProductName("10元宝");
        orderParams.setCallbackInfo("Test");
        mGameSDK.pay(orderParams);
    }

    public void initWebView() {
        mWebView = findViewById(R.id.webview);
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setNeedInitialFocus(false);
        mWebView.getSettings().setSavePassword(false);
        mWebView.getSettings().setSaveFormData(false);
        mWebView.getSettings().setTextZoom(100);//解决用户系统设置超大字体后，webview不受影响

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                LogUtil.d("url: "+url);
                if (url.startsWith("mqq")) {
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                    mWebView.stopLoading();
                    return true;
                } else {
                    view.loadUrl(url);
                    return true;
                }
            }

            //处理https请求
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, android.net.http.SslError error) {
                handler.proceed();
            }
        });
    }

    public void initData() {
        mWebView.loadUrl("https://wpa1.qq.com/3fL19AZt?_type=wpa&qidian=true");//Q群
//        mWebView.loadUrl("https://wpa1.qq.com/r4GpxFy5?_type=wpa&qidian=true");//QQ
    }

    //QQ和TIM都可以唤醒
    private boolean qqInstall() {
        if (checkApkExist(ContextUtil.getContext(), "com.tencent.mobileqq")
                || checkApkExist(ContextUtil.getContext(), "com.tencent.tim")) {//如果安装有QQ，则跳到QQ界面
            return true;
        }
        return false;
    }

    public static boolean checkApkExist(Context context, String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName,
                    PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
