package com.honyum.elevatorMan.data;

import java.io.Serializable;

/**
 * Created by star on 2018/4/13.
 */

public class UndealEleInfo implements Serializable {

    private String propertyCode = "";
    private String communityName = "";

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getPropertyCode() {
        return propertyCode;
    }

    public void setPropertyCode(String propertyCode) {
        this.propertyCode = propertyCode;
    }
}
