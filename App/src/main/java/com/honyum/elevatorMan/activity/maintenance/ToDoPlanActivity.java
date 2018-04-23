package com.honyum.elevatorMan.activity.maintenance;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.honyum.elevatorMan.R;
import com.honyum.elevatorMan.activity.common.ContractInnerAdapter;
import com.honyum.elevatorMan.activity.common.ToDoDetailDealActivity;
import com.honyum.elevatorMan.activity.common.ToDoListActivity;
import com.honyum.elevatorMan.activity.worker.WorkerBaseActivity;
import com.honyum.elevatorMan.base.Config;
import com.honyum.elevatorMan.data.ContactDataGrideInfo;
import com.honyum.elevatorMan.data.LiftInfo;
import com.honyum.elevatorMan.data.MaintenanceInfo;
import com.honyum.elevatorMan.data.ToDoMaintInfo;
import com.honyum.elevatorMan.net.AlarmListRequest;
import com.honyum.elevatorMan.net.ContactResponse;
import com.honyum.elevatorMan.net.ContractInfoDetailRequest;
import com.honyum.elevatorMan.net.LiftInfoResponse;
import com.honyum.elevatorMan.net.MaintenancePlanResponse;
import com.honyum.elevatorMan.net.NewRequestHead;
import com.honyum.elevatorMan.net.PlanResponse;
import com.honyum.elevatorMan.net.RepairSelectedWorkerRequest;
import com.honyum.elevatorMan.net.ReportPlanRequest;
import com.honyum.elevatorMan.net.UploadProcessRequest;
import com.honyum.elevatorMan.net.base.NetConstant;
import com.honyum.elevatorMan.net.base.NetTask;
import com.honyum.elevatorMan.net.base.RequestBean;
import com.honyum.elevatorMan.net.base.RequestHead;
import com.honyum.elevatorMan.utils.RemindUtils;
import com.honyum.elevatorMan.utils.Utils;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class ToDoPlanActivity extends WorkerBaseActivity {
    private ListView mListView;

    private List<LiftInfo> mInfoList;
    private String mainPlanId = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lift_plan);
        initTitleBar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
        //requestLiftInfo();
    }

    private void initTitleBar() {
        initTitleBar("维保计划审核", R.id.title,
                R.drawable.back_normal, backClickListener);;
    }

    private void initView() {
        findViewById(R.id.make_plan).setVisibility(View.GONE);
        mListView = (ListView) findViewById(R.id.lv_plan);
        findViewById(R.id.make_plan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ToDoPlanActivity.this,MakePlanMutiActivity.class));
            }
        });
        Intent intent = getIntent();
        if (intent.hasExtra("mainPlanId")) {
            mainPlanId = intent.getStringExtra("mainPlanId");
            requestPlanById(mainPlanId);
        }
    }

    /**
     * 获取请求的bean
     *
     * @param userId
     * @param token
     * @return
     */
    private RequestBean getRequestBean(String userId, String token) {

        //只需要发送一个head即可，这里使用请求报警列表的request bean
        AlarmListRequest request = new AlarmListRequest();
        RequestHead head = new RequestHead();

        head.setUserId(userId);
        head.setAccessToken(token);

        request.setHead(head);

        return request;
    }
    private void requestPlanById(String mainPlanId) {


        ContractInfoDetailRequest contractInfodetailRequest = new ContractInfoDetailRequest();
        ContractInfoDetailRequest.ContractInfoBody body = contractInfodetailRequest.new ContractInfoBody();

        body.setId(mainPlanId);
        body.setBranchId(getConfig().getBranchId());
        body.set_process_isLastNode(ToDoListActivity.Companion.getCurrLastNode());
        body.set_process_task_param(ToDoListActivity.Companion.getCurrTask());

        contractInfodetailRequest.setBody(body);
        contractInfodetailRequest.setHead(new NewRequestHead().setaccessToken(getConfig().getToken()).setuserId(getConfig().getUserId()));
//        request.setBody(request.new RequestLiftInfoBody(mainPlanId));
        //    request.setHead(new NewRequestHead().setuserId(getConfig().getUserId()).setaccessToken(getConfig().getToken()));
        String server = getConfig().getMaintenanceServer() + NetConstant.GETMAINPLAN;
        NetTask netTask = new NetTask(server, contractInfodetailRequest) {
            @Override
            protected void onResponse(NetTask task, String result) {
                MaintenancePlanResponse response = MaintenancePlanResponse.getResponse(result, MaintenancePlanResponse.class);
                String resMap = response.getBody().get_process_resultMap().get_process_task_param();
                try {
                    Config.currTask = URLDecoder.decode(resMap, "UTF-8");
                    Config.currLastNode = response.getBody().get_process_resultMap().get_process_isLastNode();

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                fillData1(response.getBody().getList());
            }
        };
        addTask(netTask);
    }

    private void fillData1(List<ToDoMaintInfo> ToDoMaintInfo) {




        TextView textView = ( (TextView)findViewById(R.id.btn_submit));
        textView.setText("立刻处理");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ToDoPlanActivity.this,ToDoDetailDealActivity.class);
                intent.putExtra("currId",mainPlanId);
                intent.putExtra("url",getConfig().getMaintenanceUrl());
                finish();
                startActivity(intent);
            }
        });
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

    private class MyAdapter extends BaseAdapter {

        private Context mContext;

        private List<LiftInfo> mList;

        public MyAdapter(Context context, List<LiftInfo> list) {
            mContext = context;
            mList = list;
        }
        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (null == convertView) {
                convertView = View.inflate(mContext, R.layout.layout_lift_item, null);
            }
            LiftInfo info = mList.get(position);
            convertView.setTag(info);
            TextView tvCode = (TextView) convertView.findViewById(R.id.tv_code);

            TextView tvAdd = (TextView) convertView.findViewById(R.id.tv_address);
            TextView index = (TextView) convertView.findViewById(R.id.tv_index);
            tvCode.setText(info.getNum());
            index.setText(position+1+"");
            tvAdd.setText(info.getAddress());

            return convertView;
        }
    }

    private void setListener(ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LiftInfo info = (LiftInfo) view.getTag();
                //Intent intent = new Intent(LiftPlanActivity.this, PlanActivity.class);
                Intent intent = new Intent(ToDoPlanActivity.this, NewLiftCompleteActivity.class);
                intent.putExtra("lift", info);
                intent.putExtra("enter_type", "add");
                startActivity(intent);
            }
        });
    }

    /**
     * 对电梯维保信息进行排序
     * @param liftInfoList
     */
    private void orderLiftInfo(List<LiftInfo> liftInfoList) {
        List<LiftInfo> noPlanList = new ArrayList<LiftInfo>();

        for (LiftInfo liftInfo : liftInfoList) {
            if (liftInfo.hasPlan()) {
                noPlanList.add(liftInfo);
            }
        }

        if (0 == noPlanList.size()) {
            findViewById(R.id.tv_tip).setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);
            return;
        }

        Collections.sort(noPlanList, new NoPlanListOrder());
        mListView.setAdapter(new MyAdapter(ToDoPlanActivity.this, noPlanList));

    }

    /**
     * 没有计划的电梯按照距离上次维保时间降序排列
     */
    class NoPlanListOrder implements Comparator {

        @Override
        public int compare(Object o1, Object o2) {
            LiftInfo liftInfo1 = (LiftInfo) o1;
            LiftInfo liftInfo2 = (LiftInfo) o2;

            Date today = new Date();
            Date lastDate1 = Utils.stringToDate(liftInfo1.getLastMainTime());
            Date lastDate2 = Utils.stringToDate(liftInfo2.getLastMainTime());

            int days1 = Utils.getIntervalDays(lastDate1, today);
            int days2 = Utils.getIntervalDays(lastDate2, today);

            //降序排列
            if (days1 > days2) {
                return -1;
            }
            return 1;
        }
    }
}
