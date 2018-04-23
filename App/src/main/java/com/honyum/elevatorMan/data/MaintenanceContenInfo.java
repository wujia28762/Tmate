package com.honyum.elevatorMan.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by LiYouGui on 2017/12/21.
 */

public class MaintenanceContenInfo implements Serializable {

            /* "bclass": "大类明细",
             "content": "梦想成真枯",
             "finish": 0,
             "id": "0cc9e989-465d-4c27-9421-a23d48cfcb53",
             "picNum": 0,
             "remark": "士大夫",
             "sclass": "小类",
             "seq": 1,
             "type": "hm"*/


    private String name;
    private String state = "1" ;
    private String bclass;
    private String content;
    private String finish;


    private String id;
    private int picNum;
    private String remark;
    private String sclass;
    private int seq;
    private String type;
    private String pic = "";

    private ArrayList<String> list_pic;

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPic() {
        return pic;
    }

    public void setList_pic(ArrayList<String> list_pic) {
        this.list_pic = list_pic;
    }

    public ArrayList<String> getList_pic() {
        return list_pic;
    }

    private ArrayList<Map<String, Object>> datas;

    public void setDatas(ArrayList<Map<String, Object>> datas) {
        this.datas = datas;
    }

    public ArrayList<Map<String, Object>> getDatas() {
        return datas;
    }


    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

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

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setBclass(String bclass) {
        this.bclass = bclass;
    }

    public int getPicNum() {
        return picNum;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSeq() {
        return seq;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }

    public String getBclass() {
        return bclass;
    }

    public String getContent() {
        return content;
    }

    public void setPicNum(int picNum) {
        this.picNum = picNum;
    }

    public String getFinish() {
        return finish;
    }

    public void setSclass(String sclass) {
        this.sclass = sclass;
    }

    public String getSclass() {
        return sclass;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }
}
