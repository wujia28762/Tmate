package com.honyum.elevatorMan.data;

import java.io.Serializable;

/**
 * Created by LiYouGui on 2017/12/11.
 */

public class ContractElevator implements Serializable{
    public String getPropertyCode() {
        return propertyCode;
    }

    public void setPropertyCode(String propertyCode) {
        this.propertyCode = propertyCode;
    }

    public String getBuildingCode() {
        return buildingCode;
    }

    public void setBuildingCode(String buildingCode) {
        this.buildingCode = buildingCode;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    private String propertyCode;
    private String buildingCode;
    private String unitCode;
    private String id;
    private String contractId; //合同ID
    private String communityId; //项目ID
    private String communityName; //项目名称
    private String elevatorId; //电梯ID
    private String liftNum; //电梯编号
    private String createTime; //创建时间
    private ElevatorInfo1 elevatorInfo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLiftNum() {
        return liftNum;
    }

    public void setLiftNum(String liftNum) {
        this.liftNum = liftNum;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getElevatorId() {
        return elevatorId;
    }

    public void setElevatorId(String elevatorId) {
        this.elevatorId = elevatorId;
    }

    public ElevatorInfo1 getElevatorInfo() {
        return elevatorInfo;
    }

    public void setElevatorInfo(ElevatorInfo1 elevatorInfo) {
        this.elevatorInfo = elevatorInfo;
    }
}
