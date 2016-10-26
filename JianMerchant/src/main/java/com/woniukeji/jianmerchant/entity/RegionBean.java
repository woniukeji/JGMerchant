package com.woniukeji.jianmerchant.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/7/21.
 */
public class RegionBean implements Parcelable {
    String region;

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    boolean isSelect = false;

    public RegionBean(String region,String id) {
        this.region = region;
        this.id = id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    @Override
    public String toString() {
        return "RegionBean{" +
                "region='" + region + '\'' +
                ", isSelect=" + isSelect +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.region);
        dest.writeString(this.id);
        dest.writeByte(this.isSelect ? (byte) 1 : (byte) 0);
    }

    protected RegionBean(Parcel in) {
        this.region = in.readString();
        this.id = in.readString();
        this.isSelect = in.readByte() != 0;
    }

    public static final Parcelable.Creator<RegionBean> CREATOR = new Parcelable.Creator<RegionBean>() {
        @Override
        public RegionBean createFromParcel(Parcel source) {
            return new RegionBean(source);
        }

        @Override
        public RegionBean[] newArray(int size) {
            return new RegionBean[size];
        }
    };
}
