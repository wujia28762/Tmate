package com.honyum.elevatorMan.data

import java.io.Serializable

/**
 * Created by LiYouGui on 2017/12/4.
 */
class MaintenanceInfo(



        var mainTime:String = "",
        var id: String = "",

        var mainType: String = "",

        var planTime: String = "",

        var code: String = "",

        var propertyFlg: String = "",

        var statusCode: String = "",

        var workerId: String = "",

        var wrongFlg: Boolean = false,

        //电梯信息
        var elevatorInfo: ElevatorInfo1 = ElevatorInfo1(),
        //维修工
        var workerInfo: WorkOrderInfo = WorkOrderInfo()

) : Serializable
{
     fun queryType():String
    {
        var type = ""
        when (mainType) {
            "hm" -> type = "半月保"
            "m" -> type = "月保"
            "s" -> type = "季保"
            "y" -> type = "年保"
            "hy" -> type = "半年保"
        }
        return type
    }
}