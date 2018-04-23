package com.honyum.elevatorMan.data;

/**
 * Created by star on 2018/3/14.
 */

public class CacheMaint {

    private String branchId;
    private String code;
    private String elevatorId;
    private String mainType;
    private String planTime;
    private String propertyFlg;
    private String statusCode;
    private String workerId;
    private boolean wrongFlg;

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getElevatorId() {
        return elevatorId;
    }

    public void setElevatorId(String elevatorId) {
        this.elevatorId = elevatorId;
    }

    public String getMainType() {
        return mainType;
    }

    public void setMainType(String mainType) {
        this.mainType = mainType;
    }

    public String getPlanTime() {
        return planTime;
    }

    public void setPlanTime(String planTime) {
        this.planTime = planTime;
    }

    public String getPropertyFlg() {
        return propertyFlg;
    }

    public void setPropertyFlg(String propertyFlg) {
        this.propertyFlg = propertyFlg;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    public boolean isWrongFlg() {
        return wrongFlg;
    }

    public void setWrongFlg(boolean wrongFlg) {
        this.wrongFlg = wrongFlg;
    }
}
