package com.woniukeji.jianmerchant.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/21.
 */

public class MerchantBean implements Serializable{

    /**
     * 商家信息实体bean
     * Created by Administrator on 2016/9/18.
     */
        private String tel;
        private int loginId;
        private int merchantId;
        private String password;
        private String token;
        private int permissions;//商家权限（1是外部商家，2是个人商户，0是内部）
        private int merchantInfoStatus;//是否填写商家资料信息 0未填写 1 正在审核 2审核拒绝 3审核通过
        private int payStatus;//是否设置了支付密码
        private String name;
        private String nickName;
        private String userImage;
        private String contactName;
        private String contactPhone;
        private String email;
        private String province;
        private String city;
        private String companyAddress;
        private String companyName;
         private String bussinessNum;
        private String qiniuToken;
        private String cardNum;
        private String cardImg;
        private String realName;
        private String handImg;
        private String bussinessImg;
        private String about;
         private String apkurl;
    private String payPassword;

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }
    public String getBussinessNum() {
        return bussinessNum;
    }
    public void setBussinessNum(String bussinessNum) {
        this.bussinessNum = bussinessNum;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getApkurl() {
        return apkurl;
    }

    public void setApkurl(String apkurl) {
        this.apkurl = apkurl;
    }

        public String getHandImg() {
            return handImg;
        }

        public void setHandImg(String handImg) {
            this.handImg = handImg;
        }

        public String getBussinessImg() {
            return bussinessImg;
        }

        public void setBussinessImg(String bussinessImg) {
            this.bussinessImg = bussinessImg;
        }

        public String getAbout() {
            return about;
        }

        public void setAbout(String about) {
            this.about = about;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getCardNum() {
            return cardNum;
        }

        public void setCardNum(String cardNum) {
            this.cardNum = cardNum;
        }

        public String getCardImg() {
            return cardImg;
        }

        public void setCardImg(String cardImg) {
            this.cardImg = cardImg;
        }

        public int getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(int merchantId) {
            this.merchantId = merchantId;
        }
        public int getMerchantInfoStatus() {
            return merchantInfoStatus;
        }

        public void setMerchantInfoStatus(int merchantInfoStatus) {
            this.merchantInfoStatus = merchantInfoStatus;
        }
        public String getQiniuToken() {
            return qiniuToken;
        }

        public void setQiniuToken(String qiniuToken) {
            this.qiniuToken = qiniuToken;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public int getLoginId() {
            return loginId;
        }

        public void setLoginId(int loginId) {
            this.loginId = loginId;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getPermissions() {
            return permissions;
        }

        public void setPermissions(int permissions) {
            this.permissions = permissions;
        }


        public int getPayStatus() {
            return payStatus;
        }

        public void setPayStatus(int payStatus) {
            this.payStatus = payStatus;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getUserImage() {
            return userImage;
        }

        public void setUserImage(String userImage) {
            this.userImage = userImage;
        }

        public String getContactName() {
            return contactName;
        }

        public void setContactName(String contactName) {
            this.contactName = contactName;
        }

        public String getContactPhone() {
            return contactPhone;
        }

        public void setContactPhone(String contactPhone) {
            this.contactPhone = contactPhone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCompanyAddress() {
            return companyAddress;
        }

        public void setCompanyAddress(String companyAddress) {
            this.companyAddress = companyAddress;
        }

}
