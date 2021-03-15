package com.huotun.sdk.report;


import com.huotun.sdk.entity.ReportBean;

public interface IReportSDK {
    void init();

    void register(String userType);

    void purchase(ReportBean report);
}
