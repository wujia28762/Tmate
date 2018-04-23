package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.net.base.RequestBean;

/**
 * Created by Star on 2017/12/22.
 */

public class UploadProcessRequest  extends RequestBean {


    private UploadProcessRequest.UploadProcessRequestBody body;

    public UploadProcessRequest.UploadProcessRequestBody getBody() {
        return body;
    }

    public UploadProcessRequest setBody(UploadProcessRequest.UploadProcessRequestBody body) {
        this.body = body;
        return this;
    }

    public class UploadProcessRequestBody {


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
        private String id;
        private String contractId;

        public String get_process_approve_opinion() {
            return _process_approve_opinion;
        }

        public void set_process_approve_opinion(String _process_approve_opinion) {
            this._process_approve_opinion = _process_approve_opinion;
        }

        public String get_process_path() {
            return _process_path;
        }

        public void set_process_path(String _process_path) {
            this._process_path = _process_path;
        }

        public String get_process_approve_source() {
            return _process_approve_source;
        }

        public void set_process_approve_source(String _process_approve_source) {
            this._process_approve_source = _process_approve_source;
        }


        private String _process_approve_opinion;
        private String _process_path;
        private String _process_approve_source;
        private String _processSelectUserId;
        private String  processSource;




        public String getBranchId() {
            return branchId;
        }

        public void setBranchId(String branchId) {
            this.branchId = branchId;
        }

        public String getContractId() {
            return contractId;
        }

        public void setContractId(String contractId) {
            this.contractId = contractId;
        }

        public String getProcessSource() {
            return processSource;
        }

        public void setProcessSource(String processSource) {
            this.processSource = processSource;
        }

        public String get_processSelectUserId() {
            return _processSelectUserId;
        }

        public void set_processSelectUserId(String _processSelectUserId) {
            this._processSelectUserId = _processSelectUserId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}