package com.huotun.sdk.entity;

//本地GameConfig.json配置
public class GameConfig {
    /**
     * platCode : ht
     * qqAppId : 1106993706
     * wxAppId : wx487645b50a711901
     * isTest : 1
     * channelId : 14
     * channelType : toutiao
     * gameId : 12345678001
     * clientSecret : c6f6c696c98663f5588e525bff96d89a
     * companyCode : hnht
     * companyName : 火豚娱乐
     */
    private String platCode = "ht";
    private String qqAppId;
    private String wxAppId;
    private String isTest = "0";
    private String gameId;
    private String clientSecret;
    private String companyCode;
    private String companyName;
    private String toutiaoAppId = "";
    private String toutiaoAppName = "";
    private String channelInfo = "htchannel_11_none";
    private boolean isXwan = true;

    public String getPlatCode() {
        return platCode;
    }

    public void setPlatCode(String platCode) {
        this.platCode = platCode;
    }

    public String getQqAppId() {
        return qqAppId;
    }

    public void setQqAppId(String qqAppId) {
        this.qqAppId = qqAppId;
    }

    public String getWxAppId() {
        return wxAppId;
    }

    public void setWxAppId(String wxAppId) {
        this.wxAppId = wxAppId;
    }

    public String getIsTest() {
        return isTest;
    }

    public void setIsTest(String isTest) {
        this.isTest = isTest;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getToutiaoAppId() {
        return toutiaoAppId;
    }

    public void setToutiaoAppId(String toutiaoAppId) {
        this.toutiaoAppId = toutiaoAppId;
    }

    public String getToutiaoAppName() {
        return toutiaoAppName;
    }

    public void setToutiaoAppName(String toutiaoAppName) {
        this.toutiaoAppName = toutiaoAppName;
    }

    public String getChannelInfo() {
        return channelInfo;
    }

    public void setChannelInfo(String channelInfo) {
        this.channelInfo = channelInfo;
    }

    public boolean isXwan() {
        return isXwan;
    }

    public void setXwan(boolean xwan) {
        isXwan = xwan;
    }
}
