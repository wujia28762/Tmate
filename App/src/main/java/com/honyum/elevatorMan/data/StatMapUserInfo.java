package com.honyum.elevatorMan.data;

import java.io.Serializable;

/**
 * Created by star on 2018/4/20.
 */

public class StatMapUserInfo implements Serializable {

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSignTime() {
        return SignTime;
    }

    public void setSignTime(String signTime) {
        SignTime = signTime;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    private  String userId;
    private  String UserName;
    private  String state;
    private  String SignTime;
    private  double lat;
    private  double lng;
}
