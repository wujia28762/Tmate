package com.honyum.elevatorMan.adapter

import android.content.Context
import com.honyum.elevatorMan.R
import com.honyum.elevatorMan.data.ContartInfo

/**
 * Created by Star on 2017/10/20.
 */
class ContractNameListAdapter(val datas: List<ContartInfo>?, context: Context?, layoutId: Int = R.layout.layout_fault_type_spanner_item) : BaseListViewAdapter<ContartInfo>(datas, context, layoutId) {
    override fun bindData(holder: BaseViewHolder?, t: ContartInfo?, index: Int) {
       holder?.setText(R.id.text1,t?.name)
    }
}