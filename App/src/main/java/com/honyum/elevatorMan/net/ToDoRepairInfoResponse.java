package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.data.RepairInfo;
import com.honyum.elevatorMan.net.base.NewResponse;

/**
 * Created by Star on 2018/1/5.
 */

public class ToDoRepairInfoResponse extends NewResponse {

    private RepairInfo body;

    public RepairInfo getBody() {
        return body;
    }

    public void setBody(RepairInfo body) {
        this.body = body;
    }
}
