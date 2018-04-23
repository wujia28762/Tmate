package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.net.base.RequestBean;

import java.io.Serializable;

/**
 * Created by star on 2018/3/14.
 */

public class getMaintResultRequest extends RequestBean {

    private getMaintResultBody body;

    public getMaintResultBody getBody() {
        return body;
    }

    public void setBody(getMaintResultBody body) {
        this.body = body;
    }

    public class getMaintResultBody implements Serializable
    {
        private String workOrderId;

        public String getWorkOrderId() {
            return workOrderId;
        }

        public void setWorkOrderId(String workOrderId) {
            this.workOrderId = workOrderId;
        }
    }
}
