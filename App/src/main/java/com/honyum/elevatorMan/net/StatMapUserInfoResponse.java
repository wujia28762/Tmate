package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.data.StatMapDetail;
import com.honyum.elevatorMan.data.StatMapUserInfo;
import com.honyum.elevatorMan.net.base.NewResponse;

import java.util.ArrayList;

/**
 * Created by star on 2018/4/18.
 */

public class StatMapUserInfoResponse extends NewResponse {
    private ArrayList<StatMapUserInfo> body = new ArrayList<StatMapUserInfo>();

    public ArrayList<StatMapUserInfo> getBody() {
        return body;
    }

    public void setBody(ArrayList<StatMapUserInfo> body) {
        this.body = body;
    }
}
