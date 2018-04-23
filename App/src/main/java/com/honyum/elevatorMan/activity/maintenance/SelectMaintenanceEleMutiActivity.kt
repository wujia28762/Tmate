package com.honyum.elevatorMan.activity.maintenance

import android.content.Context
import android.content.Intent
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.AdapterView
import com.honyum.elevatorMan.R
import com.honyum.elevatorMan.activity.maintenance.MakePlanMutiActivity.Companion.SUCCESS
import com.honyum.elevatorMan.adapter.BaseListViewAdapter
import com.honyum.elevatorMan.adapter.BaseViewHolder
import com.honyum.elevatorMan.base.BaseActivityWraper
import com.honyum.elevatorMan.constant.IntentConstant
import com.honyum.elevatorMan.data.LiftInfo
import com.honyum.elevatorMan.net.AlarmListRequest
import com.honyum.elevatorMan.net.LiftInfoResponse
import com.honyum.elevatorMan.net.base.NetConstant
import com.honyum.elevatorMan.net.base.NetTask
import com.honyum.elevatorMan.net.base.RequestBean
import com.honyum.elevatorMan.net.base.RequestHead
import kotlinx.android.synthetic.main.activity_select_eles.*
import org.jetbrains.anko.act
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.io.Serializable

class SelectMaintenanceEleMutiActivity :BaseActivityWraper() {
    override fun getTitleString(): String {
        return "电梯选择"
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

    private  var  liftInfoList: MutableList<LiftInfo>? = null

    private lateinit var adapter: SelectEleAdapter1

    /**
     * 请求电梯信息
     */
    private fun requestLiftInfo() {
        val task = object : NetTask(config.server + NetConstant.URL_GET_LIFT_INFO,
                getRequestBean(config.userId, config.token)) {
            override fun onResponse(task: NetTask, result: String) {
                val response = LiftInfoResponse.getLiftInfoResponse(result)
                liftInfoList = response.body.filter { !it.hasPlan() }.toMutableList()
                if (0 == liftInfoList?.size) {
                    tv_tip_inflate.visibility = VISIBLE
                    submit.visibility = GONE
                    return
                }

                adapter = SelectEleAdapter1(liftInfoList, act)
                lv_data.adapter = adapter
                lv_data.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                    liftInfoList?.let {
                        it[position].selected = !it[position].selected
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        }
        addTask(task)
    }
    override fun initView() {
        requestLiftInfo()
        submit.onClick {
            var returnData:MutableList<LiftInfo>?  = liftInfoList?.filter {
                it.selected
            }?.toMutableList()
            var intent = Intent()
            intent.putExtra(IntentConstant.INTENT_DATA,returnData as Serializable)
            setResult(SUCCESS,intent)
            finish()
        }
    }

    override fun getLayoutID(): Int {
        return R.layout.activity_select_eles
    }


}
class SelectEleAdapter1(datas: MutableList<LiftInfo>?, context: Context?, layoutId: Int = R.layout.item_select_eles) : BaseListViewAdapter<LiftInfo>(datas, context, layoutId) {
    override fun bindData(holder: BaseViewHolder?, t: LiftInfo?, index: Int) {
        holder?.setText(R.id.num,t?.num)?.setText(R.id.address,t?.propertyCode+" "+t?.address)
        if(t?.selected!!)
            holder?.setImageResource(R.id.image,R.drawable.selected)
        else
            holder?.setImageResource(R.id.image,R.drawable.non_select)
    }
}