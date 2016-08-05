package com.woniukeji.jianmerchant.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/22.
 */
public class CityAndCategoryBean implements Parcelable {


    /**
     * city : 三亚
     * code : 0899
     * id : 1
     * list_t_area : [{"area_name":"市辖区","city_id":1,"id":1},{"area_name":"三亚湾","city_id":1,"id":2},{"area_name":"海棠湾","city_id":1,"id":3},{"area_name":"清水湾","city_id":1,"id":4},{"area_name":"大东海","city_id":1,"id":5},{"area_name":"凤凰岛","city_id":1,"id":6},{"area_name":"吉阳镇","city_id":1,"id":7},{"area_name":"田独镇","city_id":1,"id":8},{"area_name":"崖城","city_id":1,"id":9},{"area_name":"育才","city_id":1,"id":10},{"area_name":"天涯","city_id":1,"id":11},{"area_name":"其他","city_id":1,"id":12}]
     */

    private List<ListTCity2Bean> list_t_city2;
    /**
     * id : 1
     * label_name : 工资高
     */

    private List<ListTLabelBean> list_t_label;
    /**
     * id : 1
     * limit_name : 需面试
     */

    private List<ListTLimitBean> list_t_limit;
    /**
     * id : 1
     * type_name : 礼仪模特
     */

    private List<ListTTypeBean> list_t_type;
    /**
     * id : 1
     * welfare_name : 包吃包住
     */

    private List<ListTWelfareBean> list_t_welfare;

    public List<ListTCity2Bean> getList_t_city2() {
        return list_t_city2;
    }

    public void setList_t_city2(List<ListTCity2Bean> list_t_city2) {
        this.list_t_city2 = list_t_city2;
    }

    public List<ListTLabelBean> getList_t_label() {
        return list_t_label;
    }

    public void setList_t_label(List<ListTLabelBean> list_t_label) {
        this.list_t_label = list_t_label;
    }

    public List<ListTLimitBean> getList_t_limit() {
        return list_t_limit;
    }

    public void setList_t_limit(List<ListTLimitBean> list_t_limit) {
        this.list_t_limit = list_t_limit;
    }

    public List<ListTTypeBean> getList_t_type() {
        return list_t_type;
    }

    public void setList_t_type(List<ListTTypeBean> list_t_type) {
        this.list_t_type = list_t_type;
    }

    public List<ListTWelfareBean> getList_t_welfare() {
        return list_t_welfare;
    }

    public void setList_t_welfare(List<ListTWelfareBean> list_t_welfare) {
        this.list_t_welfare = list_t_welfare;
    }

    public static class ListTCity2Bean implements Parcelable {
        private String city;
        private String code;
        private int id;
        /**
         * area_name : 市辖区
         * city_id : 1
         * id : 1
         */

        private List<ListTAreaBean> list_t_area;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public List<ListTAreaBean> getList_t_area() {
            return list_t_area;
        }

        public void setList_t_area(List<ListTAreaBean> list_t_area) {
            this.list_t_area = list_t_area;
        }

        public static class ListTAreaBean implements Parcelable {
            private String area_name;
            private int city_id;
            private int id;

            public String getArea_name() {
                return area_name;
            }

            public void setArea_name(String area_name) {
                this.area_name = area_name;
            }

            public int getCity_id() {
                return city_id;
            }

            public void setCity_id(int city_id) {
                this.city_id = city_id;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.area_name);
                dest.writeInt(this.city_id);
                dest.writeInt(this.id);
            }

            public ListTAreaBean() {
            }

            protected ListTAreaBean(Parcel in) {
                this.area_name = in.readString();
                this.city_id = in.readInt();
                this.id = in.readInt();
            }

            public static final Creator<ListTAreaBean> CREATOR = new Creator<ListTAreaBean>() {
                @Override
                public ListTAreaBean createFromParcel(Parcel source) {
                    return new ListTAreaBean(source);
                }

                @Override
                public ListTAreaBean[] newArray(int size) {
                    return new ListTAreaBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.city);
            dest.writeString(this.code);
            dest.writeInt(this.id);
            dest.writeList(this.list_t_area);
        }

        public ListTCity2Bean() {
        }

        protected ListTCity2Bean(Parcel in) {
            this.city = in.readString();
            this.code = in.readString();
            this.id = in.readInt();
            this.list_t_area = new ArrayList<ListTAreaBean>();
            in.readList(this.list_t_area, ListTAreaBean.class.getClassLoader());
        }

        public static final Creator<ListTCity2Bean> CREATOR = new Creator<ListTCity2Bean>() {
            @Override
            public ListTCity2Bean createFromParcel(Parcel source) {
                return new ListTCity2Bean(source);
            }

            @Override
            public ListTCity2Bean[] newArray(int size) {
                return new ListTCity2Bean[size];
            }
        };
    }

    public static class ListTLabelBean implements Parcelable {
        private int id;
        private String label_name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLabel_name() {
            return label_name;
        }

        public void setLabel_name(String label_name) {
            this.label_name = label_name;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.label_name);
        }

        public ListTLabelBean() {
        }

        protected ListTLabelBean(Parcel in) {
            this.id = in.readInt();
            this.label_name = in.readString();
        }

        public static final Creator<ListTLabelBean> CREATOR = new Creator<ListTLabelBean>() {
            @Override
            public ListTLabelBean createFromParcel(Parcel source) {
                return new ListTLabelBean(source);
            }

            @Override
            public ListTLabelBean[] newArray(int size) {
                return new ListTLabelBean[size];
            }
        };
    }

    public static class ListTLimitBean implements Parcelable {
        private int id;
        private String limit_name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLimit_name() {
            return limit_name;
        }

        public void setLimit_name(String limit_name) {
            this.limit_name = limit_name;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.limit_name);
        }

        public ListTLimitBean() {
        }

        protected ListTLimitBean(Parcel in) {
            this.id = in.readInt();
            this.limit_name = in.readString();
        }

        public static final Creator<ListTLimitBean> CREATOR = new Creator<ListTLimitBean>() {
            @Override
            public ListTLimitBean createFromParcel(Parcel source) {
                return new ListTLimitBean(source);
            }

            @Override
            public ListTLimitBean[] newArray(int size) {
                return new ListTLimitBean[size];
            }
        };
    }

    public static class ListTTypeBean implements Parcelable {
        private int id;
        private String type_name;
        private boolean isSelect;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.type_name);
            dest.writeByte(this.isSelect ? (byte) 1 : (byte) 0);
        }

        public ListTTypeBean() {
        }

        protected ListTTypeBean(Parcel in) {
            this.id = in.readInt();
            this.type_name = in.readString();
            this.isSelect = in.readByte() != 0;
        }

        public static final Creator<ListTTypeBean> CREATOR = new Creator<ListTTypeBean>() {
            @Override
            public ListTTypeBean createFromParcel(Parcel source) {
                return new ListTTypeBean(source);
            }

            @Override
            public ListTTypeBean[] newArray(int size) {
                return new ListTTypeBean[size];
            }
        };
    }

    public static class ListTWelfareBean implements Parcelable {
        private int id;
        private String welfare_name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getWelfare_name() {
            return welfare_name;
        }

        public void setWelfare_name(String welfare_name) {
            this.welfare_name = welfare_name;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.welfare_name);
        }

        public ListTWelfareBean() {
        }

        protected ListTWelfareBean(Parcel in) {
            this.id = in.readInt();
            this.welfare_name = in.readString();
        }

        public static final Creator<ListTWelfareBean> CREATOR = new Creator<ListTWelfareBean>() {
            @Override
            public ListTWelfareBean createFromParcel(Parcel source) {
                return new ListTWelfareBean(source);
            }

            @Override
            public ListTWelfareBean[] newArray(int size) {
                return new ListTWelfareBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.list_t_city2);
        dest.writeList(this.list_t_label);
        dest.writeList(this.list_t_limit);
        dest.writeList(this.list_t_type);
        dest.writeList(this.list_t_welfare);
    }

    public CityAndCategoryBean() {
    }

    protected CityAndCategoryBean(Parcel in) {
        this.list_t_city2 = new ArrayList<ListTCity2Bean>();
        in.readList(this.list_t_city2, ListTCity2Bean.class.getClassLoader());
        this.list_t_label = new ArrayList<ListTLabelBean>();
        in.readList(this.list_t_label, ListTLabelBean.class.getClassLoader());
        this.list_t_limit = new ArrayList<ListTLimitBean>();
        in.readList(this.list_t_limit, ListTLimitBean.class.getClassLoader());
        this.list_t_type = new ArrayList<ListTTypeBean>();
        in.readList(this.list_t_type, ListTTypeBean.class.getClassLoader());
        this.list_t_welfare = new ArrayList<ListTWelfareBean>();
        in.readList(this.list_t_welfare, ListTWelfareBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<CityAndCategoryBean> CREATOR = new Parcelable.Creator<CityAndCategoryBean>() {
        @Override
        public CityAndCategoryBean createFromParcel(Parcel source) {
            return new CityAndCategoryBean(source);
        }

        @Override
        public CityAndCategoryBean[] newArray(int size) {
            return new CityAndCategoryBean[size];
        }
    };
}
