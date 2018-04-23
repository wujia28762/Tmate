package com.honyum.elevatorMan.data;

import java.io.Serializable;

/**
 * Created by Star on 2017/12/21.
 */
public class ResultMapInfo implements Serializable {

    private String _process_bizId;
    private String _process_isLastNode;
    private String _process_task_param;
    private boolean code;
    private String msg;
    private boolean sucess;

    public void set_process_bizId(String _process_bizId) {
        this._process_bizId = _process_bizId;
    }
    public String get_process_bizId() {
        return _process_bizId;
    }

    public void set_process_isLastNode(String _process_isLastNode) {
        this._process_isLastNode = _process_isLastNode;
    }
    public String get_process_isLastNode() {
        return _process_isLastNode;
    }

    public void set_process_task_param(String _process_task_param) {
        this._process_task_param = _process_task_param;
    }
    public String get_process_task_param() {
        return _process_task_param;
    }

    public void setCode(boolean code) {
        this.code = code;
    }
    public boolean getCode() {
        return code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getMsg() {
        return msg;
    }

    public void setSucess(boolean sucess) {
        this.sucess = sucess;
    }
    public boolean getSucess() {
        return sucess;
    }

}
