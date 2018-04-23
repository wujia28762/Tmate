package com.honyum.elevatorMan.activity.workOrder

import `in`.srain.cube.views.ptr.*
import `in`.srain.cube.views.ptr.util.PtrLocalDisplay
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.honyum.elevatorMan.R
import com.honyum.elevatorMan.adapter.WorkOrderListAdapter
import com.honyum.elevatorMan.base.BaseActivityWraper
import com.honyum.elevatorMan.data.CommunityCountInfo
import com.honyum.elevatorMan.data.WorkOrderInfo
import com.honyum.elevatorMan.net.*
import com.honyum.elevatorMan.net.base.NetConstant
import com.honyum.elevatorMan.net.base.NetTask
import kotlinx.android.synthetic.main.activity_work_order_list.*
import org.jetbrains.anko.act
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*

class WorkOrderListActivity : BaseActivityWraper() {

    val FIX_KEYWORD = "维修"
    val MAIN_OTHER = "其他"
    val MAIN_KEYWORD = "维保"
    var count = 2
    private var dialog: Dialog? = null

    var keyword_array: Array<String> = arrayOf(MAIN_KEYWORD, FIX_KEYWORD, MAIN_OTHER)

    var state_array: Array<String> = arrayOf("处理中", "全部", "已处理")

    var work_order_list: MutableList<WorkOrderInfo> = ArrayList<WorkOrderInfo>()

    override fun getLayoutID(): Int {
        return R.layout.activity_work_order_list
    }

    private fun initPtrClass() {
        /**
         * 经典 风格的头部实现
         */
        val header = PtrClassicDefaultHeader(this)
        header.setPadding(0, PtrLocalDisplay.dp2px(15f), 0, 0)

        val footer = PtrClassicDefaultFooter(this)
        footer.setPadding(0, 0, 0, PtrLocalDisplay.dp2px(15f))
        work_order_ptr_classic_frame_layout.let {
            it.setHeaderView(header)
            it.addPtrUIHandler(header)

            it.setFooterView(footer)
            it.addPtrUIHandler(footer)
            //mPtrFrame.setKeepHeaderWhenRefresh(true);//刷新时保持头部的显示，默认为true
            //mPtrFrame.disableWhenHorizontalMove(true);//如果是ViewPager，设置为true，会解决ViewPager滑动冲突问题。
            it.setPtrHandler(object : PtrHandler2 {
                override fun checkCanDoLoadMore(frame: PtrFrameLayout, content: View, footer: View): Boolean {
                    // 默认实现，根据实际情况做改动

                   // return hasMore
                    return PtrDefaultHandler2.checkContentCanBePulledUp(frame, content, footer)
                }

                /**
                 * 加载更多的回调
                 * @param frame
                 */
                override fun onLoadMoreBegin(frame: PtrFrameLayout) {
                    frame.postDelayed(Runnable {
                        queryData(false)
                        it.refreshComplete()
                    }, 2000)
                }

                override fun checkCanDoRefresh(frame: PtrFrameLayout, content: View, header: View): Boolean {
                    // 默认实现，根据实际情况做改动
                    return PtrDefaultHandler2.checkContentCanBePulledDown(frame, content, header)
                }

                /**
                 * 下拉刷新的回调
                 * @param frame
                 */
                override fun onRefreshBegin(frame: PtrFrameLayout) {
                    frame.postDelayed(Runnable {
                        queryData(true)
                        it.refreshComplete()
                    }, 1000)
                }
            })
        }

    }

    override fun getTitleString(): String {
        return "工单列表"
    }

    private var currCommunity: CommunityCountInfo = CommunityCountInfo()
    lateinit var dialog1: Dialog
    private fun showProjectPanel(info: List<CommunityCountInfo>?) {
        panel = LinearLayout(act)
        var panelParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        panel.orientation = LinearLayout.VERTICAL
        panel.layoutParams = panelParams
        var size = resources.getDimension(R.dimen.x20).toInt()
        panel.setPadding(size, size, size, size)
        addProjectViews(info, panel)
        var builder = AlertDialog.Builder(this,R.style.dialogStyle)
        dialog1 = builder.setView(panel).create()
        dialog1.show()
    }

    private lateinit var panel: LinearLayout

    private fun addProjectViews(info: List<CommunityCountInfo>?, panel: LinearLayout) {

        info?.forEachIndexed { index, data ->
            var child = LayoutInflater.from(act).inflate(R.layout.item_project, null)
            var childView = child.find<TextView>(R.id.tv_project)
            if (data.count.isNullOrEmpty())
                childView.text = data.name
            else
                childView.text = data.name
            childView.onClick {
                if (dialog1.isShowing) {
                    currCommunity = data
                    tv_select_project.text = data.name
                    //queryData(true)
                    dialog1.dismiss()
                }
            }
            panel.addView(child)
        }

    }

    private var projects: MutableList<CommunityCountInfo>? = arrayListOf()

    private fun getCommunityCount() {
        var request = GetMaintListRequest()
        var head = NewRequestHead().setaccessToken(config.token).setuserId(config.userId)
        var body = request.GetMaintListBody()
        body.roleId = config.roleId

        request.body = body
        request.head = head
        var netTask = object : NetTask(config.server + NetConstant.GETWORKORDERCOUNTBYCOMMUNITY, request) {
            override fun onResponse(task: NetTask?, result: String?) {

                var response = ProjectCountInfoResponse.getResponse<ProjectCountInfoResponse>(result, ProjectCountInfoResponse::class.java)
                projects?.clear()
                var all = CommunityCountInfo()
                all.name = "所有项目"
                projects?.add(all)
                projects?.addAll(response.body)


                if (projects?.size!! > 0) {
                    showProjectPanel(projects)
                } else
                    showToast("未获取项目")

            }

        }
        addTask(netTask)

    }

    override fun initView() {
        initPtrClass()
        tv_select_project.onClick {
            //请求，显示对话框
            getCommunityCount()
            //showProjectPanel()
        }
        spinner_keyword.adapter = ArrayAdapter(this, R.layout.layout_spanner_item, keyword_array)
        spinner_state.adapter = ArrayAdapter(this, R.layout.layout_spanner_item, state_array)

        linear_add.onClick {
            //            val intent = Intent()
//            intent.setClass(act, AddMaintenanceWorkOrderActivity::class.java)
//            startActivity(intent)
            showDalog()
        }

        // lv_work_order.adapter = WorkOrderListAdapter(contart_list,act)

        tv_select_project.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                //showToast(et_keyword.text.toString())
            }

            override fun afterTextChanged(s: Editable) {
                // showToast(et_keyword.text.toString())
                queryData(true)
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // showToast(et_keyword.text.toString())
            }
        })


        spinner_state.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                queryData(true)
                //requestWorkOrder(spinner_keyword.selectedItem.toString(), et_keyword.text.toString(), spinner_state.selectedItem.toString())
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {
            }
        }

        spinner_keyword.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
//                when {
//                    spinner_keyword.selectedItem.toString() == MAIN_KEYWORD -> et_keyword.setText(MAIN_KEYWORD.substring(0, 2))
//                    spinner_keyword.selectedItem.toString() == FIX_KEYWORD -> et_keyword.setText(FIX_KEYWORD.substring(0, 2))
//                    else -> et_keyword.setText("")
//                }
                queryData(true)
                //requestWorkOrder(spinner_keyword.selectedItem.toString(), et_keyword.text.toString(), spinner_state.selectedItem.toString())
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {

            }
        }
        queryData(true)
        //requestWorkOrder(spinner_keyword.selectedItem.toString(), et_keyword.text.toString(), spinner_state.selectedItem.toString())
    }

    private fun queryData(isRefresh: Boolean) {
        //这里定义处理三个动态监听器，临时减少首次界面的加载次数
        if (count-- < 1)
            requestWorkOrder(spinner_keyword.selectedItem.toString(), currCommunity.id, spinner_state.selectedItem.toString(), isRefresh)
    }

    private var nextIdex = 2
    private var hasMore: Boolean = false

    private fun requestWorkOrder(key: String?, key_str: String?, state: String?, isRefresh: Boolean) {
        var contractInfoRequest = ContractInfoRequest()
        var body = ContractInfoRequest().ContractInfoBody()
        body.roleId = config.roleId
        body.branchId = config.branchId
        body.communityId = key_str
        if (isRefresh)
            body.page = 1
        else
            body.page = nextIdex
        var rowSize = 20
        body.rows = rowSize
        when (key) {
            MAIN_KEYWORD -> body.bizType = "1"
            FIX_KEYWORD -> body.bizType = "2"
            MAIN_OTHER -> body.bizType = "3"
        }

        when (state) {
            "全部" -> {
            }
            "处理中" -> body.statusCode = "dealing"
            "已处理" -> body.statusCode = "success"
        }
        if (body.statusCode.isEmpty() && body.orderNum.isEmpty() && body.orderName.isEmpty() && body.bizType.isEmpty())
            body.statusCodes = "111"
        contractInfoRequest.body = body
        contractInfoRequest.head = NewRequestHead().setaccessToken(config.token).setuserId(config.userId)
        var server = config.server + NetConstant.GET_WORK_ORDERS_BY_REPAIR
        var netTask = object : NetTask(server, contractInfoRequest) {
            override fun onResponse(task: NetTask?, result: String?) {
                var response = WorkOrderInfoResponse.getRepairInfoResponse(result)

//                if(response?.body?.size!!<rowSize)
//                hasMore = false
//                else {
//                    hasMore = true
//                    //   work_order_list = response.body
//                    nextIdex++
//                }
                nextIdex++
                if (isRefresh) {
                    work_order_list.clear()
                    nextIdex = 2
                }
                Log.i("sizesize",""+response.body.size)
                work_order_list.addAll(response.body)
                lv_work_order.adapter = WorkOrderListAdapter(work_order_list, act)
                if (nextIdex > 2)
                {
                    lv_work_order.setSelection(countNums)
                }
                countNums = lv_work_order.count-1

            }
        }
        addTask(netTask)
    }
var countNums = 0


    private fun showDalog() {
        var view: View = View.inflate(act, R.layout.choose_work_order_type_dialog, null)
        view.find<TextView>(R.id.tv_maintenance).onClick {
            val intent = Intent()
            intent.setClass(act, AddMaintenanceWorkOrderActivity::class.java)
            startActivity(intent)
            dialog?.dismiss()
        }
        view.find<TextView>(R.id.tv_other).onClick {

            val intent = Intent()
            intent.setClass(act, SelectEleInfoActivity::class.java)
            startActivity(intent)

            dialog?.dismiss()
        }
        dialog = Dialog(act)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setContentView(view)
        dialog?.show()
    }
}
