package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.net.base.RequestBean;

import java.io.Serializable;

/**
 * Created by star on 2018/4/21.
 */

public class ModifyOrderTimeRequest extends RequestBean {

    private ModifyOrderTimeRequestBody Body;

    public ModifyOrderTimeRequestBody getBody() {
        return Body;
    }

    public void setBody(ModifyOrderTimeRequestBody body) {
        Body = body;
    }

    public static class ModifyOrderTimeRequestBody implements Serializable
    {
    private String updateType;
    private String orderId;
    private String expectMaintainStartDate;

    public String getUpdateType() {
        return updateType;
    }

    public void setUpdateType(String updateType) {
        this.updateType = updateType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getExpectMaintainStartDate() {
        return expectMaintainStartDate;
    }

    public void setExpectMaintainStartDate(String expectMaintainStartDate) {
        this.expectMaintainStartDate = expectMaintainStartDate;
    }

    public String getExpectMaintainEndDate() {
        return expectMaintainEndDate;
    }

    public void setExpectMaintainEndDate(String expectMaintainEndDate) {
        this.expectMaintainEndDate = expectMaintainEndDate;
    }

    private String expectMaintainEndDate;
    }
}
