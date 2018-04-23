package com.honyum.elevatorMan.data;

import java.io.Serializable;

/**
 * Created by Star on 2017/12/18.
 */

public class ProcessInfo implements Serializable {


    private String bizCode;
    private String bizDesc;
    private String bizId;
    private String bizName;
    private String bizType;
    private String bizURL;
    private String branchId;
    private Long createDate;
    private String currUserId;

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public String getBizDesc() {
        return bizDesc;
    }

    public void setBizDesc(String bizDesc) {
        this.bizDesc = bizDesc;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getBizName() {
        return bizName;
    }

    public void setBizName(String bizName) {
        this.bizName = bizName;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getBizURL() {
        return bizURL;
    }

    public void setBizURL(String bizURL) {
        this.bizURL = bizURL;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    public String getCurrUserId() {
        return currUserId;
    }

    public void setCurrUserId(String currUserId) {
        this.currUserId = currUserId;
    }

    public String getCurrUserName() {
        return currUserName;
    }

    public void setCurrUserName(String currUserName) {
        this.currUserName = currUserName;
    }

    public String getCurrUserTel() {
        return currUserTel;
    }

    public void setCurrUserTel(String currUserTel) {
        this.currUserTel = currUserTel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(String isFinish) {
        this.isFinish = isFinish;
    }

    public String getIsLastNode() {
        return isLastNode;
    }

    public void setIsLastNode(String isLastNode) {
        this.isLastNode = isLastNode;
    }

    public Long getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Long lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getPreUserId() {
        return preUserId;
    }

    public void setPreUserId(String preUserId) {
        this.preUserId = preUserId;
    }

    public String getPreUserName() {
        return preUserName;
    }

    public void setPreUserName(String preUserName) {
        this.preUserName = preUserName;
    }

    public String getPreUserTel() {
        return preUserTel;
    }

    public void setPreUserTel(String preUserTel) {
        this.preUserTel = preUserTel;
    }

    public Long getProcessDate() {
        return processDate;
    }

    public void setProcessDate(Long processDate) {
        this.processDate = processDate;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getProcessOpinion() {
        return processOpinion;
    }

    public void setProcessOpinion(String processOpinion) {
        this.processOpinion = processOpinion;
    }

    public String getProcessResult() {
        return processResult;
    }

    public void setProcessResult(String processResult) {
        this.processResult = processResult;
    }

    public String getProcessSource() {
        return processSource;
    }

    public void setProcessSource(String processSource) {
        this.processSource = processSource;
    }

    public String getProcessType() {
        return processType;
    }

    public void setProcessType(String processType) {
        this.processType = processType;
    }

    public String getProcessVesion() {
        return processVesion;
    }

    public void setProcessVesion(String processVesion) {
        this.processVesion = processVesion;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    private String currUserName;
    private String currUserTel;
    private String id;
    private String isFinish;
    private String isLastNode;
    private Long lastUpdateDate;
    private String nodeId;
    private String nodeName;
    private String nodeType;
    private String parentId;
    private String preUserId;
    private String preUserName;
    private String preUserTel;
    private Long processDate;
    private String processId;
    private String processOpinion;
    private String processResult;
    private String processSource;
    private String processType;
    private String processVesion;
    private String statusCode;
}
