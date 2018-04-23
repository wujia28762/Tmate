package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.data.UndealPlanInfo;
import com.honyum.elevatorMan.net.base.NewResponse;

import java.util.List;
import java.util.Map;

/**
 * Created by star on 2018/4/13.
 */

public class UndealDealInfoResponse extends NewResponse {

    private Map<String,List<UndealPlanInfo>> body;

    public Map<String, List<UndealPlanInfo>> getBody() {
        return body;
    }

    public void setBody(Map<String, List<UndealPlanInfo>> body) {
        this.body = body;
    }
}
