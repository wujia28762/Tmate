package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.data.PersonInfo;
import com.honyum.elevatorMan.data.WorkOrderInfo;
import com.honyum.elevatorMan.net.base.NewResponse;
import com.honyum.elevatorMan.utils.UploadImageManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by star on 2018/3/23.
 */

public class UpdateWorkOrderInfoResponse extends NewResponse {

    private UpdateWorkOrderInfoResponseBody body;

    public UpdateWorkOrderInfoResponseBody getBody() {
        return body;
    }

    public void setBody(UpdateWorkOrderInfoResponseBody body) {
        this.body = body;
    }

    public class UpdateWorkOrderInfoResponseBody implements Serializable
    {

        private WorkOrderInfo orderInfo = new WorkOrderInfo();
        private List<PersonInfo> persons = new ArrayList<>();

        public WorkOrderInfo getOrderInfo() {
            return orderInfo;
        }

        public void setOrderInfo(WorkOrderInfo orderInfo) {
            this.orderInfo = orderInfo;
        }

        public List<PersonInfo> getPersons() {
            return persons;
        }

        public void setPersons(List<PersonInfo> persons) {
            this.persons = persons;
        }
    }
}
