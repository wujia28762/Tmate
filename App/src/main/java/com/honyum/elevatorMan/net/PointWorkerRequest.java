package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.net.base.RequestBean;
import com.honyum.elevatorMan.net.base.RequestBody;

/**
 * Created by Star on 2018/1/5.
 */

public class PointWorkerRequest extends RequestBean {

    private  PointWorkerRequestBody body;

    public PointWorkerRequestBody getBody() {
        return body;
    }

    public void setBody(PointWorkerRequestBody body) {
        this.body = body;
    }

    public class PointWorkerRequestBody extends RequestBody
    {
        private String baoxiuId;
        private String taskId;
        private String branchId;
        private String processResult;

        public String getBaoxiuId() {
            return baoxiuId;
        }

        public void setBaoxiuId(String baoxiuId) {
            this.baoxiuId = baoxiuId;
        }

        public String getTaskId() {
            return taskId;
        }

        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }

        public String getBranchId() {
            return branchId;
        }

        public void setBranchId(String branchId) {
            this.branchId = branchId;
        }

        public String getProcessResult() {
            return processResult;
        }

        public void setProcessResult(String processResult) {
            this.processResult = processResult;
        }

        public String getWorkId() {
            return workId;
        }

        public void setWorkId(String workId) {
            this.workId = workId;
        }

        private String workId;
    }
}
