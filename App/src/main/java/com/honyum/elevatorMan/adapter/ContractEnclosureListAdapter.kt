package com.honyum.elevatorMan.adapter

import android.content.Context
import com.honyum.elevatorMan.R
import com.honyum.elevatorMan.data.ContractFile
import org.jetbrains.anko.browse


/**
 * Created by Star on 2017/10/20.
 */
class ContractEnclosureListAdapter(val datas: List<ContractFile>?, context: Context?, layoutId: Int = R.layout.contart_enclosure_list_item) : BaseListViewAdapter<ContractFile>(datas, context, layoutId) {
    override fun bindData(holder: BaseViewHolder?, t: ContractFile?, index: Int) {
        //合同状态：draft-草稿、approve-审批中、execute-履约中、finish-已完成、cancel-已作废，back-已退回
       holder?.setText(R.id.tv_file_name,t?.fileName)
               ?.setOnClickListener(R.id.linear_item,{
                   mContext.browse(t?.url!!)
                })
    }
}