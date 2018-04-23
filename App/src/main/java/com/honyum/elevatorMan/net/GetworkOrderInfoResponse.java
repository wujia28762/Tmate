package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.data.WorkOrderInfo;
import com.honyum.elevatorMan.net.base.NewResponse;
import com.honyum.elevatorMan.net.base.Response;

import java.util.List;

/**
 * Created by star on 2018/3/13.
 */

public class GetworkOrderInfoResponse extends NewResponse {

    private WorkOrderInfo body;

    public WorkOrderInfo getBody() {
        return body;
    }

    public void setBody(WorkOrderInfo body) {
        this.body = body;
    }
}
