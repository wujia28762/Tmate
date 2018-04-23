package com.honyum.elevatorMan.activity.contract

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import com.hanbang.netsdk.Log
import com.honyum.elevatorMan.R
import com.honyum.elevatorMan.adapter.ContartListAdapter
import com.honyum.elevatorMan.base.BaseActivityWraper
import com.honyum.elevatorMan.data.ContartInfo
import com.honyum.elevatorMan.net.ContractInfoRequest
import com.honyum.elevatorMan.net.ContractInfoResponse
import com.honyum.elevatorMan.net.NewRequestHead
import com.honyum.elevatorMan.net.base.NetConstant
import com.honyum.elevatorMan.net.base.NetTask
import kotlinx.android.synthetic.main.activity_search_contart.*
import org.jetbrains.anko.act
import java.util.*



class SearchContractActivity : BaseActivityWraper() {

    var keyword_array:Array<String> = arrayOf("合同编号","公司名称","签署人")

    var state_array:Array<String> = arrayOf("全部","履约中","已完成")

    var contart_list:MutableList<ContartInfo> = ArrayList<ContartInfo>()


    override fun getTitleString(): String {
        return "合同管理"
    }

    override fun initView() {
        spinner_keyword.adapter = ArrayAdapter(this,R.layout.layout_spanner_item,keyword_array) as SpinnerAdapter?
        spinner_state.adapter = ArrayAdapter(this,R.layout.layout_spanner_item,state_array)
        requestContract(spinner_keyword.selectedItem.toString(),et_keyword.text.toString(),spinner_state.selectedItem.toString())

        et_keyword.setFocusable(true);
        et_keyword.setFocusableInTouchMode(true);
        et_keyword.requestFocus();
        et_keyword.clearFocus()

        et_keyword.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
               //showToast(et_keyword.text.toString())
            }

            override fun afterTextChanged(s: Editable) {
               // showToast(et_keyword.text.toString())
                requestContract(spinner_keyword.selectedItem.toString(),et_keyword.text.toString(),spinner_state.selectedItem.toString())
            }

           override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
              // showToast(et_keyword.text.toString())
            }
        })


        spinner_state.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
               //showToast(""+spinner_state.selectedItem)
                requestContract(spinner_keyword.selectedItem.toString(),et_keyword.text.toString(),spinner_state.selectedItem.toString())
            }
            override fun onNothingSelected(adapterView: AdapterView<*>) {

            }
        }

        spinner_keyword.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
               // showToast(""+spinner_keyword.selectedItem)
                requestContract(spinner_keyword.selectedItem.toString(),et_keyword.text.toString(),spinner_state.selectedItem.toString())
            }
            override fun onNothingSelected(adapterView: AdapterView<*>) {

            }
        }
    }

    override fun getLayoutID(): Int {
        return R.layout.activity_search_contart
    }

    //合同状态：draft-草稿、approve-审批中、execute-履约中、finish-已完成、cancel-已作废，back-已退回

    private fun requestContract(key:String,key_str:String,state:String) {
        var contractInfoRequest = ContractInfoRequest()
        var body = ContractInfoRequest().ContractInfoBody()
        body.roleId = config.roleId
        body.branchId = config.branchId

        if(key_str.equals("")){
            if(state.equals("全部")){
            }else{
                if(state.equals("草稿")){
                    body.state = "draft"
                }else if(state.equals("审批中")){
                    body.state = "approve"
                }else if(state.equals("履约中")){
                    body.state = "execute"
                }else if(state.equals("已完成")){
                    body.state = "finish"
                }else if(state.equals("已作废")){
                    body.state = "cancel"
                }else if(state.equals("已退回")){
                    body.state = "back"
                }
            }
        }else{
            if(key.equals("合同编号")){
                body.code = key_str
            }else if(key.equals("公司名称")){
                body.branchName = key_str
            }else if(key.equals("签署人")){
                body.userName = key_str
            }

            if(state.equals("全部")){
            }else{
                if(state.equals("草稿")){
                    body.state = "draft"
                }else if(state.equals("审批中")){
                    body.state = "approve"
                }else if(state.equals("履约中")){
                    body.state = "execute"
                }else if(state.equals("已完成")){
                    body.state = "finish"
                }else if(state.equals("已作废")){
                    body.state = "cancel"
                }else if(state.equals("已退回")){
                    body.state = "back"
                }
            }
        }
        contractInfoRequest.body = body
        contractInfoRequest.head = NewRequestHead().setaccessToken(config.token).setuserId(config.userId)
        var server = config.server + NetConstant.GET_CONTRACT_BY_ROLEID

        var netTask = object : NetTask(server, contractInfoRequest) {
            override fun onResponse(task: NetTask?, result: String?) {
                Log.e("contract_result",result+"==========")
                var response = ContractInfoResponse.getContratInfoResponse(result)
                contart_list = response.body
                lv_contart.adapter = ContartListAdapter(contart_list,act)
            }
        }
        addBackGroundTask(netTask)
    }
}
