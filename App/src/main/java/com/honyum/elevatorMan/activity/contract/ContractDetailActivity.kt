package com.honyum.elevatorMan.activity.contract

import android.os.Bundle
import android.support.v4.app.Fragment
import com.hanbang.netsdk.Log
import com.honyum.elevatorMan.R
import com.honyum.elevatorMan.adapter.ContartDetailAdapter
import com.honyum.elevatorMan.base.BaseActivityWraper
import com.honyum.elevatorMan.fragment.contart.*
import com.honyum.elevatorMan.net.ContractInfoDetailRequest
import com.honyum.elevatorMan.net.ContractInfoDetailResponse
import com.honyum.elevatorMan.net.NewRequestHead
import com.honyum.elevatorMan.net.base.NetConstant
import com.honyum.elevatorMan.net.base.NetTask
import kotlinx.android.synthetic.main.activity_contart_detail.*

class ContractDetailActivity : BaseActivityWraper() {

    var tabs:Array<String> = arrayOf("基本信息","联系人","电梯项目","附件","收付款")
    var fragments:MutableList<Fragment> = ArrayList<Fragment>()
    var contractInfoFragment:ContartInfoFragment = ContartInfoFragment()
    var contartContactsFragment:ContartContactsFragment = ContartContactsFragment()
    var contartProjectFragment:ContartProjectFragment = ContartProjectFragment()
    var contartEnclosureFragment:ContartEnclosureFragment = ContartEnclosureFragment()
    var contartPaymentFragment:ContartPaymentFragment = ContartPaymentFragment()

    override fun getTitleString(): String {
        return "合同详情"
    }

    override fun initView() {
        var bundle = intent.extras
        var code:String = bundle.get("code").toString()
        requestContractDeTail(code)
    }

    override fun getLayoutID(): Int {
        return R.layout.activity_contart_detail
    }

    private fun requestContractDeTail(code:String){
        var contractInfodetailRequest = ContractInfoDetailRequest()
        var body = ContractInfoDetailRequest().ContractInfoBody()
        body.id = code

        contractInfodetailRequest.body = body
        contractInfodetailRequest.head = NewRequestHead().setaccessToken(config.token).setuserId(config.userId)

        var server = config.server +config.contractUrl+NetConstant.GET_CONTRACT_INFO_BY_ID

        var netTask = object : NetTask(server, contractInfodetailRequest) {
            override fun onResponse(task: NetTask?, result: String?) {
                Log.e("contract_result",result+"==========")
                var response = ContractInfoDetailResponse.getContratInfoResponse(result)

                val bundle = Bundle()
                bundle.putSerializable("response",response)
              //  bundle.putSerializable("contract",response.body.contract)

                contractInfoFragment.arguments = bundle
                contartContactsFragment.arguments = bundle
                contartEnclosureFragment.arguments = bundle
                contartProjectFragment.arguments = bundle
                contartPaymentFragment.arguments = bundle

                fragments.add(contractInfoFragment)
                fragments.add(contartContactsFragment)
                fragments.add(contartProjectFragment)
                fragments.add(contartEnclosureFragment)
                fragments.add(contartPaymentFragment)

                var viewPageAdapter: ContartDetailAdapter = ContartDetailAdapter(supportFragmentManager,tabs,fragments)
                view_pager.adapter = viewPageAdapter
                tab_layout.setupWithViewPager(view_pager)
            }

        }
        addBackGroundTask(netTask)
    }


}
