package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.net.base.RequestBean;

import java.io.Serializable;

/**
 * Created by Star on 2018/1/10.
 */

public class LiftInfoRequest extends RequestBean {
    private RequestLiftInfoBody body;

    public RequestLiftInfoBody getBody() {
        return body;
    }

    public void setBody(RequestLiftInfoBody body) {
        this.body = body;
    }

    public class RequestLiftInfoBody implements Serializable
    {
        private String id;

        public RequestLiftInfoBody(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
