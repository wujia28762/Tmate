package com.honyum.elevatorMan.data;

import java.io.Serializable;

/**
 * Created by star on 2018/4/12.
 */

public class UndealLiftPlanInfo implements Serializable {

    private String code;
    private String id;
    private String communityName;
    private String createTime;
    private boolean checked = false;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
