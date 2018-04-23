package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.net.base.RequestBean;

import java.io.Serializable;


public class UnDealRequest extends RequestBean {
    private UnDealRequestBody body;

    public UnDealRequestBody getBody() {
        return body;
    }

    public void setBody(UnDealRequestBody body) {
        this.body = body;
    }

    public class UnDealRequestBody implements Serializable
    {
        private String branchId;

        public String getBranchId() {
            return branchId;
        }

        public void setBranchId(String branchId) {
            this.branchId = branchId;
        }
    }
}
