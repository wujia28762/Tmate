package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.data.ContartInfo;
import com.honyum.elevatorMan.net.base.NewResponse;
import com.honyum.elevatorMan.net.base.ResponseBody;

public class MoneyRecieveResponse extends NewResponse {

    private MoneyRecieveResponseBody body;

    public MoneyRecieveResponseBody getBody() {
        return body;
    }

    public void setBody(MoneyRecieveResponseBody body) {
        this.body = body;
    }

    public class MoneyRecieveResponseBody extends ResponseBody
    {
        private String id;
        private String contractId; //合同ID

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getContractId() {
            return contractId;
        }

        public void setContractId(String contractId) {
            this.contractId = contractId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPaymentTime() {
            return paymentTime;
        }

        public void setPaymentTime(String paymentTime) {
            this.paymentTime = paymentTime;
        }

        public Double getPaymentMoney() {
            return paymentMoney;
        }

        public void setPaymentMoney(Double paymentMoney) {
            this.paymentMoney = paymentMoney;
        }

        public String getPercentage() {
            return percentage;
        }

        public void setPercentage(String percentage) {
            this.percentage = percentage;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserTel() {
            return userTel;
        }

        public void setUserTel(String userTel) {
            this.userTel = userTel;
        }

        public ContartInfo getContractInfo() {
            return contractInfo;
        }

        public void setContractInfo(ContartInfo contractInfo) {
            this.contractInfo = contractInfo;
        }

        private String type; //结算类型
        private String paymentTime; //结算日期
        private Double paymentMoney; //结算金额
        private String percentage; //百分比
        private String description; //备注
        private String createTime; //创建时间
        private String userId; //收/付款人ID
        private String userName; //收/付款人姓名
        private String userTel; //收/付款人电话
        private ContartInfo contractInfo = new ContartInfo(); //合同信息

    }

}
