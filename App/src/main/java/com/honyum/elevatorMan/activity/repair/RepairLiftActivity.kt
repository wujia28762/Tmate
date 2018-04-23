package com.honyum.elevatorMan.activity.repair

import com.honyum.elevatorMan.R
import com.honyum.elevatorMan.adapter.RepairLiftListAdapter
import com.honyum.elevatorMan.base.BaseActivityWraper
import com.honyum.elevatorMan.data.LiftInfo
import com.honyum.elevatorMan.net.AlarmListRequest
import com.honyum.elevatorMan.net.LiftInfoResponse
import com.honyum.elevatorMan.net.base.NetConstant
import com.honyum.elevatorMan.net.base.NetTask
import com.honyum.elevatorMan.net.base.RequestBean
import com.honyum.elevatorMan.net.base.RequestHead
import kotlinx.android.synthetic.main.activity_repair_lift.*
import org.jetbrains.anko.ctx
import java.util.*

class RepairLiftActivity : BaseActivityWraper() {

    var lift_list:MutableList<LiftInfo> = ArrayList<LiftInfo>()

    override fun getLayoutID(): Int {
        return R.layout.activity_repair_lift
    }

    override fun getTitleString(): String {
        return "电梯报修"
    }

    override fun initView() {
        request_lift_list()
    }

    private fun request_lift_list(){
        val task = object : NetTask(config.server + NetConstant.URL_GET_LIFT_INFO,
                getRequestBean(config.userId, config.token)) {
            override fun onResponse(task: NetTask, result: String) {
                val response = LiftInfoResponse.getLiftInfoResponse(result)
                val lift_list = response.body
                lv_list_lift.adapter = RepairLiftListAdapter(lift_list,ctx)
            }
        }
        addTask(task)
    }

    private fun getRequestBean(userId: String, token: String): RequestBean {
        //只需要发送一个head即可，这里使用请求报警列表的request bean
        val request = AlarmListRequest()
        val head = RequestHead()
        head.userId = userId
        head.accessToken = token
        request.head = head
        return request
    }
}
