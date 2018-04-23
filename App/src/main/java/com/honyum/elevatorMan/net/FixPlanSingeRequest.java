package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.net.base.RequestBean;

import java.io.Serializable;

/**
 * Created by star on 2018/3/21.
 */

public class FixPlanSingeRequest extends RequestBean {


    private FixPlanSingeRequestBody body;

    public FixPlanSingeRequestBody getBody() {
        return body;
    }

    public void setBody(FixPlanSingeRequestBody body) {
        this.body = body;
    }

    public  class FixPlanSingeRequestBody implements Serializable
    {
        private String bizType;
        private String bizId;

        public String getBizId() {
            return bizId;
        }

        public void setBizId(String bizId) {
            this.bizId = bizId;
        }

        public String getBizType() {
            return bizType;
        }

        public void setBizType(String bizType) {
            this.bizType = bizType;
        }
    }
}
