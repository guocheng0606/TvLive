package com.android.guocheng.tvlive.model.main;

import java.io.Serializable;
import java.util.List;


public class Source implements Serializable{

    private Long position;
    private String name;
    private String remark;
    private List<List<String>> url;

    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<List<String>> getUrl() {
        return url;
    }

    public void setUrl(List<List<String>> url) {
        this.url = url;
    }
}
