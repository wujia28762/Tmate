package com.honyum.elevatorMan.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import com.honyum.elevatorMan.R
import com.honyum.elevatorMan.activity.workOrder.MaintenanceWorkOrderDetailActivity
import com.honyum.elevatorMan.constant.IntentConstant
import com.honyum.elevatorMan.data.WorkOrderInfo
import com.honyum.elevatorMan.utils.Utils
import com.honyum.elevatorMan.view.CircleProgressView
import okhttp3.internal.Util
import org.jetbrains.anko.makeCall
import org.jetbrains.anko.startActivity
import java.util.*

/**
 * Created by Star on 2017/10/20.
 */
class WorkOrderListAdapter(val datas: List<WorkOrderInfo>?, context: Context?, layoutId: Int = R.layout.work_order_list_item) : BaseListViewAdapter<WorkOrderInfo>(datas, context, layoutId) {
    override fun bindData(holder: BaseViewHolder?, t: WorkOrderInfo?, index: Int) {
        //合同状态：draft-草稿、approve-审批中、execute-履约中、finish-已完成、cancel-已作废，back-已退回
        //类型  1.维保 2.报修 99.其他
        var type = "维保"
        when {
            t?.bizType == "1" -> type = "维保"
            t?.bizType == "2" -> type = "报修"
            else -> type = "其他"
        }

        var state = "已处理"
        when {
            t?.statusCode.equals("draft") -> state = "草稿"
            t?.statusCode.equals("confirm") -> state = "待确认"
            t?.statusCode.equals("deal") -> state = "待处理"
            t?.statusCode.equals("dealing") -> state = "处理中"
            t?.statusCode.equals("success") -> state = "已处理"
            t?.statusCode.equals("cancel") -> state = "已取消"
            t?.statusCode.equals("back") -> state = "已退回"
            t?.statusCode.equals("finish") -> state = "已完成"
        }
        if (state == "已处理"||state == "已取消") {
            holder?.setTextColor(R.id.tv_parts, Color.GRAY)
            holder?.setVisible(R.id.circleProgressbar,false)
            holder?.setVisible(R.id.tv_state,true)
            holder?.setText(R.id.final_day,"最晚维保日期："+t?.lastMaintainDate)
            if (t?.bizType != "1")
            holder?.setVisible(R.id.final_day,false)
        }
        else
        {
            holder?.setVisible(R.id.circleProgressbar,true)
            var cir = holder?.getCustomView<CircleProgressView>(R.id.circleProgressbar)
          //  val delay = Utils.getDaySub(Utils.dateTimeToString(Date()),t?.lastMaintainDate)
            holder?.setText(R.id.final_day,"最晚维保日期："+t?.lastMaintainDate)
            if (t?.bizType != "1")
                holder?.setVisible(R.id.final_day,false)
            val day = t?.days!!
            when {
                day>10 -> cir?.setProgressAndColor(100,Color.GREEN,""+day+"天")
                day>5 -> cir?.setProgressAndColor(66, ContextCompat.getColor(mContext,R.color.goldenrod),""+""+day+"天")
                else -> cir?.setProgressAndColor(33,Color.RED,""+""+day+"天")
            }
            holder?.setVisible(R.id.tv_state,false)
        }

        holder?.setText(R.id.state_name,t?.statusName)

        if (0 == t?.isNeedParts)
        {
            holder?.setVisible(R.id.tv_parts,false)
        }

        holder?.setText(R.id.tv_code,t?.orderNum)
               ?.setText(R.id.tv_state,state)
               ?.setText(R.id.tv_address,t?.propertyCode+" "+t?.orderName)
               ?.setText(R.id.tv_type,type)
               ?.setText(R.id.tv_worker_name,t?.assistantName)
               ?.setText(R.id.tv_worker_tel,t?.assistantTel)
               ?.setText(R.id.tv_time1,t?.startDate+"至"+t?.endDate)
               ?.setOnClickListener(R.id.tv_detail,{
                    mContext.startActivity<MaintenanceWorkOrderDetailActivity>(IntentConstant.INTENT_ID to t?.id!!)
                })

        if (state != "已处理"&&state != "已取消")
        {
            holder?.setText(R.id.tv_detail,"处理")
        }
        holder?.setText(R.id.iv_id,""+(index+1))
        holder?.setOnClickListener(R.id.tv_worker_tel, {
            if (t?.assistantTel!=null)
            mContext.makeCall(t.assistantTel)
        })



    }
}