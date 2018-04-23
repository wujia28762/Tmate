package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.net.base.RequestBean;

/**
 * Created by Star on 2017/7/11.
 */

public class AddAttendanceInfoRequest extends RequestBean {

    private SignInfo body;

    public SignInfo getBody() {
        return body;
    }

    public AddAttendanceInfoRequest setBody(SignInfo body) {
        this.body = body;
        return this;
    }

    public class SignInfo {
        private String id;
        private String userId;
        private String userName;
        private String branchId;
        private String branchName;
        private String signTime;
        private String state;
        private String startTime;
        private String endTime;
        private String reason;
        private String type;
        private Double lng;
        private Double lat;
        private String orderId;
        private String orderNum;
        private String orderName;

        private String elevatorId;

        public String getElevatorId() {
            return elevatorId;
        }

        public void setElevatorId(String elevatorId) {
            this.elevatorId = elevatorId;
        }

        public void setBranchId(String branchId) {
            this.branchId = branchId;
        }

        public Double getLat() {
            return lat;
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

        public String getBranchId() {
            return branchId;
        }

        public String getBranchName() {
            return branchName;
        }

        public void setBranchName(String branchName) {
            this.branchName = branchName;
        }

        public String getSignTime() {
            return signTime;
        }

        public void setSignTime(String signTime) {
            this.signTime = signTime;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Double getLng() {
            return lng;
        }

        public void setLng(Double lng) {
            this.lng = lng;
        }

        public void setLat(Double lat) {
            this.lat = lat;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(String orderNum) {
            this.orderNum = orderNum;
        }

        public String getOrderName() {
            return orderName;
        }

        public void setOrderName(String orderName) {
            this.orderName = orderName;
        }
    }
}
