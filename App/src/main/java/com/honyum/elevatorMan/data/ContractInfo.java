package com.honyum.elevatorMan.data;

import java.io.Serializable;

/**
 * Created by Star on 2017/12/19.
 */

public class ContractInfo implements Serializable {

    private String name;
    private String id;
    private String tel;
    private Boolean selected = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}
