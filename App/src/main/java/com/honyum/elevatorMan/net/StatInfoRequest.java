package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.net.base.RequestBean;

import java.io.Serializable;

/**
 * Created by star on 2018/4/18.
 */

public class StatInfoRequest extends RequestBean {

    private StatInfoRequestBody body;

    public StatInfoRequestBody getBody() {
        return body;
    }

    public void setBody(StatInfoRequestBody body) {
        this.body = body;
    }

    public static class StatInfoRequestBody implements Serializable
    {
        public String getBranchId() {
            return branchId;
        }

        public void setBranchId(String branchId) {
            this.branchId = branchId;
        }

        public String getCommunityId() {
            return communityId;
        }

        public void setCommunityId(String communityId) {
            this.communityId = communityId;
        }

        public String getDepartId() {
            return departId;
        }

        public void setDepartId(String departId) {
            this.departId = departId;
        }

        public String getQueryTime() {
            return queryTime;
        }

        public void setQueryTime(String queryTime) {
            this.queryTime = queryTime;
        }

        public String getRoleId() {
            return roleId;
        }

        public void setRoleId(String roleId) {
            this.roleId = roleId;
        }


        private  String branchId;
        private String communityId;
        private String departId;
        private String queryTime;
        private String roleId;
        private String statusType;
        private String status;

        public String getStatusType() {
            return statusType;
        }

        public void setStatusType(String statusType) {
            this.statusType = statusType;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
