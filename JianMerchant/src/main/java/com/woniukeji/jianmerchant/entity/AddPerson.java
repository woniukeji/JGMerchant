package com.woniukeji.jianmerchant.entity;

/**
 * Created by Se7enGM on 2016/9/8.
 */
public class AddPerson {
    private String name;
    private String tel;

    @Override
    public String toString() {
        return "AddPerson{" +
                "name='" + name + '\'' +
                ", tel='" + tel + '\'' +
                '}';
    }

    public AddPerson(String name, String tel) {
        this.name = name;
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
