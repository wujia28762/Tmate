package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.net.base.RequestBean;

/**
 * Created by Star on 2017/7/11.
 */

public class RepairInfoRequest extends RequestBean {

    private RepairInfoBody body;

    public RepairInfoBody getBody() {
        return body;
    }

    public RepairInfoRequest setBody(RepairInfoBody body) {
        this.body = body;
        return this;
    }

    public class RepairInfoBody {
        private String roleId;
        public void setRoleId(String roleId) {
            this.roleId = roleId;
        }
        public String getRoleId() {
            return roleId;
        }
    }
}
