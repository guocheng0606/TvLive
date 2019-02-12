package com.android.guocheng.tvlive.model.custom;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class CustomSource {

    @Id(autoincrement = true)
    private Long id;
    private String name;
    private String remark;
    private String url;
    @Generated(hash = 1831882248)
    public CustomSource(Long id, String name, String remark, String url) {
        this.id = id;
        this.name = name;
        this.remark = remark;
        this.url = url;
    }
    @Generated(hash = 1329150935)
    public CustomSource() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getRemark() {
        return this.remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

}
