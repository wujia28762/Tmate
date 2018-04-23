package com.honyum.elevatorMan.activity.maintenance

import android.text.TextUtils
import com.honyum.elevatorMan.R
import com.honyum.elevatorMan.base.BaseActivityWraper
import com.honyum.elevatorMan.constant.IntentConstant
import com.honyum.elevatorMan.data.WorkOrderInfo
import com.honyum.elevatorMan.net.NewRequestHead
import com.honyum.elevatorMan.net.SuccessMaintWorkOrderInfoRequest
import com.honyum.elevatorMan.net.base.NetConstant
import com.honyum.elevatorMan.net.base.NetTask
import com.honyum.elevatorMan.utils.SharedPreferenceUtil
import kotlinx.android.synthetic.main.activity_other_maintenance.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by star on 2018/4/11.
 */
class OtherMaintenanceActivity  :BaseActivityWraper(){
    override fun getTitleString(): String {
        return "维保提交"
    }
    var workOrderInfo: WorkOrderInfo = WorkOrderInfo()
    override fun initView() {
        if (intent.hasExtra(IntentConstant.INTENT_DATA)) {
            workOrderInfo = intent.getSerializableExtra(IntentConstant.INTENT_DATA) as WorkOrderInfo
            submit.onClick {
                if (et_remark.text.toString().isNullOrEmpty())
                {
                    showToast("请输入维保内容！")
                    return@onClick
                }
                successMaintWorkOrder(workOrderInfo)
            }
        }

    }
    private fun successMaintWorkOrder(workOrderInfo: WorkOrderInfo) {
        var maintItemInfoRequest = SuccessMaintWorkOrderInfoRequest()
        var body = maintItemInfoRequest.MaintWorkOrderInfoBody()
        body.workOrderId = workOrderInfo.id
        //body.items = list_maintItem_submit
        body.processResult = et_remark.text.toString()
        maintItemInfoRequest.body = body

        maintItemInfoRequest.head = NewRequestHead().setaccessToken(config.token).setuserId(config.userId)
        var server = config.server + NetConstant.SUCCESS_MAINT_WORK_ORDER
        var netTask = object : NetTask(server, maintItemInfoRequest) {
            override fun onResponse(task: NetTask?, result: String?) {
                showToast("提交成功")
                finish()
            }
        }
        addTask(netTask)
    }
    override fun getLayoutID(): Int {
        return R.layout.activity_other_maintenance
    }
}