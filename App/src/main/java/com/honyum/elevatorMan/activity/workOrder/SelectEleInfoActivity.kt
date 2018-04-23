package com.honyum.elevatorMan.activity.workOrder

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import com.honyum.elevatorMan.R
import com.honyum.elevatorMan.activity.maintenance.SelectMaintenanceProjectMutiActivity
import com.honyum.elevatorMan.adapter.BaseListViewAdapter
import com.honyum.elevatorMan.adapter.BaseViewHolder
import com.honyum.elevatorMan.base.BaseActivityWraper
import com.honyum.elevatorMan.data.ElevatorInfo1
import com.honyum.elevatorMan.data.LiftInfo
import com.honyum.elevatorMan.data.MaintenanceInfo
import com.honyum.elevatorMan.net.AlarmListRequest
import com.honyum.elevatorMan.net.LiftInfoResponse
import com.honyum.elevatorMan.net.base.NetConstant
import com.honyum.elevatorMan.net.base.NetTask
import com.honyum.elevatorMan.net.base.RequestBean
import com.honyum.elevatorMan.net.base.RequestHead
import kotlinx.android.synthetic.main.activity_select_eles.*
import org.jetbrains.anko.act
import org.jetbrains.anko.sdk25.coroutines.onChildClick

/**
 * Created by star on 2018/4/11.
 */
class SelectEleInfoActivity :BaseActivityWraper() {
    override fun getTitleString(): String {
        return "选择电梯"
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
    private lateinit var adapter: MyExpandableListAdapter
    private lateinit var dataKeys: Array<String>
    /**
     * 请求电梯信息
     */
    private fun requestLiftInfo() {
        val task = object : NetTask(config.server + NetConstant.URL_GET_LIFT_INFO,
                getRequestBean(config.userId, config.token)) {
            override fun onResponse(task: NetTask, result: String) {
                val response = LiftInfoResponse.getLiftInfoResponse(result)
                liftInfoList = response.body
                if (0 == liftInfoList?.size) {
                    tv_tip_inflate.visibility = View.VISIBLE
                    submit.visibility = View.GONE
                    return
                }
                data = liftInfoList!!.groupBy {
                    it.communityName
                }
                dataKeys = data.keys.toTypedArray()
                adapter = MyExpandableListAdapter(data, data.keys.toTypedArray(), act)
                lv_data.setAdapter(adapter)
                lv_data.onChildClick { parent, v, groupPosition, childPosition, id ->
                    var info = data[dataKeys[groupPosition]]?.get(childPosition)
                    var maintenanceInfo = MaintenanceInfo()
                    var elevatorInfo = ElevatorInfo1()
                    elevatorInfo.liftNum = info?.id
                    elevatorInfo.id = info?.id
                    elevatorInfo.branchId = config.branchId
                    elevatorInfo.communityId = info?.communityId
                    maintenanceInfo.id = info?.id!!
                    maintenanceInfo.mainType = info.mainType
                    maintenanceInfo.elevatorInfo = elevatorInfo

                    val intent = Intent()
                    intent.setClass(act, AddWorkOrderDetailActivity::class.java)
                    var bundle = Bundle()
                    intent.putExtra("other",true)
                    bundle.putSerializable("maintenanceInfo",maintenanceInfo)
                    intent.putExtras(bundle)
                    startActivity(intent)
                    finish()


                }
//                lv_data.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
//
//                    //adapter.notifyDataSetChanged()
//                    }
            }
        }
        addTask(task)
    }

    override fun initView() {
        requestLiftInfo()
        submit.visibility = GONE
    }
    private lateinit var data: Map<String, List<LiftInfo>>
    override fun getLayoutID(): Int {
        return R.layout.activity_select_eles
    }
    internal class MyExpandableListAdapter(data:Map<String, List<LiftInfo>>,allKeys:Array<String>,context:Context): BaseExpandableListAdapter() {

        var datas = data
        var context = context
        var allKeys = allKeys
        override fun getGroup(groupPosition: Int): Any {
            return allKeys[groupPosition]
        }

        override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
            return true
        }

        override fun hasStableIds(): Boolean {
            return false
        }

        override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {

            var groupViewHolder:GroupViewHolder
            var convertView = convertView
            if (convertView== null) {
                convertView = LayoutInflater.from(context)?.inflate(R.layout.item_expand_list, parent, false)
                groupViewHolder = GroupViewHolder()
                groupViewHolder.tvTitle = convertView?.findViewById(R.id.tv_commnity_name) as TextView
                convertView?.tag = groupViewHolder
            } else {
                groupViewHolder = convertView?.tag as GroupViewHolder
            }
            groupViewHolder.tvTitle?.text = allKeys[groupPosition]
            return convertView
        }

        override fun getChildrenCount(groupPosition: Int): Int {
            return datas[allKeys[groupPosition]]?.size!!
        }

        override fun getChild(groupPosition: Int, childPosition: Int): Any {
            return datas[allKeys[groupPosition]]?.get(childPosition)!!
        }

        override fun getGroupId(groupPosition: Int): Long {
            return groupPosition.toLong()
        }

        override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
            var itemViewHolder:ItemViewHolder
            var convertView = convertView
            if (convertView== null) {
                convertView = LayoutInflater.from(context)?.inflate(R.layout.item_select_eles, parent, false)
                itemViewHolder = ItemViewHolder()
                itemViewHolder.address = convertView?.findViewById(R.id.address) as TextView
                itemViewHolder.image = convertView?.findViewById(R.id.image) as ImageView
                itemViewHolder.num = convertView?.findViewById(R.id.num) as TextView
                convertView?.tag = itemViewHolder
            } else {
                itemViewHolder = convertView?.tag as ItemViewHolder
            }
            itemViewHolder.address?.text = datas[allKeys[groupPosition]]?.get(childPosition)?.propertyCode+" "+datas[allKeys[groupPosition]]?.get(childPosition)?.address
            if (datas[allKeys[groupPosition]]?.get(childPosition)?.selected!!) {
                itemViewHolder.image?.setImageResource(R.drawable.selected)
            }
            else
            {
                itemViewHolder.image?.setImageResource(R.drawable.non_select)
            }
            itemViewHolder.num?.text = datas[allKeys[groupPosition]]?.get(childPosition)?.num
            return convertView
        }

        override fun getChildId(groupPosition: Int, childPosition: Int): Long {

            return childPosition.toLong()
        }

        override fun getGroupCount(): Int {
            return allKeys.size
        }
    }

    internal class GroupViewHolder {
        var tvTitle: TextView? = null
    }
    internal class ItemViewHolder {
        var address: TextView? = null
        var image: ImageView? = null
        var num: TextView? = null

    }
}
