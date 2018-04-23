package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.data.ContartInfo;
import com.honyum.elevatorMan.data.ContractElevator;
import com.honyum.elevatorMan.data.ContractFile;
import com.honyum.elevatorMan.data.ContractPayment;
import com.honyum.elevatorMan.data.IdAndName;
import com.honyum.elevatorMan.data.ProcessStateInfo;
import com.honyum.elevatorMan.data.ResultMapInfo;
import com.honyum.elevatorMan.net.base.Response;
import com.honyum.elevatorMan.net.base.ResponseBody;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Star on 2017/12/21.
 */

public class ProcessStateResponse extends Response {


    private ProcessStateResponse.ProcessStateResponseBody body;

    public void setBody(ProcessStateResponse.ProcessStateResponseBody body) {
        this.body = body;
    }

    public ProcessStateResponse.ProcessStateResponseBody getBody() {
        return body;
    }

    public static class ProcessStateResponseBody extends ResponseBody {

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public List<ProcessStateInfo> getObj() {
            return obj;
        }

        public void setObj(List<ProcessStateInfo> obj) {
            this.obj = obj;
        }

        private String msg;
        private List<ProcessStateInfo> obj;
    }


    /**
     * 根据json生成对象
     * @param json
     * @return
     */
    public static ProcessStateResponse getProcessStateResponse(String json) {
        return (ProcessStateResponse) parseFromJson(ProcessStateResponse.class, json);
    }
}

