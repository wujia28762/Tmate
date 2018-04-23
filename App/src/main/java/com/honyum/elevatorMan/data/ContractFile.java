package com.honyum.elevatorMan.data;

import java.io.Serializable;

/**
 * Created by LiYouGui on 2017/12/11.
 */

public class ContractFile implements Serializable{
    private String id;
    private String contractId; //合同ID
    private String fileName; //附件名称
    private String url = ""; //附件路径
    private String createTime; //创建时间

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

}
