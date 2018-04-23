package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.data.StatMapDetail;
import com.honyum.elevatorMan.data.StatResult;
import com.honyum.elevatorMan.net.base.NewResponse;

import java.util.ArrayList;

/**
 * Created by star on 2018/4/18.
 */

public class StatMapInfoResponse extends NewResponse {
    private ArrayList<StatMapDetail> body = new ArrayList<StatMapDetail>();

    public ArrayList<StatMapDetail> getBody() {
        return body;
    }

    public void setBody(ArrayList<StatMapDetail> body) {
        this.body = body;
    }
}
