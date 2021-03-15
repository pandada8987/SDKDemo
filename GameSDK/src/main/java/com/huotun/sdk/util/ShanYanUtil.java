package com.huotun.sdk.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.chuanglan.shanyan_sdk.OneKeyLoginManager;
import com.chuanglan.shanyan_sdk.listener.GetPhoneInfoListener;
import com.chuanglan.shanyan_sdk.listener.InitListener;
import com.chuanglan.shanyan_sdk.listener.OneKeyLoginListener;
import com.chuanglan.shanyan_sdk.listener.OpenLoginAuthListener;
import com.chuanglan.shanyan_sdk.tool.ShanYanUIConfig;
import com.huotun.sdk.entity.ShanYanBean;
import com.huotun.sdk.open.AppNoExtend;
import com.huotun.sdk.plugin.EventManager;
import com.huotun.sdk.plugin.EventType;

import org.json.JSONException;
import org.json.JSONObject;

public class ShanYanUtil {
    //插件反射调用，不可删除
    public static void init(String key) {
        OneKeyLoginManager.getInstance().init(AppNoExtend.getContext(), key, new InitListener() {
            @Override
            public void getInitStatus(int code, String result) {
                LogUtil.d(result);
                if (code == 1022) {//1022初始化成功
                    OneKeyLoginManager.getInstance().getPhoneInfo(new GetPhoneInfoListener() {
                        @Override
                        public void getPhoneInfoStatus(int i, String s) {
                            if (i != 1022) {//1022预取号成功，可以一键登录
                                showError(s);
                            }
                        }
                    });
                } else {
                    showError(result);
                }
            }
        });
    }

    //插件反射调用，不可删除
    public static void login() {
        OneKeyLoginManager.getInstance().setAuthThemeConfig(getDialogUiConfig(AppNoExtend.getContext()));
        OneKeyLoginManager.getInstance().openLoginAuth(true, new OpenLoginAuthListener() {
            @Override
            public void getOpenLoginAuthStatus(int i, String s) {
                if (i != 1000) {
                    EventManager.getInstance().sendMessage(EventType.AUTH_FAILED);
                    showError(s);
                }
            }
        }, new OneKeyLoginListener() {
            @Override
            public void getOneKeyLoginStatus(int i, String s) {
                switch (i) {
                    case 1000:
                        JSONObject object = null;
                        try {
                            object = new JSONObject(s);
                            if (object.has("token")) {
                                String token = object.getString("token");
                                EventManager.getInstance().sendMessage(EventType.AUTH_SUCCESS, token);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 1011://1011是关闭界面
                        break;
                    default:
                        showError(s);
                        EventManager.getInstance().sendMessage(EventType.AUTH_FAILED);
                        break;
                }
            }
        });
    }

    //dialog竖屏样式设置
    public static ShanYanUIConfig getDialogUiConfig(final Context context) {
        /************************************************自定义控件**************************************************************/
        //loading自定义加载框
        LayoutInflater inflater = LayoutInflater.from(context);
        RelativeLayout view_dialog = (RelativeLayout) inflater.inflate(ResourceUtil.getLayoutId(context, "view_auth_loading"), null);
        RelativeLayout.LayoutParams mLayoutParams3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        view_dialog.setLayoutParams(mLayoutParams3);
        view_dialog.setVisibility(View.GONE);

        //设置授权页固有控件
        Drawable authNavHidden = context.getResources().getDrawable(ResourceUtil.getDrawableId(context, "shape_main_dialog"));
        Drawable navReturnImgPath = context.getResources().getDrawable(ResourceUtil.getDrawableId(context, "icon_btn_close"));
        Drawable logBtnImgPath = context.getResources().getDrawable(ResourceUtil.getDrawableId(context, "selector_auth_bt"));
        Drawable uncheckedImgPath = context.getResources().getDrawable(ResourceUtil.getDrawableId(context, "icon_pitch_off"));
        Drawable checkedImgPath = context.getResources().getDrawable(ResourceUtil.getDrawableId(context, "icon_pitch_on"));
        ShanYanUIConfig uiConfig = new ShanYanUIConfig.Builder()
                .setDialogTheme(true, 336, 336, 0, 0, false)
                .setAuthBGImgPath(authNavHidden)//设置授权页背景图片
                //授权页导航栏：
                .setNavColor(Color.parseColor("#ffffff"))  //设置导航栏颜色
                .setNavText("")  //设置导航栏标题文字
                .setNavTextColor(0xff080808) //设置标题栏文字颜色
                .setNavReturnImgPath(navReturnImgPath)  //设置标题栏返回按钮图片
                .setNavReturnBtnWidth(25)//设置标题栏返回按钮宽度
                .setNavReturnBtnHeight(25)//设置标题栏返回按钮高度
                .setNavReturnBtnOffsetRightX(10)//设置标题栏返回按钮据屏幕右边距

                //授权页号码栏：
                .setNumberColor(0xff397BF9)  //设置手机号码字体颜色
                .setNumFieldOffsetY(50)    //设置号码栏相对于标题栏下边缘y偏移
                .setNumberSize(18)//设置手机号码字体大小

                //授权页登录按钮：
                .setLogBtnText("本机号码一键登录")  //设置登录按钮文字
                .setLogBtnTextColor(0xffffffff)   //设置登录按钮文字颜色
                .setLogBtnImgPath(logBtnImgPath)   //设置登录按钮图片
                .setLogBtnOffsetY(120)   //设置登录按钮相对于标题栏下边缘y偏移
                .setLogBtnTextSize(15)//设置登录按钮文字大小
                .setLogBtnWidth(250)//设置登录按钮宽度
                .setLogBtnHeight(40)//设置登录按钮高度

                //授权页隐私栏：
                .setAppPrivacyOne(AppUtil.getCompanyName() + "服务规章", "https://api-yw.huotun.com/page/user_agreement.html")  //设置隐私条款1名称和URL(名称，url)
                .setAppPrivacyColor(0xff666666, 0xff0085d0)   //	设置隐私条款名称颜色(基础文字颜色，协议文字颜色)
                .setPrivacyText("同意", "和", "、", "、", "")
                .setPrivacyOffsetBottomY(30)//设置隐私条款相对于屏幕下边缘y偏
                .setUncheckedImgPath(uncheckedImgPath)//设置隐私栏复选框未选中时背景
                .setCheckedImgPath(checkedImgPath)//设置隐私栏复选框选中时背景

                //授权页slogan：
                .setSloganTextColor(0xff999999)  //设置slogan文字颜色
                .setSloganOffsetY(104)  //设置slogan相对于标题栏下边缘y偏移
                .setSloganTextSize(9)//设置slogan文字大小
                //设置loading样式
                .setLoadingView(view_dialog)//设置自定义loading布局
                .build();
        return uiConfig;
    }

    private static void showError(String error) {
        ShanYanBean bean = JSON.parseObject(error, ShanYanBean.class);
        if (!TextUtils.isEmpty(bean.getInnerDesc())) {
            ToastUtil.showToast(bean.getInnerDesc());
        }
    }
}
