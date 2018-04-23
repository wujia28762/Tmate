package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.net.base.RequestBean;

/**
 * Created by Star on 2017/7/11.
 */

public class SaveRequestRemarkRequest extends RequestBean {

    private SaveProcessBody body;

    public SaveProcessBody getBody() {
        return body;
    }

    public SaveRequestRemarkRequest setBody(SaveProcessBody body) {
        this.body = body;
        return this;
    }

    public class SaveProcessBody {

        private String baoxiuId;
        private String state;
        private String branchId;
        private String remark;
        private String result;
        private String errorElevatorId;

        public String getErrorElevatorId() {
            return errorElevatorId;
        }

        public void setErrorElevatorId(String errorElevatorId) {
            this.errorElevatorId = errorElevatorId;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getState() {
            return state;
        }

        public void setBaoxiuId(String baoxiuId) {
            this.baoxiuId = baoxiuId;
        }

        public String getBaoxiuId() {
            return baoxiuId;
        }

        public void setBranchId(String branchId) {
            this.branchId = branchId;
        }

        public String getBranchId() {
            return branchId;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getRemark() {
            return remark;
        }
    }
}
