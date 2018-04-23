package com.honyum.elevatorMan.adapter

import android.content.Context
import android.view.View
import com.honyum.elevatorMan.R
import com.honyum.elevatorMan.activity.workOrder.WorkOrderMaintResultActivity
import com.honyum.elevatorMan.constant.IntentConstant
import com.honyum.elevatorMan.data.MaintenanceContenInfo
import org.jetbrains.anko.startActivity

/**
 * Created by Star on 2017/10/20.
 */
class MaintenanceContentResultAdapter(val datas: List<MaintenanceContenInfo>?, context: Context?, layoutId: Int = R.layout.maintenance_content_item) : BaseListViewAdapter<MaintenanceContenInfo>(datas, context, layoutId) {
    override fun bindData(holder: BaseViewHolder?, t: MaintenanceContenInfo?, index: Int) {

        var state:Boolean = false
//        if(t?.state.equals("0")){
//            state = false
//        }else if(t?.state.equals("1")){
//            state = true
//        }
        state = t?.finish != "0"


       if(t?.picNum == 0){
            holder?.setVisible(R.id.iv_camera, false)
        }else {
           holder?.setVisible(R.id.iv_camera, true)
           holder?.setImageResource(R.id.iv_camera, R.drawable.camera2)
           holder?.setOnClickListener(R.id.linear_img,View.OnClickListener {
               mContext.startActivity<WorkOrderMaintResultActivity>(IntentConstant.INTENT_DATA to t!!)
           })
        }


       holder?.setText(R.id.checkbox,t?.content)
               ?.setChecked(R.id.checkbox,state)?.setText(R.id.class_name,t?.content)
             /*  ?.setOnClickListener(R.id.linear_img, {
                   val intent = Intent()
                   intent.setClass(mContext, MaintenanceContentPictureActivity::class.java)
                   mContext.startActivity(intent)
               })*/
    }
}