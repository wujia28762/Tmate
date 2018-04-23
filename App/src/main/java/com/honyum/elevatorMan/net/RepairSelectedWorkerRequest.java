package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.net.base.RequestBean;
import com.honyum.elevatorMan.net.base.RequestBody;

/**
 * Created by Star on 2018/1/5.
 */

public class RepairSelectedWorkerRequest extends RequestBean {
    private RepairSelectedWorkerBody body;

    public RepairSelectedWorkerBody getBody() {
        return body;
    }

    public void setBody(RepairSelectedWorkerBody body) {
        this.body = body;
    }


    public class RepairSelectedWorkerBody extends RequestBody
    {
        private String branchId;
        private String name;
        private String _process_task_param;

        public String getBranchId() {
            return branchId;
        }

        public void setBranchId(String branchId) {
            this.branchId = branchId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBaoxiuId() {
            return baoxiuId;
        }

        public void setBaoxiuId(String baoxiuId) {
            this.baoxiuId = baoxiuId;
        }

        private String baoxiuId;

        public String get_process_task_param() {
            return _process_task_param;
        }

        public void set_process_task_param(String _process_task_param) {
            this._process_task_param = _process_task_param;
        }
    }
}
