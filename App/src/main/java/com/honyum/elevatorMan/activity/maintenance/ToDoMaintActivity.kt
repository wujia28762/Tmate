package com.honyum.elevatorMan.activity.maintenance

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import com.honyum.elevatorMan.R
import com.honyum.elevatorMan.activity.common.ToDoDetailDealActivity
import com.honyum.elevatorMan.activity.common.ToDoListActivity
import com.honyum.elevatorMan.activity.worker.WorkerBaseActivity
import com.honyum.elevatorMan.base.Config
import com.honyum.elevatorMan.constant.IntentConstant
import com.honyum.elevatorMan.data.LiftInfo
import com.honyum.elevatorMan.data.ToDoMaintInfo
import com.honyum.elevatorMan.net.AlarmListRequest
import com.honyum.elevatorMan.net.ContractInfoDetailRequest
import com.honyum.elevatorMan.net.MaintenancePlanResponse
import com.honyum.elevatorMan.net.NewRequestHead
import com.honyum.elevatorMan.net.base.NetConstant
import com.honyum.elevatorMan.net.base.NetTask
import com.honyum.elevatorMan.net.base.RequestBean
import com.honyum.elevatorMan.net.base.RequestHead
import kotlinx.android.synthetic.main.activity_lift_plan.*
import org.jetbrains.anko.startActivity
import java.io.UnsupportedEncodingException
import java.net.URLDecoder

class ToDoMaintActivity : WorkerBaseActivity() {
    private var mListView: ListView? = null

    private val mInfoList: List<LiftInfo>? = null
    private var mainPlanId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lift_plan)
        initTitleBar()
    }

    override fun onResume() {
        super.onResume()
        initView()
        //requestLiftInfo();
    }

    private fun initTitleBar() {
        initTitleBar("维保计划审核", R.id.title,
                R.drawable.back_normal, backClickListener)
    }

    private fun initView() {
        findViewById(R.id.make_plan).visibility = View.GONE
        btn_submit.visibility = View.VISIBLE
        if (intent.getBooleanExtra("done",false))
        {
            btn_submit.visibility = View.GONE
        }
        mListView = findViewById(R.id.lv_plan) as ListView
        findViewById(R.id.make_plan).setOnClickListener { startActivity(Intent(this@ToDoMaintActivity, MakePlanMutiActivity::class.java)) }
        val intent = intent
        if (intent.hasExtra("mainPlanId")) {
            mainPlanId = intent.getStringExtra("mainPlanId")
            requestPlanById(mainPlanId)
        }
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

    private fun requestPlanById(mainPlanId: String) {


        val contractInfodetailRequest = ContractInfoDetailRequest()
        val body = contractInfodetailRequest.ContractInfoBody()

        body.id = mainPlanId
        body.branchId = config.branchId
        body._process_isLastNode = ToDoListActivity.currLastNode
        body._process_task_param = ToDoListActivity.currTask

        contractInfodetailRequest.body = body
        contractInfodetailRequest.head = NewRequestHead().setaccessToken(config.token).setuserId(config.userId)
        //        request.setBody(request.new RequestLiftInfoBody(mainPlanId));
        //    request.setHead(new NewRequestHead().setuserId(getConfig().getUserId()).setaccessToken(getConfig().getToken()));
        val server = config.maintenanceServer + NetConstant.GETMAINPLAN
        val netTask = object : NetTask(server, contractInfodetailRequest) {
            override fun onResponse(task: NetTask, result: String) {
                val response = MaintenancePlanResponse.getResponse<MaintenancePlanResponse>(result, MaintenancePlanResponse::class.java)
                val resMap = response.body._process_resultMap._process_task_param
                try {
                    Config.currTask = URLDecoder.decode(resMap, "UTF-8")
                    Config.currLastNode = response.body._process_resultMap._process_isLastNode

                } catch (e: UnsupportedEncodingException) {
                    e.printStackTrace()
                }

                fillData1(response.body.list)
            }
        }
        addTask(netTask)
    }

    private lateinit var list: List<Pair<String, List<ToDoMaintInfo>>>

    private lateinit var list1:MutableList<ToDoMaintInfo>

    private fun fillData1(toDoMaintInfo: List<ToDoMaintInfo>) {

        var map = toDoMaintInfo.groupBy { it.elevatorInfo.id }
        list1 = arrayListOf()
        map.keys.forEach{
            list1.add(map[it]?.get(0)!!)
        }
//        list1  = arrayListOf()
//        map?.forEachIndexed { index, pair ->
//            pair.second[0].index = index
//            list1?.add(pair.second[0])
//        }
        var adapter = MyAdapter(this@ToDoMaintActivity,list1)
        lv_plan.adapter = adapter
        lv_plan.setOnItemClickListener { parent, view, position, id ->
            startActivity<NewLiftCompleteActivity>(IntentConstant.INTENT_ID to list1[position].elevatorInfo.id)
        }


        val textView = findViewById(R.id.btn_submit) as TextView

        textView.text = "立刻处理"
        textView.setOnClickListener {
            val intent = Intent(this@ToDoMaintActivity, ToDoDetailDealActivity::class.java)
            intent.putExtra("currId", mainPlanId)
            intent.putExtra("url", config.maintenanceUrl)
            finish()
            startActivity(intent)
        }
    }

    //    /**
    //     * 请求电梯信息
    //     */
    //    private void requestLiftInfo() {
    //        NetTask task = new NetTask(getConfig().getServer()+ NetConstant.URL_GET_LIFT_INFO,
    //                getRequestBean(getConfig().getUserId(), getConfig().getToken())) {
    //            @Override
    //            protected void onResponse(NetTask task, String result) {
    //                LiftInfoResponse response = LiftInfoResponse.getLiftInfoResponse(result);
    //                List<LiftInfo> liftInfoList = response.getBody();
    //
    //                //设置提醒
    //                for (LiftInfo liftInfo : liftInfoList) {
    //                    RemindUtils.setRemind(ToDoPlanActivity.this, liftInfo, getConfig().getUserId());
    //                }
    //
    //                orderLiftInfo(liftInfoList);
    //                setListener(mListView);
    //            }
    //        };
    //        addTask(task);
    //    }

    private inner class MyAdapter(private val mContext: Context, private val mList: MutableList<ToDoMaintInfo>) : BaseAdapter() {
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
            tvCode.text = info.elevatorInfo.liftNum
            index.text = (position + 1).toString() + ""
            tvAdd.text = info.elevatorInfo.communityName+info.elevatorInfo.buildingCode+"楼"+info.elevatorInfo.unitCode+"单元"

            return convertView
        }
    }

    private fun setListener(listView: ListView) {
        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val info = view.tag as LiftInfo
            //Intent intent = new Intent(LiftPlanActivity.this, PlanActivity.class);
            val intent = Intent(this@ToDoMaintActivity, NewLiftCompleteActivity::class.java)
            intent.putExtra("lift", info)
            intent.putExtra("enter_type", "add")
            startActivity(intent)
        }
    }


}
