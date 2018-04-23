package com.honyum.elevatorMan.fragment.workOorder

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.honyum.elevatorMan.R
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.act

class WorkOrderEvaluateFragment : Fragment() {

   /* var response: ContractInfoDetailResponse = ContractInfoDetailResponse()
    var contractInfo: ContartInfo = ContartInfo()*/

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
     /*   var bundle: Bundle = arguments
        response = bundle.getSerializable("response") as ContractInfoDetailResponse
        contractInfo = response.body.contract*/
        var view: View = inflater!!.inflate(R.layout.work_order_evaluate_fragment,null)
        //initView(view)
        view?.find<TextView>(R.id.tip).visibility = View.VISIBLE
        return view
    }

}