package com.woniukeji.jianmerchant.entity;

/**
 * Created by Administrator on 2016/8/18.
 */
public class Pigeon {

    /**
     * cancel_job : 2
     * complete_job : 11
     * credit : 0
     * id : 0
     * integral : 0
     * intoschool_date_resume : 2015-09-01
     * login_id : 1
     * login_time : 2016-05-14 10:51:29
     * name : 麦迪
     * name_image : http://7xlell.com2.z0.glb.qiniucdn.com/FuqME5y5L1hzn1XFV8sdSuOcd3Nz
     * nickname : 机关枪
     * pigeon_count : 3
     * realname : 0
     * regedit_time : 2016-05-14 10:51:29
     * school : 三亚城市职业学院
     * sex_resume : 0
     * tel : 18101050625222
     * user_status : 0
     */

    private UserInfoBean user_info;

    public UserInfoBean getUser_info() {
        return user_info;
    }

    public void setUser_info(UserInfoBean user_info) {
        this.user_info = user_info;
    }

    public static class UserInfoBean {
        private int cancel_job;
        private int complete_job;
        private int credit;
        private int id;
        private int integral;
        private String intoschool_date_resume;
        private int login_id;
        private String login_time;
        private String name;
        private String name_image;
        private String nickname;
        private int pigeon_count;
        private int realname;
        private String regedit_time;
        private String school;
        private int sex_resume;
        private String tel;
        private int user_status;

        public int getCancel_job() {
            return cancel_job;
        }

        public void setCancel_job(int cancel_job) {
            this.cancel_job = cancel_job;
        }

        public int getComplete_job() {
            return complete_job;
        }

        public void setComplete_job(int complete_job) {
            this.complete_job = complete_job;
        }

        public int getCredit() {
            return credit;
        }

        public void setCredit(int credit) {
            this.credit = credit;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIntegral() {
            return integral;
        }

        public void setIntegral(int integral) {
            this.integral = integral;
        }

        public String getIntoschool_date_resume() {
            return intoschool_date_resume;
        }

        public void setIntoschool_date_resume(String intoschool_date_resume) {
            this.intoschool_date_resume = intoschool_date_resume;
        }

        public int getLogin_id() {
            return login_id;
        }

        public void setLogin_id(int login_id) {
            this.login_id = login_id;
        }

        public String getLogin_time() {
            return login_time;
        }

        public void setLogin_time(String login_time) {
            this.login_time = login_time;
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

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getPigeon_count() {
            return pigeon_count;
        }

        public void setPigeon_count(int pigeon_count) {
            this.pigeon_count = pigeon_count;
        }

        public int getRealname() {
            return realname;
        }

        public void setRealname(int realname) {
            this.realname = realname;
        }

        public String getRegedit_time() {
            return regedit_time;
        }

        public void setRegedit_time(String regedit_time) {
            this.regedit_time = regedit_time;
        }

        public String getSchool() {
            return school;
        }

        public void setSchool(String school) {
            this.school = school;
        }

        public int getSex_resume() {
            return sex_resume;
        }

        public void setSex_resume(int sex_resume) {
            this.sex_resume = sex_resume;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public int getUser_status() {
            return user_status;
        }

        public void setUser_status(int user_status) {
            this.user_status = user_status;
        }
    }
}
