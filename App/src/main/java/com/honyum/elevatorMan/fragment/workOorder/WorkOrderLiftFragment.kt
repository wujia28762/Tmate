package com.honyum.elevatorMan.fragment.workOorder

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.honyum.elevatorMan.R
import com.honyum.elevatorMan.data.WorkOrderInfo
import org.jetbrains.anko.find

class WorkOrderLiftFragment : Fragment() {

    var workOrderInfo: WorkOrderInfo = WorkOrderInfo()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        var bundle: Bundle = arguments
        workOrderInfo = bundle.getSerializable("workOrderInfo") as WorkOrderInfo
        var view: View = inflater!!.inflate(R.layout.work_order_lift_fragment,null)
        initView(view)
        return view
    }

    fun initView(view:View){
        view.find<TextView>(R.id.tv_communityName).text = workOrderInfo.communityInfo.name
        view.find<TextView>(R.id.tv_lift_num).text = workOrderInfo.elevatorInfo.liftNum
        view.find<TextView>(R.id.tv_lift_address).text = workOrderInfo.elevatorInfo.address
        view.find<TextView>(R.id.tv_branch_name).text = workOrderInfo.propertyBranchName
        view.find<TextView>(R.id.tv_worker_company).text = workOrderInfo.branchName
        view.find<TextView>(R.id.tv_worker_name).text = workOrderInfo.elevatorInfo.maintUserName
        view.find<TextView>(R.id.tv_worker_tel).text = workOrderInfo.elevatorInfo.maintUserTel
        view.find<TextView>(R.id.tv_use_num).text = workOrderInfo.elevatorInfo.propertyCode

    }
}