package com.honyum.elevatorMan.adapter

import android.app.ActionBar
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import com.honyum.elevatorMan.R
import com.honyum.elevatorMan.activity.repair.RepairDetailActivity
import com.honyum.elevatorMan.base.BaseFragmentActivity
import com.honyum.elevatorMan.data.RepairInfo

/**
 * Created by Star on 2017/10/20.
 */
class RepairListAdapter(val datas: List<RepairInfo>?, context: Context?, layoutId: Int = R.layout.repair_list_item, private val isManager: Boolean = false) : BaseListViewAdapter<RepairInfo>(datas, context, layoutId) {

    override fun bindData(holder: BaseViewHolder?, t: RepairInfo?, index: Int) {
        //合同状态：draft-草稿、approve-审批中、execute-履约中、finish-已完成、cancel-已作废，back-已退回
        var state: String = ""
        var type: String = ""
        //"1"-"出发"  "2"-"到达"  "3"-"完成"
        if (t?.state == "0") {
            state = "待确认"
        } else if (t?.state == "0.5") {
            state = "已确认"
        } else if (t?.state == "1") {
            state = "已出发"
        } else if (t?.state.equals("2")) {
            state = "已到达"
        } else if (t?.state.equals("3")) {
            state = "已完成"
        } else {
            state = "未处理"
        }


        if (t?.type.equals("1")) {
            type = "普通报修"
        } else if (t?.type.equals("2")) {
            type = "急修"
        }

        if (state.equals("未处理")) {
            val ll = holder?.getView<LinearLayout>(R.id.linear_work) as LinearLayout
            val lp = ll.layoutParams
            lp.height = 0
            holder?.getView<LinearLayout>(R.id.linear_work)?.visibility = View.INVISIBLE
        } else {
            val ll = holder?.getView<LinearLayout>(R.id.linear_work) as LinearLayout
            val lp = ll.layoutParams
            lp.height = ActionBar.LayoutParams.WRAP_CONTENT
            holder?.getView<LinearLayout>(R.id.linear_work)?.visibility = View.VISIBLE
            holder?.setText(R.id.tv_work_name, "处理人：" + t?.workName)
                    ?.setText(R.id.tv_work_tel, t?.workTel)
        }

        if (t?.back == "1")
        {
            state = "已撤销"
        }


        holder?.setText(R.id.tv_code, t?.code)
                ?.setText(R.id.tv_code, t?.code)
                ?.setText(R.id.tv_state, state)
                ?.setText(R.id.tv_date, t?.createTime)
                ?.setText(R.id.tv_fault_type, t?.faultName)
                ?.setText(R.id.tv_worker_name, "报修人：" + t?.repairUserName)
                ?.setText(R.id.tv_worker_tel, t?.repairTel)
                ?.setText(R.id.tv_type, type)
                ?.setText(R.id.tv_address, t?.elevatorInfo?.propertyCode+"/"+t?.communityInfo?.address)
                ?.setOnClickListener(R.id.linear_item, {
                    val intent = Intent()
                    //获取intent对象
                    intent.setClass(mContext, RepairDetailActivity::class.java)
                    var bundle = Bundle()
                    bundle.putSerializable("repair", t)
                    bundle.putBoolean("isManager", isManager)
                    intent.putExtras(bundle)
                    mContext.startActivity(intent)
                    if(t?.state.equals("2"))
                        (mContext as BaseFragmentActivity).finish()
                })
    }
}