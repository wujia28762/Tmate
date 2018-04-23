package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.net.base.NewResponse;
import com.honyum.elevatorMan.net.base.RequestBean;

import java.util.Map;


public class UnDealResponse extends NewResponse {

//    private UnDetailRequestBody body;
//
//    public UnDetailRequestBody getBody() {
//        return body;
//    }
//
//    public void setBody(UnDetailRequestBody body) {
//        this.body = body;
//    }
//
//    public class UnDetailRequestBody implements Serializable
//    {
//        Map<String,boolean>
//    }
    private Map<String,Integer> body;

    public Map<String, Integer> getBody() {
        return body;
    }

    public void setBody(Map<String, Integer> body) {
        this.body = body;
    }
}
