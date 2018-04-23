package com.honyum.elevatorMan.activity.repair

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import com.honyum.elevatorMan.R
import com.honyum.elevatorMan.base.BaseActivityWraper
import com.honyum.elevatorMan.data.RepairInfo
import com.honyum.elevatorMan.net.NewRequestHead
import com.honyum.elevatorMan.net.SaveRequestRemarkRequest
import com.honyum.elevatorMan.net.base.NetConstant
import com.honyum.elevatorMan.net.base.NetTask
import kotlinx.android.synthetic.main.activity_remark_detail.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class RemarkDetailActivity : BaseActivityWraper() {

    private var state:String = ""
    private var repairInfo:RepairInfo = RepairInfo()

    //private var result:String = "1"


    var result_array:Array<String> = arrayOf("无需处理","误报","已处理","生成工单","其他")

    override fun getLayoutID(): Int {
        return R.layout.activity_remark_detail
    }

    override fun getTitleString(): String {
        return "备注详情"
    }

    override fun initView() {
        spinner_result.adapter = ArrayAdapter(this,R.layout.layout_spanner_item,result_array)

        spinner_result.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                //requestWorkOrder(spinner_keyword.selectedItem.toString(),et_keyword.text.toString(),spinner_state.selectedItem.toString())
               // if(spinner_result.)
            }
            override fun onNothingSelected(adapterView: AdapterView<*>) {
            }
        }


        var bundle = intent.extras
        repairInfo = bundle.getSerializable("repairInfo") as RepairInfo
        state = bundle.getString("state")
        et_lift_mun.setText(repairInfo.elevatorId)
        if(repairInfo.state.equals("2")){
            linear_result.visibility = View.VISIBLE
            linear_lift_num.visibility = View.VISIBLE
            val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
           // params.leftMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5f, resources.displayMetrics).toInt()

            linear_lift_num.layoutParams = params
            linear_result.layoutParams = params
        }

        tv_submit.onClick {
            if(et_remark.text.toString().equals("")){
                showToast("请输入详情")
                return@onClick
            }
            if(et_lift_mun.text.toString().equals("")){
                showToast("请输入电梯20位编号或者6位救援识别码")
                return@onClick
            }
            requestSaveRepairProcess(spinner_result.selectedItem.toString())
        }
    }

    private fun requestSaveRepairProcess(spinner_result:String){
        var result = "1"

        when (spinner_result) {
            "无需处理" -> result = "1"
            "误报" -> result = "2"
            "已处理" -> result = "3"
            "生成维修工单" -> result = "4"
            "其他" -> result = "5"
        }

        val saveRepairRemark = SaveRequestRemarkRequest()
        val body = SaveRequestRemarkRequest().SaveProcessBody()
        body.state = state
        body.baoxiuId = repairInfo.id
        body.branchId = repairInfo.communityInfo.branchId
        body.remark = et_remark.text.toString()

        if(repairInfo.state.equals("2")){
            body.result = result
            body.errorElevatorId = et_lift_mun.text.toString()
        }

        saveRepairRemark.body = body
        saveRepairRemark.head = NewRequestHead().setaccessToken(config.token).setuserId(config.userId)

        val server = config.server + NetConstant.SAVE_BAOXIU_PROCESS
        val netTask = object : NetTask(server, saveRepairRemark) {
            override fun onResponse(task: NetTask?, result: String?) {
                showToast("提交成功")
                finish()
            }
        }
        addTask(netTask)
    }
}
