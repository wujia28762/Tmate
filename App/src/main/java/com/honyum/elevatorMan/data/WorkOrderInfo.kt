package com.honyum.elevatorMan.data

import java.io.Serializable

/**
 * Created by LiYouGui on 2017/12/4.
 */
class WorkOrderInfo(
        val updateMsg:String = "",
        val updateFlag:String = "",
        val positionUpLoadTime:String = "",
        val statusName:String= "",
        val endDate:String= "",
        val startDate:String= "",
        val assistantTel:String= "",
        val workId:String="",//协同负责人
        val workName:String="",//协同负责人
        val propertyCode:String = "",
        val assistantId:String = "",
        val assistantName:String = "",
        val propertyBranchName:String = "",
        val branchName:String = "",
        val lastMaintainDate:String = "",
        val realMaintainStartDate:String= "",
        val realMaintainEndDate:String= "",
        val maintainCommitDate:String= "",
        val name:String = "",
        val id: String? = "",
        val bizCode: String? = "",
        val bizId: String? = "",
        val bizType: String? = "",
        val comment: String? = "",
        val communityInfo: CommunityInfo = CommunityInfo(),
        val contractInfo: ContartInfo = ContartInfo(),
        val createBy: String? = "",
        val createDate: String? = "",
        val createUserId: String? = "",
        val createUserName: String? = "",
        val createUserTel: String? = "",
        val elevatorInfo: ElevatorInfo1 = ElevatorInfo1(),
        val evaluateScore: Int = 0,
        val expectMaintainEndDate: String? = "",
        val expectMaintainStartDate: String? = "",
        val isNeedParts: Int = 0, //0否 1 是
        val lastupdateBy: String? = "",
        val lastupdateDate: String? = "",
        val moneyDesc: String? = "",
        val orderContent: String? = "",
        val orderName: String? = "",
        val orderNum: String? = "",
        val otherMoney: Int = 0,
        val partsNeedDate: String? = "",
        val purchaseMoney: Int = 0,
        val realMoney: Int = 0,
        val rebateMoney: Int = 0,
        val receivableMoney: Int = 0,
        val statusCode: String? = "",
        val pic :String ? = "",
        val result:String? = "",
        val appearance:String? = "",
        val reason:String? = "",
        val processResult:String? = "",
        val faultCode:String? = "",
        val preMaintainDate:String? = "",
        val preventiveMeasure:String? = "",
        val days: Int = -99
): Serializable