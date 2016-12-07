package com.woniukeji.jianmerchant.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by invinjun on 2016/4/13.
 */
public class AffordUser implements Serializable{
        /**
         * total : 2
         * list : [{"money":50,"user_id":800630391256780800,"job_id":803808942302826496,"sex":2,"name":"军哥","tel":18101050625,"id":803809397791657984,"head_img_url":"http://7xlell.com2.z0.glb.qiniucdn.com/android_18101050625_1479895627910"},{"money":50,"user_id":800624884655722496,"job_id":803808942302826496,"sex":2,"name":"周杰伦","tel":13614093590,"id":803809480356532224,"head_img_url":"http://7xlell.com2.z0.glb.qiniucdn.com/Fl3jdnicY4_89DTRPYGcIrlHloDn"}]
         */

        private int total;
        private List<ListBean> list;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements Serializable{
            /**
             * money : 50
             * user_id : 800630391256780800
             * job_id : 803808942302826496
             * sex : 2
             * name : 军哥
             * tel : 18101050625
             * id : 803809397791657984
             * head_img_url : http://7xlell.com2.z0.glb.qiniucdn.com/android_18101050625_1479895627910
             */

            private double money;
            private long pay_user_id;
            private long receive_user_id;
            private long job_id;
            private int sex;
            private String name;
            private String tel;
            private long id;
            private String head_img_url;
            private String note;
            private int type;



            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }


            public long getPay_user_id() {
                return pay_user_id;
            }

            public void setPay_user_id(long pay_user_id) {
                this.pay_user_id = pay_user_id;
            }

            public long getReceive_user_id() {
                return receive_user_id;
            }

            public void setReceive_user_id(long receive_user_id) {
                this.receive_user_id = receive_user_id;
            }

            public double getMoney() {
                return money;
            }

            public void setMoney(double money) {
                this.money = money;
            }


            public long getJob_id() {
                return job_id;
            }

            public void setJob_id(long job_id) {
                this.job_id = job_id;
            }

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
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

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public String getHead_img_url() {
                return head_img_url;
            }

            public void setHead_img_url(String head_img_url) {
                this.head_img_url = head_img_url;
            }

            public String getNote() {
                return note;
            }

            public void setNote(String note) {
                this.note = note;
            }
        }
}
