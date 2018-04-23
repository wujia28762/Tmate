package com.honyum.elevatorMan.activity.workOrder

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.view.View.GONE
import android.widget.LinearLayout
import com.honyum.elevatorMan.R
import com.honyum.elevatorMan.activity.maintenance.OtherMaintenanceActivity
import com.honyum.elevatorMan.adapter.ContartDetailAdapter
import com.honyum.elevatorMan.base.BaseActivityWraper
import com.honyum.elevatorMan.constant.IntentConstant
import com.honyum.elevatorMan.data.WorkOrderInfo
import com.honyum.elevatorMan.fragment.workOorder.*
import com.honyum.elevatorMan.net.ContractInfoRequest
import com.honyum.elevatorMan.net.GetWorkOrderRequest
import com.honyum.elevatorMan.net.GetworkOrderInfoResponse
import com.honyum.elevatorMan.net.NewRequestHead
import com.honyum.elevatorMan.net.base.NetConstant
import com.honyum.elevatorMan.net.base.NetTask
import kotlinx.android.synthetic.main.activity_maintenance_work_order_detail.*
import org.jetbrains.anko.act
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.io.Serializable


class MaintenanceWorkOrderDetailActivity : BaseActivityWraper() {

    var tabs:Array<String> = arrayOf("基本信息","电梯信息","处理结果","评价")

    var fragments:MutableList<Fragment> = ArrayList<Fragment>()

    var workOrderInfo: WorkOrderInfo = WorkOrderInfo()


    override fun getLayoutID(): Int {
        return R.layout.activity_maintenance_work_order_detail
    }

    override fun getTitleString(): String {
        return "工单管理"
    }
    public fun getWorkOrdersById(id:String)
    {
        var request = GetWorkOrderRequest()
        var body = request.GetWorkOrderRequestBody()
        body.id = id
        var head = NewRequestHead()
        head.setaccessToken(config.token)
        head.setuserId(config.userId)
        request.body = body
        request.head = head

        var netTask = object : NetTask(config.server+NetConstant.GETWORKORDERSBYID,request) {
            override fun onResponse(task: NetTask?, result: String?) {
                var response = GetworkOrderInfoResponse.getResponse<GetworkOrderInfoResponse>(result,GetworkOrderInfoResponse::class.java)
                if (response!=null&&response.body!=null)
                {
                    var bundle = Bundle()
                    bundle.putSerializable("workOrderInfo",response.body)
                    intent.putExtras(bundle)
                    initView()
                }
            }
        }
        addTask(netTask)
    }
    lateinit var id :String
    private  var viewPageAdapter: ContartDetailAdapter?=null

    override fun initView() {

        if (!intent.hasExtra("workOrderInfo"))
        {
            id = intent.getStringExtra(IntentConstant.INTENT_ID)
            if (id.isNotBlank())
                getWorkOrdersById(id)
                return
        }
        var bundle = intent.extras
         workOrderInfo = bundle.getSerializable("workOrderInfo") as WorkOrderInfo//  showDeleteDiaog()
//            var layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                    LinearLayout.LayoutParams.MATCH_PARENT)
//            alert {
//                customView{
//                    linearLayout {
//                        backgroundColor = Color.WHITE
//                        textView("取消后不可以恢复，您确定取消吗？")
//                        {
//                            textColor=Color.BLACK
//                        }.lparams{width = matchParent
//                            height = matchParent}
//                         }.layoutParams = layoutParams
//                }
//                yesButton {requestCancelWorkOrder()
//                }
//            }.show()
//开始维修
//                val intent = Intent()
//                intent.setClass(act, RepairContentDetailActivity::class.java)
//                startActivity(intent)

        //startActivity<MaintenanceFinishActivity>(IntentConstant.INTENT_DATA to workOrderInfo)
        // success_repair_work_order(workOrderInfo)
        //linear_btn.he
        //val textView = findViewById(R.id.textview) as TextView
        //
        //
        //使设置好的布局参数应用到控件
        when {
            workOrderInfo.bizType.equals("1") -> tv_start_maintenance.text = "开始维保"
            workOrderInfo.bizType.equals("2") -> tv_start_maintenance.text = "开始维修"
            workOrderInfo.bizType.equals("3") -> tv_start_maintenance.text = "开始维保"
        }


        if(workOrderInfo.statusCode.equals("success")||workOrderInfo.statusCode.equals("cancel")){
            linear_btn.visibility = View.INVISIBLE
            //linear_btn.he
            //val textView = findViewById(R.id.textview) as TextView
            val linearParams:LinearLayout.LayoutParams = linear_btn.layoutParams as LinearLayout.LayoutParams
            linearParams.height = 0//
            linearParams.width = matchParent//
            linear_btn.layoutParams = linearParams //使设置好的布局参数应用到控件
        }
        tv_parts.onClick {
        }
        if (workOrderInfo.isNeedParts == 0)
            tv_parts.visibility = GONE



        tv_start_maintenance.onClick {
            if(workOrderInfo.bizType.equals("1")){
                val intent = Intent()
                intent.setClass(act, MaintenanceContentActivity::class.java)
                var bundle = Bundle()
                bundle.putSerializable("workOrderInfo",workOrderInfo)
                intent.putExtras(bundle)
                startActivity(intent)
                finish()
            }else if(workOrderInfo.bizType.equals("2")){
                //开始维修
//                val intent = Intent()
//                intent.setClass(act, RepairContentDetailActivity::class.java)
//                startActivity(intent)
                val intent = Intent()
                intent.setClass(act, MaintenanceFinishActivity::class.java)
                intent.putExtra(IntentConstant.INTENT_DATA,workOrderInfo as Serializable)
                startActivity(intent)
                finish()

                //startActivity<MaintenanceFinishActivity>(IntentConstant.INTENT_DATA to workOrderInfo)
               // success_repair_work_order(workOrderInfo)
            }
            else if (workOrderInfo.bizType.equals("3")){
                //开始维修
//                val intent = Intent()
//                intent.setClass(act, RepairContentDetailActivity::class.java)
//                startActivity(intent)
                val intent = Intent()
                intent.setClass(act, OtherMaintenanceActivity::class.java)
                intent.putExtra(IntentConstant.INTENT_DATA,workOrderInfo as Serializable)
                startActivity(intent)
                finish()

                //startActivity<MaintenanceFinishActivity>(IntentConstant.INTENT_DATA to workOrderInfo)
                // success_repair_work_order(workOrderInfo)
            }
        }

        var workOrderInfoFragment:WorkOrderInfoFragment = WorkOrderInfoFragment()
        var workOrderLiftFragment:WorkOrderLiftFragment = WorkOrderLiftFragment()
        //var workOrderPaymentFragment:WorkOrderPaymentFragment = WorkOrderPaymentFragment()
        var workOrderEnclosureFragment:WorkOrderEnclosureFragment = WorkOrderEnclosureFragment()
        var workOrderEvaluateFragment:WorkOrderEvaluateFragment = WorkOrderEvaluateFragment()


        workOrderInfoFragment.arguments = bundle
        workOrderLiftFragment.arguments = bundle
     //   workOrderPaymentFragment.arguments = bundle
        workOrderEnclosureFragment.arguments = bundle
        workOrderEvaluateFragment.arguments = bundle


        fragments.add(workOrderInfoFragment)
        fragments.add(workOrderLiftFragment)
       // fragments.add(workOrderPaymentFragment)
        fragments.add(workOrderEnclosureFragment)
        fragments.add(workOrderEvaluateFragment)
        viewPageAdapter = ContartDetailAdapter(supportFragmentManager, tabs, fragments)
        view_pager.adapter = viewPageAdapter
        tab_layout.setupWithViewPager(view_pager)
        tv_cancel.onClick {
            showDeleteDiaog()
          //  requestCancelWorkOrder()
//            var layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                    LinearLayout.LayoutParams.MATCH_PARENT)
//            alert {
//                customView{
//                    linearLayout {
//                        backgroundColor = Color.WHITE
//                        textView("取消后不可以恢复，您确定取消吗？")
//                        {
//                            textColor=Color.BLACK
//                        }.lparams{width = matchParent
//                            height = matchParent}
//                         }.layoutParams = layoutParams
//                }
//                yesButton {requestCancelWorkOrder()
//                }
//            }.show()


        }
    }

    fun showDeleteDiaog() {
        //这里的构造函数使用两个参数，第二个参数即为设置样式
        val builder = AlertDialog.Builder(this, R.style.dialogStyle)
        builder.setMessage("取消后不可以恢复，您确定取消吗？").setCancelable(false).setTitle("提示")
                .setPositiveButton("确定", DialogInterface.OnClickListener { dialog, id ->
                    requestCancelWorkOrder()
                    dialog.cancel()
                })
                .setNegativeButton("取消", DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })
        builder.show()
    }

    private fun success_repair_work_order(workOrderInfo:WorkOrderInfo){
        var contractInfoRequest = ContractInfoRequest()
        var body = ContractInfoRequest().ContractInfoBody()
        body.workOrderId = workOrderInfo.id
        contractInfoRequest.body = body
        contractInfoRequest.head = NewRequestHead().setaccessToken(config.token).setuserId(config.userId)
        var server = config.server + NetConstant.SUCCESS_BAOXIU_WORK_ORDER
        var netTask = object : NetTask(server, contractInfoRequest) {
            override fun onResponse(task: NetTask?, result: String?) {
                /*var response = WorkOrderInfoResponse.getRepairInfoResponse(result)
                work_order_list = response.body
                lv_work_order.adapter = WorkOrderListAdapter(work_order_list,applicationContext)*/
                showToast("提交成功")
                finish()
            }
        }
        addTask(netTask)
    }

    private fun requestCancelWorkOrder(){
        var contractInfoRequest = ContractInfoRequest()
        var body = ContractInfoRequest().ContractInfoBody()
        body.workOrderId = workOrderInfo.id
        contractInfoRequest.body = body
        contractInfoRequest.head = NewRequestHead().setaccessToken(config.token).setuserId(config.userId)
        var server = config.server + NetConstant.CANCEL_WORK_ORDER
        var netTask = object : NetTask(server, contractInfoRequest) {
            override fun onResponse(task: NetTask?, result: String?) {
                /*var response = WorkOrderInfoResponse.getRepairInfoResponse(result)
                work_order_list = response.body
                lv_work_order.adapter = WorkOrderListAdapter(work_order_list,applicationContext)*/
                showToast("取消成功")
                finish()
            }
        }
        addTask(netTask)
    }
}
