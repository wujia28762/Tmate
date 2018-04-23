package com.honyum.elevatorMan.activity.common

import android.content.Context
import com.hanbang.netsdk.Log
import com.honyum.elevatorMan.R
import com.honyum.elevatorMan.adapter.BaseListViewAdapter
import com.honyum.elevatorMan.adapter.BaseViewHolder
import com.honyum.elevatorMan.base.BaseActivityWraper
import com.honyum.elevatorMan.base.Config
import com.honyum.elevatorMan.data.ProcessInfo
import com.honyum.elevatorMan.net.*
import com.honyum.elevatorMan.net.base.NetConstant
import com.honyum.elevatorMan.net.base.NetTask
import com.honyum.elevatorMan.utils.Utils
import kotlinx.android.synthetic.main.activity_approval_process.*
import org.jetbrains.anko.ctx
import java.util.*

/**
 * Created by Star on 2017/12/18.
 */
class ApprovalProcessActivity : BaseActivityWraper() {
    private lateinit var processInfos: MutableList<ProcessInfo>
    override fun getTitleString(): String {
        return "审批进度"
    }

    private fun requestProcessHistory() {
        var processHistoryRequest = ProcessHistoryRequest()
        var body = processHistoryRequest.ProcessHistoryRequestBody()

        body._process_isLastNode = Config.currLastNode
        body._process_task_param = Config.currTask
        body.branchId = config.branchId

        processHistoryRequest.head = NewRequestHead().setaccessToken(config.token).setuserId(config.userId)
        processHistoryRequest.body = body
        var server = config.newServer + NetConstant.GETAPPROVEHISTORYDATAGRID

        var netTask = object : NetTask(server, processHistoryRequest) {
            override fun onResponse(task: NetTask?, result: String?) {
                Log.e("contract_result", result + "==========")
                var response = ProcessInfoResponse.getProcessInfoResponse(result)
                list_view.adapter = ProcessAdapter(response?.body, ctx)
            }

        }
        addTask(netTask)
    }

    override fun initView() {
        requestProcessHistory()
                if("0" == Config.isFinish)
                    tv_state.text = "待审核"
                    else
                    tv_state.text = "已审核"
    }

    override fun getLayoutID(): Int {
        return R.layout.activity_approval_process
    }
}

class ProcessAdapter(datas: MutableList<ProcessInfo>?, context: Context?, layoutId: Int = R.layout.item_app_process) : BaseListViewAdapter<ProcessInfo>(datas, context, layoutId) {
    override fun bindData(holder: BaseViewHolder?, t: ProcessInfo?, index: Int) {
        holder?.setText(R.id.process_text, t?.currUserName)?.setText(R.id.tv_time, Utils.dateTimeToString(t?.processDate?.let {
            Date(it)
        }))?.setText(R.id.process_result,t?.processResult)?.setText(R.id.process_result_area,t?.processOpinion)
        if (index == mDatas.size - 1)  holder?.setImageResource(R.id.process_state,R.drawable.process_select)
        else
            holder?.setImageResource(R.id.process_state,R.drawable.process_no)
    }
}