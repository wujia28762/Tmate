package com.honyum.elevatorMan.data;

import java.io.Serializable;

/**
 * Created by Star on 2017/12/21.
 */

public class IdAndName implements Serializable{

    private String id;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
