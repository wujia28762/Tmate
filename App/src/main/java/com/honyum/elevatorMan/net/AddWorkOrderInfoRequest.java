package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.net.base.RequestBean;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Star on 2017/7/11.
 */

public class AddWorkOrderInfoRequest extends RequestBean {


    private AddWorkOrderInfoBody body;

    public AddWorkOrderInfoBody getBody() {
        return body;
    }

    public AddWorkOrderInfoRequest setBody(AddWorkOrderInfoBody body) {
        this.body = body;
        return this;
    }

    public class AddWorkOrderInfoBody {
        /*"bizType":"1",
                "orderName":"工单名称",
                "bizId":"795927da-c224-4ae0-a293-49c560039fdb",
                "communityId":"f4135d24-86ce-4150-8eea-0a071544b121",
                "elevatorId":"f7b43523-093d-4d4c-bfe5-4036d345c90c",
                "branchId":"ffcd9687-c142-42e3-a84b-548bd64df4da",
                "contractId":"3fa7c29d-3fd6-4bf1-a16d-8e0f048f58d9",
                "propertyBranchId":"8776f746-984e-4d4e-b441-eae357073e84",
                "isNeedParts":1,
                "partsNeedDate":"2017-12-03 09:46:44",
                "expectMaintainStartDate":"2017-12-27 09:46:49",
                "expectMaintainEndDate":"2018-01-02 09:46:53",
                "orderContent":"维保/维修内容简述",
                "bizCode":"00001(hm)",
                "createUserName":"测试维修1",
                "createUserTel":"13800138000"*/
        private String bizType;

        private String workOrderId;

        public String getLiftNum() {
            return liftNum;
        }

        public void setLiftNum(String liftNum) {
            this.liftNum = liftNum;
        }

        public String getBaoxiuId() {
            return baoxiuId;
        }

        public void setBaoxiuId(String baoxiuId) {
            this.baoxiuId = baoxiuId;
        }

        private String liftNum;
        private String baoxiuId;


        private String orderName;
        private String bizId;
        private String communityId;
        private String elevatorId;
        private String branchId;
        private String contractId;
        private String propertyBranchId;
        private int isNeedParts;
        private String partsNeedDate;
        private String expectMaintainStartDate;
        private String expectMaintainEndDate;
        private String orderContent;

        public String getAppearance() {
            return appearance;
        }

        public void setAppearance(String appearance) {
            this.appearance = appearance;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getProcessResult() {
            return processResult;
        }

        public void setProcessResult(String processResult) {
            this.processResult = processResult;
        }

        public String getPreventiveMeasure() {
            return preventiveMeasure;
        }

        public void setPreventiveMeasure(String preventiveMeasure) {
            this.preventiveMeasure = preventiveMeasure;
        }

        private String appearance;//现象
        private String reason;//原因
        private String processResult;//处理结果
        private String preventiveMeasure;//预防措施
        private String bizCode;
        private String createUserName;
        private String createUserTel;
        private String assistantId;
        private String workId;

        public void setIsNeedParts(int isNeedParts) {
            this.isNeedParts = isNeedParts;
        }

        public int getIsNeedParts() {
            return isNeedParts;
        }

        public void setCommunityId(String communityId) {
            this.communityId = communityId;
        }

        public String getCommunityId() {
            return communityId;
        }

        public void setElevatorId(String elevatorId) {
            this.elevatorId = elevatorId;
        }

        public String getElevatorId() {
            return elevatorId;
        }

        public void setBranchId(String branchId) {
            this.branchId = branchId;
        }

        public String getBranchId() {
            return branchId;
        }

        public void setOrderName(String orderName) {
            this.orderName = orderName;
        }

        public String getOrderName() {
            return orderName;
        }

        public void setBizType(String bizType) {
            this.bizType = bizType;
        }

        public String getBizType() {
            return bizType;
        }

        public void setContractId(String contractId) {
            this.contractId = contractId;
        }

        public String getContractId() {
            return contractId;
        }

        public String getBizCode() {
            return bizCode;
        }

        public String getBizId() {
            return bizId;
        }

        public void setBizCode(String bizCode) {
            this.bizCode = bizCode;
        }

        public void setBizId(String bizId) {
            this.bizId = bizId;
        }

        public void setCreateUserName(String createUserName) {
            this.createUserName = createUserName;
        }

        public void setCreateUserTel(String createUserTel) {
            this.createUserTel = createUserTel;
        }

        public void setExpectMaintainEndDate(String expectMaintainEndDate) {
            this.expectMaintainEndDate = expectMaintainEndDate;
        }

        public void setExpectMaintainStartDate(String expectMaintainStartDate) {
            this.expectMaintainStartDate = expectMaintainStartDate;
        }



        public void setPartsNeedDate(String partsNeedDate) {
            this.partsNeedDate = partsNeedDate;
        }

        public void setPropertyBranchId(String propertyBranchId) {
            this.propertyBranchId = propertyBranchId;
        }

        public String getCreateUserName() {
            return createUserName;
        }

        public String getCreateUserTel() {
            return createUserTel;
        }

        public String getExpectMaintainEndDate() {
            return expectMaintainEndDate;
        }

        public String getExpectMaintainStartDate() {
            return expectMaintainStartDate;
        }



        public String getPartsNeedDate() {
            return partsNeedDate;
        }

        public String getPropertyBranchId() {
            return propertyBranchId;
        }

        public String getAssistantId() {
            return assistantId;
        }

        public void setAssistantId(String assistantId) {
            this.assistantId = assistantId;
        }

        public String getWorkId() {
            return workId;
        }

        public void setWorkId(String workId) {
            this.workId = workId;
        }

        public String getOrderContent() {
            return orderContent;
        }

        public void setOrderContent(String orderContent) {
            this.orderContent = orderContent;
        }

        public String getWorkOrderId() {
            return workOrderId;
        }

        public void setWorkOrderId(String workOrderId) {
            this.workOrderId = workOrderId;
        }
    }
}
