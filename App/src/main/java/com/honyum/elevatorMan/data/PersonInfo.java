package com.honyum.elevatorMan.data;

import java.io.Serializable;

public class PersonInfo implements Serializable
{


    /**
     * createTime : 2017-10-27 11:35:58
     * id : 4027635d-9ba2-4fb9-9d80-1d69b2ef4a7a
     * userId : 1c49f397-040b-4a0e-a579-706845595b1d
     * userName : 测试维修2
     * userTel : 18513831372
     */

    private String createTime;
    private String id;
    private String userId;
    private String userName;
    private String userTel;
    private Boolean selected = false;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}
