package com.honyum.elevatorMan.activity.repair

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.honyum.elevatorMan.R
import com.honyum.elevatorMan.activity.workOrder.MaintenanceWorkOrderDetailActivity
import com.honyum.elevatorMan.adapter.ContractNameListAdapter
import com.honyum.elevatorMan.adapter.RepairPicListAdapter
import com.honyum.elevatorMan.base.BaseActivityWraper
import com.honyum.elevatorMan.base.BaseFragmentActivity
import com.honyum.elevatorMan.base.ListItemCallback
import com.honyum.elevatorMan.constant.IntentConstant
import com.honyum.elevatorMan.data.ContartInfo
import com.honyum.elevatorMan.data.FaultTypeInfo
import com.honyum.elevatorMan.data.RepairInfo
import com.honyum.elevatorMan.net.*
import com.honyum.elevatorMan.net.base.NetConstant
import com.honyum.elevatorMan.net.base.NetTask
import com.honyum.elevatorMan.utils.Utils
import com.honyum.elevatorMan.utils.ViewUtils
import kotlinx.android.synthetic.main.activity_repair_detail.*
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.act
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class RepairDetailActivity : BaseActivityWraper(), ListItemCallback<String> {
    private var isManager: Boolean = false
    var contractInfo_list: MutableList<ContartInfo> = ArrayList<ContartInfo>()
    override fun performItemCallback(data: String?) {
        screen_img.visibility = View.VISIBLE
        Glide.with(this).load(data).into(screen_img)
        screen_img.onClick {
            it?.visibility = View.GONE
        }
    }

    var state: String = ""

    override fun getLayoutID(): Int {
        return R.layout.activity_repair_detail
    }

    override fun getTitleString(): String {
        return "详情"
    }
    private fun requestSaveRepairProcess(repairInfo:RepairInfo ) {

        val saveRepairRemark = SaveRequestRemarkRequest()
        val body = SaveRequestRemarkRequest().SaveProcessBody()
        body.state =  "3"
        body.baoxiuId = repairInfo.id
        body.branchId = repairInfo.communityInfo.branchId
        body.errorElevatorId = repairInfo.elevatorInfo.liftNum

        saveRepairRemark.body = body
        saveRepairRemark.head = NewRequestHead().setaccessToken(config.token).setuserId(config.userId)

        val server = config.server + NetConstant.SAVE_BAOXIU_PROCESS
        val netTask = object : NetTask(server, saveRepairRemark) {
            override fun onResponse(task: NetTask?, result: String?) {
                showToast("提交成功")
                //finish()
            }
        }
        addTask(netTask)
    }
    override fun initView() {
        var bundle = intent.extras
        var repairInfo: RepairInfo = bundle.getSerializable("repair") as RepairInfo
        isManager = bundle.getBoolean("isManager")
        if (isManager)
            linear_process.visibility = GONE
        tv_communityName.text = repairInfo.communityInfo.name
        tv_lifuNum.text = repairInfo.elevatorInfo.liftNum
        tv_branchName.text = repairInfo.communityInfo.branchName
        //  tv_propertyUname.text = repairInfo.communityInfo.propertyUname
        tv_repair_name.text = repairInfo.repairUserName
        tv_repair_tel.text = repairInfo.repairTel
        tv_fault_type.text = repairInfo.faultName
        if (repairInfo.type.equals("1")) {
            tv_type.text = "普通"
        } else if (repairInfo.type.equals("2")) {
            tv_type.text = "紧急"
        }
        tv_fault_description.text = repairInfo.description
        var pic_list: List<String> = repairInfo.pic.split(",")
        grid_view.adapter = RepairPicListAdapter(pic_list, this)

        tv_submit.onClick {
            if (repairInfo.state.equals("0")) {
                confirm_repair_process("0.5", repairInfo)
            } else if (repairInfo.state.equals("0.5")) {
                request_save_repair_process("1", repairInfo)
            } else if (repairInfo.state.equals("1")) {
                request_save_repair_process("2", repairInfo)
            } else if (repairInfo.state.equals("2")) {
//                val intent = Intent()
//                intent.setClass(act, AddWorkOrderRepairDetailActivity::class.java)
//                var bundle = Bundle()
//                bundle.putString("state", "3")
//                bundle.putSerializable("repairInfo", repairInfo)
//                intent.putExtras(bundle)
//                startActivity(intent)
//                finish()

                // show popwindow
//                if(repairInfo.)
//                requestSaveRepairProcess(repairInfo)
                getContractInfo(repairInfo)

            } else if ("3" == repairInfo.state) {
                getWorkOrderInfo(repairInfo.type,repairInfo.id)

               // getContractInfo(repairInfo)
            }
        }

        tv_accident.onClick {
            val intent = Intent()
            //获取intent对象
            intent.setClass(act, RemarkDetailActivity::class.java)
            var bundle = Bundle()
            bundle.putString("state", "4")
            bundle.putSerializable("repairInfo", repairInfo)
            intent.putExtras(bundle)
            startActivity(intent)
            finish()
        }


        if (repairInfo.state == "-1"  || repairInfo.state == "4" ||repairInfo.back == "1") {
            linear_process.visibility = View.INVISIBLE
        } else {
            // showToast(repairInfo.workName+"==="+config.name+"==="+repairInfo.state)
            if (repairInfo.workName == config.name) {
                when {
                    repairInfo.state == "0" -> act.find<TextView>(R.id.tv_submit).text = "确认"
                    repairInfo.state == "0.5" -> act.find<TextView>(R.id.tv_submit).text = "出发"
                    repairInfo.state == "1" -> act.find<TextView>(R.id.tv_submit).text = "到达"
                    repairInfo.state == "2" -> {
                        act.find<TextView>(R.id.tv_submit).text = "生成维修工单"
                        tv_accident.setTextColor(Color.GRAY)
                        tv_accident.isClickable = false
                    }
                    repairInfo.state == "3" -> {
                        act.find<TextView>(R.id.tv_submit).text = "查看工单详情"
                        tv_accident.setTextColor(Color.GRAY)
                        tv_accident.isClickable = false
                    }
                }
            } else {
                linear_process.visibility = View.INVISIBLE
            }
        }
    }

    fun getWorkOrderInfo(type: String?, id: String?): Unit {

        var request = FixPlanSingeRequest()
        var body = request.FixPlanSingeRequestBody()
        body.bizId = id
        body.bizType = "2"

        var head = NewRequestHead().setuserId((act as BaseFragmentActivity).config.userId).setaccessToken((act as BaseFragmentActivity).config.token)
        request.body = body
        request.head = head

        var netTask = object : NetTask(config.server+NetConstant.GETWORKORDERBYBIZIDORBIZTYPE,request) {
            override fun onResponse(task: NetTask?, result: String?) {

                var response = WorkOrderInfoSingleResponse.getRepairInfoResponse(result)

                startActivity<MaintenanceWorkOrderDetailActivity>("workOrderInfo" to response.body)
            }

        }
        addTask(netTask)
    }

    var datePicker: DatePicker? = null
    private var fault_type_list: MutableList<FaultTypeInfo> = ArrayList<FaultTypeInfo>()
    var timePicker: TimePicker? = null

    var dialogLayout: View? = null

    var alertDialog: AlertDialog? = null

    var s1: String = ""
    var s2: String = ""

    var date: Date = Date()

    var isTimePass: Boolean = false
    private lateinit var selectTime: TextView

    private  fun initDialog(tv_time: TextView){

        dialogLayout = LayoutInflater.from(this).inflate(R.layout.dia_datetime_layout, null)

        datePicker = dialogLayout!!.findViewById(R.id.datePicker) as DatePicker

        timePicker = dialogLayout!!.findViewById(R.id.timePicker) as TimePicker
        selectTime = dialogLayout!!.findViewById(R.id.select_time) as TextView
        selectTime.onClick {
            datePicker!!.visibility= GONE
            timePicker!!.visibility = View.VISIBLE
        }
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

    fun request_save_repair_process(state: String, repairInfo: RepairInfo) {
        var saveRepairRemark = SaveRequestRemarkRequest()
        var body = SaveRequestRemarkRequest().SaveProcessBody()
        body.state = state
        body.baoxiuId = repairInfo.id
        body.branchId = repairInfo.communityInfo.branchId
        //  body.remark = et_remark.text.toString()
        saveRepairRemark.body = body
        saveRepairRemark.head = NewRequestHead().setaccessToken(config.token).setuserId(config.userId)

        var server = config.server + NetConstant.SAVE_BAOXIU_PROCESS
        var netTask = object : NetTask(server, saveRepairRemark) {
            override fun onResponse(task: NetTask?, result: String?) {
                showToast("提交成功")
                finish()
            }
        }
        addTask(netTask)
    }

    var is_need_part: Int = 0
    lateinit var liftNum: EditText
    lateinit var spinnerName: Spinner
    lateinit var needTime: RelativeLayout
    lateinit var tvNeedTime: TextView
    lateinit var startTime: LinearLayout
    lateinit var endTime: LinearLayout
    lateinit var tvStartTime: TextView
    lateinit var tvEndTime: TextView
    lateinit var tv_submit1: TextView


    private fun show1(repairInfo: RepairInfo) {
        var button1Dialog: Dialog = Dialog(this, R.style.BottomDialog)
        var view: View = LayoutInflater.from(this).inflate(R.layout.dialog_insure_fix, null)
        button1Dialog.setContentView(view)
        var layoutParams: ViewGroup.LayoutParams = view.layoutParams
        layoutParams.width = resources.displayMetrics.widthPixels
        view.layoutParams = layoutParams
        button1Dialog.window.setGravity(Gravity.BOTTOM)
        button1Dialog.window.setWindowAnimations(R.style.BottomDialogAnimation)
        liftNum = view.find(R.id.et_lift_mun)
        liftNum.setText(repairInfo.elevatorInfo.liftNum)
        spinnerName = view.find(R.id.spinner_contract_name)
        spinnerName.adapter = ContractNameListAdapter(contractInfo_list, act)
        tvStartTime = view.find(R.id.tv_start_time)
        tvStartTime.text= Utils.dateTimeToString(Date())



        tvEndTime = view.find(R.id.tv_end_time)
        tvEndTime.text= Utils.addDateInval(Utils.dateTimeToString(Date()), 1, 2)

        tvStartTime.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                tvEndTime.text = Utils.addDateInval(s.toString()+":00", 1, 2)
            }

        })
        needTime = view.find(R.id.linear_need_time)
        startTime = view.find(R.id.linear_start_time)
        endTime = view.find(R.id.linear_end_time)
        tvNeedTime = view.find(R.id.tv_need_part_time)
        tvNeedTime.text = Utils.dateTimeToString(Date())
        tv_submit1 = view.find(R.id.tv_submit)
//        view.find<Switch>(R.id.switch_need_part).setOnCheckedChangeListener { buttonView, isChecked ->
//            if (isChecked) {
//                needTime.visibility = View.VISIBLE
//                is_need_part = 1
//                tvNeedTime.text = Utils.dateTimeToString(Date())
//            } else {
//                needTime.visibility = View.INVISIBLE
//                is_need_part = 0
//            }
//        }
        view.find<Switch>(R.id.switch_need_part).onClick {
            alert("程序猿正在努力ing，敬请期待！") {
                title = "提示"
            }.show()
        }
        startTime.onClick {
            initDialog(tvStartTime)
        }

        tv_submit1.onClick {
            if(is_need_part.equals("1")){
                if(tvNeedTime.text.toString().equals("")){
                    showToast("请选择需要配件时间")
                    return@onClick
                }
            }

            if(tvStartTime.text.toString().equals("")){
                showToast("请选择开始维保时间")
                return@onClick
            }

            if(tvEndTime.text.toString().equals("")){
                showToast("请选择结束维保时间")
                return@onClick
            }
            if (button1Dialog.isShowing)
            {
                button1Dialog.dismiss()
            }
            addWorkOrderInfo(repairInfo)
        }
        endTime.onClick {
            initDialog(tvEndTime)
        }

        needTime.onClick {
            initDialog(tvNeedTime)
        }
        button1Dialog.show()
    }
    private fun addWorkOrderInfo(repairInfo: RepairInfo){
        var addWorkOrderInfoRequest = AddWorkOrderInfoRequest()
        var body = AddWorkOrderInfoRequest().AddWorkOrderInfoBody()
        //body.roleId = config.roleId
        body.bizType = "2"
        body.baoxiuId = repairInfo.id
        body.liftNum = repairInfo.elevatorInfo.liftNum
        //body.orderName = et_name.text.toString()
        body.bizId = repairInfo.id
        body.branchId = config.branchId
        body.communityId = repairInfo.communityInfo.id
        body.elevatorId = repairInfo.elevatorInfo.id
        if(contractInfo_list.size == 0){

        }else{
            body.contractId = contractInfo_list.get(spinnerName.selectedItemPosition).id
            body.propertyBranchId = contractInfo_list.get(spinnerName.selectedItemPosition).branchId
        }

        body.isNeedParts = is_need_part
        if(is_need_part==1){
            body.partsNeedDate = tvNeedTime.text.toString()
        }

        body.partsNeedDate = tvNeedTime.text.toString()

        body.expectMaintainStartDate = tvStartTime.text.toString()
        body.expectMaintainEndDate = tvEndTime.text.toString()
        body.bizCode = repairInfo.code
        body.createUserName = config.name
        body.createUserTel = config.tel

        addWorkOrderInfoRequest.body = body
        addWorkOrderInfoRequest.head = NewRequestHead().setaccessToken(config.token).setuserId(config.userId)
        var server = config.server + NetConstant.ADD_WORK_ORDER
        var netTask = object : NetTask(server, addWorkOrderInfoRequest) {
            override fun onResponse(task: NetTask?, result: String?) {

                var response = RepairOrderResponse.getResponse<RepairOrderResponse>(result,RepairOrderResponse::class.java)
                var body = response.body
                if (body.isNotBlank()) {
                    /*var response = MaintenanceInfoResponse.getRepairInfoResponse(result)
                maintenance_list = response.body
                lv_list_maintenance_order.adapter = MaintenanceListAdapter(maintenance_list,applicationContext)*/
                    /*var response = ContractInfoDetailResponse.getContratInfoResponse(result)
                contractInfo = response.body.contract*/
                    showToast("添加成功")
                    startActivity<MaintenanceWorkOrderDetailActivity>(IntentConstant.INTENT_ID to body)
                    finish()
                }
                else
                    showToast("添加工单失败")
            }
        }
        addBackGroundTask(netTask)
    }
    private fun getContractInfo(repairInfo: RepairInfo) {
        var contractInfoRequest = ContractInfoRequest()
        var body = ContractInfoRequest().ContractInfoBody()
        //body.roleId = config.roleId
        body.branchId = config.branchId
        body.baoxiuId = repairInfo.id
        body.liftNum = repairInfo.elevatorInfo.liftNum
        // body.communityId = maintenanceInfo.elevatorInfo.communityId
        body.elevatorId = repairInfo.elevatorInfo.id
        contractInfoRequest.body = body
        contractInfoRequest.head = NewRequestHead().setaccessToken(config.token).setuserId(config.userId)
        var server = config.server + NetConstant.GET_CONTRACT
        var netTask = object : NetTask(server, contractInfoRequest) {
            override fun onResponse(task: NetTask?, result: String?) {
                var response = ContractInfoResponse.getContratInfoResponse(result)
                contractInfo_list = response.body
                //finish()
                show1(repairInfo)
            }
        }
        addTask(netTask)
    }

    fun confirm_repair_process(state: String, repairInfo: RepairInfo) {
        var saveRepairRemark = SaveRequestRemarkRequest()
        var body = SaveRequestRemarkRequest().SaveProcessBody()
        //body.state = state
        body.baoxiuId = repairInfo.id
        // body.branchId = repairInfo.communityInfo.branchId
        //  body.remark = et_remark.text.toString()
        saveRepairRemark.body = body
        saveRepairRemark.head = NewRequestHead().setaccessToken(config.token).setuserId(config.userId)

        var server = config.server + NetConstant.CONFIRM_BAOXIU_PROCESS
        var netTask = object : NetTask(server, saveRepairRemark) {
            override fun onResponse(task: NetTask?, result: String?) {
                showToast("已确认")
                finish()
            }
        }
        addTask(netTask)
    }
}