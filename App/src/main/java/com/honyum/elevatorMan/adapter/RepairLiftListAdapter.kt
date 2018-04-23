package com.honyum.elevatorMan.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.honyum.elevatorMan.R
import com.honyum.elevatorMan.activity.repair.RepairLiftDetailActivity
import com.honyum.elevatorMan.base.BaseFragmentActivity
import com.honyum.elevatorMan.data.LiftInfo

/**
 * Created by Star on 2017/10/20.
 */
class RepairLiftListAdapter(val datas: List<LiftInfo>?, context: Context?, layoutId: Int = R.layout.repair_lift_list_item) : BaseListViewAdapter<LiftInfo>(datas, context, layoutId) {
    override fun bindData(holder: BaseViewHolder?, t: LiftInfo?, index: Int) {
       holder?.setText(R.id.tv_num,""+(index+1))
              ?.setText(R.id.tv_code,t?.num)
               ?.setText(R.id.tv_address,t?.address)
               ?.setOnClickListener(R.id.linear_item,{
                   val intent = Intent()
                   //获取intent对象
                   intent.setClass(mContext, RepairLiftDetailActivity::class.java)
                   var bundle = Bundle()
                   bundle.putSerializable("lift",t)
                   intent.putExtras(bundle)
                   mContext.startActivity(intent)
                   (mContext as BaseFragmentActivity).finish()
                })
    }
}