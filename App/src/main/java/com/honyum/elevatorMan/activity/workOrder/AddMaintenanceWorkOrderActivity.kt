package com.honyum.elevatorMan.activity.workOrder

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import com.honyum.elevatorMan.R
import com.honyum.elevatorMan.adapter.MaintenanceListAdapter
import com.honyum.elevatorMan.base.BaseActivityWraper
import com.honyum.elevatorMan.data.MaintenanceInfo
import com.honyum.elevatorMan.net.ContractInfoRequest
import com.honyum.elevatorMan.net.MaintenanceInfoResponse
import com.honyum.elevatorMan.net.NewRequestHead
import com.honyum.elevatorMan.net.base.NetConstant
import com.honyum.elevatorMan.net.base.NetTask
import kotlinx.android.synthetic.main.activity_add_maintenance_work_order.*
import org.jetbrains.anko.act
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.text.SimpleDateFormat
import java.util.*

class AddMaintenanceWorkOrderActivity : BaseActivityWraper() {

    var maintenance_list:MutableList<MaintenanceInfo> = ArrayList<MaintenanceInfo>()

    var index:Int = 0

    override fun getLayoutID(): Int {
        return R.layout.activity_add_maintenance_work_order
    }

    override fun getTitleString(): String {
        return "工单选择"
    }

    override fun initView() {
//        tv_submit.onClick {
//            if(tv_lift_num.text.equals("请选择电梯")||tv_lift_num.text.equals("")){
//                showToast("请选择电梯")
//            }else{
//                val intent = Intent()
//                intent.setClass(act, AddWorkOrderDetailActivity::class.java)
//                var bundle = Bundle()
//                bundle.putSerializable("maintenanceInfo",maintenance_list.get(index))
//                intent.putExtras(bundle)
//                startActivity(intent)
//                finish()
//            }
//        }
        title_layout.visibility = GONE
        getMaintListByRepair()

        lv_list_maintenance_order.setOnItemClickListener { parent, view, position, id ->
           // view.setBackgroundColor(Color.GRAY)
//            tv_lift_num.text = maintenance_list.get(position).elevatorInfo.liftNum
//            index = position
            val intent = Intent()
            intent.setClass(act, AddWorkOrderDetailActivity::class.java)
            var bundle = Bundle()
            bundle.putSerializable("maintenanceInfo",maintenance_list.get(position))
            intent.putExtras(bundle)
            startActivity(intent)
            finish()
        }

    }
    override fun onResume() {
        super.onResume()
        tv_lift_num.text = ""
        getMaintListByRepair()
    }
    private fun getMaintListByRepair(){
        var contractInfoRequest = ContractInfoRequest()
        var body = ContractInfoRequest().ContractInfoBody()
        body.roleId = config.roleId
        contractInfoRequest.body = body
        contractInfoRequest.head = NewRequestHead().setaccessToken(config.token).setuserId(config.userId)
        var server = config.server + NetConstant.GET_MAINT_LIST_BY_REPAIR
        var netTask = object : NetTask(server, contractInfoRequest) {
            override fun onResponse(task: NetTask?, result: String?) {
                var response = MaintenanceInfoResponse.getRepairInfoResponse(result)
                maintenance_list = response.body
                val sdf = SimpleDateFormat("yyyy-MM-dd")
//                maintenance_list.sortByDescending {
//                    //sdf.parse(it.planTime).time
//                    //直接对比时间字符串即可
//                    it.planTime
//                }
                lv_list_maintenance_order.adapter = MaintenanceListAdapter(maintenance_list,act)
            }
        }
        addTask(netTask)
    }

}
