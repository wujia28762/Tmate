package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.data.MaintenanceContenInfo;
import com.honyum.elevatorMan.net.base.RequestBean;

import java.util.ArrayList;

/**
 * Created by Star on 2017/7/11.
 */

public class SuccessMaintWorkOrderInfoRequest extends RequestBean {


    private MaintWorkOrderInfoBody body;

    public MaintWorkOrderInfoBody getBody() {
        return body;
    }

    public SuccessMaintWorkOrderInfoRequest setBody(MaintWorkOrderInfoBody body) {
        this.body = body;
        return this;
    }

    public class MaintWorkOrderInfoBody {
        private String workOrderId;
        private ArrayList<MaintenanceContenInfo> items;
        private String processResult;
        public void setItems(ArrayList<MaintenanceContenInfo> items) {
            this.items = items;
        }
        public ArrayList<MaintenanceContenInfo> getItems() {
            return items;
        }
        public void setWorkOrderId(String workOrderId) {
            this.workOrderId = workOrderId;
        }
        public String getWorkOrderId() {
            return workOrderId;
        }

        public String getProcessResult() {
            return processResult;
        }

        public void setProcessResult(String processResult) {
            this.processResult = processResult;
        }
    }

}
