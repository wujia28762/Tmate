package com.honyum.elevatorMan.activity.common

import android.text.TextUtils
import android.view.View.GONE
import com.honyum.elevatorMan.R
import com.honyum.elevatorMan.base.BaseActivityWraper
import com.honyum.elevatorMan.constant.IntentConstant
import com.honyum.elevatorMan.net.MoneyRecieveRequest
import com.honyum.elevatorMan.net.MoneyRecieveResponse
import com.honyum.elevatorMan.net.NewRequestHead
import com.honyum.elevatorMan.net.base.NetConstant.GETCONTRACTPAYMENTINFOBYID
import com.honyum.elevatorMan.net.base.NetTask
import kotlinx.android.synthetic.main.activity_recevie.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity

class MoneyDetailActivity:BaseActivityWraper() {
    lateinit var currId:String
    override fun getTitleString(): String {
        return if (intent.hasExtra(IntentConstant.INTENT_DATA))
            intent.getStringExtra(IntentConstant.INTENT_DATA)
        else
            "收付款信息"
    }

    override fun initView() {
        if (intent.hasExtra(IntentConstant.INTENT_ID)) {
            currId = intent.getStringExtra(IntentConstant.INTENT_ID)
        }
        else
            finish()
        contract_content.onClick {
            startActivity<ToDoDetailActivity>(IntentConstant.INTENT_ID to currId,"readonly" to true)
        }
    }

    override fun onResume() {
        super.onResume()
        if (!TextUtils.isEmpty(currId))
        getMoneyInfo(currId)
    }

    //点击立即处理。响应事件
    fun dealMoneyInfo(id: String?) {

        var request = MoneyRecieveRequest()
        request.body = request.MoneyRecieveRequestBody().setId(id)
        var head = NewRequestHead().setaccessToken(config.token).setuserId(config.userId)
        request.head = head

        var netTask = object : NetTask(config.server+config.contractUrl+GETCONTRACTPAYMENTINFOBYID,request) {
            override fun onResponse(task: NetTask?, result: String?) {
            }

        }
        addTask(netTask)

    }

    fun getMoneyInfo(id: String) {

        var request = MoneyRecieveRequest()
        request.body = request.MoneyRecieveRequestBody().setId(id)
        var head = NewRequestHead().setaccessToken(config.token).setuserId(config.userId)
        request.head = head

        var netTask = object : NetTask(config.server+config.contractUrl+GETCONTRACTPAYMENTINFOBYID,request) {
            override fun onResponse(task: NetTask?, result: String?) {
                var response = MoneyRecieveResponse.getResponse<MoneyRecieveResponse>(result,MoneyRecieveResponse::class.java)
                fillPage(response.body)
            }

        }
        addTask(netTask)

    }

    //获取返回数据，填充页面
    private fun fillPage(body: MoneyRecieveResponse.MoneyRecieveResponseBody?) {
        type_content.text = body?.type
        date_content.text = body?.paymentTime
        money_content.text = "${body?.paymentMoney}"
        percent_content.text = body?.percentage
        create_content.text = body?.createTime
        name_content.text = body?.userName
        phone_content.text = body?.userTel
        if(intent.getBooleanExtra("done",false))
            deal_money.visibility = GONE
        deal_money.onClick {
            dealMoneyInfo(body?.contractId)
        }
    }

    override fun getLayoutID(): Int {
        return R.layout.activity_recevie
    }
}