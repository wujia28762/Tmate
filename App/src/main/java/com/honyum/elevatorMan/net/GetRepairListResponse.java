package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.data.MaintRecInfo;
import com.honyum.elevatorMan.data.RepairInfo;
import com.honyum.elevatorMan.net.base.Response;

import java.util.List;
import java.util.Map;

/**
 * Created by Star on 2017/6/15.
 */

public class GetRepairListResponse extends Response {


    public Map<String, List<RepairInfo>> getBody() {
        return body;
    }

    public void setBody(Map<String, List<RepairInfo>> body) {
        this.body = body;
    }

    private Map<String, List<RepairInfo>> body;

    /**
     * 根据json生成对象
     *
     * @param json
     * @return
     */
    public static GetRepairListResponse getRepairListResponse(String json) {
        return (GetRepairListResponse) parseFromJson(GetRepairListResponse.class, json);
    }
}
