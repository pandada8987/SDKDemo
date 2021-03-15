package com.huotun.sdk.entity;

import java.util.List;

public class PluginUpdateBean {
    /**
     * data : [{"url":"https://cdn-pkg.huotun.com/sdk_plugin/com.huotun.plugin/com.huotun.plugin-130.apk","package_name":"com.huotun.plugin","version":"130"}]
     * code : 1
     * msg : success
     */
    private int code;
    private String msg;
    private List<PluginDetail> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<PluginDetail> getData() {
        return data;
    }

    public void setData(List<PluginDetail> data) {
        this.data = data;
    }

    public static class PluginDetail implements Comparable<PluginDetail> {
        /**
         * url : https://cdn-pkg.huotun.com/sdk_plugin/com.huotun.plugin/com.huotun.plugin-130.apk
         * package_name : com.huotun.plugin
         * version : 130
         */
        private String url;
        private String package_name;
        private int version;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getPackage_name() {
            return package_name;
        }

        public void setPackage_name(String package_name) {
            this.package_name = package_name;
        }

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        @Override
        public int compareTo(PluginDetail o) {
            return o.getVersion() - this.version;
        }
    }
}
