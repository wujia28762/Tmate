package com.honyum.elevatorMan.activity.maintenance

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import com.honyum.elevatorMan.R
import com.honyum.elevatorMan.base.BaseActivityWraper
import com.honyum.elevatorMan.constant.IntentConstant
import com.honyum.elevatorMan.data.LiftInfo
import com.honyum.elevatorMan.data.UndealLiftPlanInfo
import com.honyum.elevatorMan.net.*
import com.honyum.elevatorMan.net.base.NetConstant
import com.honyum.elevatorMan.net.base.NetTask
import com.honyum.elevatorMan.net.base.RequestBean
import com.honyum.elevatorMan.net.base.RequestHead
import com.honyum.elevatorMan.utils.RemindUtils
import com.honyum.elevatorMan.utils.Utils
import kotlinx.android.synthetic.main.activity_lift_plan1.*
import org.jetbrains.anko.act
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity
import java.util.*

/**
 * Created by star on 2018/4/3.
 */
class SelectLiftPlanProjectActivity : BaseActivityWraper() {
    override fun getTitleString(): String {
        return "项目选择"
    }

    override fun getLayoutID(): Int {
        return R.layout.activity_lift_plan1
    }

    private var mListView: ListView? = null



    override fun onResume() {
        super.onResume()
        initView()
        requestLiftInfo()
        getUndealDatas()
    }


    //private lateinit var button_plan: FloatingActionButton

    override fun initView() {
        mListView = findViewById(R.id.lv_plan) as ListView
        make_plan1.setOnClickListener { startActivity(Intent(this@SelectLiftPlanProjectActivity, MakePlanMutiActivity::class.java)) }
        make_plan.setOnClickListener { startActivity(Intent(this@SelectLiftPlanProjectActivity, MakePlanMutiActivity::class.java)) }
    }

    private var undealList: MutableList<UndealLiftPlanInfo>? = null


    private fun getUndealDatas()
    {

        var request = GetMaintListRequest()
        var head = NewRequestHead().setaccessToken(config.token).setuserId(config.userId)
        var body = request.GetMaintListBody()
        body.roleId = config.roleId

        request.body = body
        request.head = head

        var netTask = object : NetTask(config.server+config.maintenanceUrl+NetConstant.GETDRAFTMAINTENANCEDEALLIST,request) {
            override fun onResponse(task: NetTask?, result: String?) {

                var response = UndealLiftPlanResponse.getResponse<UndealLiftPlanResponse>(result,UndealLiftPlanResponse::class.java)
                undealList = response.body
                if (undealList!=null&&undealList?.size==0)
                    undeal.visibility = GONE
                else {
                    //undeal.visibility = VISIBLE
                    var mSnackbar = Snackbar.make(make_plan, "您有" + undealList?.size + "条未审批计划", Snackbar.LENGTH_INDEFINITE)
                            .setAction("去处理", {
                                startActivity<DisplayUndealLiftInfoActivity>(IntentConstant.INTENT_DATA to undealList!!)
                            })
                    mSnackbar.view.setBackgroundColor(ContextCompat.getColor(act,R.color.goldenrod))
                    mSnackbar?.view.find<TextView>(R.id.snackbar_text).setTextColor(Color.WHITE)
                    mSnackbar.show()

                }
//                undeal.onClick {
//
//                }
            }

        }
        addTask(netTask)
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

    /**
     * 请求电梯信息
     */
    private fun requestLiftInfo() {
        val task = object : NetTask(config.server + NetConstant.URL_GET_LIFT_INFO,
                getRequestBean(config.userId, config.token)) {
            override fun onResponse(task: NetTask, result: String) {
                val response = LiftInfoResponse.getLiftInfoResponse(result)
                val liftInfoList = response.body

                //设置提醒
                for (liftInfo in liftInfoList) {
                    RemindUtils.setRemind(this@SelectLiftPlanProjectActivity, liftInfo, config.userId)
                }

                orderLiftInfo(liftInfoList)
                setListener(mListView)
            }
        }
        addTask(task)
    }

    private inner class MyAdapterProject(private val mContext: Context, private val mList: Array<String>) : BaseAdapter() {
        override fun getCount(): Int {
            return mList.size
        }

        override fun getItem(position: Int): Any {
            return mList[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var convertView = convertView

            if (null == convertView) {
                convertView = View.inflate(mContext, R.layout.layout_lift_item, null)
            }
            val info = mList[position]
            convertView!!.tag = info
            val tvCode = convertView.findViewById(R.id.tv_code) as TextView

            val tvAdd = convertView.findViewById(R.id.tv_address) as TextView
            val index = convertView.findViewById(R.id.tv_index) as TextView
            tvCode.visibility= GONE
            index.text = (position + 1).toString() + ""
            tvAdd.text = info

            return convertView
        }
    }

    private fun setListener(listView: ListView?) {
        listView!!.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val info = view.tag
            //Intent intent = new Intent(LiftPlanActivity.this, PlanActivity.class);
            startActivity<LiftPlanActivity>("info" to data[info]!!)
        }
    }

    private lateinit var data: Map<String, List<LiftInfo>>

    /**
     * 对电梯维保信息进行排序
     * @param liftInfoList
     */
    private fun orderLiftInfo(liftInfoList: List<LiftInfo>) {
        val noPlanList = ArrayList<LiftInfo>()

        for (liftInfo in liftInfoList) {
            if (liftInfo.hasPlan()) {
                noPlanList.add(liftInfo)
            }
        }

        if (0 == noPlanList.size) {
            val tv = findViewById(R.id.tv_tip) as TextView
            tv.visibility = View.VISIBLE
            tv.text = "您暂时没有已制定维保计划的电梯"
            mListView!!.visibility = View.GONE
            return
        }

        Collections.sort<LiftInfo>(noPlanList, NoPlanListOrder())

        data = noPlanList.groupBy { it.communityName }

        mListView!!.adapter = MyAdapterProject(this@SelectLiftPlanProjectActivity, data.keys.toTypedArray())

    }

    /**
     * 没有计划的电梯按照距离上次维保时间降序排列
     */
   class NoPlanListOrder : Comparator<Any> {

        override fun compare(o1: Any, o2: Any): Int {
            val liftInfo1 = o1 as LiftInfo
            val liftInfo2 = o2 as LiftInfo

            val today = Date()
            val lastDate1 = Utils.stringToDate(liftInfo1.lastMainTime)
            val lastDate2 = Utils.stringToDate(liftInfo2.lastMainTime)

            val days1 = Utils.getIntervalDays(lastDate1, today)
            val days2 = Utils.getIntervalDays(lastDate2, today)

            //降序排列
            return if (days1 > days2) {
                -1
            } else 1
        }
    }
}