package com.huotun.sdk.entity;

//第三方上报，宿主目前只用于广点通上报
public class ReportBean {
    private String productName;//商品名称
    private String productId;//商品id
    private String amount = "1";//数量
    private String payType;//支付类型
    private String curtype;//货币类型
    private Boolean isSuccess = true;//是否成功
    private int money;//金额

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getCurtype() {
        return curtype;
    }

    public void setCurtype(String curtype) {
        this.curtype = curtype;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
