package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.data.MaintenanceInfo;
import com.honyum.elevatorMan.data.ResultMapInfo;
import com.honyum.elevatorMan.data.ToDoMaintInfo;
import com.honyum.elevatorMan.net.base.NewResponse;
import com.honyum.elevatorMan.net.base.Response;

import java.util.List;
import java.util.Map;

/**
 * Created by Star on 2018/1/10.
 */

public class MaintenancePlanResponse extends NewResponse {
    private MaintenancePlanResponseBody body;

    public MaintenancePlanResponseBody getBody() {
        return body;
    }

    public void setBody(MaintenancePlanResponseBody body) {
        this.body = body;
    }

    public class MaintenancePlanResponseBody{
         private ResultMapInfo _process_resultMap;
         private MaintenanceInfo maintenance;

         private List<ToDoMaintInfo> list;

         public MaintenanceInfo getMaintenance() {
             return maintenance;
         }

         public void setMaintenance(MaintenanceInfo maintenance) {
             this.maintenance = maintenance;
         }

         public ResultMapInfo get_process_resultMap() {
             return _process_resultMap;
         }

         public void set_process_resultMap(ResultMapInfo _process_resultMap) {
             this._process_resultMap = _process_resultMap;
         }

        public List<ToDoMaintInfo> getList() {
            return list;
        }

        public void setList(List<ToDoMaintInfo> list) {
            this.list = list;
        }
    }
}
