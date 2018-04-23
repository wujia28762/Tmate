package com.honyum.elevatorMan.data

import java.io.Serializable

/**
 * Created by LiYouGui on 2017/12/4.
 */
class RepairInfo(
        var id:String = "",
        var elevatorId:String = "",
        var communityId:String = "",
        var code:String = "",
        var state:String = "",
        var type:String = "",
        var faultCode:String = "",
        var description:String = "",
        var repairUserId:String = "",
        var repairUserName:String = "",
        var repairTel:String = "",
        var createTime:String = "",
        var backReason:String = "",
        var remark:String = "",
        var updateTime:String = "",
        var pic:String = "",
        var repairUserType:String = "",
        var back:String = "",
        var backTime:String = "",
        var faultName:String ="",
        var workName:String = "",
        var workTel :String  = "",
        var workId :String  = "",
       //电梯信息
        var elevatorInfo:ElevatorInfo1 = ElevatorInfo1(),
        //项目信息
        var communityInfo:CommunityInfo = CommunityInfo(),
        //报修人信息
        var repairUserInfo:WorkerInfo = WorkerInfo()
): Serializable