package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.data.CommunityCountInfo;
import com.honyum.elevatorMan.net.base.NewResponse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by star on 2018/4/11.
 */

public class ProjectCountInfoResponse extends NewResponse {

    private List<CommunityCountInfo> body;

    public List<CommunityCountInfo> getBody() {
        return body;
    }

    public void setBody(List<CommunityCountInfo> body) {
        this.body = body;
    }
}
