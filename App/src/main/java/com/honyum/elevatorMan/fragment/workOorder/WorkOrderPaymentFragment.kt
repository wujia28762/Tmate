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
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.startActivity

class WorkOrderPaymentFragment : Fragment() {

    var workOrderInfo: WorkOrderInfo = WorkOrderInfo()


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var bundle: Bundle = arguments
        workOrderInfo = bundle.getSerializable("workOrderInfo") as WorkOrderInfo

        var view: View = inflater!!.inflate(R.layout.work_order_payment_fragment,null)
        initView(view)
        return view
    }

    fun initView(view:View){



        view.find<TextView>(R.id.tv_contract_name).text = workOrderInfo.contractInfo.name

        view.find<TextView>(R.id.tv_contract_code).text = workOrderInfo.contractInfo.code
        view.find<TextView>(R.id.tv_contract_name).text = workOrderInfo.contractInfo.name
        if (!(""+workOrderInfo.contractInfo.payMoney).isEmpty())
        view.find<TextView>(R.id.tv_contract_money).text = ""+workOrderInfo.contractInfo.payMoney

        view.find<TextView>(R.id.tv_purchase_money).text = ""+workOrderInfo.purchaseMoney
        view.find<TextView>(R.id.tv_rabate_money).text = ""+workOrderInfo.rebateMoney
        view.find<TextView>(R.id.tv_other_money).text = ""+workOrderInfo.otherMoney
        view.find<TextView>(R.id.tv_receivable_money).text = ""+workOrderInfo.receivableMoney
        view.find<TextView>(R.id.tv_money_desc).text = workOrderInfo.moneyDesc
    }
}