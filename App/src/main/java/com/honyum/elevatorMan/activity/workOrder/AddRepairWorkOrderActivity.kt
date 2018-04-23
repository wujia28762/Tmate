package com.honyum.elevatorMan.activity.workOrder

import android.content.Intent
import android.os.Bundle
import com.honyum.elevatorMan.R
import com.honyum.elevatorMan.adapter.WorkOrderRepairListAdapter
import com.honyum.elevatorMan.base.BaseActivityWraper
import com.honyum.elevatorMan.data.RepairInfo
import com.honyum.elevatorMan.net.ContractInfoRequest
import com.honyum.elevatorMan.net.FaultTypeResponse
import com.honyum.elevatorMan.net.NewRequestHead
import com.honyum.elevatorMan.net.RepairInfoResponse
import com.honyum.elevatorMan.net.base.NetConstant
import com.honyum.elevatorMan.net.base.NetTask
import kotlinx.android.synthetic.main.activity_add_repair_work_order.*
import kotlinx.android.synthetic.main.activity_work_order_list.*
import org.jetbrains.anko.act
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddRepairWorkOrderActivity : BaseActivityWraper() {

    var repair_list:MutableList<RepairInfo> = ArrayList<RepairInfo>()

    var index:Int = 0

    var fault_type:String = ""


    override fun getLayoutID(): Int {
        return R.layout.activity_add_repair_work_order
    }

    override fun getTitleString(): String {
        return "工单管理"
    }

    override fun initView() {

        get_repair_list()

        tv_submit.onClick {
            if(tv_lift_num.text.equals("请选择电梯")||tv_lift_num.text.equals("")){
                showToast("请选择电梯")
            }else{
                val intent = Intent()
                intent.setClass(act, AddWorkOrderRepairDetailActivity::class.java)
                var bundle = Bundle()
                bundle.putSerializable("repairInfo",repair_list.get(index))
                intent.putExtras(bundle)
                startActivity(intent)
            }
        }

        lv_list_repair_order.setOnItemClickListener { parent, view, position, id ->
            tv_lift_num.text = repair_list.get(position).elevatorInfo.liftNum
            index = position
        }
    }

    override fun onResume() {
        super.onResume()
       /* repair_list = ArrayList<RepairInfo>()
        lv_list_repair_order.adapter = WorkOrderRepairListAdapter(repair_list,act)*/
        tv_lift_num.text = ""
        get_repair_list()
    }


    private fun get_repair_list(){
        var contractInfoRequest = ContractInfoRequest()
        var body = ContractInfoRequest().ContractInfoBody()
        body.branchId = config.branchId
        contractInfoRequest.body = body
        contractInfoRequest.head = NewRequestHead().setaccessToken(config.token).setuserId(config.userId)
        var server = config.server + NetConstant.GET_BAOXIU_LIST

        var netTask = object : NetTask(server, contractInfoRequest) {
            override fun onResponse(task: NetTask?, result: String?) {
                var response = RepairInfoResponse.getRepairInfoResponse(result)
                repair_list = response.body
                val sdf = SimpleDateFormat("yyyy-MM-dd")
                repair_list.sortByDescending {
                    sdf.parse(it.createTime).time
                }


                for(index in repair_list.indices){
                    get_fault_type(repair_list.get(index).faultCode,index)
                }
                lv_list_repair_order.adapter = WorkOrderRepairListAdapter(repair_list,act)
            }
        }
        addTask(netTask)
    }


    private fun get_fault_type(code:String,index:Int){
        var contractInfoRequest = ContractInfoRequest()
        var body = ContractInfoRequest().ContractInfoBody()
        body.code = code
        contractInfoRequest.body = body
        contractInfoRequest.head = NewRequestHead().setaccessToken(config.token).setuserId(config.userId)
        var server = config.server + NetConstant.GET_FAULT_TYPE
        var netTask = object : NetTask(server, contractInfoRequest) {
            override fun onResponse(task: NetTask?, result: String?) {
                var response = FaultTypeResponse.getFaultTypeResponse(result)
                repair_list.get(index).faultName = response.body.name

                if(index == repair_list.size-1){
                    lv_list_repair_order.adapter = WorkOrderRepairListAdapter(repair_list,act)
                }

            }
        }
        addTask(netTask)
    }
}
