package com.honyum.elevatorMan.data;

import java.io.Serializable;

/**
 * Created by star on 2018/4/11.
 */

public class CommunityCountInfo implements Serializable {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    private String id = "";
    private String name;
    private String count = "";

}
