package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.net.base.RequestBean;

import java.io.Serializable;

/**
 * Created by star on 2018/4/13.
 */

public class DraftMaintenanceDetailRequest extends RequestBean {
    private DraftMaintenanceDetailRequestBody body;

    public DraftMaintenanceDetailRequestBody getBody() {
        return body;
    }

    public void setBody(DraftMaintenanceDetailRequestBody body) {
        this.body = body;
    }

    public class DraftMaintenanceDetailRequestBody implements Serializable
    {
        private String dealId;

        public String getDealId() {
            return dealId;
        }

        public void setDealId(String dealId) {
            this.dealId = dealId;
        }
    }
}
