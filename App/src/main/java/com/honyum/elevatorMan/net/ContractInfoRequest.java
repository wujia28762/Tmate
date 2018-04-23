package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.net.base.RequestBean;

/**
 * Created by Star on 2017/7/11.
 */

public class ContractInfoRequest extends RequestBean {


    private ContractInfoBody body;

    public ContractInfoBody getBody() {
        return body;
    }

    public ContractInfoRequest setBody(ContractInfoBody body) {
        this.body = body;
        return this;
    }

    public class ContractInfoBody {

        private int page;
        private int rows;
        private String statusCodes;
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
        private String pic;
        private String id;
        private String mainTime;
        private String faultCode;
        private String roleId;
        private String code;
        private String userName;
        private String branchName;
        private String state;

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

        private String result;

        private String orderNum = "";
        private String orderName = "";
        private String bizType = "";

        private String statusCode = "";

        private String branchId;
        private String communityId;
        private String elevatorId;

        private String workOrderId;

        private String orderId;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMainTime() {
            return mainTime;
        }

        public void setMainTime(String mainTime) {
            this.mainTime = mainTime;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public void setWorkOrderId(String workOrderId) {
            this.workOrderId = workOrderId;
        }

        public String getWorkOrderId() {
            return workOrderId;
        }

        public void setBranchId(String branchId) {
            this.branchId = branchId;
        }

        public String getBranchId() {
            return branchId;
        }

        public void setElevatorId(String elevatorId) {
            this.elevatorId = elevatorId;
        }

        public String getElevatorId() {
            return elevatorId;
        }

        public void setCommunityId(String communityId) {
            this.communityId = communityId;
        }

        public String getCommunityId() {
            return communityId;
        }

        public void setStatusCode(String statusCode) {
            this.statusCode = statusCode;
        }

        public String getStatusCode() {
            return statusCode;
        }

        public void setBizType(String bizType) {
            this.bizType = bizType;
        }

        public String getBizType() {
            return bizType;
        }

        public void setOrderName(String orderName) {
            this.orderName = orderName;
        }

        public String getOrderName() {
            return orderName;
        }

        public void setOrderNum(String orderNum) {
            this.orderNum = orderNum;
        }

        public String getOrderNum() {
            return orderNum;
        }

        public void setBranchName(String branchName) {
            this.branchName = branchName;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public void setRoleId(String roleId) {
            this.roleId = roleId;
        }

        public void setState(String state) {
            this.state = state;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getCode() {
            return code;
        }

        public String getBranchName() {
            return branchName;
        }

        public String getRoleId() {
            return roleId;
        }

        public String getState() {
            return state;
        }

        public String getUserName() {
            return userName;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getFaultCode() {
            return faultCode;
        }

        public void setFaultCode(String faultCode) {
            this.faultCode = faultCode;
        }

        public String getStatusCodes() {
            return statusCodes;
        }

        public void setStatusCodes(String statusCodes) {
            this.statusCodes = statusCodes;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getRows() {
            return rows;
        }

        public void setRows(int row) {
            this.rows = row;
        }
    }
}
