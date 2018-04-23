package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.net.base.NewResponse;
import com.honyum.elevatorMan.net.base.Response;

/**
 * Created by star on 2018/3/13.
 */

public class RepairOrderResponse extends NewResponse{

    private String body;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
