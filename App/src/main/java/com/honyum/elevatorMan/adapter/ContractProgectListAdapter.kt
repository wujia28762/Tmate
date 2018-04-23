package com.honyum.elevatorMan.adapter

import android.content.Context
import com.honyum.elevatorMan.R
import com.honyum.elevatorMan.data.ContractElevator

/**
 * Created by Star on 2017/10/20.
 */
class ContractProgectListAdapter(val datas: List<ContractElevator>?, context: Context?, layoutId: Int = R.layout.contart_progect_list_item) : BaseListViewAdapter<ContractElevator>(datas, context, layoutId) {
    override fun bindData(holder: BaseViewHolder?, t: ContractElevator?, index: Int) {
        //合同状态：draft-草稿、approve-审批中、execute-履约中、finish-已完成、cancel-已作废，back-已退回
       holder?.setText(R.id.tv_communityId,"使用编号："+ t?.propertyCode + "/" + t?.buildingCode + "号楼" + t?.unitCode + "单元")
               ?.setText(R.id.tv_communityName,"项目名称："+t?.communityName)
               ?.setText(R.id.tv_liftNum,"电梯编号："+t?.liftNum)
               ?.setOnClickListener(R.id.linear_item,{
                   /* val intent = Intent()
                    //获取intent对象
                    intent.setClass(mContext, ContractDetailActivity::class.java)

                    var bundle = Bundle()
                   bundle.putString("code",t?.id)
                   intent.putExtras(bundle)
                    mContext.startActivity(intent)*/
                })
    }
}