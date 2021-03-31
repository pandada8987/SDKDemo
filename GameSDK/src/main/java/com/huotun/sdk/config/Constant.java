package com.huotun.sdk.config;

import com.huotun.sdk.entity.GameConfig;

public class Constant {
    public static final String HUOTUN = "com.huotun.plugin";

    public static GameConfig GAME_CONFIG;//assets/GameConfig.json文件中的配置
    public static String[] ChannelInfo;

    public static String CHANNEL_ID = "11";//默认渠道ID
    public static String CHANNEL_TYPE = ChannelType.NONE;//默认渠道类型
    public static String UID = "";//默认渠道ID
}
