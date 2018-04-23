package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.data.UndealLiftPlanInfo;
import com.honyum.elevatorMan.net.base.NewResponse;

import java.util.List;

/**
 * Created by star on 2018/4/12.
 */

public class UndealLiftPlanResponse extends NewResponse {

    private List<UndealLiftPlanInfo> body;

    public List<UndealLiftPlanInfo> getBody() {
        return body;
    }

    public void setBody(List<UndealLiftPlanInfo> body) {
        this.body = body;
    }
}
