package com.honyum.elevatorMan.activity.maintenance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.honyum.elevatorMan.R;
import com.honyum.elevatorMan.base.BaseFragmentActivity;
import com.honyum.elevatorMan.data.LiftInfo;
import com.honyum.elevatorMan.net.AlarmListRequest;
import com.honyum.elevatorMan.net.LiftInfoResponse;
import com.honyum.elevatorMan.net.base.NetConstant;
import com.honyum.elevatorMan.net.base.NetTask;
import com.honyum.elevatorMan.net.base.RequestBean;
import com.honyum.elevatorMan.net.base.RequestHead;
import com.honyum.elevatorMan.utils.RemindUtils;
import com.honyum.elevatorMan.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class LiftPlanActivity extends BaseFragmentActivity {


    private ListView mListView;

    private List<LiftInfo> liftInfoList;

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
        requestLiftInfo();
    }

    private void initTitleBar() {
        initTitleBar("维保计划", R.id.title,
                R.drawable.back_normal, backClickListener);;
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.lv_plan);
        findViewById(R.id.make_plan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LiftPlanActivity.this,MakePlanMutiActivity.class));
            }
        });
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

    /**
     * 请求电梯信息
     */
    private void requestLiftInfo() {
        if (!getIntent().hasExtra("info"))
            return;
        liftInfoList = (List<LiftInfo>) getIntent().getSerializableExtra("info");
        orderLiftInfo(liftInfoList);
        setListener(mListView);
    }

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
            tvAdd.setText(info.getPropertyCode()+"/"+info.getAddress());

            return convertView;
        }
    }

    private void setListener(ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LiftInfo info = (LiftInfo) view.getTag();
                //Intent intent = new Intent(LiftPlanActivity.this, PlanActivity.class);
                Intent intent = new Intent(LiftPlanActivity.this, NewLiftCompleteActivity.class);
                intent.putExtra("lift", info);
                intent.putExtra("id", info.getId());
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
            TextView tv = (TextView) findViewById(R.id.tv_tip);
            tv.setVisibility(View.VISIBLE);
            tv.setText("您暂时没有已制定维保计划的电梯");
            mListView.setVisibility(View.GONE);
            return;
        }

        Collections.sort(noPlanList, new NoPlanListOrder());
        mListView.setAdapter(new MyAdapter(LiftPlanActivity.this, noPlanList));

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
