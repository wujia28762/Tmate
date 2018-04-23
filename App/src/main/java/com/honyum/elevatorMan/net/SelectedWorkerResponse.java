package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.data.ContractInfo;
import com.honyum.elevatorMan.net.base.NewResponse;
import com.honyum.elevatorMan.net.base.Response;

import java.util.List;

/**
 * Created by Star on 2018/1/5.
 */

public class SelectedWorkerResponse extends NewResponse{

    private List<ContractInfo> body;

    public List<ContractInfo> getBody() {
        return body;
    }
    public void setBody(List<ContractInfo> body) {
        this.body = body;
    }
}
