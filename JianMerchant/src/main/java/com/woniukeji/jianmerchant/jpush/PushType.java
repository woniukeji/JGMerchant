package com.woniukeji.jianmerchant.jpush;

/**
 * Created by Administrator on 2016/6/28.
 */

public class PushType {
    private String type;
    /**
     * html_url : www.baidu.com
     */

    private String html_url;

    private String jobid;

    public String getJobid() {
        return jobid;
    }

    public void setJobid(String jobid) {
        this.jobid = jobid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }
}
