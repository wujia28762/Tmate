package com.honyum.elevatorMan.activity.workOrder

import android.content.Context
import android.content.Intent
import android.view.View.VISIBLE
import android.widget.AdapterView
import com.honyum.elevatorMan.R
import com.honyum.elevatorMan.activity.workOrder.AddWorkOrderDetailActivity.Companion.SCUESS
import com.honyum.elevatorMan.adapter.BaseListViewAdapter
import com.honyum.elevatorMan.adapter.BaseViewHolder
import com.honyum.elevatorMan.base.BaseActivityWraper
import com.honyum.elevatorMan.constant.IntentConstant
import com.honyum.elevatorMan.data.PersonInfo
import com.honyum.elevatorMan.net.AlarmListRequest
import com.honyum.elevatorMan.net.NewRequestHead
import com.honyum.elevatorMan.net.SelectRequest
import com.honyum.elevatorMan.net.WorkOrderPersonResponse
import com.honyum.elevatorMan.net.base.NetConstant
import com.honyum.elevatorMan.net.base.NetTask
import com.honyum.elevatorMan.net.base.RequestBean
import com.honyum.elevatorMan.net.base.RequestHead
import kotlinx.android.synthetic.main.activity_select_list.*
import org.jetbrains.anko.act
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.io.Serializable

class SelectMaintenancePersonMutiActivity : BaseActivityWraper() {
    private var personList: MutableList<PersonInfo>? = arrayListOf()
    override fun getTitleString(): String {
        return "协同人选择"
    }

    private fun getSelectInfo() {
        var request = SelectRequest()

        var body = request.SelectRequestBody()

        body.elevatorId = id

        request.body = body

        request.head = NewRequestHead().setuserId(config.userId).setaccessToken(config.token)

        var server = config.server + NetConstant.GETSELECT

        var netTask =
                object : NetTask(server, request) {
                    override fun onResponse(task: NetTask?, result: String?) {

                        var response = WorkOrderPersonResponse.getResponse<WorkOrderPersonResponse>(result, WorkOrderPersonResponse::class.java)


                        if (0 == response.body?.size) {
                            tv_tip_inflate.visibility = VISIBLE
                            return
                        }
                        personList = response.body.filter { it.userId != userId }.toMutableList()

                        adapter = SelectEleAdapter1(personList, act)
                        lv_data.adapter = adapter
                        lv_data.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                            personList?.let {
                                it[position].selected = !it[position].selected
                                adapter.notifyDataSetChanged()
                            }
                        }

                    }

                }
        addBackGroundTask(netTask)

    }

    /**
     * 获取请求的bean
     *
     * @param userId
     * @param token
     * @return
     */
    private fun getRequestBean(userId: String, token: String): RequestBean {

        //只需要发送一个head即可，这里使用请求报警列表的request bean
        val request = AlarmListRequest()
        val head = RequestHead()

        head.userId = userId
        head.accessToken = token

        request.head = head

        return request
    }


    private lateinit var adapter: SelectEleAdapter1

//    /**
//     * 请求电梯信息
//     */
//    private fun requestLiftInfo() {
//        val task = object : NetTask(config.server + NetConstant.URL_GET_LIFT_INFO,
//                getRequestBean(config.userId, config.token)) {
//            override fun onResponse(task: NetTask, result: String) {
//                val response = LiftInfoResponse.getLiftInfoResponse(result)
//                liftInfoList = response.body
//                if (0 == liftInfoList?.size) {
//                    tv_tip_inflate.visibility = VISIBLE
//                    return
//                }
//
//                adapter = SelectEleAdapter1(liftInfoList, act)
//                lv_data.adapter = adapter
//                lv_data.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
//                    liftInfoList?.let {
//                        it[position].selected = !it[position].selected
//                        adapter.notifyDataSetChanged()
//                    }
//                }
//            }
//        }
//        addTask(task)
//    }

    private var id: String? = ""

    private var userId: String? = ""
    override fun initView() {
        //requestLiftInfo()
        if (intent.hasExtra(IntentConstant.INTENT_ID)&&intent.hasExtra(IntentConstant.INTENT_DATA)) {

            userId = intent.getStringExtra(IntentConstant.INTENT_DATA)
            id = intent.getStringExtra(IntentConstant.INTENT_ID)
        }
        getSelectInfo()
        submit.onClick {
            var returnData: MutableList<PersonInfo>? = personList?.filter {
                it.selected
            }?.toMutableList()
            var intent = Intent()
            intent.putExtra(IntentConstant.INTENT_DATA, returnData as Serializable)
            setResult(SCUESS, intent)
            finish()
        }
    }

    override fun getLayoutID(): Int {
        return R.layout.activity_select_list
    }


}

class SelectEleAdapter1(datas: MutableList<PersonInfo>?, context: Context?, layoutId: Int = R.layout.item_select_eles) : BaseListViewAdapter<PersonInfo>(datas, context, layoutId) {
    override fun bindData(holder: BaseViewHolder?, t: PersonInfo?, index: Int) {
        holder?.setText(R.id.num, t?.userName)?.setText(R.id.address, t?.userTel)
        if (t?.selected!!)
            holder?.setImageResource(R.id.image, R.drawable.selected)
        else
            holder?.setImageResource(R.id.image, R.drawable.non_select)
    }
}