package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.data.StatResult;
import com.honyum.elevatorMan.net.base.NewResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by star on 2018/4/18.
 */

public class StatInfoResponse extends NewResponse {
    private ArrayList<StatResult> body = new ArrayList<StatResult>();

    public ArrayList<StatResult> getBody() {
        return body;
    }

    public void setBody(ArrayList<StatResult> body) {
        this.body = body;
    }
}
