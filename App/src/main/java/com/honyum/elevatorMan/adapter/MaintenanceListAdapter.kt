package com.honyum.elevatorMan.adapter

import android.content.Context
import com.honyum.elevatorMan.R
import com.honyum.elevatorMan.data.MaintenanceInfo

/**
 * Created by Star on 2017/10/20.
 */
class MaintenanceListAdapter(val datas: List<MaintenanceInfo>?, context: Context?, layoutId: Int = R.layout.maintenance_list_item) : BaseListViewAdapter<MaintenanceInfo>(datas, context, layoutId) {
    override fun bindData(holder: BaseViewHolder?, t: MaintenanceInfo?, index: Int) {
        //合同状态：draft-草稿、approve-审批中、execute-履约中、finish-已完成、cancel-已作废，back-已退回
        //类型  1.维保 2.报修 99.其他

        var type:String = ""

        if(t?.mainType.equals("hm")){
            type = "半月保"
        }else if(t?.mainType.equals("m")){
            type = "月保"
        }else if(t?.mainType.equals("s")){
            type = "季保"
        }else if(t?.mainType.equals("y")){
            type = "年保"
        } else if(t?.mainType.equals("hy")){
            type = "半年保"
        }

       holder?.setText(R.id.tv_lift_num,t?.elevatorInfo?.liftNum)
               ?.setText(R.id.tv_address,t?.elevatorInfo?.propertyCode+"/"+t?.elevatorInfo?.communityName+""+t?.elevatorInfo?.buildingCode+"号楼"+t?.elevatorInfo?.unitCode+"单元")
               ?.setText(R.id.tv_plan_time,"维保时间："+t?.planTime)
               ?.setText(R.id.tv_type,"维保类型："+type)
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