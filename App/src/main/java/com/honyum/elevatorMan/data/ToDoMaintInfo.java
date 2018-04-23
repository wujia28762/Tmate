package com.honyum.elevatorMan.data;

import java.io.Serializable;

public class ToDoMaintInfo implements Serializable {

    /**
     * code : hm20181227
     * elevatorInfo : {"address":"798","areaCode":"100000","bindTel":"","bindWorker":false,"branchId":"ffcd9687-c142-42e3-a84b-548bd64df4da","branchName":"维修分公司","brand":"","buildingCode":"1","communityId":"f4135d24-86ce-4150-8eea-0a071544b121","communityName":"798(test)","contractEndTime":"","doorAmount":"","dragMode":"1","elevatorType":"","examineUnit":"","factoryCode":"","gatheringTime":"","id":"f7b43523-093d-4d4c-bfe5-4036d345c90c","installTime":"2004-12-01","installUnit":"","isDelete":"0","layerAmount":"","liftNum":"00001","loadingWeight":"","maintUserName":"111","maintUserTel":"15231526059","maintenanceType":"1","manufacturer":"","manufacturerCode":"","model":"","nextYesrCheck":"","number":"00001","overhaulTime":"","propertyBranch":"","speed":"","stopAmount":"","unitCode":"1","usePlace":"2","useState":"1"}
     * id : c896e322-9a08-4b8f-ba1f-ad9d137008ce
     * mainType : hm
     * maintenanceDealId : 062242fa-d065-4960-bda8-7b98881873c5
     * planTime : 2018-12-27
     * propertyFlg : 1
     * statusCode : approve
     * workerInfo : {"age":34,"autograph":"1516786495670.jpg","code":"","createdatetime":1433931534000,"education":"本科","hawkeye":"0","id":"76546ccd-2b8b-4bb2-a600-3dd897ca92b9","identityCard":"211204199010190512","isDelete":"0","isdefault":1,"loginname":"work1","major":"computer science","name":"测试维修1","operationCard":"hijk88","password":"e10adc3949ba59abbe56e057f20f883e","pic":"1510715624635.jpg","political":"群众","remark":"","sex":1,"state":1,"tel":"15231526059","type":"3","usertype":0}
     * wrongFlg : false
     */

    public int index = -1;
    private String code;
    private ElevatorInfoBean elevatorInfo;
    private String id;
    private String mainType;
    private String maintenanceDealId;
    private String planTime;
    private String propertyFlg;
    private String statusCode;
    private WorkerInfoBean workerInfo;
    private boolean wrongFlg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ElevatorInfoBean getElevatorInfo() {
        return elevatorInfo;
    }

    public void setElevatorInfo(ElevatorInfoBean elevatorInfo) {
        this.elevatorInfo = elevatorInfo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMainType() {
        return mainType;
    }

    public void setMainType(String mainType) {
        this.mainType = mainType;
    }

    public String getMaintenanceDealId() {
        return maintenanceDealId;
    }

    public void setMaintenanceDealId(String maintenanceDealId) {
        this.maintenanceDealId = maintenanceDealId;
    }

    public String getPlanTime() {
        return planTime;
    }

    public void setPlanTime(String planTime) {
        this.planTime = planTime;
    }

    public String getPropertyFlg() {
        return propertyFlg;
    }

    public void setPropertyFlg(String propertyFlg) {
        this.propertyFlg = propertyFlg;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public WorkerInfoBean getWorkerInfo() {
        return workerInfo;
    }

    public void setWorkerInfo(WorkerInfoBean workerInfo) {
        this.workerInfo = workerInfo;
    }

    public boolean isWrongFlg() {
        return wrongFlg;
    }

    public void setWrongFlg(boolean wrongFlg) {
        this.wrongFlg = wrongFlg;
    }

    public static class ElevatorInfoBean {
        /**
         * address : 798
         * areaCode : 100000
         * bindTel :
         * bindWorker : false
         * branchId : ffcd9687-c142-42e3-a84b-548bd64df4da
         * branchName : 维修分公司
         * brand :
         * buildingCode : 1
         * communityId : f4135d24-86ce-4150-8eea-0a071544b121
         * communityName : 798(test)
         * contractEndTime :
         * doorAmount :
         * dragMode : 1
         * elevatorType :
         * examineUnit :
         * factoryCode :
         * gatheringTime :
         * id : f7b43523-093d-4d4c-bfe5-4036d345c90c
         * installTime : 2004-12-01
         * installUnit :
         * isDelete : 0
         * layerAmount :
         * liftNum : 00001
         * loadingWeight :
         * maintUserName : 111
         * maintUserTel : 15231526059
         * maintenanceType : 1
         * manufacturer :
         * manufacturerCode :
         * model :
         * nextYesrCheck :
         * number : 00001
         * overhaulTime :
         * propertyBranch :
         * speed :
         * stopAmount :
         * unitCode : 1
         * usePlace : 2
         * useState : 1
         */

        private String address;
        private String areaCode;
        private String bindTel;
        private boolean bindWorker;
        private String branchId;
        private String branchName;
        private String brand;
        private String buildingCode;
        private String communityId;
        private String communityName;
        private String contractEndTime;
        private String doorAmount;
        private String dragMode;
        private String elevatorType;
        private String examineUnit;
        private String factoryCode;
        private String gatheringTime;
        private String id;
        private String installTime;
        private String installUnit;
        private String isDelete;
        private String layerAmount;
        private String liftNum;
        private String loadingWeight;
        private String maintUserName;
        private String maintUserTel;
        private String maintenanceType;
        private String manufacturer;
        private String manufacturerCode;
        private String model;
        private String nextYesrCheck;
        private String number;
        private String overhaulTime;
        private String propertyBranch;
        private String speed;
        private String stopAmount;
        private String unitCode;
        private String usePlace;
        private String useState;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAreaCode() {
            return areaCode;
        }

        public void setAreaCode(String areaCode) {
            this.areaCode = areaCode;
        }

        public String getBindTel() {
            return bindTel;
        }

        public void setBindTel(String bindTel) {
            this.bindTel = bindTel;
        }

        public boolean isBindWorker() {
            return bindWorker;
        }

        public void setBindWorker(boolean bindWorker) {
            this.bindWorker = bindWorker;
        }

        public String getBranchId() {
            return branchId;
        }

        public void setBranchId(String branchId) {
            this.branchId = branchId;
        }

        public String getBranchName() {
            return branchName;
        }

        public void setBranchName(String branchName) {
            this.branchName = branchName;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getBuildingCode() {
            return buildingCode;
        }

        public void setBuildingCode(String buildingCode) {
            this.buildingCode = buildingCode;
        }

        public String getCommunityId() {
            return communityId;
        }

        public void setCommunityId(String communityId) {
            this.communityId = communityId;
        }

        public String getCommunityName() {
            return communityName;
        }

        public void setCommunityName(String communityName) {
            this.communityName = communityName;
        }

        public String getContractEndTime() {
            return contractEndTime;
        }

        public void setContractEndTime(String contractEndTime) {
            this.contractEndTime = contractEndTime;
        }

        public String getDoorAmount() {
            return doorAmount;
        }

        public void setDoorAmount(String doorAmount) {
            this.doorAmount = doorAmount;
        }

        public String getDragMode() {
            return dragMode;
        }

        public void setDragMode(String dragMode) {
            this.dragMode = dragMode;
        }

        public String getElevatorType() {
            return elevatorType;
        }

        public void setElevatorType(String elevatorType) {
            this.elevatorType = elevatorType;
        }

        public String getExamineUnit() {
            return examineUnit;
        }

        public void setExamineUnit(String examineUnit) {
            this.examineUnit = examineUnit;
        }

        public String getFactoryCode() {
            return factoryCode;
        }

        public void setFactoryCode(String factoryCode) {
            this.factoryCode = factoryCode;
        }

        public String getGatheringTime() {
            return gatheringTime;
        }

        public void setGatheringTime(String gatheringTime) {
            this.gatheringTime = gatheringTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getInstallTime() {
            return installTime;
        }

        public void setInstallTime(String installTime) {
            this.installTime = installTime;
        }

        public String getInstallUnit() {
            return installUnit;
        }

        public void setInstallUnit(String installUnit) {
            this.installUnit = installUnit;
        }

        public String getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(String isDelete) {
            this.isDelete = isDelete;
        }

        public String getLayerAmount() {
            return layerAmount;
        }

        public void setLayerAmount(String layerAmount) {
            this.layerAmount = layerAmount;
        }

        public String getLiftNum() {
            return liftNum;
        }

        public void setLiftNum(String liftNum) {
            this.liftNum = liftNum;
        }

        public String getLoadingWeight() {
            return loadingWeight;
        }

        public void setLoadingWeight(String loadingWeight) {
            this.loadingWeight = loadingWeight;
        }

        public String getMaintUserName() {
            return maintUserName;
        }

        public void setMaintUserName(String maintUserName) {
            this.maintUserName = maintUserName;
        }

        public String getMaintUserTel() {
            return maintUserTel;
        }

        public void setMaintUserTel(String maintUserTel) {
            this.maintUserTel = maintUserTel;
        }

        public String getMaintenanceType() {
            return maintenanceType;
        }

        public void setMaintenanceType(String maintenanceType) {
            this.maintenanceType = maintenanceType;
        }

        public String getManufacturer() {
            return manufacturer;
        }

        public void setManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
        }

        public String getManufacturerCode() {
            return manufacturerCode;
        }

        public void setManufacturerCode(String manufacturerCode) {
            this.manufacturerCode = manufacturerCode;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getNextYesrCheck() {
            return nextYesrCheck;
        }

        public void setNextYesrCheck(String nextYesrCheck) {
            this.nextYesrCheck = nextYesrCheck;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getOverhaulTime() {
            return overhaulTime;
        }

        public void setOverhaulTime(String overhaulTime) {
            this.overhaulTime = overhaulTime;
        }

        public String getPropertyBranch() {
            return propertyBranch;
        }

        public void setPropertyBranch(String propertyBranch) {
            this.propertyBranch = propertyBranch;
        }

        public String getSpeed() {
            return speed;
        }

        public void setSpeed(String speed) {
            this.speed = speed;
        }

        public String getStopAmount() {
            return stopAmount;
        }

        public void setStopAmount(String stopAmount) {
            this.stopAmount = stopAmount;
        }

        public String getUnitCode() {
            return unitCode;
        }

        public void setUnitCode(String unitCode) {
            this.unitCode = unitCode;
        }

        public String getUsePlace() {
            return usePlace;
        }

        public void setUsePlace(String usePlace) {
            this.usePlace = usePlace;
        }

        public String getUseState() {
            return useState;
        }

        public void setUseState(String useState) {
            this.useState = useState;
        }
    }

    public static class WorkerInfoBean {
        /**
         * age : 34
         * autograph : 1516786495670.jpg
         * code :
         * createdatetime : 1433931534000
         * education : 本科
         * hawkeye : 0
         * id : 76546ccd-2b8b-4bb2-a600-3dd897ca92b9
         * identityCard : 211204199010190512
         * isDelete : 0
         * isdefault : 1
         * loginname : work1
         * major : computer science
         * name : 测试维修1
         * operationCard : hijk88
         * password : e10adc3949ba59abbe56e057f20f883e
         * pic : 1510715624635.jpg
         * political : 群众
         * remark :
         * sex : 1
         * state : 1
         * tel : 15231526059
         * type : 3
         * usertype : 0
         */

        private int age;
        private String autograph;
        private String code;
        private long createdatetime;
        private String education;
        private String hawkeye;
        private String id;
        private String identityCard;
        private String isDelete;
        private int isdefault;
        private String loginname;
        private String major;
        private String name;
        private String operationCard;
        private String password;
        private String pic;
        private String political;
        private String remark;
        private int sex;
        private int state;
        private String tel;
        private String type;
        private int usertype;

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getAutograph() {
            return autograph;
        }

        public void setAutograph(String autograph) {
            this.autograph = autograph;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public long getCreatedatetime() {
            return createdatetime;
        }

        public void setCreatedatetime(long createdatetime) {
            this.createdatetime = createdatetime;
        }

        public String getEducation() {
            return education;
        }

        public void setEducation(String education) {
            this.education = education;
        }

        public String getHawkeye() {
            return hawkeye;
        }

        public void setHawkeye(String hawkeye) {
            this.hawkeye = hawkeye;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIdentityCard() {
            return identityCard;
        }

        public void setIdentityCard(String identityCard) {
            this.identityCard = identityCard;
        }

        public String getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(String isDelete) {
            this.isDelete = isDelete;
        }

        public int getIsdefault() {
            return isdefault;
        }

        public void setIsdefault(int isdefault) {
            this.isdefault = isdefault;
        }

        public String getLoginname() {
            return loginname;
        }

        public void setLoginname(String loginname) {
            this.loginname = loginname;
        }

        public String getMajor() {
            return major;
        }

        public void setMajor(String major) {
            this.major = major;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOperationCard() {
            return operationCard;
        }

        public void setOperationCard(String operationCard) {
            this.operationCard = operationCard;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getPolitical() {
            return political;
        }

        public void setPolitical(String political) {
            this.political = political;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getUsertype() {
            return usertype;
        }

        public void setUsertype(int usertype) {
            this.usertype = usertype;
        }
    }
}
