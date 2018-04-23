package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.data.MaintenanceInfo;
import com.honyum.elevatorMan.data.RepairInfo;
import com.honyum.elevatorMan.net.base.NewResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by star on 2018/3/21.
 */

public class FixPlanSingleResponse extends NewResponse {
    private Map<String,RepairInfo> body = new HashMap<>();

    public Map<String, RepairInfo> getBody() {
        return body;
    }

    public void setBody(Map<String, RepairInfo> body) {
        this.body = body;
    }
}
