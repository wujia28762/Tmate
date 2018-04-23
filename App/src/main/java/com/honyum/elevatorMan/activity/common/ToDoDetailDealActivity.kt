package com.honyum.elevatorMan.activity.common

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.text.TextUtils
import android.view.View
import android.view.Window
import android.widget.*
import com.hanbang.netsdk.Log
import com.honyum.elevatorMan.R
import com.honyum.elevatorMan.R.id.url
import com.honyum.elevatorMan.R.id.visible
import com.honyum.elevatorMan.adapter.BaseListViewAdapter
import com.honyum.elevatorMan.adapter.BaseViewHolder
import com.honyum.elevatorMan.base.BaseActivityWraper
import com.honyum.elevatorMan.base.Config
import com.honyum.elevatorMan.data.ContactDataGrideInfo
import com.honyum.elevatorMan.data.ProcessStateInfo
import com.honyum.elevatorMan.net.*
import com.honyum.elevatorMan.net.base.NetConstant
import com.honyum.elevatorMan.net.base.NetTask
import com.honyum.elevatorMan.net.base.Response
import com.honyum.elevatorMan.utils.ToastUtils
import kotlinx.android.synthetic.main.process_dialog.*
import org.jetbrains.anko.act
import org.jetbrains.anko.dip
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast

/**
 * Created by Star on 2017/12/19.
 */
class ToDoDetailDealActivity : BaseActivityWraper() {

    //private lateinit var processDialog: Dialog
    private var addContractDialog: Dialog? = null
    private var selectedStateDialog: AlertDialog.Builder? = null
    private var proDatas: MutableList<ProcessStateInfo>? = arrayListOf()
    private lateinit var datas: MutableList<ContactDataGrideInfo>
    private var uploadContracts: MutableList<ContactDataGrideInfo> = arrayListOf()

    override fun getTitleString(): String {
        return "立即处理"
    }

    private fun requestContract(name: String = "", tel: String = "") {
        var addContractRequest = AddContractRequest()
        var body = AddContractRequest().AddContractRequestBody()
        body.branchId = config.branchId
        body.name = name
        body.tel = tel
        body.page = 0
        body.pageSize = 10
        body._process_isLastNode = Config.currLastNode
        body._process_task_param = Config.currTask
        body._process_approve_opinion = et_result_area.text.toString()
        body._process_path = tv_state.text.toString()
        var userIds = ""
        body.contractId = currId
        body.id = currId
        uploadContracts?.forEach {
            userIds = userIds + it.id + ","
        }
        body.processSource = "2"
        body._processSelectUserId = userIds

        addContractRequest.head = NewRequestHead().setaccessToken(config.token).setuserId(config.userId)
        addContractRequest.body = body

        var server = config.newServer + NetConstant.SELECTUSERDATAGRID

        var netTask = object : NetTask(server, addContractRequest) {
            override fun onResponse(task: NetTask?, result: String?) {
                Log.e("contract_result", result + "==========")
                var response = ContactResponse.getContactResponse(result)

                addContractDialog = initAlertDiaogMuti(response)
                addContractDialog!!.show()

            }

        }
        addTask(netTask)
    }

    override fun onDestroy() {
        super.onDestroy()
        addContractDialog?.dismiss()

        selectedStateDialog = null

    }

    private fun requestProcessStateInfo(isFirst: Boolean = false) {
        var pathRequest = PathRequest()
        var body = pathRequest.PathRequestBody()
        pathRequest.head = NewRequestHead().setaccessToken(config.token).setuserId(config.userId)
        body._process_isLastNode = Config.currLastNode
        body._process_task_param = Config.currTask
        body.branchId = config.branchId
        pathRequest.body = body
        var server = config.newServer + NetConstant.GETPROCESSPATH

        var netTask = object : NetTask(server, pathRequest) {
            override fun onResponse(task: NetTask?, result: String?) {
                Log.e("contract_result", result + "==========")
                var response = ProcessStateResponse.getProcessStateResponse(result)
                proDatas = response?.body?.obj
                if (!isFirst) {
                    selectedStateDialog = initAlertDialog(proDatas?.map { it.name }, { which ->
                        tv_state.text = proDatas?.get(which)?.name
                        dataLayoutVisible(proDatas?.get(which))

                    })
                    selectedStateDialog?.show()
                } else {
                    dataLayoutVisible(proDatas?.get(0))
                }
            }

        }
        addTask(netTask)
    }

    fun dataLayoutVisible(currData: ProcessStateInfo?) {
        tv_state.text = currData?.name
        var visi = {
            ll_selected_user.visibility = View.VISIBLE
        }
        var gone = {
            ll_selected_user.visibility = View.GONE
        }
        switchData(Config.currLastNode,currData?.name,gone,gone,gone,visi)

//        if ((Config.currLastNode == "0"&&currData?.name == "同意")||currData?.name == "转办") {
//
//        } else {
//
//        }
    }
//
//    private fun requestProcessSub() {
//        var uploadProcessRequest = UploadProcessRequest()
//        var body = uploadProcessRequest.UploadProcessRequestBody()
//        uploadProcessRequest.head = NewRequestHead().setaccessToken(config.token).setuserId(config.userId)
//        body._process_isLastNode = Config.currLastNode
//        body._process_task_param = Config.currTask
//        body._process_approve_opinion = et_result_area.text.toString()
//        body._process_path = tv_state.text.toString()
//        var userIds = ""
//        body.contractId = currId
//        uploadContracts?.forEach {
//            userIds + it.id + ","
//        }
//        body._process_SelectUserId = userIds
//        //body._process_approve_source
//        body.processSource = "2"
//        body.branchId = config.branchId
//        uploadProcessRequest.body = body
//        var url = ""
//        if ("1" == body._process_isLastNode
//                && "同意" == body._process_path) {
//            //	alert("要完成"+_process_isLastNode+","+_process_path);
//            //如果是完成，由业务类实现该方法
//            //    _processExecuteFinishBiz(_processCommitParam);
//            url = "/finish"
//           // return
//
//        } else if ("不同意" == body._process_path
//                && (body._process_isLastNode.toInt()) < 2) {
//            //如果是退回，需要审批页面执行以下方法
//            //	alert("要退回"+_process_isLastNode+","+_process_path);
//            // _processExecuteBackBiz(_processCommitParam);
//
//            url = "/back"
//        } else if ("结束" == body._process_path) {
//            //如果是退回，需要审批页面执行以下方法
//            //	alert("结束"+_process_isLastNode+","+_process_path);
//            //      _processExecuteCancelBiz(_processCommitParam);
//            url = "/cancel"
//        }
//        else
//        {
//            return
//        }
//
//        var server = config.newServer + url
//
//        var netTask = object : NetTask(server, uploadProcessRequest) {
//            override fun onResponse(task: NetTask?, result: String?) {
//                var res = Response.getResponse(result)
//                ToastUtils.showToast(act, "提交成功！")
//            }
//
//        }
//        addBackGroundTask(netTask)
//    }

    private fun<T> switchData(isLastNode:String,path:String?,fun1:()->T,fun2:()->T,fun3:()->T,fun4:()->T):T
    {
        if (("1" == isLastNode
                && "同意" == path)||"完成"==path) {
            return fun1()
           // "/finish"
        } else if ("不同意" == path
                && (isLastNode.toInt()) < 2) {
            return fun2()
         //   "/back"
        } else if ("结束" == path) {
            return fun3()
          //  "/cancel"
        } else {
            //选人
            return fun4()
//            url1 = "/process"
//            NetConstant.COMMITNEXTPROCESSNODE
        }
    }
    private fun requestUploadProcess() {
        var uploadProcessRequest = UploadProcessRequest()
        var body = uploadProcessRequest.UploadProcessRequestBody()
        uploadProcessRequest.head = NewRequestHead().setaccessToken(config.token).setuserId(config.userId)
        body._process_isLastNode = Config.currLastNode
        body._process_task_param = Config.currTask
        body._process_approve_opinion = et_result_area.text.toString()
        body._process_path = tv_state.text.toString()
        var userIds = ""
        body.contractId = currId
        body.id = currId
        if (ll_selected_user.visibility == View.VISIBLE)
            uploadContracts?.forEach {
                userIds = userIds + it.id + ","
            }
        body.processSource = "2"
        body._processSelectUserId = userIds
        //body._process_approve_source

        body.branchId = config.branchId
        uploadProcessRequest.body = body

        val url: String
        url1 = intent.getStringExtra("url")
        url = switchData(body._process_isLastNode,body._process_path,{"/finish"},{"/back"},{"/cancel"},{url1 = "/process"
            NetConstant.COMMITNEXTPROCESSNODE})
//        url = if ("1" == body._process_isLastNode
//                && "同意" == body._process_path) {
//            "/finish"
//        } else if ("不同意" == body._process_path
//                && (body._process_isLastNode.toInt()) < 2) {
//            "/back"
//        } else if ("结束" == body._process_path) {
//            "/cancel"
//        } else {
//            //选人
//            url1 = "/process"
//            NetConstant.COMMITNEXTPROCESSNODE
//        }

        var server = config.server + url1 + url

        var netTask = object : NetTask(server, uploadProcessRequest) {
            override fun onResponse(task: NetTask?, result: String?) {
                Log.e("contract_result", result + "==========")
//                var response = ProcessStateResponse.getProcessStateResponse(result)
//                proDatas = response?.body?.obj
//                selectedStateDialog = initAlertDialog(proDatas?.map { it.name }, { which ->
//                    tv_state.text = proDatas?.get(which)?.name
//                })
//                selectedStateDialog.show()
                ToastUtils.showToast(act, "提交成功！")
                finish()
            }

        }
        addTask(netTask)
    }

    var currId = ""
    var url1 = ""
    override fun initView() {

        currId = intent.getStringExtra("currId")
        //url1 = intent.getStringExtra("url")
        tv_submit.onClick {

            if("同意" != tv_state.text&&"完成"!=tv_state.text)
            {
                if(et_result_area.text.toString().isBlank()) {
                    toast("请输入完整信息")
                    return@onClick
                }
            }
            else
                requestUploadProcess()
        }
        var params = list_contract.layoutParams
        params.height = 0
        list_contract.layoutParams = params
        list_contract.adapter = ContractAdapter(uploadContracts, this)
        list_contract.setOnItemClickListener { parent, view, position, id ->

            uploadContracts.removeAt(position)
            (list_contract.adapter as ContractAdapter).notifyDataSetChanged()
            if (uploadContracts.size == 0) {
                var params = list_contract.layoutParams
                params.height = 0
                list_contract.layoutParams = params
            }

        }
        list_contract.setOnTouchListener { _, _ ->
            scroll.requestDisallowInterceptTouchEvent(true)
            false
        }
        requestProcessStateInfo(true)
        add_contract.onClick {
            requestContract()
        }
        rl_selected_state.onClick {
            requestProcessStateInfo()
        }


    }

    private lateinit var builderMuti: Dialog

    private var view: View? = null
    private var mLv_contanct: ListView? = null

    private var mSearch: TextView? = null

    private fun Context.initAlertDiaogMuti(array: ContactResponse): Dialog {
        if (addContractDialog != null && addContractDialog!!.isShowing)
            addContractDialog!!.dismiss()
        datas = array.body.dataGrid
        builderMuti = Dialog(this)
        // builderMuti.setIcon(id)
        builderMuti.requestWindowFeature(Window.FEATURE_NO_TITLE)
        view = View.inflate(this, R.layout.dialog_search_contranct, null)
        builderMuti.setContentView(view)
        var currTag: String = resources.getStringArray(R.array.type)[0]
        mLv_contanct = view?.find(R.id.lv_contanct)
        var input: EditText? = view?.find<EditText>(R.id.input_tel)
        input?.hint = "请输入" + currTag
        var spinner1: Spinner? = view?.find<Spinner>(R.id.spinner1)
        spinner1?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val type = resources.getStringArray(R.array.type)
                currTag = type[position]
                input?.hint = "请输入" + currTag
            }

        }
        view?.find<TextView>(R.id.search)?.onClick {
            if (!TextUtils.isEmpty(input?.text.toString())) {
                if ("姓名" == currTag)
                    requestContract(input?.text.toString(), "")
                else
                    requestContract("", input?.text.toString())
            }
        }
        view?.find<TextView>(R.id.submit)?.onClick {
            datas.forEach {
                if (it.isSelected)
                    uploadContracts.add(it)
            }
            if (addContractDialog?.isShowing!!)
                addContractDialog?.hide()
            if (uploadContracts.size > 0) {
                var par = list_contract.layoutParams
                par.height = dip(200)
                list_contract.layoutParams = par
                (list_contract.adapter as ContractAdapter).notifyDataSetChanged()
            }

        }


        uploadContracts.forEach {
            if (array.body.dataGrid.contains(it)) {
                array.body.dataGrid.remove(it)
            }
        }
        mLv_contanct?.adapter = ContractInnerAdapter(array?.body.dataGrid, this)
        mLv_contanct?.setOnItemClickListener { _, _, position, _ ->
            datas[position].isSelected = !datas[position].isSelected
            (mLv_contanct?.adapter as ContractInnerAdapter).notifyDataSetChanged()
        }
        return builderMuti
    }

    private fun Context.initAlertDialog(array: List<String>?, listener: ((which: Int) -> Unit), id: Int = R.drawable.ic_launcher, title: String = "审批意见"): AlertDialog.Builder {
        val builder = AlertDialog.Builder(this)
        builder.setIcon(null)
        builder.setTitle(title)
        builder.setItems(array?.toTypedArray(), { _, which ->
            listener(which)
        })
        return builder
    }

    override fun getLayoutID(): Int {
        return R.layout.process_dialog
    }
}

class ContractAdapter(datas: MutableList<ContactDataGrideInfo>?, context: Context?, layoutId: Int = R.layout.item_contract) : BaseListViewAdapter<ContactDataGrideInfo>(datas, context, layoutId) {
    override fun bindData(holder: BaseViewHolder?, t: ContactDataGrideInfo?, index: Int) {
        holder?.setText(R.id.tv_name, t?.name)
        if (t?.isSelected!!)
            holder?.setVisible(R.id.iv_delete, true)
        else
            holder?.setVisible(R.id.iv_delete, false)
    }
}

class ContractInnerAdapter(datas: MutableList<ContactDataGrideInfo>?, context: Context?, layoutId: Int = R.layout.item_contract_inner) : BaseListViewAdapter<ContactDataGrideInfo>(datas, context, layoutId) {
    override fun bindData(holder: BaseViewHolder?, t: ContactDataGrideInfo?, index: Int) {
        holder?.setText(R.id.name, t?.name)?.setText(R.id.tel, t?.tel)
        if (!t?.isSelected!!)
            holder?.setImageResource(R.id.image, R.drawable.non_select)
        else
            holder?.setImageResource(R.id.image, R.drawable.selected)
    }
}


