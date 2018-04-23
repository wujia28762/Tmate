package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.net.base.RequestBean;

public class MoneyRecieveRequest extends RequestBean {

    private MoneyRecieveRequestBody body;

    public MoneyRecieveRequestBody getBody() {
        return body;
    }

    public void setBody(MoneyRecieveRequestBody body) {
        this.body = body;
    }

    public class MoneyRecieveRequestBody
    {
        private String Id;

        public String getId() {
            return Id;
        }

        public MoneyRecieveRequestBody setId(String id) {
            Id = id;
            return this;
        }
    }
}
