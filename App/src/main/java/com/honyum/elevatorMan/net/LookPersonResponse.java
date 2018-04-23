package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.net.base.NewResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by star on 2018/4/3.
 */

public class LookPersonResponse extends NewResponse {


    /**
     * body : {"busy":[{"lat":39.986067,"lng":116.484124,"signTime":"2018-03-29 10:28:23","state":"0","userId":"76546ccd-2b8b-4bb2-a600-3dd897ca92b9","userName":"测试维修1"}],"free":[{"lat":39.986371,"lng":116.484092,"signTime":"2018-03-14 19:57:55","state":"1","userId":"1c49f397-040b-4a0e-a579-706845595b1d","userName":"测试维修2"}]}
     * head : {"appVersion":"37","rspCode":"0","rspMsg":"accept"}
     */

    private BodyBean body;

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public static class BodyBean {
        private List<BusyBean> busy = new ArrayList<>();
        private List<BusyBean> free = new ArrayList<>();

        public List<BusyBean> getBusy() {
            return busy;
        }

        public void setBusy(List<BusyBean> busy) {
            this.busy = busy;
        }

        public List<BusyBean> getFree() {
            return free;
        }

        public void setFree(List<BusyBean> free) {
            this.free = free;
        }

        public static class BusyBean implements Serializable{
            /**
             * lat : 39.986067
             * lng : 116.484124
             * signTime : 2018-03-29 10:28:23
             * state : 0
             * userId : 76546ccd-2b8b-4bb2-a600-3dd897ca92b9
             * userName : 测试维修1
             */

            private double lat;
            private double lng;
            private String signTime;
            private String state;
            private String userId;
            private String userName;

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }

            public double getLng() {
                return lng;
            }

            public void setLng(double lng) {
                this.lng = lng;
            }

            public String getSignTime() {
                return signTime;
            }

            public void setSignTime(String signTime) {
                this.signTime = signTime;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }
        }
    }
}
