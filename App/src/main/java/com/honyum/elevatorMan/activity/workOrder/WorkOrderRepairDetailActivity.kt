package com.honyum.elevatorMan.activity.workOrder

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.DatePicker
import android.widget.ScrollView
import android.widget.TextView
import android.widget.TimePicker
import com.hanbang.netsdk.Log
import com.honyum.elevatorMan.R
import com.honyum.elevatorMan.adapter.ContractNameListAdapter
import com.honyum.elevatorMan.adapter.FaultTypeListAdapter
import com.honyum.elevatorMan.base.BaseActivityWraper
import com.honyum.elevatorMan.data.ContartInfo
import com.honyum.elevatorMan.data.FaultTypeInfo
import com.honyum.elevatorMan.data.RepairInfo
import com.honyum.elevatorMan.net.*
import com.honyum.elevatorMan.net.base.NetConstant
import com.honyum.elevatorMan.net.base.NetTask
import com.honyum.elevatorMan.utils.Utils
import com.honyum.elevatorMan.utils.ViewUtils
import kotlinx.android.synthetic.main.activity_add_work_order_repair_detail.*
import org.jetbrains.anko.act
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.sdk25.coroutines.onTouch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class WorkOrderRepairDetailActivity : BaseActivityWraper() {

    var datePicker: DatePicker? = null
    private var fault_type_list: MutableList<FaultTypeInfo> = ArrayList<FaultTypeInfo>()
    var timePicker: TimePicker? = null

    var dialogLayout: View? = null

    var alertDialog: AlertDialog? = null

    var s1: String = ""
    var s2: String = ""

    var date: Date = Date()

    var isTimePass: Boolean = false

    var repairInfo: RepairInfo = RepairInfo()

    var contractInfo_list:MutableList<ContartInfo> = ArrayList<ContartInfo>()

    var contract_name_array :Array<String> ?= null

    var is_need_part:Int = 0

    //获取故障类型
    private fun requestFault() {
        var contractInfoRequest = ContractInfoRequest()
        contractInfoRequest.head = NewRequestHead().setaccessToken(config.token).setuserId(config.userId)
        var server = config.server + NetConstant.GET_REPAIR_TYPE_LIST
        var netTask = object : NetTask(server, contractInfoRequest) {
            override fun onResponse(task: NetTask?, result: String?) {
                Log.e("contract_result", result + "==========")
                var response = FaultTypeInfoResponse.getContratInfoResponse(result)
                fault_type_list = response.body
                spinner_fault_type.adapter = FaultTypeListAdapter(fault_type_list, act)
            }
        }
        addTask(netTask)
    }
    override fun getLayoutID(): Int {
        return R.layout.activity_add_work_order_repair_detail
    }

    override fun getTitleString(): String {
        return "报修工单"
    }

    override fun initView() {
        var bundle: Bundle = intent.extras
        repairInfo = bundle.getSerializable("repairInfo") as RepairInfo

        tv_start_time.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                tv_end_time.text = Utils.addDateInval(s.toString()+":00", 1, 2)
            }

        })
        tv_start_time.text = getTime()
        tv_end_time.text = Utils.addDateInval(getTime(), 1, 2)
        et_lift_mun.setText(repairInfo.elevatorInfo.liftNum)

        tv_submit.onClick {
//            if(et_name.text.toString().equals("")){
//                showToast("请输入工单名称")
//                return@onClick
//            }


            if(et_appearance.text.toString().equals("")){
                showToast("请输入故障现象")
                return@onClick
            }

            if(et_reason.text.toString().equals("")){
                showToast("请输入故障原因")
                return@onClick
            }
            if(et_deal.text.toString().equals("")){
                showToast("请输入处理方法")
                return@onClick
            }
            if(et_protect.text.toString().equals("")){
                showToast("请输入预防措施")
                return@onClick
            }

            if(is_need_part.equals("1")){
                if(tv_need_part_time.text.toString().equals("")){
                    showToast("请选择需要配件时间")
                    return@onClick
                }
            }

            if(tv_start_time.text.toString().equals("")){
                showToast("请选择开始维保时间")
                return@onClick
            }

            if(tv_end_time.text.toString().equals("")){
                showToast("请选择结束维保时间")
                return@onClick
            }
            addWorkOrderInfo()
        }


        linear_start_time.onClick {
            initDialog(tv_start_time)
        }

        linear_end_time.onClick {
            initDialog(tv_end_time)
        }

        linear_need_part_time.onClick {
            initDialog(tv_need_part_time)
        }


        switch_need_part.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                linear_need_time.visibility = View.VISIBLE
                is_need_part = 1
                tv_need_part_time.text = getTime()
            }else{
                linear_need_time.visibility = View.INVISIBLE
                is_need_part = 0
            }
        }
        getContractInfo()
    }

    //获取系统时间
    private fun getTime():String {
        val sDateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        val date = sDateFormat.format(Date())
        return date
    }
    private fun addWorkOrderInfo(){
        var addWorkOrderInfoRequest = AddWorkOrderInfoRequest()
        var body = AddWorkOrderInfoRequest().AddWorkOrderInfoBody()
        //body.roleId = config.roleId
        body.bizType = "2"
        body.orderName = et_name.text.toString()
        body.bizId = repairInfo.id
        body.branchId = config.branchId
        body.communityId = repairInfo.communityInfo.id
        body.elevatorId = repairInfo.elevatorInfo.id
        if(contractInfo_list.size == 0){

        }else{
            body.contractId = contractInfo_list.get(spinner_contract_name.selectedItemPosition).id
            body.propertyBranchId = contractInfo_list.get(spinner_contract_name.selectedItemPosition).branchId
        }

        body.isNeedParts = is_need_part
        if(is_need_part==1){
            body.partsNeedDate = tv_need_part_time.text.toString()
        }

        body.partsNeedDate = tv_need_part_time.text.toString()

        body.expectMaintainStartDate = tv_start_time.text.toString()
        body.expectMaintainEndDate = tv_end_time.text.toString()
        body.bizCode = repairInfo.code
        body.appearance = et_appearance.text.toString()
        body.reason = et_reason.text.toString()
        body.processResult = et_deal.text.toString()
        body.preventiveMeasure = et_protect.text.toString()
        body.createUserName = config.name
        body.createUserTel = config.tel

        addWorkOrderInfoRequest.body = body
        addWorkOrderInfoRequest.head = NewRequestHead().setaccessToken(config.token).setuserId(config.userId)
        var server = config.server + NetConstant.ADD_WORK_ORDER
        var netTask = object : NetTask(server, addWorkOrderInfoRequest) {
            override fun onResponse(task: NetTask?, result: String?) {
                /*var response = MaintenanceInfoResponse.getRepairInfoResponse(result)
                maintenance_list = response.body
                lv_list_maintenance_order.adapter = MaintenanceListAdapter(maintenance_list,applicationContext)*/
                /*var response = ContractInfoDetailResponse.getContratInfoResponse(result)
                contractInfo = response.body.contract*/
                showToast("添加成功")
                finish()
            }
        }
        addTask(netTask)
    }


    private fun getContractInfo(){
        var contractInfoRequest = ContractInfoRequest()
        var body = ContractInfoRequest().ContractInfoBody()
        //body.roleId = config.roleId
        body.branchId = config.branchId
        // body.communityId = maintenanceInfo.elevatorInfo.communityId
        body.elevatorId = repairInfo.elevatorInfo.id
        contractInfoRequest.body = body
        contractInfoRequest.head = NewRequestHead().setaccessToken(config.token).setuserId(config.userId)
        var server = config.server + NetConstant.GET_CONTRACT
        var netTask = object : NetTask(server, contractInfoRequest) {
            override fun onResponse(task: NetTask?, result: String?) {
                var response = ContractInfoResponse.getContratInfoResponse(result)
                contractInfo_list = response.body
                spinner_contract_name.adapter = ContractNameListAdapter(contractInfo_list, act)
            }
        }
        addTask(netTask)
    }

    private lateinit var scr_picker1: ScrollView

    private  fun initDialog(tv_time: TextView){

        dialogLayout = LayoutInflater.from(this).inflate(R.layout.dia_datetime_layout, null)

        datePicker = dialogLayout!!.findViewById(R.id.datePicker) as DatePicker


        timePicker = dialogLayout!!.findViewById(R.id.timePicker) as TimePicker

        ViewUtils.resizePikcer(datePicker)
        ViewUtils.resizePikcer(timePicker)
        timePicker?.setIs24HourView(true)
        timePicker?.currentHour = date.getHours() + 1
        timePicker?.currentMinute = 0
        val minute = timePicker?.currentMinute
        val hour = timePicker?.currentHour
        s2 = " " + (if (hour?.let { it<10 }!!) "0" + hour else hour) + ":" + if (minute?.let { it<10 }!!) "0" + minute else minute
        timePicker?.setOnTimeChangedListener { view, hourOfDay, minute -> s2 = " " + (if (hourOfDay?.let { it<10 }!!) "0" + hourOfDay else hourOfDay) + ":" + if (minute < 10) "0" + minute else minute }
        alertDialog = AlertDialog.Builder(this).setTitle("选择时间").setView(dialogLayout).setPositiveButton("确定"
        ) { dialog, arg1 ->
            var month = ""
            month = if ((datePicker?.month!! +1)>10) {
                ""+(datePicker?.month!! +1)
            } else {
                "0"+(datePicker?.month!! +1)
            }
            var day = ""
            day = if (datePicker?.dayOfMonth!!<10) {
                "0"+(datePicker?.dayOfMonth)
            } else {
                ""+(datePicker?.dayOfMonth)
            }
            s1 = datePicker?.year.toString() + "-" + month + "-" +day
            val dateString = s1 + s2+":00"
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            var d: Date
            try {
                isTimePass = false
                d = sdf.parse(dateString)
                val t = d.time
                val cl = System.currentTimeMillis()
                isTimePass = cl <= t
                tv_time.text = dateString
                dialog.dismiss()
            } catch (e: ParseException) {
                e.printStackTrace()
            }

        }.setNegativeButton("取消") { dialog, arg1 ->{}
            dialog.dismiss()
        }.show()
    }

}