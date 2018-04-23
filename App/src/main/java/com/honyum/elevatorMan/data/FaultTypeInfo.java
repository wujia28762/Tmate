package com.honyum.elevatorMan.data;

import java.io.Serializable;

/**
 * Created by changhaozhang on 16/1/11.
 */

public class FaultTypeInfo implements Serializable {

   /*  "code": "0804",
        "id": "02ab2a8d-d5b7-4bbb-b0da-6501e23efa96",
             "isDelete": "0",
             "name": "主电源断开",
             "pid": "5e6542b3-9c16-4651-b790-d4b76bf9dc2b",
             "pname": "电气系统"*/

    private String code;

    private String id;

    private String name;

    private String pname;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPname() {
        return pname;
    }
}
