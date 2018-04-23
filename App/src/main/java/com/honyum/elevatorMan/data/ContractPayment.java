package com.honyum.elevatorMan.data;

import java.io.Serializable;

/**
 * Created by LiYouGui on 2017/12/11.
 */

public class ContractPayment implements Serializable{
    private String id;
    private String contractId; //合同ID
    private String type; //结算类型
    private String paymentTime; //结算日期
    private Double paymentMoney; //结算金额
    private String percentage; //百分比
    private String description; //备注
    private String createTime; //创建时间
    private String userId; //收/付款人ID
    private String userName; //收/付款人姓名
    private String userTel; //收/付款人电话

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPaymentMoney() {
        return paymentMoney;
    }

    public void setPaymentMoney(Double paymentMoney) {
        this.paymentMoney = paymentMoney;
    }

    public String getDescription() {
        return description;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    public String getUserTel() {
        return userTel;
    }
}
