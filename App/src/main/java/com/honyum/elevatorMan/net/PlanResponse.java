package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.net.base.NewResponse;
import com.honyum.elevatorMan.net.base.Response;

import java.io.Serializable;

/**
 * Created by Star on 2018/1/9.
 */

public class PlanResponse extends NewResponse {
    private PlanResponseBody body = new PlanResponseBody();

    public PlanResponseBody getBody() {
        return body;
    }

    public void setBody(PlanResponseBody body) {
        this.body = body;
    }

    public class PlanResponseBody implements Serializable
    {
        private String _process_task_param;
        private String id;

        public String get_process_task_param() {
            return _process_task_param;
        }

        public void set_process_task_param(String _process_task_param) {
            this._process_task_param = _process_task_param;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
