package com.honyum.elevatorMan.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.honyum.elevatorMan.R
import com.honyum.elevatorMan.activity.contract.ContractDetailActivity
import com.honyum.elevatorMan.data.ContartInfo

/**
 * Created by Star on 2017/10/20.
 */
class ContartListAdapter(val datas: List<ContartInfo>?, context: Context?, layoutId: Int = R.layout.contart_list_item) : BaseListViewAdapter<ContartInfo>(datas, context, layoutId) {
    override fun bindData(holder: BaseViewHolder?, t: ContartInfo?, index: Int) {

        //合同状态：draft-草稿、approve-审批中、execute-履约中、finish-已完成、cancel-已作废，back-已退回
        var state_image:Int = R.drawable.contract_state_execute
        if(t?.state!!.equals("approve")){
            state_image = R.drawable.contract_state_execute
        }else if(t.state.equals("draft")){
            state_image = R.drawable.contract_state_execute
            //state = "草稿"
        }else if(t.state.equals("execute")){
            state_image = R.drawable.contract_state_execute
           // state = "履约中"
        }else if(t.state.equals("finish")){
            state_image = R.drawable.contratc_state_finish
            //state = "已完成"
        }else if(t.state.equals("cancel")){
            state_image = R.drawable.contract_state_execute
            //state = "已作废"
        }else if(t.state.equals("back")){
            state_image = R.drawable.contract_state_execute
            //state = "已退回"
        }
       holder?.setText(R.id.tv_code,t.code)
               ?.setText(R.id.tv_sign_person,"签署人："+t.userName)
               ?.setText(R.id.tv_business_type,t.businessType)
               ?.setText(R.id.tv_company,t.branchName)
               ?.setText(R.id.tv_procurement_type,t.procurementType)
               ?.setImageResource(R.id.iv_state,state_image)
               ?.setOnClickListener(R.id.linear_item,{
                    val intent = Intent()
                    //获取intent对象
                    intent.setClass(mContext, ContractDetailActivity::class.java)
                    var bundle = Bundle()
                   bundle.putString("code",t?.id)
                   intent.putExtras(bundle)
                   mContext.startActivity(intent)
                })
    }
}