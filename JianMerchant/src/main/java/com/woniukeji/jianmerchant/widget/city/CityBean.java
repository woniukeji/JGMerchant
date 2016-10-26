package com.woniukeji.jianmerchant.widget.city;


public class CityBean {
    private String name;
    private String cityCode;
    private String parent;

    public CityBean() {
    }

    public CityBean(long id, String name, String cityCode, String parent){
        this.name = name;
        this.cityCode = cityCode;
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    //这个用来显示在PickerView上面的字符串,PickerView会通过反射获取getPickerViewText方法显示出来。
    public String getPickerViewText() {
        //这里还可以判断文字超长截断再提供显示
        return name;
    }
    public String getPickerViewCityCode() {
        //这里还可以判断文字超长截断再提供显示
        return cityCode;
    }
}
