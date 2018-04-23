package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.net.base.RequestBean;

/**
 * Created by Star on 2017/7/11.
 */

public class ElevatorInfoRequest extends RequestBean {

    private ElevatorInfoBody body;

    public ElevatorInfoBody getBody() {
        return body;
    }

    public ElevatorInfoRequest setBody(ElevatorInfoBody body) {
        this.body = body;
        return this;
    }

    public class ElevatorInfoBody {
        private String liftNum;
        private String number;

        public void setLiftNum(String liftNum) {
            this.liftNum = liftNum;
        }

        public String getLiftNum() {
            return liftNum;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getNumber() {
            return number;
        }
    }
}
