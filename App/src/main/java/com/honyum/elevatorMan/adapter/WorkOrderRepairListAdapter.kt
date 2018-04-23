package com.honyum.elevatorMan.adapter

import android.content.Context
import com.honyum.elevatorMan.R
import com.honyum.elevatorMan.data.RepairInfo

/**
 * Created by Star on 2017/10/20.
 */
class WorkOrderRepairListAdapter(val datas: List<RepairInfo>?, context: Context?, layoutId: Int = R.layout.maintenance_list_item) : BaseListViewAdapter<RepairInfo>(datas, context, layoutId) {
    override fun bindData(holder: BaseViewHolder?, t: RepairInfo?, index: Int) {
        //合同状态：draft-草稿、approve-审批中、execute-履约中、finish-已完成、cancel-已作废，back-已退回
        //类型  1.维保 2.报修 99.其他
        var type:String = ""

       /* if(t?.mainType.equals("hm")){
            type = "半月保"
        }else if(t?.mainType.equals("m")){
            type = "月保"
        }else if(t?.mainType.equals("s")){
            type = "季保"
        }else if(t?.mainType.equals("y")){
            type = "年保"
        }*/

       holder?.setText(R.id.tv_lift_num,t?.elevatorInfo?.liftNum)
               ?.setText(R.id.tv_address,t?.communityInfo?.address+""+t?.elevatorInfo?.buildingCode+"号楼"+t?.elevatorInfo?.unitCode+"单元")
               ?.setText(R.id.tv_type,t?.faultName)
               ?.setText(R.id.tv_plan_time,"报修时间："+t?.createTime)
             /*  ?.setOnClickListener(R.id.tv_detail,{
                    *//*val intent = Intent()
                    intent.setClass(mContext, MaintenanceWorkOrderDetailActivity::class.java)
                    var bundle = Bundle()
                    bundle.putSerializable("workOrderInfo",t)
                    intent.putExtras(bundle)
                    mContext.startActivity(intent)*//*
                })*/
    }
}