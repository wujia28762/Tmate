package com.honyum.elevatorMan.data;

import java.io.Serializable;

/**
 * Created by Star on 2017/12/21.
 */

public class ContactDataGrideInfo implements Serializable {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

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

    private String id;
    private String loginname;
    private String name;
    private String tel;
    private boolean selected = false;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ContactDataGrideInfo) {
            ContactDataGrideInfo user = (ContactDataGrideInfo) obj;
            if (this.id.equals(user.getId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();

    }
}
