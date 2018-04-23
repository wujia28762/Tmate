package com.honyum.elevatorMan.activity.repair

import android.content.Intent
import android.view.View
import android.view.View.GONE
import android.widget.AdapterView
import com.honyum.elevatorMan.R
import com.honyum.elevatorMan.adapter.RepairListAdapter
import com.honyum.elevatorMan.base.BaseActivityWraper
import com.honyum.elevatorMan.data.MaintRecInfo
import com.honyum.elevatorMan.data.RepairInfo
import com.honyum.elevatorMan.net.NewRequestHead
import com.honyum.elevatorMan.net.RepairInfoRequest
import com.honyum.elevatorMan.net.RepairInfoResponse
import com.honyum.elevatorMan.net.base.NetConstant
import com.honyum.elevatorMan.net.base.NetTask
import kotlinx.android.synthetic.main.activity_repair_list.*
import org.jetbrains.anko.act
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity
import java.util.*

class RepairListActivity : BaseActivityWraper() {

    var repair_list:MutableList<RepairInfo> = ArrayList<RepairInfo>()
    var isManager:Boolean = false


    override fun getLayoutID(): Int {
        return R.layout.activity_repair_list
    }

    override fun getTitleString(): String {
        return "电梯报修"
    }
    override fun initView() {
        tv_repair.onClick {
            val intent = Intent()
            intent.setClass(act, RepairWayActivity::class.java)
            startActivity(intent)
        }
        if (intent.hasExtra("Info")) {
            repair_list = intent.getSerializableExtra("Info") as MutableList<RepairInfo>
            lv_list_repair.adapter = RepairListAdapter(repair_list,act)
            tv_repair.visibility= GONE
            isManager  = true
            lv_list_repair.setOnItemClickListener { parent, view, position, id ->
                startActivity<RepairDetailActivity>("repair" to repair_list[position])
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (!isManager)
        requestRepairList()
    }

    private fun requestRepairList(){
        var repairInfoRequest = RepairInfoRequest()
        var body = RepairInfoRequest().RepairInfoBody()
        body.roleId = config.roleId
        repairInfoRequest.body = body
        repairInfoRequest.head = NewRequestHead().setaccessToken(config.token).setuserId(config.userId)
        var server = config.server + NetConstant.GET_BAOXIU_BY_ROIE_ID
        var netTask = object : NetTask(server, repairInfoRequest) {
            override fun onResponse(task: NetTask?, result: String?) {
                var response = RepairInfoResponse.getRepairInfoResponse(result)
                repair_list = response.body
                lv_list_repair.adapter = RepairListAdapter(repair_list,act)
            }
        }
        addTask(netTask)
    }
}
