package com.honyum.elevatorMan.activity.maintenance

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.Window
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import com.honyum.elevatorMan.R
import com.honyum.elevatorMan.activity.common.ContractInnerAdapter
import com.honyum.elevatorMan.adapter.BaseListViewAdapter
import com.honyum.elevatorMan.adapter.BaseViewHolder
import com.honyum.elevatorMan.adapter.CacheMaintAdapter
import com.honyum.elevatorMan.base.BaseActivityWraper
import com.honyum.elevatorMan.constant.IntentConstant
import com.honyum.elevatorMan.data.*
import com.honyum.elevatorMan.net.*
import com.honyum.elevatorMan.net.base.NetConstant
import com.honyum.elevatorMan.net.base.NetTask
import com.honyum.elevatorMan.net.base.RequestBean
import com.honyum.elevatorMan.net.base.RequestHead
import kotlinx.android.synthetic.main.activity_plan_preview.*
import org.jetbrains.anko.act
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.textColor
import java.io.UnsupportedEncodingException
import java.net.URLDecoder

/**
 * Created by star on 2018/4/12.
 */
class DisplayUndealLiftInfoActivity :BaseActivityWraper() {
    override fun getTitleString(): String {
        return "计划预览"
    }

    private lateinit var undealInfo: MutableList<UndealLiftPlanInfo>

    override fun initView() {
        undealInfo = intent.getSerializableExtra(IntentConstant.INTENT_DATA) as MutableList<UndealLiftPlanInfo>

        lv_data.adapter = UndealItemAdapter(undealInfo,act)

        submit.onClick {
            var count = 0
            var currInfo:UndealLiftPlanInfo? = UndealLiftPlanInfo()
            val pair = checkSingleSelected(count, currInfo)
            count = pair.first
            currInfo = pair.second
            when {
                count == 0 -> {
                    showToast("请选择要审批的计划！")
                    return@onClick
                }
                count>1 -> {
                    showToast("目前仅支持单个维保计划操作!")
                    return@onClick
                }
            }
            getPlanCache(config.userId, config.token,currInfo?.id!! , "-1",
                    "", "add")
        }
        cancel.onClick {
            var count = 0
            var currInfo:UndealLiftPlanInfo? = UndealLiftPlanInfo()
            val pair = checkSingleSelected(count, currInfo)
            count = pair.first
            currInfo = pair.second
            when {
                count == 0 -> {
                    showToast("请选择要操作的计划！")
                    return@onClick
                }
                count>1 -> {
                    showToast("目前仅支持单个维保计划操作!")
                    return@onClick
                }
            }
            deleteDraftMaintenanceDetail(currInfo?.id!!)
        }
    }
    private fun deleteDraftMaintenanceDetail(id:String)
    {
        var request = DraftMaintenanceDetailRequest()
        var body = request.DraftMaintenanceDetailRequestBody()
        var head = NewRequestHead().setuserId(config.userId).setaccessToken(config.token)

        body.dealId =  id
        request.head = head
        request.body = body

        var netTask = object : NetTask(config.server+config.maintenanceUrl+NetConstant.DELETEMAINTENANCEDEALINFO,request) {
            override fun onResponse(task: NetTask?, result: String?) {
                showToast("操作成功!")
                finish()
            }

        }
        addTask(netTask)

    }
    private fun checkSingleSelected(count: Int, currInfo: UndealLiftPlanInfo?): Pair<Int, UndealLiftPlanInfo?> {
        var count1 = count
        var currInfo1 = currInfo
        undealInfo.forEach {
            if (it.isChecked) {
                count1++
                currInfo1 = it
            }

        }
        return Pair(count1, currInfo1)
    }

    private fun getPlanCache(userId: String, token: String, id: String, planDate: String,
                             planType: String, type: String) {

        var server = ""
        if (type == "add") {

            server = config.server + config.maintenanceUrl + NetConstant.MAIN_PLAN_CACHE
        } else if (type == "modify") {

            server = config.server + config.maintenanceUrl + NetConstant.URL_MODIFY_PLAN
        }
        val netTask = object : NetTask(server, getReportPlanRequest(userId, token, id, planDate,
                planType, config.branchId)) {
            override fun onResponse(task: NetTask, result: String) {
                val response = CacheMaintResponse.getResponse<CacheMaintResponse>(result,CacheMaintResponse::class.java)
                initCacheDialog1(response,userId, token, id, planDate,
                        planType, type)


//                val response = PlanResponse.getResponse<PlanResponse>(result, PlanResponse::class.java)
//                try {
//                    initDialog(URLDecoder.decode(response.body._process_task_param, "UTF-8"), response.body.id)
//                } catch (e: UnsupportedEncodingException) {
//                    e.printStackTrace()
//                }

                //   initDialog()
                // showToast("维保计划提交成功，请及时到记录上传里面完成您的维保计划");
                // finish();

                //Intent liftIntent = new Intent(PlanActivity.this, MyLiftActivity.class);
                //liftIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //startActivity(liftIntent);
            }
        }
        addTask(netTask)
    }
    private fun reportPlan(userId: String, token: String, id: String, planDate: String,
                           planType: String, type: String) {

        var server = ""
        if (type == "add") {

            server = config.server + config.maintenanceUrl + NetConstant.URL_REPORT_PLAN
        } else if (type == "modify") {

            server = config.server + config.maintenanceUrl + NetConstant.URL_MODIFY_PLAN
        }
        val netTask = object : NetTask(server, getReportPlanRequest(userId, token, id, planDate,
                planType, config.branchId)) {
            override fun onResponse(task: NetTask, result: String) {
                val response = PlanResponse.getResponse<PlanResponse>(result, PlanResponse::class.java)
                try {
                    initDialog(URLDecoder.decode(response.body._process_task_param, "UTF-8"), response.body.id)
                } catch (e: UnsupportedEncodingException) {
                    e.printStackTrace()
                }

                //   initDialog()
                // showToast("维保计划提交成功，请及时到记录上传里面完成您的维保计划");
                // finish();

                //Intent liftIntent = new Intent(PlanActivity.this, MyLiftActivity.class);
                //liftIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //startActivity(liftIntent);
            }
        }
        addTask(netTask)
    }
    private var currIndex = -1
    lateinit var contracts: MutableList<ContactDataGrideInfo>
    private lateinit var selectedWorkDialog: Dialog
    private fun initDialog(decode: String, id: String) {

        selectedWorkDialog = Dialog(this)
        selectedWorkDialog.setCanceledOnTouchOutside(true)
        selectedWorkDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        selectedWorkDialog.setContentView(R.layout.dialog_select_pointer)
        val list = selectedWorkDialog.findViewById(R.id.list_data) as ListView
        val numText = selectedWorkDialog.findViewById(R.id.name) as EditText
        requestRepairWork("", decode, list)
        selectedWorkDialog.findViewById(R.id.search).setOnClickListener(View.OnClickListener { requestRepairWork(numText.text.toString(), decode, list) })


        selectedWorkDialog.findViewById(R.id.submit).setOnClickListener(View.OnClickListener {
            if (currIndex == -1) {
                showToast("请选择指派人！")
                return@OnClickListener
            }
            requestPointWorker(id, decode, contracts.get(currIndex).getId())
        })
        selectedWorkDialog.show()
    }
    private fun requestPointWorker(id: String, process_task_param: String, s: String) {
        val uploadProcessRequest = UploadProcessRequest()
        val body = uploadProcessRequest.UploadProcessRequestBody()
        uploadProcessRequest.head = NewRequestHead().setaccessToken(config.token).setuserId(config.userId)
        body._process_isLastNode = "0"
        body._process_task_param = process_task_param
        body._process_path = "同意"
        body._processSelectUserId = s
        //body._process_approve_source
        body._processSelectUserId = s
        body.id = id
        body.branchId = config.branchId

        uploadProcessRequest.body = body

        val server = config.server + config.maintenanceUrl + NetConstant.COMMITMAINPLAN

        val netTask = object : NetTask(server, uploadProcessRequest) {
            override fun onResponse(task: NetTask, result: String) {
                if (selectedWorkDialog.isShowing)
                    selectedWorkDialog.dismiss()
                showToast("提交成功！")
                finish()
            }
        }

        addTask(netTask)
    }
    private fun transDefault() {
        for (info in contracts) {
            info.isSelected = false
        }

    }
    private fun requestRepairWork(name: String, param: String, list: ListView) {
        val request = RepairSelectedWorkerRequest()
        val body = request.RepairSelectedWorkerBody()
        body.name = name
        body._process_task_param = param
        body.branchId = config.branchId
        //  body.remark = et_remark.text.toString()
        request.body = body
        request.head = NewRequestHead().setaccessToken(config.token).setuserId(config.userId)

        val server = config.newServer + NetConstant.SELECTUSERDATAGRID
        val netTask = object : NetTask(server, request) {
            override fun onResponse(task: NetTask, result: String) {
                val response = ContactResponse.getContactResponse(result)
                contracts = response.body.dataGrid

                list.adapter = ContractInnerAdapter(contracts, this@DisplayUndealLiftInfoActivity, R.layout.item_contract_inner)
                list.setOnItemClickListener { parent, view, position, id ->
                    transDefault()
                    contracts.get(position).setSelected(true)
                    currIndex = position
                    (list.adapter as ContractInnerAdapter).notifyDataSetChanged()
                }

            }
        }
        addTask(netTask)
    }
    private fun getDraftMaintenanceDetail(id:String)
    {
        var request = DraftMaintenanceDetailRequest()
        var body = request.DraftMaintenanceDetailRequestBody()
        var head = NewRequestHead().setuserId(config.userId).setaccessToken(config.token)

        body.dealId =  id
        request.head = head
        request.body = body

        var netTask = object : NetTask(config.server+config.maintenanceUrl+NetConstant.GETDRAFTMAINTENANCEDEALINFO,request) {
            override fun onResponse(task: NetTask?, result: String?) {

                var response = UndealDealInfoResponse.getResponse<UndealDealInfoResponse>(result,UndealDealInfoResponse::class.java)

                initDetailDialog(response?.body?.get("eles") as List<UndealPlanInfo>,response?.body?.get("plans") as List<UndealPlanInfo>)
            }

        }
        addTask(netTask)

    }
    /**
     * 获取提交维保计划的bean
     *
     * @param userId
     * @param token
     * @param id
     * @param planDate
     * @param planType
     * @return
     */
    fun getReportPlanRequest(userId: String, token: String, id: String, planDate: String,
                             planType: String, branchId: String): RequestBean {
        val request = ReportPlanRequest()
        val head = RequestHead()
        val body = request.ReportPlanReqBody()

        head.userId = userId
        head.accessToken = token

        body.id = id
        body.planTime = planDate
        body.mainType = planType
        body.branchId = branchId

        request.head = head
        request.body = body

        return request
    }
    override fun getLayoutID(): Int {
        return R.layout.activity_plan_preview
    }

    inner class UndealItemAdapter(val datas: List<UndealLiftPlanInfo>?, context: Context?, layoutId: Int = R.layout.item_undeal_lift_info) : BaseListViewAdapter<UndealLiftPlanInfo>(datas, context, layoutId) {

        override fun bindData(holder: BaseViewHolder?, t: UndealLiftPlanInfo?, index: Int) {
            holder?.setText(R.id.name,t?.communityName)?.setText(R.id.code,t?.code)?.setOnClickListener(R.id.content,{
                getDraftMaintenanceDetail(t?.id!!)
            })?.setChecked(R.id.check_state,t?.isChecked!!)?.setOnCheckedChangeListener(R.id.check_state,{ _, b ->
                t.isChecked = b
            })
        }

    }
    private fun initCacheDialog1(data:CacheMaintResponse,userId: String, token: String, id: String, planDate: String,
                                planType: String, type: String)
    {
        cacheDialog  = Dialog(this)
        cacheDialog.setCanceledOnTouchOutside(true)
        cacheDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        cacheDialog.setContentView(R.layout.dialog_ele_info_cache)
        val list = cacheDialog.findViewById(R.id.data_list) as ListView
        val submit = cacheDialog.findViewById(R.id.insure_submit) as TextView
        list.adapter = CacheMaintAdapter(data.body,act)
        submit.onClick {
            if (cacheDialog.isShowing)
                cacheDialog.dismiss()
            reportPlan(userId, token, id, planDate,
                    planType, type)
        }
        cacheDialog.show()

    }
    private lateinit var cacheDialog: Dialog
    private lateinit var detailDialog: Dialog
    private lateinit var planAdapter: UndealPlanAdapter

    private lateinit var eleAdapter: UndealEleAdapter

    fun changeTab(isEle: Boolean,ele:TextView,plan:TextView) {
        if (isEle)
        {
            ele.textColor = ContextCompat.getColor(act,R.color.title_bg_color)
            plan.textColor = Color.BLACK
        }
        else{
            plan.textColor = ContextCompat.getColor(act,R.color.title_bg_color)
            ele.textColor = Color.BLACK
        }
    }
    private fun initDetailDialog(eleInfo: List<UndealPlanInfo>, planInfo: List<UndealPlanInfo>)
    {
        detailDialog  = Dialog(this)
        detailDialog.setCanceledOnTouchOutside(true)
        detailDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        detailDialog.setContentView(R.layout.dialog_tab_ele)
        val ele = detailDialog.findViewById(R.id.ele) as TextView
        val plan = detailDialog.findViewById(R.id.plan) as TextView
        val datalist =  detailDialog.findViewById(R.id.lv_data) as ListView
        planAdapter = UndealPlanAdapter(planInfo,act)
        eleAdapter = UndealEleAdapter(eleInfo, act)
        changeTab(true,ele,plan)
        datalist.adapter =eleAdapter
        ele.onClick {
            changeTab(true,ele,plan)
            datalist.adapter =eleAdapter
            eleAdapter.notifyDataSetChanged()
//            if (detailDialog.isShowing)
//                detailDialog.dismiss()
        }
        plan.onClick {
            changeTab(false,ele,plan)
            datalist.adapter =planAdapter
            planAdapter.notifyDataSetChanged()
        }
        detailDialog.show()

    }
}
class UndealPlanAdapter(val datas: List<UndealPlanInfo>?, context: Context?, layoutId: Int = R.layout.item_cache_dialog) : BaseListViewAdapter<UndealPlanInfo>(datas, context, layoutId) {

    override fun bindData(holder: BaseViewHolder?, t: UndealPlanInfo?, index: Int) {

        holder?.setText(R.id.plan_time,t?.planTime)
        holder?.setText(R.id.plan_type,t?.mainType)
    }


}
class UndealEleAdapter(val datas: List<UndealPlanInfo>?, context: Context?, layoutId: Int = R.layout.item_cache_dialog) : BaseListViewAdapter<UndealPlanInfo>(datas, context, layoutId) {

    override fun bindData(holder: BaseViewHolder?, t: UndealPlanInfo?, index: Int) {

        holder?.setText(R.id.plan_time, t?.propertyCode)?.setText(R.id.plan_type, t?.communityName)


    }
}