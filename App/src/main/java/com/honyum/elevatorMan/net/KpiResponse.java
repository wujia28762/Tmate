package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.data.KpiData;
import com.honyum.elevatorMan.net.base.NewResponse;

/**
 * Created by star on 2018/4/18.
 */

public class KpiResponse extends NewResponse {

    private KpiData body;

    public KpiData getBody() {
        return body;
    }

    public void setBody(KpiData body) {
        this.body = body;
    }
}
