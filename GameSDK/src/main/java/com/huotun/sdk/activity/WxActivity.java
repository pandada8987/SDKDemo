package com.huotun.sdk.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.huotun.sdk.config.Constant;
import com.huotun.sdk.plugin.EventManager;
import com.huotun.sdk.plugin.EventType;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WxActivity extends Activity {
    private IWXAPI api;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        api = WXAPIFactory.createWXAPI(this, Constant.GAME_CONFIG.getWxAppId(), false);
        api.handleIntent(getIntent(), mHandler);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    private IWXAPIEventHandler mHandler = new IWXAPIEventHandler() {
        @Override
        public void onReq(BaseReq baseReq) {

        }

        @Override
        public void onResp(BaseResp baseResp) {
            if (baseResp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {//微信登录
                switch (baseResp.errCode) {
                    case BaseResp.ErrCode.ERR_OK:
                        callBackToPlugin(baseResp);
                        break;
                    case BaseResp.ErrCode.ERR_USER_CANCEL:
                        Toast.makeText(WxActivity.this, "取消登录", Toast.LENGTH_SHORT).show();
                        break;
                    case BaseResp.ErrCode.ERR_AUTH_DENIED:
                        Toast.makeText(WxActivity.this, "用户拒绝", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {//微信支付
                switch (baseResp.errCode) {
                    case 0://支付成功
                        if (EventManager.getInstance().getSDKListener() != null) {
                            EventManager.getInstance().getSDKListener().onPaySuccess();
                        }
                        EventManager.getInstance().sendMessage(EventType.WECHAT_PAY_SUCCESS);//通知插件上报第三方SDK
                        break;
                    case -1://错误
                        if (EventManager.getInstance().getSDKListener() != null) {
                            EventManager.getInstance().getSDKListener().onPayFailed("支付错误");
                        }
                        break;
                    case -2://用户取消
                        EventManager.getInstance().sendMessage(EventType.WECHAT_PAY_CANCEL);//通知插件取消订单
                        break;
                }
            }
            finish();
        }
    };

    private void callBackToPlugin(BaseResp resp) {
        SendAuth.Resp authResp = (SendAuth.Resp) resp;
        String code = authResp.code;
        EventManager.getInstance().sendMessage(EventType.WECHAT_CODE, code);
    }
}
