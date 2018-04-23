package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.net.base.RequestBean;

import java.io.Serializable;

public class NHMprojectSelectRequest extends RequestBean {

   private NHMprojectSelectRequestBody body;

    public NHMprojectSelectRequestBody getBody() {
        return body;
    }

    public void setBody(NHMprojectSelectRequestBody body) {
        this.body = body;
    }

    public class NHMprojectSelectRequestBody implements Serializable{

        private int rows;
        private int page;

        public int getRows() {
            return rows;
        }

        public void setRows(int rows) {
            this.rows = rows;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }
    }
}
