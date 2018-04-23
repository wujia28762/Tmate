package com.honyum.elevatorMan.adapter

import android.content.Context
import com.honyum.elevatorMan.R
import com.honyum.elevatorMan.data.FaultTypeInfo

/**
 * Created by Star on 2017/10/20.
 */
 class FaultTypeListAdapter(val datas: List<FaultTypeInfo>?, context: Context?, layoutId: Int = R.layout.layout_fault_type_spanner_item) : BaseListViewAdapter<FaultTypeInfo>(datas, context, layoutId) {
    override fun bindData(holder: BaseViewHolder?, t: FaultTypeInfo?, index: Int) {
       holder?.setText(R.id.text1,t?.name)
    }
}