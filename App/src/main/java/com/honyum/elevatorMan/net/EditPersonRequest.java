package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.net.base.RequestBean;

import java.io.Serializable;

/**
 * Created by star on 2018/4/9.
 */

public class EditPersonRequest extends RequestBean {
    private EditPersonRequestBody body;

    public EditPersonRequestBody getBody() {
        return body;
    }

    public void setBody(EditPersonRequestBody body) {
        this.body = body;
    }

    public class EditPersonRequestBody implements Serializable
    {
        private String reDistributeType;
        private String branchId;
        private String orderId;
        private String assistantId;
        private String assistantName;
        private String workId;
        private String workName;

        public String getBranchId() {
            return branchId;
        }

        public void setBranchId(String branchId) {
            this.branchId = branchId;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getAssistantId() {
            return assistantId;
        }

        public void setAssistantId(String assistantId) {
            this.assistantId = assistantId;
        }

        public String getAssistantName() {
            return assistantName;
        }

        public void setAssistantName(String assistantName) {
            this.assistantName = assistantName;
        }

        public String getWorkId() {
            return workId;
        }

        public void setWorkId(String workId) {
            this.workId = workId;
        }

        public String getWorkName() {
            return workName;
        }

        public void setWorkName(String workName) {
            this.workName = workName;
        }

        public String getReDistributeType() {
            return reDistributeType;
        }

        public void setReDistributeType(String reDistributeType) {
            this.reDistributeType = reDistributeType;
        }
    }
}
