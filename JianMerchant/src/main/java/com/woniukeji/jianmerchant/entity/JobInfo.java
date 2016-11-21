package com.woniukeji.jianmerchant.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/18.
 */

public class JobInfo implements Serializable{


    /**
     * end_date : 0
     * publisher_id : 0
     * address : wry你呢
     * girl_sum : 0
     * count : 0
     * end_time : 0
     * begin_time : 0
     * sum : 0
     * is_model : 0
     * type : 0
     * job_type_id : 0
     * mode : 2
     * job_name : 1400
     * money : 1
     * createTime : 0
     * boy_sum : 0
     * browse_count : 0
     * tel : 0
     * term : 2
     * id : 799475434868576256
     * limit_sex : 1
     * start_date : 1481558400000
     * status : 0
     */

    private long end_date;
    private int publisher_id;
    private String address;
    private int girl_sum;
    private int count;
    private long end_time;
    private long begin_time;
    private int sum;
    private int is_model;
    private int type;
    private int job_type_id;
    private int mode;
    private String job_name;
    private int money;
    private int createTime;
    private int boy_sum;
    private int browse_count;
    private int tel;
    private int term;
    private long id;
    private int limit_sex;
    private long start_date;
    private int status;//兼职状态
    private String model_name;

    public String getModel_name() {
        return model_name;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }

    public long getEnd_date() {
        return end_date;
    }

    public void setEnd_date(long end_date) {
        this.end_date = end_date;
    }

    public int getPublisher_id() {
        return publisher_id;
    }

    public void setPublisher_id(int publisher_id) {
        this.publisher_id = publisher_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getGirl_sum() {
        return girl_sum;
    }

    public void setGirl_sum(int girl_sum) {
        this.girl_sum = girl_sum;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }

    public long getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(long begin_time) {
        this.begin_time = begin_time;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getIs_model() {
        return is_model;
    }

    public void setIs_model(int is_model) {
        this.is_model = is_model;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getJob_type_id() {
        return job_type_id;
    }

    public void setJob_type_id(int job_type_id) {
        this.job_type_id = job_type_id;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public String getJob_name() {
        return job_name;
    }

    public void setJob_name(String job_name) {
        this.job_name = job_name;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getCreateTime() {
        return createTime;
    }

    public void setCreateTime(int createTime) {
        this.createTime = createTime;
    }

    public int getBoy_sum() {
        return boy_sum;
    }

    public void setBoy_sum(int boy_sum) {
        this.boy_sum = boy_sum;
    }

    public int getBrowse_count() {
        return browse_count;
    }

    public void setBrowse_count(int browse_count) {
        this.browse_count = browse_count;
    }

    public int getTel() {
        return tel;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getLimit_sex() {
        return limit_sex;
    }

    public void setLimit_sex(int limit_sex) {
        this.limit_sex = limit_sex;
    }

    public long getStart_date() {
        return start_date;
    }

    public void setStart_date(long start_date) {
        this.start_date = start_date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
