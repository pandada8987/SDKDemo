package com.huotun.sdk.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.huotun.sdk.R;


public class ToastUtil {
    private static Toast mToast;
    private static TextView mTvMsg;

    public static void showToast(String message) {
        Context context = ContextUtil.getContext();
        if (context == null) {
            return;
        }
        if (mToast == null) {
            mToast = new Toast(context);
            View view = LayoutInflater.from(context).inflate(ResourceUtil.getLayoutId(context, "view_toast"), (ViewGroup) null);
            mTvMsg = (TextView) view.findViewById(ResourceUtil.getId(context, "tv_message"));
            mToast.setView(view);
            mToast.setGravity(49, 0, 0);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mTvMsg.setText(message);
        mToast.show();
    }
}
