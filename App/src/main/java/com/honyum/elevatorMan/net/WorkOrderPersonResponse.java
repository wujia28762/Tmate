package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.data.PersonInfo;
import com.honyum.elevatorMan.net.base.NewResponse;
import com.honyum.elevatorMan.net.base.Response;

import java.util.List;

public class WorkOrderPersonResponse extends NewResponse {

    private List<PersonInfo> body;

    public List<PersonInfo> getBody() {
        return body;
    }

    public void setBody(List<PersonInfo> body) {
        this.body = body;
    }
}
