package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.net.base.RequestBean;

/**
 * Created by Star on 2017/7/11.
 */

public class ContractInfoDetailRequest extends RequestBean {


    private ContractInfoBody body;

    public ContractInfoBody getBody() {
        return body;
    }

    public ContractInfoDetailRequest setBody(ContractInfoBody body) {
        this.body = body;
        return this;
    }

    public class ContractInfoBody {
        private String branchId;
        private String roleId;
        private String code;
        private String userName;
        private String branchName;

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

        private String state;
        private String id;
        private String _process_isLastNode;
        private String _process_task_param;




        public void setBranchName(String branchName) {
            this.branchName = branchName;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public void setRoleId(String roleId) {
            this.roleId = roleId;
        }

        public void setState(String state) {
            this.state = state;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getCode() {
            return code;
        }

        public String getBranchName() {
            return branchName;
        }

        public String getRoleId() {
            return roleId;
        }

        public String getState() {
            return state;
        }

        public String getUserName() {
            return userName;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public String getBranchId() {
            return branchId;
        }

        public void setBranchId(String branchId) {
            this.branchId = branchId;
        }
    }
}
