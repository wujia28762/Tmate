package com.honyum.elevatorMan.fragment.workOorder

import android.app.AlertDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.*
import com.honyum.elevatorMan.R
import com.honyum.elevatorMan.activity.maintenance.NewMaintenanceActivity
import com.honyum.elevatorMan.activity.repair.RepairDetailActivity
import com.honyum.elevatorMan.activity.workOrder.MaintenanceWorkOrderDetailActivity
import com.honyum.elevatorMan.base.BaseFragmentActivity
import com.honyum.elevatorMan.data.PersonInfo
import com.honyum.elevatorMan.data.WorkOrderInfo
import com.honyum.elevatorMan.net.*
import com.honyum.elevatorMan.net.base.NetConstant
import com.honyum.elevatorMan.net.base.NetTask
import com.honyum.elevatorMan.utils.DialogUtil
import com.honyum.elevatorMan.utils.ViewUtils
import kotlinx.android.synthetic.main.activity_add_work_order_detail.*
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.startActivity
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class WorkOrderInfoFragment : Fragment() {
    var currId = ""
    var workOrderInfo:WorkOrderInfo = WorkOrderInfo()

    var is_need_part_0:Int = 0

    var is_need_part_1:Int = 1


    private fun getDetailInfo(type:String?, id:String?)
    {
        var request = FixPlanSingeRequest()
        var body = request.FixPlanSingeRequestBody()
        body.bizId = id
        body.bizType = type

        var head = NewRequestHead().setuserId((act as BaseFragmentActivity).config.userId).setaccessToken((act as BaseFragmentActivity).config.token)
        request.body = body
        request.head = head

        var netTask = object : NetTask((act as BaseFragmentActivity).config.server+NetConstant.GETBAOXIUORMAINTENANCEBYBIZID,request) {
            override fun onResponse(task: NetTask?, result: String?) {
                if (type == "1")
                {
                   var response =  MaintenanceSingleResponse.getResponse<MaintenanceSingleResponse>(result,MaintenanceSingleResponse::class.java)
                    if (response!=null&& response.body["maintenance"] !=null)
                    startActivity<NewMaintenanceActivity>("lift" to response.body["maintenance"]!!,"showImage" to false)
                }
                else if (type == "2")
                {

                    var response = FixPlanSingleResponse.getResponse<FixPlanSingleResponse>(result,FixPlanSingleResponse::class.java)
                    if (response!=null&&response.body["baoxiu"] !=null)
                        startActivity<RepairDetailActivity>("repair" to response.body["baoxiu"]!!)
                }
            }

        }
        (act as BaseFragmentActivity).addBackGroundTask(netTask)

    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        var bundle: Bundle = arguments
        workOrderInfo = bundle.getSerializable("workOrderInfo") as WorkOrderInfo
        var view: View = inflater!!.inflate(R.layout.work_order_info_fragment,null)
        initView(view)
        return view
    }

    private lateinit var tvEndTime: TextView

    private lateinit var tvStartTime: TextView

    fun initView(view:View){
        view.find<TextView>(R.id.tv_code).text = workOrderInfo.orderNum
        view.find<TextView>(R.id.tv_name).text = workOrderInfo.orderName
        //tv_type.text = workOrderInfo.bizType
        when {
            workOrderInfo.bizType.equals("1") -> view.find<TextView>(R.id.tv_type).text = "维保"
            workOrderInfo.bizType.equals("2") -> view.find<TextView>(R.id.tv_type).text = "维修"
            else -> view.find<TextView>(R.id.tv_type).text = "其他"
        }
        var code = view.find<TextView>(R.id.tv_num)
        var id = view.find<TextView>(R.id.tv_biz_code)
        if (workOrderInfo.bizType == "1")
        {
            code.text = "计划编号"
        }
        else if (workOrderInfo.bizType == "2")
        {
            code.text = "报修编号"
        }
        id.onClick {
            getDetailInfo(workOrderInfo.bizType,workOrderInfo.bizId)
        }

        id.text = workOrderInfo.bizCode
        currId = workOrderInfo.bizId!!


        if(workOrderInfo.isNeedParts == is_need_part_1){
            view.find<TextView>(R.id.tv_parts).text = "是"
            view.find<LinearLayout>(R.id.need_date).visibility = VISIBLE
        }else if(workOrderInfo.isNeedParts == is_need_part_0){
            view.find<TextView>(R.id.tv_parts).text = "否"
            view.find<LinearLayout>(R.id.need_date).visibility = GONE
        }

        view.find<TextView>(R.id.tv_need_time).text = workOrderInfo.partsNeedDate

        tvStartTime = view.find<TextView>(R.id.tv_start_time)
        tvStartTime.text = workOrderInfo.expectMaintainStartDate


        tvEndTime = view.find<TextView>(R.id.tv_end_time)
        tvEndTime.text = workOrderInfo.expectMaintainEndDate


        view.find<TextView>(R.id.tv_create_person).text = workOrderInfo.createUserName
        view.find<TextView>(R.id.tv_main_person).text = workOrderInfo.assistantName
        view.find<TextView>(R.id.tv_ass_person).text = workOrderInfo.workName


        view.find<TextView>(R.id.tv_create_time).text = workOrderInfo.createDate

        var state = "已完成"
        when {
            workOrderInfo.statusCode.equals("draft") -> state = "草稿"
            workOrderInfo.statusCode.equals("confirm") -> state = "待确认"
            workOrderInfo.statusCode.equals("deal") -> state = "待处理"
            workOrderInfo.statusCode.equals("dealing") -> state = "处理中"
            workOrderInfo.statusCode.equals("success") -> state = "已处理"
            workOrderInfo.statusCode.equals("cancel") -> state = "已取消"
            workOrderInfo.statusCode.equals("back") -> state = "已退回"
            workOrderInfo.statusCode.equals("finish") -> state = "已完成"
        }

        view.find<TextView>(R.id.final_date).text = workOrderInfo.lastMaintainDate
        view.find<TextView>(R.id.lastdate).text = workOrderInfo.preMaintainDate
        view.find<TextView>(R.id.tv_state).text = state
        view.find<TextView>(R.id.tv_sketched).text = workOrderInfo.orderContent

        if(workOrderInfo.statusCode.equals("success")||workOrderInfo.statusCode.equals("cancel")) {
            view.find<TextView>(R.id.edit_main).visibility =GONE
            view.find<TextView>(R.id.edit_ass).visibility =GONE
            view.find<LinearLayout>(R.id.ll_real_start_time).visibility = VISIBLE
            view.find<LinearLayout>(R.id.ll_real_end_time).visibility = VISIBLE
            view.find<LinearLayout>(R.id.ll_submit_time).visibility = VISIBLE
        }
        view.find<TextView>(R.id.tv_real_start_time).text = workOrderInfo.realMaintainStartDate
        view.find<TextView>(R.id.tv_real_end_time).text = workOrderInfo.realMaintainEndDate
        view.find<TextView>(R.id.tv_submit_time).text = workOrderInfo.maintainCommitDate



        if (workOrderInfo?.updateMsg.isNotBlank())
        {
            view.find<LinearLayout>(R.id.ll_tips).visibility = VISIBLE
            view.find<TextView>(R.id.date_tip).text = workOrderInfo?.updateMsg
        }
        if ("1" == workOrderInfo?.updateFlag) {
            var startTime = view.find<ImageView>(R.id.start_time)
            var endTime = view.find<ImageView>(R.id.end_time)
            startTime.visibility = VISIBLE
            endTime.visibility = VISIBLE
            startTime.onClick {
                tvStartTime.tag= "s"
                initDialog(tvStartTime)
            }
            endTime.onClick {
                tvEndTime.tag = "e"
                initDialog(tvEndTime)
            }
        }


        view.find<TextView>(R.id.edit_main).onClick {
            popMainInfo()
        }
        view.find<TextView>(R.id.edit_ass).onClick {
            popMainInfo1()
        }




    }

    var alertDialog: AlertDialog? = null
    var isTimePass: Boolean = false
    var s1: String = ""
    var s2: String = ""
    var date: Date = Date()
    var datePicker: DatePicker? = null
    private  var dialogLayout: View? = null
    var timePicker: TimePicker? = null
    private lateinit var scr_picker1: ScrollView

    private lateinit var selectTime: TextView

    private  fun initDialog(tv_time: TextView){

        dialogLayout = LayoutInflater.from(activity).inflate(R.layout.dia_datetime_layout, null)

        datePicker = dialogLayout!!.findViewById(R.id.datePicker) as DatePicker

        timePicker = dialogLayout!!.findViewById(R.id.timePicker) as TimePicker
        ViewUtils.resizePikcer(datePicker)
        ViewUtils.resizePikcer(timePicker)

        selectTime = dialogLayout!!.findViewById(R.id.select_time) as TextView
        selectTime.onClick {
            datePicker!!.visibility= GONE
            timePicker!!.visibility = VISIBLE
        }

        timePicker?.setIs24HourView(true)
        timePicker?.currentHour = date.getHours() + 1
        timePicker?.currentMinute = 0
        val minute = timePicker?.currentMinute
        val hour = timePicker?.currentHour
        s2 = " " + (if (hour?.let { it<10 }!!) "0" + hour else hour) + ":" + if (minute?.let { it<10 }!!) "0" + minute else minute
        timePicker?.setOnTimeChangedListener { view, hourOfDay, minute -> s2 = " " + (if (hourOfDay?.let { it<10 }!!) "0" + hourOfDay else hourOfDay) + ":" + if (minute < 10) "0" + minute else minute }
        alertDialog = AlertDialog.Builder(activity).setTitle("选择时间").setView(dialogLayout).setPositiveButton("确定"
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
                modifyTime(tv_time,dateString)
                dialog.dismiss()
            } catch (e: ParseException) {
                e.printStackTrace()
            }

        }.setNegativeButton("选择时间") { dialog, arg1 ->
            dialog.dismiss()
        }.show()

    }

    private fun modifyTime(textView:TextView,time:String)
    {
        var baseActivity = act as BaseFragmentActivity
        var request = ModifyOrderTimeRequest()
        var body = ModifyOrderTimeRequest.ModifyOrderTimeRequestBody()
        var head = NewRequestHead().setaccessToken(baseActivity.config.token).setuserId(baseActivity.config.userId)
        body.orderId = workOrderInfo.id
        if ("s" == textView.tag.toString()) {
            body.expectMaintainStartDate = time
            body.updateType = "expectMaintainStartDate"
        }
            else if ("e" == textView.tag.toString()) {
            body.expectMaintainEndDate = time
            body.updateType = "expectMaintainEndDate"
        }
        request.body = body
        request.head = head

        var netTask = object : NetTask(baseActivity.config.server+NetConstant.UPDATEWORKORDER,request) {
            override fun onResponse(task: NetTask?, result: String?) {
                textView.text = time
            }

        }
        baseActivity.addTask(netTask)

    }

    private fun popMainInfo()
    {
        getSelectInfo({

            var dialogUtil =   DialogUtil()
            dialogUtil.showSingleSelectDialog(act,"提示", items.toTypedArray(),
                    { dialog, which ->
                        editPerson("","",mainPersonList?.get(which)?.userId!!,mainPersonList?.get(which)?.userName!!,"assistant")
                        dialog.dismiss()
                    })
        })
        //
    }
    private fun popMainInfo1()
    {
        var resultBody = {
            mainPersonList?.forEach { it?.selected = false }
            //   checks = BooleanArray(items.size)
            var dialogUtil =   DialogUtil()
            dialogUtil.showMutiSelectDialog(act,"提示", items.toTypedArray(), "", "",
                    { _, which, isChecked ->
                        mainPersonList?.get(which)?.selected = isChecked
                    }, { dialog, which ->
                var workId = ""
                var workName = ""
                mainPersonList?.forEach {
                    if (it.selected) {
                        workId += it.userId+","
                        workName+=it.userName+","
                    }
                }
                editPerson(workId,workName,"","","work")
                dialog.dismiss()
            }, null, null)}
       // var checks:BooleanArray
        if (mainPersonList==null||items.size==0)
        getSelectInfo(resultBody)
        else if (items.size>0) {
            resultBody()
        }
        //
    }
    private fun editPerson(workId: String, workName: String, assistantId: String, assistantName: String, reDistributeType: String)
    {
        var activity = act as MaintenanceWorkOrderDetailActivity
        var request = EditPersonRequest()
        var head = NewRequestHead().setuserId(activity.config.userId).setaccessToken(activity.config.token)
        var body = request.EditPersonRequestBody()
        body.branchId = (activity.config.branchId)
        body.orderId = workOrderInfo.id
        body.workId = workId
        body.workName = workName
        body.assistantId = assistantId
        body.assistantName = assistantName
        body.reDistributeType = reDistributeType
        request.head = head
        request.body = body

        var netTask = object : NetTask(activity.config.server+NetConstant.REDISTRIBUTE,request) {
            override fun onResponse(task: NetTask?, result: String?) {
                activity.showToast("成功！")
                if(reDistributeType == "assistant")
                {
                    activity.find<TextView>(R.id.tv_main_person).text = assistantName
                }
                else
                {
                    activity.find<TextView>(R.id.tv_ass_person).text = workName
                }
//                activity.getWorkOrdersById(workOrderInfo.id!!)
            }
        }
        activity.addTask(netTask)
    }

    private  var mainPersonList: MutableList<PersonInfo>? = null

    private var items: ArrayList<String>  = arrayListOf()

    private fun getSelectInfo(dealBody:()->Unit) {
        var request = SelectRequest()

        var body = request.SelectRequestBody()

        body.elevatorId = workOrderInfo.elevatorInfo.id

        request.body = body

        request.head = NewRequestHead().setuserId((act as BaseFragmentActivity).config.userId).setaccessToken((act as BaseFragmentActivity).config.token)

        var server = (act as BaseFragmentActivity).config.server + NetConstant.GETSELECT

        var netTask =
                object : NetTask(server, request) {
                    override fun onResponse(task: NetTask?, result: String?) {

                        var response = WorkOrderPersonResponse.getResponse<WorkOrderPersonResponse>(result, WorkOrderPersonResponse::class.java)

                        mainPersonList = response.body
                        if (response.body != null) {
                            items = arrayListOf()
                            mainPersonList?.forEach {
                                items.add(it.userName)
                            }
                           dealBody()
                        }
                    }

                }
        (act as BaseFragmentActivity).addBackGroundTask(netTask)

    }

}