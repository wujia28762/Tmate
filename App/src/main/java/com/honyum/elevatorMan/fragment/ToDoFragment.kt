package com.honyum.elevatorMan.activity.common

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import com.honyum.elevatorMan.R
import com.honyum.elevatorMan.activity.common.ToDoListActivity.Companion.currLastNode
import com.honyum.elevatorMan.activity.common.ToDoListActivity.Companion.currTask
import com.honyum.elevatorMan.activity.maintenance.PlanActivity
import com.honyum.elevatorMan.activity.maintenance.ToDoMaintActivity
import com.honyum.elevatorMan.activity.maintenance.ToDoPlanActivity
import com.honyum.elevatorMan.adapter.BaseListViewAdapter
import com.honyum.elevatorMan.adapter.BaseViewHolder
import com.honyum.elevatorMan.base.BaseFragmentActivity
import com.honyum.elevatorMan.base.Config
import com.honyum.elevatorMan.constant.IntentConstant
import com.honyum.elevatorMan.data.ToDoInfo
import com.honyum.elevatorMan.net.ToDoResponse
import com.honyum.elevatorMan.net.UploadPageRequest
import com.honyum.elevatorMan.net.base.NetConstant.UNDEALDATAGRID
import com.honyum.elevatorMan.net.base.NetTask
import com.honyum.elevatorMan.net.base.NewRequestHead
import com.honyum.elevatorMan.net.base.RequestBean
import com.honyum.elevatorMan.utils.Utils
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.startActivity
import java.util.*

/**
 * Created by Star on 2017/12/14.
 */
class ToDoFragment : Fragment() {

    private lateinit var mContext: BaseFragmentActivity
    private var mView: View? = null
    private fun getImageRequestBean(userId: String, token: String): RequestBean {
        val request = UploadPageRequest()
        request.head = NewRequestHead().setuserId(userId).setaccessToken(token)
        request.body = request.UploadPageRequestBody().setPage(0).setRows(5).setBranchId(mContext.config.branchId)
        return request
    }

    private fun requestSubmitInfo() {

        val server: String =
                mContext.config.newServer + UNDEALDATAGRID
        val netTask = object : NetTask(server, getImageRequestBean(mContext.config.userId, mContext.config.token)) {
            override fun onResponse(task: NetTask?, result: String?) {

                val response: ToDoResponse = ToDoResponse.getToDoInfoResponse(result)
                val listView = mView?.find<ListView>(R.id.lv_list)
                listView?.adapter = ToDoAdapter(response?.body, mContext)
                listView?.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                    response?.body[position].bizType?.let {
                        currLastNode = response?.body[position].isLastNode
                        Config.isFinish = response?.body[position].isFinish
                        currTask = response?.body[position].bizURL?.split("&_process_task_param=")?.get(1)
                        forwardDispatcher(it, response?.body[position].bizId,response?.body[position].id)
                    }

                }
                // listView?.adapter = ToDoAdapter(,mContext)
                //  mView?.find<ListView>(R.id.lv_list)?.adapter = ToDoAdapter(,mContext)
            }
        }
        mContext.addTask(netTask)

    }

    fun forwardDispatcher(type: String, id: String?, id1: String) {
        when (type) {
            "合同管理" -> id?.let {
                startActivity<ToDoDetailActivity>(IntentConstant.INTENT_ID to id)
            }
            "报修指派" -> id?.let {
                startActivity<ToDoRepairDetail>("repairId" to id,"taskId" to id1)
            }
            "维保计划审批" -> id?.let {
                startActivity<ToDoMaintActivity>("mainPlanId" to id,"enter_type" to "process")

            }
            "收款提醒"-> id?.let {
                startActivity<MoneyDetailActivity>(IntentConstant.INTENT_ID to id,IntentConstant.INTENT_DATA to type)
            }
            "付款提醒" -> id?.let {
                startActivity<MoneyDetailActivity>(IntentConstant.INTENT_ID to id,IntentConstant.INTENT_DATA to type)
            }

        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mContext = activity as BaseFragmentActivity
        mView = inflater?.inflate(R.layout.todo_fragment, container, false)
        return mView
    }

    override fun onResume() {
        super.onResume()
        requestSubmitInfo()
    }


    class ToDoAdapter(datas: MutableList<ToDoInfo>?, context: Context?, layoutId: Int = R.layout.item_todo) : BaseListViewAdapter<ToDoInfo>(datas, context, layoutId) {
        override fun bindData(holder: BaseViewHolder?, t: ToDoInfo, index: Int) {
            holder?.setText(R.id.tv_num, t.bizCode)?.setText(R.id.tv_name, t.bizName)?.setText(R.id.tv_node, t.nodeName)?.setText(R.id.tv_person, t.preUserName)?.setText(R.id.tv_biztype, t.bizType)?.setText(R.id.tv_des, t.bizDesc)?.setText(R.id.tv_date, Utils.dateTimeToString(Date(t?.lastUpdateDate)).split(" ")[0])

                    ?.setText(R.id.time,Utils.dateTimeToString(Date(t?.lastUpdateDate)).split(" ")[1])
        }
    }
}