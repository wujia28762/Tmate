package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.data.AddressInfo;
import com.honyum.elevatorMan.net.base.Response;
import com.honyum.elevatorMan.net.base.ResponseBody;
import com.honyum.elevatorMan.net.base.ResponseHead;

public class LoginResponse extends Response {

    private ResponseHead head;

    private LoginRspBody body;

    public ResponseHead getHead() {
        return head;
    }

    public void setHead(ResponseHead head) {
        this.head = head;
    }

    public LoginRspBody getBody() {
        return body;
    }

    public void setBody(LoginRspBody body) {
        this.body = body;
    }

    /**
     * 根据json生成对象
     *
     * @param json
     * @return
     */
    public static LoginResponse getLoginResonse(String json) {
        return (LoginResponse) parseFromJson(LoginResponse.class, json);
    }

    public class LoginRspBody extends ResponseBody {

        public String getRoleId() {
            return roleId;
        }

        public void setRoleId(String roleId) {
            this.roleId = roleId;
        }

        private String roleId = "";
        private String userId; // 用户id

        private double lat;

        private double lng;

        public int getLocationUpload() {
            return locationUpload;
        }

        public void setLocationUpload(int locationUpload) {
            this.locationUpload = locationUpload;
        }

        public int getLocationUploadTask() {
            return locationUploadTask;
        }

        public void setLocationUploadTask(int locationUploadTask) {
            this.locationUploadTask = locationUploadTask;
        }

        //无任务位置上传频率
        private int locationUpload = 30;
        //有任务位置上传频率
        private int locationUploadTask = 5;


        private String type; // 用户角色

        private String name; // 用户姓名

        private int age;    //  年龄

        private String operationCard;   //操作证号

        private int sex;    //性别,0:女 1:男

        private String tel; //电话号码

        private String branchName;  //公司名称

        private String branchId;   //公司id

        private String pic;

        private String autograph;

        private AddressInfo userAttach;

        public String getBranchId() {
            return branchId;
        }

        public void setBranchId(String branchId) {
            this.branchId = branchId;
        }

        public AddressInfo getUserAttach() {
            return userAttach;
        }

        public void setUserAttach(AddressInfo userAttach) {
            this.userAttach = userAttach;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getOperationCard() {
            return operationCard;
        }

        public void setOperationCard(String operationCard) {
            this.operationCard = operationCard;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getBranchName() {
            return branchName;
        }

        public void setBranchName(String branchName) {
            this.branchName = branchName;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getAutograph() {
            return autograph;
        }

        public void setAutograph(String autograph) {
            this.autograph = autograph;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }


    }
}
