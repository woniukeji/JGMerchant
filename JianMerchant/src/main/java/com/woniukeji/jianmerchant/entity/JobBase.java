package com.woniukeji.jianmerchant.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2016/7/22.
 */
public class JobBase implements Parcelable {

        /**
         * name : 工资高
         * id : 1
         */

        private List<LabelListBean> label_list;
        /**
         * name : 礼仪模特
         * id : 1
         */

        private List<TypeListBean> type_list;
        /**
         * name : 需面试
         * id : 1
         */

        private List<LimitListBean> limit_list;
        /**
         * name : 包吃包住
         * id : 1
         */

        private List<WelfareListBean> welfare_list;
        /**
         * code : 0899
         * cityName : 三亚
         * areaList : [{"areaName":"市辖区","id":1,"city_id":"0899"},{"areaName":"三亚湾","id":2,"city_id":"0899"},{"areaName":"海棠湾","id":3,"city_id":"0899"},{"areaName":"清水湾","id":4,"city_id":"0899"},{"areaName":"大东海","id":5,"city_id":"0899"},{"areaName":"凤凰岛","id":6,"city_id":"0899"},{"areaName":"吉阳镇","id":7,"city_id":"0899"},{"areaName":"田独镇","id":8,"city_id":"0899"},{"areaName":"崖城","id":9,"city_id":"0899"},{"areaName":"育才","id":10,"city_id":"0899"},{"areaName":"天涯","id":11,"city_id":"0899"},{"areaName":"其他","id":12,"city_id":"0899"}]
         * id : 1
         */

        private List<CityListBean> city_list;

    protected JobBase(Parcel in) {
    }

    public static final Creator<JobBase> CREATOR = new Creator<JobBase>() {
        @Override
        public JobBase createFromParcel(Parcel in) {
            return new JobBase(in);
        }

        @Override
        public JobBase[] newArray(int size) {
            return new JobBase[size];
        }
    };

    public List<LabelListBean> getLabel_list() {
            return label_list;
        }

        public void setLabel_list(List<LabelListBean> label_list) {
            this.label_list = label_list;
        }

        public List<TypeListBean> getType_list() {
            return type_list;
        }

        public void setType_list(List<TypeListBean> type_list) {
            this.type_list = type_list;
        }

        public List<LimitListBean> getLimit_list() {
            return limit_list;
        }

        public void setLimit_list(List<LimitListBean> limit_list) {
            this.limit_list = limit_list;
        }

        public List<WelfareListBean> getWelfare_list() {
            return welfare_list;
        }

        public void setWelfare_list(List<WelfareListBean> welfare_list) {
            this.welfare_list = welfare_list;
        }

        public List<CityListBean> getCity_list() {
            return city_list;
        }

        public void setCity_list(List<CityListBean> city_list) {
            this.city_list = city_list;
        }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public static class LabelListBean implements Parcelable{
            private String name;
            private int id;

        protected LabelListBean(Parcel in) {
            name = in.readString();
            id = in.readInt();
        }

        public static final Creator<LabelListBean> CREATOR = new Creator<LabelListBean>() {
            @Override
            public LabelListBean createFromParcel(Parcel in) {
                return new LabelListBean(in);
            }

            @Override
            public LabelListBean[] newArray(int size) {
                return new LabelListBean[size];
            }
        };

        public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
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
            dest.writeString(name);
            dest.writeInt(id);
        }
    }

        public static class TypeListBean implements Parcelable{
            private String name;
            private int id;
            private boolean isSelect;

            public boolean isSelect() {
                return isSelect;
            }

            public void setSelect(boolean select) {
                isSelect = select;
            }

            protected TypeListBean(Parcel in) {
                name = in.readString();
                id = in.readInt();
            }

            public static final Creator<TypeListBean> CREATOR = new Creator<TypeListBean>() {
                @Override
                public TypeListBean createFromParcel(Parcel in) {
                    return new TypeListBean(in);
                }

                @Override
                public TypeListBean[] newArray(int size) {
                    return new TypeListBean[size];
                }
            };

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
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
                dest.writeString(name);
                dest.writeInt(id);
            }
        }

        public static class LimitListBean implements Parcelable{
            private String name;
            private int id;

            protected LimitListBean(Parcel in) {
                name = in.readString();
                id = in.readInt();
            }

            public static final Creator<LimitListBean> CREATOR = new Creator<LimitListBean>() {
                @Override
                public LimitListBean createFromParcel(Parcel in) {
                    return new LimitListBean(in);
                }

                @Override
                public LimitListBean[] newArray(int size) {
                    return new LimitListBean[size];
                }
            };

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
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
                dest.writeString(name);
                dest.writeInt(id);
            }
        }

        public static class WelfareListBean implements Parcelable{
            private String name;
            private int id;

            protected WelfareListBean(Parcel in) {
                name = in.readString();
                id = in.readInt();
            }

            public static final Creator<WelfareListBean> CREATOR = new Creator<WelfareListBean>() {
                @Override
                public WelfareListBean createFromParcel(Parcel in) {
                    return new WelfareListBean(in);
                }

                @Override
                public WelfareListBean[] newArray(int size) {
                    return new WelfareListBean[size];
                }
            };

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
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
                dest.writeString(name);
                dest.writeInt(id);
            }
        }

        public static class CityListBean implements Parcelable{
            private String code;
            private String cityName;
            private int id;
            /**
             * areaName : 市辖区
             * id : 1
             * city_id : 0899
             */

            private List<AreaListBean> areaList;

            protected CityListBean(Parcel in) {
                code = in.readString();
                cityName = in.readString();
                id = in.readInt();
            }

            public static final Creator<CityListBean> CREATOR = new Creator<CityListBean>() {
                @Override
                public CityListBean createFromParcel(Parcel in) {
                    return new CityListBean(in);
                }

                @Override
                public CityListBean[] newArray(int size) {
                    return new CityListBean[size];
                }
            };

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getCityName() {
                return cityName;
            }

            public void setCityName(String cityName) {
                this.cityName = cityName;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public List<AreaListBean> getAreaList() {
                return areaList;
            }

            public void setAreaList(List<AreaListBean> areaList) {
                this.areaList = areaList;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(code);
                dest.writeString(cityName);
                dest.writeInt(id);
            }

            public static class AreaListBean {
                private String areaName;
                private int id;
                private String city_id;

                public String getAreaName() {
                    return areaName;
                }

                public void setAreaName(String areaName) {
                    this.areaName = areaName;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getCity_id() {
                    return city_id;
                }

                public void setCity_id(String city_id) {
                    this.city_id = city_id;
                }
            }
        }
}
