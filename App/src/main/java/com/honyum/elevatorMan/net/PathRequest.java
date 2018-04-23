package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.net.base.RequestBean;

/**
 * Created by Star on 2017/12/19.
 */

public class PathRequest extends RequestBean {


    private PathRequest.PathRequestBody body;

    public PathRequest.PathRequestBody getBody() {
        return body;
    }

    public PathRequest setBody(PathRequest.PathRequestBody body) {
        this.body = body;
        return this;
    }

    public class PathRequestBody {


        public String get_process_isLastNode() {
            return _process_isLastNode;
        }

        public void set_process_isLastNode(String _process_isLastNode) {
            this._process_isLastNode = _process_isLastNode;
        }

        public String get_process_task_param() {
            return _process_task_param;
        }

        public void set_process_task_param(String _process_task_param) {
            this._process_task_param = _process_task_param;
        }

        private String _process_isLastNode;
        private String _process_task_param;
        private String branchId;


        public String getBranchId() {
            return branchId;
        }

        public void setBranchId(String branchId) {
            this.branchId = branchId;
        }
    }
}
