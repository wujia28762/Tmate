package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.net.base.RequestBean;

/**
 * Created by Star on 2017/7/11.
 */

public class AddRepairInfoRequest extends RequestBean {


    private RepairInfoBody body;

    public RepairInfoBody getBody() {
        return body;
    }

    public AddRepairInfoRequest setBody(RepairInfoBody body) {
        this.body = body;
        return this;
    }

    public class RepairInfoBody {
        private String elevatorId;
        private String type;
        private String faultCode;
        private String description;
        private String pic;

        public void setType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        public void setElevatorId(String elevatorId) {
            this.elevatorId = elevatorId;
        }

        public void setFaultCode(String faultCode) {
            this.faultCode = faultCode;
        }

        public String getElevatorId() {
            return elevatorId;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getFaultCode() {
            return faultCode;
        }

        public String getPic() {
            return pic;
        }
    }
}
