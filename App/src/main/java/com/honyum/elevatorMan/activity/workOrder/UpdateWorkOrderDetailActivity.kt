package com.honyum.elevatorMan.activity.workOrder

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.GONE
import android.widget.AdapterView
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import com.honyum.elevatorMan.R
import com.honyum.elevatorMan.adapter.BaseListViewAdapter
import com.honyum.elevatorMan.adapter.BaseViewHolder
import com.honyum.elevatorMan.adapter.ContractNameListAdapter
import com.honyum.elevatorMan.base.BaseActivityWraper
import com.honyum.elevatorMan.constant.IntentConstant
import com.honyum.elevatorMan.data.ContartInfo
import com.honyum.elevatorMan.data.MaintenanceInfo
import com.honyum.elevatorMan.data.PersonInfo
import com.honyum.elevatorMan.data.WorkOrderInfo
import com.honyum.elevatorMan.net.*
import com.honyum.elevatorMan.net.base.NetConstant
import com.honyum.elevatorMan.net.base.NetTask
import com.honyum.elevatorMan.utils.Utils
import com.honyum.elevatorMan.utils.ViewUtils
import kotlinx.android.synthetic.main.activity_add_work_order_detail.*
import org.jetbrains.anko.act
import org.jetbrains.anko.alert
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class UpdateWorkOrderDetailActivity : BaseActivityWraper() {

    var datePicker: DatePicker? = null

    companion object {
        var  REQUESTPERSON = 1
        var  SCUESS = 2
    }

    var timePicker: TimePicker? = null

    var dialogLayout: View? = null

    var alertDialog: AlertDialog? = null

    var s1: String = ""
    var s2: String = ""

    var date: Date = Date()

    var isTimePass: Boolean = false

    var workOrderInfo: WorkOrderInfo = WorkOrderInfo()

    var contractInfo_list:MutableList<ContartInfo> = ArrayList<ContartInfo>()

    var contract_name_array :Array<String> ?= null

    var is_need_part:Int = 0

    override fun getLayoutID(): Int {
        return R.layout.activity_add_work_order_detail
    }

    override fun getTitleString(): String {
        return "下一次维保信息"
    }

    private  var currPerson: PersonInfo = PersonInfo()


    private var mainPersonChanged: Boolean = false

    fun initSelectDialog(body: MutableList<PersonInfo>)
    {
        var items:Array<String> = body.map { it.userName }.toTypedArray()

//        body.forEachIndexed { index, personInfo ->
//            items[index] = personInfo.userName
//        }

        select_person.onClick {
            mainPersonChanged = true
            var dialog = AlertDialog.Builder(this@UpdateWorkOrderDetailActivity,R.style.dialogStyle)
            dialog.let {
                it.setItems(items, { _, which ->
                    currPerson = mainPersonList?.get(which)!!
                    tv_person.text = currPerson.userName
                })
             it.show()
            }
        }
    }


    private var persons: List<PersonInfo> =  arrayListOf()

    private var assChanged: Boolean = false

    override fun initView() {
        var bundle: Bundle = intent.extras
        workOrderInfo = bundle.getSerializable("workOrderInfo") as WorkOrderInfo
        persons = (bundle.getSerializable("persons") as List<PersonInfo>).filterNot {
            it.userId == workOrderInfo.assistantId
        }
        tv_person.text = workOrderInfo.assistantName
        currPerson = PersonInfo()
        currPerson.userId = workOrderInfo.assistantId
        currPerson.userName = workOrderInfo.assistantName
        contract_info.visibility = GONE
        if (persons.isNotEmpty())
        {

            if (persons.isNotEmpty()) {
                listData = persons.toMutableList()
                lv_person.visibility = View.VISIBLE
                adapter = SelectEleAdapter2(listData, act)
                lv_person.adapter = adapter
                lv_person.setOnTouchListener { v, event ->
                    if (event.action == MotionEvent.ACTION_UP) {
                        //允许ScrollView截断点击事件，ScrollView可滑动
                        scroll.requestDisallowInterceptTouchEvent(false)
                    } else {
                        //不允许ScrollView截断点击事件，点击事件由子View处理
                        scroll.requestDisallowInterceptTouchEvent(true)
                    }
                    false
                }
                // scroll.requestDisallowInterceptTouchEvent(true)
                lv_person.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                    listData.removeAt(position)
                    adapter.notifyDataSetChanged()
                }
            }
        }
        getSelectInfo()
        select_person_ass.onClick {
            assChanged = true;
            if (TextUtils.isEmpty(currPerson.userId) || TextUtils.isEmpty(workOrderInfo.elevatorInfo.id))
            {
                showToast("请先选择主要负责人")
                return@onClick
            }
            var intent = Intent(this@UpdateWorkOrderDetailActivity, SelectMaintenancePersonMutiActivity::class.java)
            intent.putExtra(IntentConstant.INTENT_ID,workOrderInfo.elevatorInfo.id)
            intent.putExtra(IntentConstant.INTENT_DATA,currPerson.userId)
            startActivityForResult(intent,REQUESTPERSON)
        }
        tv_start_time.text  = workOrderInfo.expectMaintainStartDate
        last_time.text = workOrderInfo.lastMaintainDate
        tv_start_time.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                tv_end_time.text = Utils.addDateInval(s.toString() + ":00", 1, 2)
            }

        })
        tv_end_time.text = workOrderInfo.expectMaintainEndDate
        tv_submit.onClick {
//            if(et_name.text.toString().equals("")){
//                showToast("请输入工单名称")
//                return@onClick
//            }

//            if(et_description.text.toString().equals("")){
//                showToast("请输入维保内容")
//                return@onClick
//            }

            if(is_need_part.equals("1")){
                if(tv_need_part_time.text.toString().equals("")){
                    showToast("请选择需要配件时间")
                    return@onClick
                }
            }
            if (TextUtils.isEmpty(tv_person.text))
            {
                showToast("请选择负责人！")

                return@onClick
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


//        switch_need_part.setOnCheckedChangeListener { buttonView, isChecked ->
//            if(isChecked){
//                linear_need_time.visibility = View.VISIBLE
//                is_need_part = 1
//                tv_need_part_time.text = getTime()
//            }else{
//                linear_need_time.visibility = View.INVISIBLE
//                is_need_part = 0
//            }
//        }
        switch_need_part.onClick {
            alert("程序猿正在努力ing，敬请期待！") {
                title = "提示"
            }.show()
        }
        //getContractInfo()
    }


    //获取系统时间
    private fun getTime():String {
        val sDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = sDateFormat.format(Date())
        return date
    }


    private fun getTime1():String {
        val sDateFormat = SimpleDateFormat("yyyyMMddhhmmss")
        val date = sDateFormat.format(Date())
        return date
    }


    private lateinit var selectTime: TextView

    private  fun initDialog(tv_time: TextView){

        dialogLayout = LayoutInflater.from(this).inflate(R.layout.dia_datetime_layout, null)

        datePicker = dialogLayout!!.findViewById(R.id.datePicker) as DatePicker

        timePicker = dialogLayout!!.findViewById(R.id.timePicker) as TimePicker
        ViewUtils.resizePikcer(datePicker)
        ViewUtils.resizePikcer(timePicker)
        selectTime = dialogLayout!!.findViewById(R.id.select_time) as TextView
        selectTime.onClick {
            datePicker!!.visibility= GONE
            timePicker!!.visibility = View.VISIBLE
        }
        timePicker?.setIs24HourView(true)
        timePicker?.currentHour = date.getHours() + 1
        timePicker?.currentMinute = 0
        val minute = timePicker?.currentMinute
        val hour = timePicker?.currentHour
        s2 = " " + (if (hour?.let { it<10 }!!) "0" + hour else hour) + ":" + if (minute?.let { it<10 }!!) "0" + minute else minute
        timePicker?.setOnTimeChangedListener { view, hourOfDay, minute -> s2 = " " + (if (hourOfDay?.let { it<10 }!!) "0" + hourOfDay else hourOfDay) + ":" + if (minute < 10) "0" + minute else minute }
        alertDialog = AlertDialog.Builder(this,R.style.dialogStyle).setTitle("选择时间").setView(dialogLayout).setPositiveButton("确定"
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

    private  var mainPersonList: MutableList<PersonInfo>? = null

    private fun getSelectInfo()
    {
        var request = SelectRequest()

        var body = request.SelectRequestBody()

        body.elevatorId = workOrderInfo.elevatorInfo.id

        request.body = body

        request.head = NewRequestHead().setuserId(config.userId).setaccessToken(config.token)

        var server = config.server+ NetConstant.GETSELECT

        var netTask =
            object : NetTask(server,request) {
                override fun onResponse(task: NetTask?, result: String?) {

                    var response = WorkOrderPersonResponse.getResponse<WorkOrderPersonResponse>(result, WorkOrderPersonResponse::class.java)
                    mainPersonList = response.body
                    if (response.body!=null) {
                        if(mainPersonList?.size == 1)
                        {
                            tv_person.text = mainPersonList?.get(0)?.userName
                            currPerson = mainPersonList?.get(0)!!
                        }
                        initSelectDialog(response.body)
                    }
                }

            }
        addBackGroundTask(netTask)

    }

    private fun getContractInfo(){
        var contractInfoRequest = ContractInfoRequest()
        var body = ContractInfoRequest().ContractInfoBody()
        //body.roleId = config.roleId
        body.branchId = workOrderInfo.elevatorInfo.branchId
       // body.communityId = maintenanceInfo.elevatorInfo.communityId
        body.elevatorId = workOrderInfo.elevatorInfo.id
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

    private fun addWorkOrderInfo(){
        var addWorkOrderInfoRequest = AddWorkOrderInfoRequest()
        var body = AddWorkOrderInfoRequest().AddWorkOrderInfoBody()
        //body.roleId = config.roleId
        var ids = ""
        listData.forEach {
            ids+=(it.userId+",")
        }
        if (ids.length>1)
        ids =  ids.subSequence(0,ids.length-1).toString()

        body.bizType = "1"
        body.orderName = et_name.text.toString()
        body.bizId = workOrderInfo.id
        body.branchId = workOrderInfo.elevatorInfo.branchId
        body.communityId = workOrderInfo.elevatorInfo.communityId
        body.elevatorId = workOrderInfo.elevatorInfo.id
        if(contractInfo_list.size==0){

        }else{
            body.contractId = contractInfo_list.get(spinner_contract_name.selectedItemPosition).id
            body.propertyBranchId = contractInfo_list.get(spinner_contract_name.selectedItemPosition).branchId
        }

        body.isNeedParts = is_need_part

        if(is_need_part==1){
            body.partsNeedDate = tv_need_part_time.text.toString()
        }

        body.workOrderId = workOrderInfo.id
        body.partsNeedDate = tv_need_part_time.text.toString()

        body.expectMaintainStartDate = tv_start_time.text.toString()
        body.expectMaintainEndDate = tv_end_time.text.toString()
        body.orderContent = et_description.text.toString()
        body.createUserName = config.name
        body.createUserTel = config.tel

        body.assistantId = currPerson.userId

        body.workId = ids


        addWorkOrderInfoRequest.body = body
        addWorkOrderInfoRequest.head = NewRequestHead().setaccessToken(config.token).setuserId(config.userId)
        var server = config.server + NetConstant.EDIT_WORK_ORDER
        var netTask = object : NetTask(server, addWorkOrderInfoRequest) {
            override fun onResponse(task: NetTask?, result: String?) {
                /*var response = MaintenanceInfoResponse.getRepairInfoResponse(result)
                maintenance_list = response.body
                lv_list_maintenance_order.adapter = MaintenanceListAdapter(maintenance_list,applicationContext)*/
                /*var response = ContractInfoDetailResponse.getContratInfoResponse(result)
                contractInfo = response.body.contract*/
                showToast("制定成功！")
                finish()
            }
        }
        addTask(netTask)
    }

    private  var listData: MutableList<PersonInfo> = arrayListOf()

    private lateinit var adapter: SelectEleAdapter2

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == SCUESS) {
            listData = data?.getSerializableExtra(IntentConstant.INTENT_DATA) as MutableList<PersonInfo>
            if (listData.size>0) {
                lv_person.visibility = View.VISIBLE
                adapter = SelectEleAdapter2(listData, act)
                lv_person.adapter = adapter
                lv_person.setOnTouchListener { v, event ->
                    if (event.action == MotionEvent.ACTION_UP) {
                        //允许ScrollView截断点击事件，ScrollView可滑动
                        scroll.requestDisallowInterceptTouchEvent(false)
                    } else {
                        //不允许ScrollView截断点击事件，点击事件由子View处理
                        scroll.requestDisallowInterceptTouchEvent(true)
                    }
                     false
                }
               // scroll.requestDisallowInterceptTouchEvent(true)
                lv_person.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                    listData.removeAt(position)
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

}

class SelectEleAdapter2(datas: MutableList<PersonInfo>?, context: Context?, layoutId: Int = R.layout.item_select_eles) : BaseListViewAdapter<PersonInfo>(datas, context, layoutId) {
    override fun bindData(holder: BaseViewHolder?, t: PersonInfo?, index: Int) {
        holder?.setText(R.id.num, t?.userName)?.setText(R.id.address, t?.userTel)?.setVisible(R.id.image, false)?.setVisible(R.id.image_cancel, true)
    }
}