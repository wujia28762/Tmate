package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.net.base.RequestBean;
import com.honyum.elevatorMan.net.base.RequestBody;

/**
 * Created by Star on 2017/6/8.
 */

public class UnDealCountRequest extends RequestBean {

    private UnDealCountBody body;



    public UnDealCountBody getBody() {
        return body;
    }

    public UnDealCountRequest setBody(UnDealCountBody body) {
        this.body = body;
        return this;
    }

    public class UnDealCountBody extends RequestBody {

        private String branchId;


        public String getBranchId() {
            return branchId;
        }

        public void setBranchId(String branchId) {
            this.branchId = branchId;
        }
    }

}
