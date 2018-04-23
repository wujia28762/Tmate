package com.honyum.elevatorMan.fragment.contart

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hanbang.netsdk.Log
import com.honyum.elevatorMan.R
import com.honyum.elevatorMan.data.ContartInfo
import com.honyum.elevatorMan.net.ContractInfoDetailResponse
import org.jetbrains.anko.find

class ContartInfoFragment : Fragment() {

    var response:ContractInfoDetailResponse = ContractInfoDetailResponse()
    var contractInfo:ContartInfo = ContartInfo()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        var bundle:Bundle = arguments
        response = bundle.getSerializable("response") as ContractInfoDetailResponse
        contractInfo = response.body.contract
        var view:View = inflater!!.inflate(R.layout.activity_contart_info_fragment,null)

        initView(view)
        return view
    }

    fun initView(view:View) {
        //tv_code.text = contractInfo.code
        view.find<TextView>(R.id.tv_code).text = contractInfo.code
        view.find<TextView>(R.id.tv_name).text = contractInfo.name
        view.find<TextView>(R.id.tv_date).text = contractInfo.createTime
        view.find<TextView>(R.id.tv_start_time).text = contractInfo.startTime
        view.find<TextView>(R.id.tv_end_time).text = contractInfo.endTime
        view.find<TextView>(R.id.tv_username).text = contractInfo.userName
        view.find<TextView>(R.id.tv_type).text = contractInfo.type
        view.find<TextView>(R.id.tv_procurement_type).text = contractInfo.procurementType
        view.find<TextView>(R.id.tv_business_type).text = contractInfo.businessType
        view.find<TextView>(R.id.tv_isIncrementInvoice).text = contractInfo.isIncrementInvoice
        view.find<TextView>(R.id.tv_expenditure).text = contractInfo.expenditure
        view.find<TextView>(R.id.tv_payMoney).text = contractInfo.payMoney.toString()
        view.find<TextView>(R.id.tv_sketched).text = contractInfo.sketched
        view.find<TextView>(R.id.mainClause).text = contractInfo.mainClause

        Log.e("isIncrementInvoice",contractInfo.isIncrementInvoice)
    }
}
