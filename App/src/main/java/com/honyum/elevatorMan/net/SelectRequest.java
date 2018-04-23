package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.net.base.RequestBean;

public class SelectRequest extends RequestBean {

    private SelectRequestBody body;

    public SelectRequestBody getBody() {
        return body;
    }

    public void setBody(SelectRequestBody body) {
        this.body = body;
    }

    public class SelectRequestBody
    {
        private String elevatorId;
        private String exceptUserId;

        public String getExceptUserId() {
            return exceptUserId;
        }

        public void setExceptUserId(String exceptUserId) {
            this.exceptUserId = exceptUserId;
        }

        public String getElevatorId() {
            return elevatorId;
        }

        public void setElevatorId(String elevatorId) {
            this.elevatorId = elevatorId;
        }
    }
}
