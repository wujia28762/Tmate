package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.net.base.RequestBean;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Star on 2017/12/19.
 */

public class AddContractRequest extends RequestBean {


    private AddContractRequest.AddContractRequestBody body;

    public AddContractRequest.AddContractRequestBody getBody() {
        return body;
    }

    public AddContractRequest setBody(AddContractRequest.AddContractRequestBody body) {
        this.body = body;
        return this;
    }

    public class AddContractRequestBody {
        private String branchId;
        private String name;
        private String tel;
        private int page;
        private int pageSize;
        @NotNull
        public String _process_approve_opinion;
        @NotNull
        public String _process_path;
        @NotNull
        public String contractId;
        @NotNull
        public String processSource;
        @NotNull
        public String _processSelectUserId;
        public String id;

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


        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

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

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }





    }
}
