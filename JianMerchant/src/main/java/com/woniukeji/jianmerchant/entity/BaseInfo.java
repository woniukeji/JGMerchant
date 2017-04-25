package com.woniukeji.jianmerchant.entity;

/**
 * Created by Administrator on 2017/4/24.
 */

public class BaseInfo {


    /**
     * auth_status : 0
     * b_user_id : 846275032236560384
     * city_id : 010
     * companyAdress : 西三环中路
     * companyName : 打地鼠
     * contact_name : 打地鼠
     * contact_phone : 18947560082
     * email : zltguang@163.com
     * id : 846275032244948993
     * introduce :
     * nickname : 打地鼠
     * phone : 0
     * province_id : 53
     * realname : 打地鼠
     * sex : 0
     * status : 3
     * tel : 18947560082
     * type : 3
     *
     */

    private int auth_status;
    private String bus_licence_num; //营业执照
    private long b_user_id;
    private String city_id;
    private String companyAdress;
    private String companyName;
    private String contact_name;
    private String contact_phone;
    private String email;
    private long id;
    private String introduce;
    private String nickname;
    private int phone;
    private int province_id;
    private String realname;
    private String id_number;
    private int sex;
    private int business_type; //商家权限（3是内部,2是外部商家，1是个人商户）
    private String head_img_url;
    private String tel;
    private int type;

    public String getId_number() {
        return id_number;
    }

    public void setId_number(String id_number) {
        this.id_number = id_number;
    }

    public String getBus_licence_num() {
        return bus_licence_num;
    }

    public void setBus_licence_num(String bus_licence_num) {
        this.bus_licence_num = bus_licence_num;
    }

    public int getBusiness_type() {
        return business_type;
    }

    public void setBusiness_type(int business_type) {
        this.business_type = business_type;
    }

    public int getAuth_status() {
        return auth_status;
    }

    public void setAuth_status(int auth_status) {
        this.auth_status = auth_status;
    }

    public long getB_user_id() {
        return b_user_id;
    }

    public void setB_user_id(long b_user_id) {
        this.b_user_id = b_user_id;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getCompanyAdress() {
        return companyAdress;
    }

    public void setCompanyAdress(String companyAdress) {
        this.companyAdress = companyAdress;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getContact_phone() {
        return contact_phone;
    }

    public void setContact_phone(String contact_phone) {
        this.contact_phone = contact_phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public int getProvince_id() {
        return province_id;
    }

    public void setProvince_id(int province_id) {
        this.province_id = province_id;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getHead_img_url() {
        return head_img_url;
    }

    public void setHead_img_url(String head_img_url) {
        this.head_img_url = head_img_url;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
