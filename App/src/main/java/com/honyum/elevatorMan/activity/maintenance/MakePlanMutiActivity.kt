package com.honyum.elevatorMan.activity.maintenance

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.Window
import android.widget.*
import com.honyum.elevatorMan.base.BaseActivityWraper
import com.honyum.elevatorMan.R
import com.honyum.elevatorMan.activity.common.ContractInnerAdapter
import com.honyum.elevatorMan.adapter.BaseListViewAdapter
import com.honyum.elevatorMan.adapter.BaseViewHolder
import com.honyum.elevatorMan.adapter.CacheMaintAdapter
import com.honyum.elevatorMan.constant.IntentConstant
import com.honyum.elevatorMan.data.ContactDataGrideInfo
import com.honyum.elevatorMan.data.LiftInfo
import com.honyum.elevatorMan.net.*
import com.honyum.elevatorMan.net.base.NetConstant
import com.honyum.elevatorMan.net.base.NetTask
import com.honyum.elevatorMan.net.base.RequestBean
import com.honyum.elevatorMan.net.base.RequestHead
import kotlinx.android.synthetic.main.activity_make_plan_muti.*
import org.jetbrains.anko.act
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.util.*

class MakePlanMutiActivity : BaseActivityWraper() {


    private  var listData: MutableList<LiftInfo> = arrayListOf()

    companion object {

        var REQUESTCODE = 1
        var SUCCESS = 2
    }

    override fun getTitleString(): String {

        return "制定计划"
    }


    private var onDateSetListener:DatePickerDialog.OnDateSetListener = object: DatePickerDialog.OnDateSetListener{

        override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
            var mYear = year
            var mMonth = month
            var  mDay = dayOfMonth
            var days :String
            if (mMonth + 1 < 10) {
                if (mDay < 10) {
                    days =  StringBuffer().append(mYear).append("-").append("0").
                            append(mMonth + 1).append("-").append("0").append(mDay).toString();
                } else {
                    days =  StringBuffer().append(mYear).append("-").append("0").
                            append(mMonth + 1).append("-").append(mDay).toString();
                }

            } else {
                if (mDay < 10) {
                    days =  StringBuffer().append(mYear).append("-").
                            append(mMonth + 1).append("-").append("0").append(mDay).toString();
                } else {
                    days =  StringBuffer().append(mYear).append("-").
                            append(mMonth + 1).append("-").append(mDay).toString();
                }

            }
            date.text = days
        }

    }
    override fun initView() {

        val ca = Calendar.getInstance()
        var mYear = ca.get(Calendar.YEAR)
        var mMonth = ca.get(Calendar.MONTH)
        var mDay = ca.get(Calendar.DAY_OF_MONTH)

        date.onClick {
                DatePickerDialog(this@MakePlanMutiActivity, onDateSetListener, mYear, mMonth, mDay).show();
        }
        select_ele.onClick {
            startActivityForResult(Intent(this@MakePlanMutiActivity, SelectMaintenanceProjectMutiActivity::class.java), REQUESTCODE)
        }

        submit.onClick {
            var ids = ""
            listData.forEach {
                ids+=(it.id+",")
            }
            if(ids.length>1)
            ids =  ids.subSequence(0,ids.length-1).toString()
            if (date.text.isNotEmpty())
//            reportPlan(config.userId, config.token,ids , date.text.toString(),
//                    "", "add")

                getPlanCache(config.userId, config.token,ids , date.text.toString(),
                    "", "add")
            else
                showToast("请输入完整信息!")
        }
    }

    private lateinit var adapter: SelectEleAdapter

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == SUCCESS) {
            listData = data?.getSerializableExtra(IntentConstant.INTENT_DATA) as MutableList<LiftInfo>
            adapter = SelectEleAdapter(listData, act)
            lv_data.adapter = adapter
            lv_data.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                listData.removeAt(position)
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun getLayoutID(): Int {
        return R.layout.activity_make_plan_muti
    }

    private lateinit var selectedWorkDialog: Dialog
    private lateinit var cacheDialog: Dialog
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

    lateinit var contracts: MutableList<ContactDataGrideInfo>
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

                list.adapter = ContractInnerAdapter(contracts, this@MakePlanMutiActivity, R.layout.item_contract_inner)
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
    private var currIndex = -1

    private fun initCacheDialog(data:CacheMaintResponse,userId: String, token: String, id: String, planDate: String,
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
                showToast("请选择提交审批人！")
                return@OnClickListener
            }
            requestPointWorker(id, decode, contracts[currIndex].getId())
        })
        selectedWorkDialog.show()
    }
    /**
     * 上传维保计划
     *
     * @param userId
     * @param token
     * @param id
     * @param planDate
     * @param planType
     */
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
                initCacheDialog(response,userId, token, id, planDate,
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
    /**
     * 上传维保计划
     *
     * @param userId
     * @param token
     * @param id
     * @param planDate
     * @param planType
     */
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
}

class SelectEleAdapter(datas: MutableList<LiftInfo>?, context: Context?, layoutId: Int = R.layout.item_select_eles) : BaseListViewAdapter<LiftInfo>(datas, context, layoutId) {
    override fun bindData(holder: BaseViewHolder?, t: LiftInfo?, index: Int) {
        holder?.setText(R.id.num, t?.num)?.setText(R.id.address, t?.address)?.setVisible(R.id.image, false)?.setVisible(R.id.image_cancel, true)
    }
}