package com.honyum.elevatorMan.adapter

import android.content.Context
import com.honyum.elevatorMan.R
import com.honyum.elevatorMan.data.ContractPayment

/**
 * Created by Star on 2017/10/20.
 */
class ContractPaymentListAdapter(val datas: List<ContractPayment>?, context: Context?, layoutId: Int = R.layout.contart_payment_list_item) : BaseListViewAdapter<ContractPayment>(datas, context, layoutId) {
    override fun bindData(holder: BaseViewHolder?, t: ContractPayment?, index: Int) {
        //合同状态：draft-草稿、approve-审批中、execute-履约中、finish-已完成、cancel-已作废，back-已退回
       holder?.setText(R.id.tv_paymentMoney,"￥"+t?.paymentMoney)
               ?.setText(R.id.tv_paymentTime,t?.paymentTime)
               ?.setText(R.id.tv_percentage,"付款百分比："+t?.percentage+"%")
               ?.setText(R.id.tv_userName,"收款人："+t?.userName)
               ?.setText(R.id.tv_type,t?.type)
               ?.setOnClickListener(R.id.linear_item,{
                })
    }
}