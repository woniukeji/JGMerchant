package com.woniukeji.jianmerchant.entity;

/**
 * Created by Administrator on 2016/12/2.
 */

public class Version {

        /**
         * ios_user_version : 2
         * ios_business_version : 2
         * android_business_version : null
         * android_user_version : 2
         * android_business_url : http://v3.jianguojob.com:8080/JianMerchant-release.apk
         * android_user_url : http://v3.jianguojob.com:8080/JianMerchant-release.apk
         */

        private String android_business_version;
        private String android_user_version;
        private String android_business_url;
        private String android_user_url;

    public String getAndroid_business_version() {
        return android_business_version;
    }

    public void setAndroid_business_version(String android_business_version) {
        this.android_business_version = android_business_version;
    }

    public String getAndroid_user_version() {
        return android_user_version;
    }

    public void setAndroid_user_version(String android_user_version) {
        this.android_user_version = android_user_version;
    }

    public String getAndroid_business_url() {
        return android_business_url;
    }

    public void setAndroid_business_url(String android_business_url) {
        this.android_business_url = android_business_url;
    }

    public String getAndroid_user_url() {
        return android_user_url;
    }

    public void setAndroid_user_url(String android_user_url) {
        this.android_user_url = android_user_url;
    }
}
