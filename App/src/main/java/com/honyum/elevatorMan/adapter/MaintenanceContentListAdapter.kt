package com.honyum.elevatorMan.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.CompoundButton
import com.honyum.elevatorMan.R
import com.honyum.elevatorMan.activity.workOrder.MaintenanceContentActivity
import com.honyum.elevatorMan.activity.workOrder.MaintenanceContentPictureActivity
import com.honyum.elevatorMan.data.MaintenanceContenInfo
import kotlinx.android.synthetic.main.activity_maintenance_content.*
import org.jetbrains.anko.act

/**
 * Created by Star on 2017/10/20.
 */
class MaintenanceContentListAdapter(val datas: List<MaintenanceContenInfo>?, context: Context?, layoutId: Int = R.layout.maintenance_content_item) : BaseListViewAdapter<MaintenanceContenInfo>(datas, context, layoutId) {
    override fun bindData(holder: BaseViewHolder?, t: MaintenanceContenInfo?, index: Int) {

        var state: Boolean = false
        if (t?.state.equals("0")) {
            state = false
        } else if (t?.state.equals("1")) {
            state = true
        }


        if (t?.picNum == 0) {
            holder?.setVisible(R.id.iv_camera, false)
        } else {
            if (t?.datas != null&&t?.datas.size>0&&t?.picNum == t?.datas.size) {
                holder?.setVisible(R.id.iv_camera, true)
                holder?.setImageResource(R.id.iv_camera, R.drawable.camera2)
            } else {
                holder?.setVisible(R.id.iv_camera, true)
                holder?.setImageResource(R.id.iv_camera, R.drawable.camera1)
            }
        }

        holder?.setText(R.id.checkbox, t?.bclass)
                ?.setChecked(R.id.checkbox, state)
                ?.setOnClickListener(R.id.linear_img, {
                    val intent = Intent()
                    intent.setClass(mContext, MaintenanceContentPictureActivity::class.java)
                    var bundle = Bundle()
                    bundle.putSerializable("maintItemInfo", t)
                    intent.putExtras(bundle)
                    (mContext as MaintenanceContentActivity).startActivityForResult(intent, index)
                })?.setOnCheckedChangeListener(R.id.checkbox, CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                t?.state = "1"
            } else {
                t?.state = "0"
                //btn_check.isChecked = isChecked
            }
            //adapter?.notifyDataSetChanged()

            (mContext as MaintenanceContentActivity).performItemCallback(t)
        })?.setText(R.id.class_name,t?.content)


    }
}