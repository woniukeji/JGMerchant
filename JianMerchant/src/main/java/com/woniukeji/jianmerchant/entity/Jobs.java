package com.woniukeji.jianmerchant.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by invinjun on 2016/3/7.
 */
public class Jobs implements Serializable {


    /**
     * address : t3航站楼
     * alike : 0
     * area_id : 30
     * city_id : 3
     * count : 0
     * day : 0
     * hot : 1
     * id : 177
     * is_model : 1
     * label_name : ["1"]
     * limit_name : ["1"]
     * limit_sex : 2
     * look : 1
     * max : 0
     * merchant_id : 3
     * mode : 2
     * money : 99.0
     * name : 机场派发传单
     * name_image : http://v3.jianguojob.com/logo.png
     * reg_date : 2016-08-04 12:21:41
     * regedit_time : 2016-08-04 12:21:41:924
     * start_date : 1473955200
     * status : 1
     * stop_date : 1476547200
     * sum : 6
     * term : 2
     * type_id : 3
     * user_count : 0
     * welfare_name : ["1"]
     */

    private TJobBean t_job;

    public TJobBean getT_job() {
        return t_job;
    }

    public void setT_job(TJobBean t_job) {
        this.t_job = t_job;
    }

    public static class TJobBean implements Serializable {
        private String address;
        private String alike;
        private int area_id;
        private int city_id;
        private int count;
        private int day;
        private int hot;
        private int id;
        private int is_model;
        private int limit_sex;
        private int look;
        private int max;
        private int merchant_id;
        private int mode;
        private double money;
        private String name;
        private String name_image;
        private String reg_date;
        private String regedit_time;
        private String start_date;
        private int status;
        private String stop_date;
        private int sum;
        private int term;
        private int type_id;
        private int user_count;
        private List<String> label_name;
        private List<String> limit_name;
        private List<String> welfare_name;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAlike() {
            return alike;
        }

        public void setAlike(String alike) {
            this.alike = alike;
        }

        public int getArea_id() {
            return area_id;
        }

        public void setArea_id(int area_id) {
            this.area_id = area_id;
        }

        public int getCity_id() {
            return city_id;
        }

        public void setCity_id(int city_id) {
            this.city_id = city_id;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public int getHot() {
            return hot;
        }

        public void setHot(int hot) {
            this.hot = hot;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIs_model() {
            return is_model;
        }

        public void setIs_model(int is_model) {
            this.is_model = is_model;
        }

        public int getLimit_sex() {
            return limit_sex;
        }

        public void setLimit_sex(int limit_sex) {
            this.limit_sex = limit_sex;
        }

        public int getLook() {
            return look;
        }

        public void setLook(int look) {
            this.look = look;
        }

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public int getMerchant_id() {
            return merchant_id;
        }

        public void setMerchant_id(int merchant_id) {
            this.merchant_id = merchant_id;
        }

        public int getMode() {
            return mode;
        }

        public void setMode(int mode) {
            this.mode = mode;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName_image() {
            return name_image;
        }

        public void setName_image(String name_image) {
            this.name_image = name_image;
        }

        public String getReg_date() {
            return reg_date;
        }

        public void setReg_date(String reg_date) {
            this.reg_date = reg_date;
        }

        public String getRegedit_time() {
            return regedit_time;
        }

        public void setRegedit_time(String regedit_time) {
            this.regedit_time = regedit_time;
        }

        public String getStart_date() {
            return start_date;
        }

        public void setStart_date(String start_date) {
            this.start_date = start_date;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getStop_date() {
            return stop_date;
        }

        public void setStop_date(String stop_date) {
            this.stop_date = stop_date;
        }

        public int getSum() {
            return sum;
        }

        public void setSum(int sum) {
            this.sum = sum;
        }

        public int getTerm() {
            return term;
        }

        public void setTerm(int term) {
            this.term = term;
        }

        public int getType_id() {
            return type_id;
        }

        public void setType_id(int type_id) {
            this.type_id = type_id;
        }

        public int getUser_count() {
            return user_count;
        }

        public void setUser_count(int user_count) {
            this.user_count = user_count;
        }

        public List<String> getLabel_name() {
            return label_name;
        }

        public void setLabel_name(List<String> label_name) {
            this.label_name = label_name;
        }

        public List<String> getLimit_name() {
            return limit_name;
        }

        public void setLimit_name(List<String> limit_name) {
            this.limit_name = limit_name;
        }

        public List<String> getWelfare_name() {
            return welfare_name;
        }

        public void setWelfare_name(List<String> welfare_name) {
            this.welfare_name = welfare_name;
        }
    }
}
