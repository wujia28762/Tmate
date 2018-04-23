package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.data.ProcessInfo;
import com.honyum.elevatorMan.net.base.Response;

import java.util.List;

/**
 * Created by Star on 2017/12/21.
 */

public class ProcessInfoResponse extends Response {


    private List<ProcessInfo> body;
    /**
     * 根据json生成对象
     * @param json
     * @return
     */
    public static ProcessInfoResponse getProcessInfoResponse(String json) {
        return (ProcessInfoResponse) parseFromJson(ProcessInfoResponse.class, json);
    }

    public List<ProcessInfo> getBody() {
        return body;
    }

    public void setBody(List<ProcessInfo> body) {
        this.body = body;
    }
}
