package com.huotun.sdk.util;

import android.content.Context;
import android.text.TextUtils;

import com.huotun.sdk.config.Constant;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class AppUtil {

    public static String loadConfigFromAssets(Context context, String fileName) {
        String data = null;
        try {
            InputStreamReader streamReader = new InputStreamReader(context.getAssets().open(fileName), "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(streamReader);
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }
            bufferedReader.close();
            streamReader.close();
            data = builder.toString();
        } catch (Exception e) {

        }
        return data;
    }

    public static String getCompanyName() {
        String name = Constant.GAME_CONFIG.getCompanyName();
        if (TextUtils.isEmpty(name)){
            return "玩家";
        }
        return name;
    }
}
