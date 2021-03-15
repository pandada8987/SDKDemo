package com.huotun.sdk.util;

import android.app.Activity;
import android.content.Context;

public class ContextUtil {
    private static Context mContext;
    public static void setContext(Activity activity) {
        mContext = activity;
    }

    public static Context getContext() {
        return mContext;
    }
}
