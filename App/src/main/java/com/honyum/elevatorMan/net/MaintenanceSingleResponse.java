package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.data.MaintenanceInfo;
import com.honyum.elevatorMan.net.base.NewResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by star on 2018/3/21.
 */

public class MaintenanceSingleResponse extends NewResponse {
    private Map<String,MaintenanceInfo> body = new HashMap<>();

    public Map<String, MaintenanceInfo> getBody() {
        return body;
    }

    public void setBody(Map<String, MaintenanceInfo> body) {
        this.body = body;
    }
}
