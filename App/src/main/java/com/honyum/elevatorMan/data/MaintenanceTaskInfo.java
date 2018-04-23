package com.honyum.elevatorMan.data;

import java.io.Serializable;

/**
 * 维修任务信息bean
 */

public class MaintenanceTaskInfo implements Serializable {
    private String confirmTime;

    private String createTime;

    private String finishTime;

    private String id;

    private String isPay;

    private MaintOrderInfo maintOrderInfo = new MaintOrderInfo();

    private String maintUserFeedback;

    private String maintUserId;

    private MaintUserInfo maintUserInfo = new MaintUserInfo();

    private String planTime;

    private String state;

    private String taskCode;

    private String afterImg;
    private String afterImg1;
    private String beforeImg;

    public String getAfterImg() {
        return afterImg;
    }

    public void setAfterImg(String afterImg) {
        this.afterImg = afterImg;
    }

    public String getBeforeImg() {
        return beforeImg;
    }

    public void setBeforeImg(String beforeImg) {
        this.beforeImg = beforeImg;
    }

    public String getEvaluateResult() {
        return evaluateResult;
    }

    public void setEvaluateResult(String evaluateResult) {
        this.evaluateResult = evaluateResult;
    }

    public String getEvaluateContent() {
        return evaluateContent;
    }

    public void setEvaluateContent(String evaluateContent) {
        this.evaluateContent = evaluateContent;
    }

    public String getEvaluateTime() {
        return evaluateTime;
    }

    public void setEvaluateTime(String evaluateTime) {
        this.evaluateTime = evaluateTime;
    }

    private String evaluateResult = "0";
    private String evaluateContent;
    private String evaluateTime;

    public void setConfirmTime(String confirmTime){
        this.confirmTime = confirmTime;
    }
    public String getConfirmTime(){
        return this.confirmTime;
    }
    public void setCreateTime(String createTime){
        this.createTime = createTime;
    }
    public String getCreateTime(){
        return this.createTime;
    }
    public void setFinishTime(String finishTime){
        this.finishTime = finishTime;
    }
    public String getFinishTime(){
        return this.finishTime;
    }
    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }
    public void setIsPay(String isPay){
        this.isPay = isPay;
    }
    public String getIsPay(){
        return this.isPay;
    }
    public void setMaintOrderInfo(MaintOrderInfo maintOrderInfo){
        this.maintOrderInfo = maintOrderInfo;
    }
    public MaintOrderInfo getMaintOrderInfo(){
        return this.maintOrderInfo;
    }
    public void setMaintUserFeedback(String maintUserFeedback){
        this.maintUserFeedback = maintUserFeedback;
    }
    public String getMaintUserFeedback(){
        return this.maintUserFeedback;
    }
    public void setMaintUserId(String maintUserId){
        this.maintUserId = maintUserId;
    }
    public String getMaintUserId(){
        return this.maintUserId;
    }
    public void setMaintUserInfo(MaintUserInfo maintUserInfo){
        this.maintUserInfo = maintUserInfo;
    }
    public MaintUserInfo getMaintUserInfo(){
        return this.maintUserInfo;
    }
    public void setPlanTime(String planTime){
        this.planTime = planTime;
    }
    public String getPlanTime(){
        return this.planTime;
    }
    public void setState(String state){
        this.state = state;
    }
    public String getState(){
        return this.state;
    }
    public void setTaskCode(String taskCode){
        this.taskCode = taskCode;
    }
    public String getTaskCode(){
        return this.taskCode;
    }


    public String getAfterImg1() {
        return afterImg1;
    }

    public void setAfterImg1(String afterImg1) {
        this.afterImg1 = afterImg1;
    }
}
