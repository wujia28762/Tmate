package com.honyum.elevatorMan.data;

import java.io.Serializable;

/**
 * Created by star on 2018/4/18.
 */

public class KpiData implements Serializable {

    public String getAlarm() {
        return alarm;
    }

    public void setAlarm(String alarm) {
        this.alarm = alarm;
    }

    public String getBaoxiu() {
        return baoxiu;
    }

    public void setBaoxiu(String baoxiu) {
        this.baoxiu = baoxiu;
    }

    public String getMaint() {
        return maint;
    }

    public void setMaint(String maint) {
        this.maint = maint;
    }

    private String alarm;
    private String baoxiu;

    private String maint;

}
