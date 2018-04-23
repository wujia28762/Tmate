package com.honyum.elevatorMan.fragment.contart

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.honyum.elevatorMan.R
import com.honyum.elevatorMan.data.ContartInfo
import com.honyum.elevatorMan.net.ContractInfoDetailResponse
import org.jetbrains.anko.find

class ContartContactsFragment : Fragment() {

    var response: ContractInfoDetailResponse = ContractInfoDetailResponse()

    var contractInfo: ContartInfo = ContartInfo()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var view:View = inflater!!.inflate(R.layout.activity_contart_contacts_fragment,null)
        var bundle:Bundle = arguments
        response = bundle.getSerializable("response") as ContractInfoDetailResponse
        contractInfo = response.body.contract
        initView(view)
        return view
    }

    fun initView(view:View){
        view.find<TextView>(R.id.tv_username).text = contractInfo.userName
        view.find<TextView>(R.id.tv_userTel).text = contractInfo.userTel
        view.find<TextView>(R.id.tv_userEmail).text = contractInfo.userEmail
        view.find<TextView>(R.id.tv_branch_name).text = contractInfo.branchName
        view.find<TextView>(R.id.tv_branchManager).text = contractInfo.branchManager
        view.find<TextView>(R.id.tv_branchManagerTel).text = contractInfo.branchTel
        view.find<TextView>(R.id.tv_branchManagerEmail).text = contractInfo.branchManagerEmail
    }
}
