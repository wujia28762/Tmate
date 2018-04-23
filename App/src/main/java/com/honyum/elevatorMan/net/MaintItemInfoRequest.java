package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.net.base.RequestBean;

/**
 * Created by Star on 2017/7/11.
 */

public class MaintItemInfoRequest extends RequestBean {


    private MaintItemInfoInfoBody body;

    public MaintItemInfoInfoBody getBody() {
        return body;
    }

    public MaintItemInfoRequest setBody(MaintItemInfoInfoBody body) {
        this.body = body;
        return this;
    }

    public class MaintItemInfoInfoBody {
        private String workOrderId;

        public void setWorkOrderId(String workOrderId) {
            this.workOrderId = workOrderId;
        }

        public String getWorkOrderId() {
            return workOrderId;
        }
    }
}
