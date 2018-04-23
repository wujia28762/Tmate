package com.honyum.elevatorMan.activity.common

import android.app.Dialog
import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.Window
import android.widget.AdapterView
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.honyum.elevatorMan.R
import com.honyum.elevatorMan.adapter.BaseListViewAdapter
import com.honyum.elevatorMan.adapter.BaseViewHolder
import com.honyum.elevatorMan.adapter.RepairPicListAdapter
import com.honyum.elevatorMan.base.BaseActivityWraper
import com.honyum.elevatorMan.base.ListItemCallback
import com.honyum.elevatorMan.data.ContractInfo
import com.honyum.elevatorMan.data.RepairInfo
import com.honyum.elevatorMan.net.*
import com.honyum.elevatorMan.net.base.NetConstant
import com.honyum.elevatorMan.net.base.NetTask
import kotlinx.android.synthetic.main.activity_repair_detail.*
import org.jetbrains.anko.backgroundDrawable
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.textColor
import org.jetbrains.anko.toast

/**
 * Created by Star on 2018/1/5.
 */
class ToDoRepairDetail : BaseActivityWraper(), ListItemCallback<String> {

    private lateinit var repairId: String
    private lateinit var selectedWorkDialog: Dialog
    private lateinit var currentSelectedWorker: String
    private lateinit var contracts: MutableList<ContractInfo>
    private lateinit var taskId: String
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


    private var done: Boolean = false

    override fun initView() {
        val bundle = intent.extras
        repairId = bundle.getString("repairId")
        taskId = bundle.getString("taskId")
        requestFixDetailByRepairId(repairId)
        done = intent.getBooleanExtra("done",false)
        if (done) {
            linear_process.visibility= GONE
        }
    }

    private fun fillPageData(repairInfo: RepairInfo) {
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
            // show select worker Id dialog and submit
            initDialog()
        }


        tv_accident.visibility = GONE
        tv_submit.visibility = VISIBLE
        tv_submit.backgroundDrawable = ContextCompat.getDrawable(this,R.drawable.selector_plan_submit)
        tv_submit.text = "指派"
        tv_submit.textColor = ContextCompat.getColor(this,R.color.white)

    }

    private fun initDialog() {

        selectedWorkDialog = Dialog(this)
        selectedWorkDialog.let {
            it.setCanceledOnTouchOutside(true)
            it.requestWindowFeature(Window.FEATURE_NO_TITLE)
            it.setContentView(R.layout.dialog_select_pointer)
            var list: ListView = it.find(R.id.list_data)
            var numText = it.find<EditText>(R.id.name)
            requestRepairWork(repairId,"",list)
            it.find<TextView>(R.id.search).onClick {
                requestRepairWork(repairId, numText.text.toString(), list)
            }

            it.find<TextView>(R.id.submit).onClick {
                if (currIndex == -1) {
                    toast("请选择指派人！")
                    return@onClick
                }
                requestPointWorker(repairId,contracts[currIndex].id)
            }
            selectedWorkDialog.show()
        }

    }

    private var currIndex: Int = -1

    private fun requestRepairWork(repairId: String, name: String = "", list: ListView) {
        var request = RepairSelectedWorkerRequest()
        var body = request.RepairSelectedWorkerBody()
        body.name = name
        body.baoxiuId = repairId
        body.branchId = config.branchId
        //  body.remark = et_remark.text.toString()
        request.body = body
        request.head = NewRequestHead().setaccessToken(config.token).setuserId(config.userId)

        var server = config.server + NetConstant.GETUSERLISTBYBAOXIUID
        var netTask = object : NetTask(server, request) {
            override fun onResponse(task: NetTask?, result: String?) {
                var response = SelectedWorkerResponse.getResponse<SelectedWorkerResponse>(result, SelectedWorkerResponse::class.java)
                contracts = response.body

                list.adapter = SelectedWorkerAdapter(contracts, this@ToDoRepairDetail)
                list.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                    transDefault()
                    contracts[position].selected = true
                    currIndex = position
                    (list.adapter as SelectedWorkerAdapter).notifyDataSetChanged()
                }

            }
        }
        addTask(netTask)
    }

    private fun transDefault()
    {
        contracts.forEach {
            it.selected = false
        }
    }
    private fun requestPointWorker(id: String,mWorkId:String) {
        var request = PointWorkerRequest()
        var body = request.PointWorkerRequestBody()
        body.processResult = "同意"
        body.taskId = taskId
        body.workId = mWorkId
        body.baoxiuId = id
        body.branchId = config.branchId
        //  body.remark = et_remark.text.toString()
        request.body = body
        request.head = NewRequestHead().setaccessToken(config.token).setuserId(config.userId)

        var server = config.server + NetConstant.ASSIGNBAOXIUUSER
        var netTask = object : NetTask(server, request) {
            override fun onResponse(task: NetTask?, result: String?) {
                if (selectedWorkDialog.isShowing)
                    selectedWorkDialog.dismiss()
                toast("提交成功！")
                finish()
            }
        }
        addTask(netTask)
    }

    private fun requestFixDetailByRepairId(id: String) {
        var request = PointWorkerRequest()
        var body = request.PointWorkerRequestBody()
        body.baoxiuId = id
        //  body.remark = et_remark.text.toString()
        request.body = body
        request.head = NewRequestHead().setaccessToken(config.token).setuserId(config.userId)

        var server = config.server + NetConstant.GETBAOXIUDETAILBYID
        var netTask = object : NetTask(server, request) {
            override fun onResponse(task: NetTask?, result: String?) {
                var response = ToDoRepairInfoResponse.getResponse<ToDoRepairInfoResponse>(result,ToDoRepairInfoResponse::class.java)
                fillPageData(response.body)
            }
        }
        addTask(netTask)
    }

}

class SelectedWorkerAdapter(datas: MutableList<ContractInfo>?, context: Context?, layoutId: Int = R.layout.item_contract_worker) : BaseListViewAdapter<ContractInfo>(datas, context, layoutId) {
    override fun bindData(holder: BaseViewHolder?, t: ContractInfo?, index: Int) {
        holder?.setText(R.id.name, t?.name)?.setText(R.id.tel, t?.tel)

        if (t?.selected!!)
            holder?.setVisible(R.id.selected, true)
        else
            holder?.setInVisible(R.id.selected)
    }

}
