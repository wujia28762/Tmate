package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.net.base.RequestBean;

import java.io.Serializable;

/**
 * Created by star on 2018/3/13.
 */

public class GetWorkOrderRequest extends RequestBean {

    private GetWorkOrderRequestBody body;

    public GetWorkOrderRequestBody getBody() {
        return body;
    }

    public void setBody(GetWorkOrderRequestBody body) {
        this.body = body;
    }

    public class GetWorkOrderRequestBody implements Serializable
    {
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
