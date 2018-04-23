package com.honyum.elevatorMan.activity.common

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View.GONE
import com.hanbang.netsdk.Log
import com.honyum.elevatorMan.R
import com.honyum.elevatorMan.adapter.ContartDetailAdapter
import com.honyum.elevatorMan.base.BaseActivityWraper
import com.honyum.elevatorMan.base.Config
import com.honyum.elevatorMan.constant.IntentConstant
import com.honyum.elevatorMan.fragment.contart.*
import com.honyum.elevatorMan.net.ContractInfoDetailRequest
import com.honyum.elevatorMan.net.ContractInfoDetailResponse
import com.honyum.elevatorMan.net.NewRequestHead
import com.honyum.elevatorMan.net.base.NetConstant
import com.honyum.elevatorMan.net.base.NetTask
import kotlinx.android.synthetic.main.activity_to_detail_contract.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity
import java.net.URLDecoder


/**
 * Created by Star on 2017/12/15.
 */
class ToDoDetailActivity : BaseActivityWraper() {


    var tabs: Array<String> = arrayOf("基本信息", "联系人", "电梯项目", "附件", "收付款")
    var fragments: MutableList<Fragment> = ArrayList()
    var contractInfoFragment: ContartInfoFragment = ContartInfoFragment()
    var contartContactsFragment: ContartContactsFragment = ContartContactsFragment()
    var contartProjectFragment: ContartProjectFragment = ContartProjectFragment()
    var contartEnclosureFragment: ContartEnclosureFragment = ContartEnclosureFragment()
    var contartPaymentFragment: ContartPaymentFragment = ContartPaymentFragment()


    override fun getTitleString(): String {
        return "详情"
    }

    private var currId: String = ""
    private var readonly: Boolean = false
    private var done: Boolean = false

    override fun initView() {
        currId = intent.getStringExtra(IntentConstant.INTENT_ID)
        currId?.let {
            requestContractDeTail(it)
        }
        readonly = intent.getBooleanExtra("readonly",false)
        done = intent.getBooleanExtra("done",false)
        if (readonly||done)
        {
            tv_process.visibility = GONE
            tv_submit_process.visibility = GONE
        }
        tv_process.onClick {
            startActivity<ApprovalProcessActivity>()
        }
    }


    private fun requestContractDeTail(code: String) {
        var contractInfodetailRequest = ContractInfoDetailRequest()
        var body = ContractInfoDetailRequest().ContractInfoBody()
        body.id = code
        body.branchId = config.branchId
        body._process_isLastNode = ToDoListActivity.currLastNode
        body._process_task_param = ToDoListActivity.currTask

        contractInfodetailRequest.body = body
        contractInfodetailRequest.head = NewRequestHead().setaccessToken(config.token).setuserId(config.userId)

        var server = config.server+config.contractUrl + NetConstant.GET_CONTRACT_INFO_BY_ID

        var netTask = object : NetTask(server, contractInfodetailRequest) {
            override fun onResponse(task: NetTask?, result: String?) {
                Log.e("contract_result", result + "==========")
                var response = ContractInfoDetailResponse.getContratInfoResponse(result)
                var resMap = response.body._process_resultMap._process_task_param
                Config.currTask = URLDecoder.decode(resMap, "UTF-8")
                Config.currLastNode = response.body._process_resultMap._process_isLastNode
                fillPage(response)
            }

        }
        addBackGroundTask(netTask)
    }

    fun fillPage(info: ContractInfoDetailResponse) {
        val bundle = Bundle()
        bundle.putSerializable("response", info)
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

        var viewPageAdapter = ContartDetailAdapter(supportFragmentManager, tabs, fragments)
        view_pager.adapter = viewPageAdapter
        tab_layout.setupWithViewPager(view_pager)


        tv_submit_process.onClick {

            startActivity<ToDoDetailDealActivity>("currId" to currId,"url" to config.contractUrl)
        }


    }

    override fun getLayoutID(): Int {
        return R.layout.activity_to_detail_contract
    }
}

