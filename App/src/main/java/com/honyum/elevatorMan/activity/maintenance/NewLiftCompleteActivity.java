package com.honyum.elevatorMan.activity.maintenance;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.honyum.elevatorMan.R;
import com.honyum.elevatorMan.base.BaseFragmentActivity;
import com.honyum.elevatorMan.constant.IntentConstant;
import com.honyum.elevatorMan.data.LiftInfo;
import com.honyum.elevatorMan.data.MaintenanceInfo;
import com.honyum.elevatorMan.net.AlarmListRequest;
import com.honyum.elevatorMan.net.LiftInfoResponse;
import com.honyum.elevatorMan.net.LiftVideoRequest;
import com.honyum.elevatorMan.net.MaintenanceInfoResponse;
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

public class NewLiftCompleteActivity extends BaseFragmentActivity {

    private ListView mListView;

    private LiftInfo info;
    private String id = " ";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lift_complete);
        initTitleBar();

        if (getIntent().hasExtra("lift"))
        {
            info = (LiftInfo)getIntent().getSerializableExtra("lift");
        }
        id = getIntent().getStringExtra("id");

    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
        //requestLiftInfo();
    }

    private void initTitleBar() {
        initTitleBar("计划详情", R.id.title_lift_complete, R.drawable.back_normal, backClickListener);
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.lv_plan);
        findViewById(R.id.make_plan).setOnClickListener(v -> startActivity(new Intent(NewLiftCompleteActivity.this,LiftPlanActivity.class)));
        requestLiftInfo1();
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
     * 获取请求的bean
     *
     * @param userId
     * @param token
     * @return
     */
    private RequestBean getRequestBean1(String userId, String token) {

        //只需要发送一个head即可，这里使用请求报警列表的request bean
        LiftVideoRequest request = new LiftVideoRequest();
        LiftVideoRequest.LiftVideoReqBody body= request.new LiftVideoReqBody();
        body.setElevatorId(id);
        RequestHead head = new RequestHead();

        head.setUserId(userId);
        head.setAccessToken(token);
        request.setBody(body);
        request.setHead(head);

        return request;
    }

    /**
     * 请求电梯信息
     */
    private void requestLiftInfo1() {
        NetTask task = new NetTask(getConfig().getServer()+getConfig().getMaintenanceUrl() + NetConstant.URL_GET_LIFT_INFO1,
                getRequestBean1(getConfig().getUserId(), getConfig().getToken())) {
            @Override
            protected void onResponse(NetTask task, String result) {
                MaintenanceInfoResponse response = MaintenanceInfoResponse.getRepairInfoResponse(result);

                mListView.setAdapter(new MyAdapter(NewLiftCompleteActivity.this,response.getBody()));



            }
        };
        addTask(task);
    }
    /**
     * 请求电梯信息
     */
    private void requestLiftInfo() {
        NetTask task = new NetTask(getConfig().getServer() + NetConstant.URL_GET_LIFT_INFO,
                getRequestBean(getConfig().getUserId(), getConfig().getToken())) {
            @Override
            protected void onResponse(NetTask task, String result) {
                LiftInfoResponse response = LiftInfoResponse.getLiftInfoResponse(result);
                List<LiftInfo> liftInfoList = response.getBody();

                //设置提醒
                for (LiftInfo liftInfo : liftInfoList) {
                    RemindUtils.setRemind(NewLiftCompleteActivity.this, liftInfo, getConfig().getUserId());
                }

               // orderLiftInfo(liftInfoList);
            }
        };
        addTask(task);
    }

//    /**
//     * 对电梯维保信息进行排序
//     *
//     * @param liftInfoList
//     */
//    private void orderLiftInfo(List<LiftInfo> liftInfoList) {
//        List<LiftInfo> hasPlanList = new ArrayList<LiftInfo>();
//
//        for (LiftInfo liftInfo : liftInfoList) {
//            if (liftInfo.hasPlan()) {
//                hasPlanList.add(liftInfo);
//            }
//        }
//
//        if (0 == hasPlanList.size()) {
//            findViewById(R.id.tv_tip).setVisibility(View.VISIBLE);
//            mListView.setAdapter(new MyAdapter(this, hasPlanList));
//            return;
//        }
//
//        Collections.sort(hasPlanList, new PlanListOrder());
//        mListView.setAdapter(new MyAdapter(this, hasPlanList));
//    }

    /**
     * 有计划的电梯按照距离计划的日期升序排列
     */
    class PlanListOrder implements Comparator {

        @Override
        public int compare(Object o1, Object o2) {
            LiftInfo liftInfo1 = (LiftInfo) o1;
            LiftInfo liftInfo2 = (LiftInfo) o2;

            Date today = new Date();
            Date planDate1 = Utils.stringToDate(liftInfo1.getPlanMainTime());
            Date planDate2 = Utils.stringToDate(liftInfo2.getPlanMainTime());

            int days1 = Utils.getIntervalDays(today, planDate1);
            int days2 = Utils.getIntervalDays(today, planDate2);


            //升序排列
            if (days1 > days2) {
                return 1;
            }
            return -1;
        }
    }


    /**
     * 电梯信息展示
     */
    private class MyAdapter extends BaseAdapter {

        private Context mContext;

        private List<MaintenanceInfo> mLiftInfoList;

        public MyAdapter(Context context, List<MaintenanceInfo> liftInfoList) {
            mContext = context;
            mLiftInfoList = liftInfoList;
        }

        @Override
        public int getCount() {
            return mLiftInfoList.size();
        }

        @Override
        public Object getItem(int position) {
            return mLiftInfoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            if (null == convertView) {
                convertView = View.inflate(mContext, R.layout.layout_my_lift, null);
                holder = new ViewHolder();

                holder.flState = (FrameLayout) convertView.findViewById(R.id.fl_state);

                holder.tvDay = (TextView) convertView.findViewById(R.id.tv_day);

                holder.tvLiftCode = (TextView) convertView.findViewById(R.id.tv_lift_code);
                holder.tvLiftAdd = (TextView) convertView.findViewById(R.id.tv_address);


                holder.llPlanInfo = (LinearLayout) convertView.findViewById(R.id.ll_plan_info);

                holder.tvPlanTitle = (TextView) convertView.findViewById(R.id.tv_plan_title);
                holder.tvPlanState = (TextView) convertView.findViewById(R.id.tv_plan_state);

                holder.tvPlanType = (TextView) convertView.findViewById(R.id.tv_plan_type);

                holder.tvEdit = (TextView) convertView.findViewById(R.id.tv_edit);
                holder.tvDel = (TextView) convertView.findViewById(R.id.tv_del);

                holder.llDetail = (LinearLayout) convertView.findViewById(R.id.ll_detail);

                holder.ivVideo = (ImageView) convertView.findViewById(R.id.iv_video);

                //根据实际的配置确认是否显示视频图标
                if (getConfig().getVideoEnable()) {
                    holder.ivVideo.setVisibility(View.VISIBLE);
                }


                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            final MaintenanceInfo liftInfo = mLiftInfoList.get(position);

            holder.tvLiftCode.setText(liftInfo.getElevatorInfo().getLiftNum());
            holder.tvLiftAdd.setText(liftInfo.getElevatorInfo().getPropertyCode()+"/"+liftInfo.getElevatorInfo().getCommunityName()+liftInfo.getElevatorInfo().getBuildingCode()+"号楼"+liftInfo.getElevatorInfo().getUnitCode()+"单元");

            holder.ivVideo.setOnClickListener(v -> startLiftVideo(liftInfo.getId()));


            holder.flState.setVisibility(View.VISIBLE);
            holder.llPlanInfo.setVisibility(View.VISIBLE);
            final int days = Utils.getIntervalDays(new Date(), Utils.stringToDate(liftInfo.getPlanTime()));
            //setBackColor(holder.llPlanInfo, days);

            if (days < 0) {
                holder.tvDay.setTextSize(getResources().getDimension(R.dimen.days_size_no_plan));
                holder.tvDay.setText("已过期");
            } else {
                holder.tvDay.setTextSize(getResources().getDimension(R.dimen.days_size_plan));
                holder.tvDay.setText("" + days);
            }

            if (liftInfo.getPropertyFlg().equals("3")) {
                holder.tvDay.setTextSize(getResources().getDimension(R.dimen.days_size_no_plan));
                holder.tvDay.setText("已拒绝");
            }

            holder.tvPlanTitle.setText(getString(R.string.next_main_date));
            holder.tvPlanState.setText(liftInfo.getPlanTime());

            holder.tvPlanType.setText(getTypeName(liftInfo.getMainType()));


            holder.tvEdit.setOnClickListener(v -> {
                Intent intent = new Intent(NewLiftCompleteActivity.this, PlanActivity.class);
                intent.putExtra("enter_type", "modify");
                intent.putExtra("lift", liftInfo);
                intent.putExtra("pos", position);

                startActivity(intent);
            });

            holder.tvDel.setOnClickListener(v -> new AlertDialog.Builder(NewLiftCompleteActivity.this)
                    .setMessage("确认删除此电梯维保计划?")
                    .setTitle("提示")
                    .setPositiveButton("确认", (dialog, which) -> {
                        dialog.dismiss();
                        deletePlan(getConfig().getUserId(), getConfig().getToken(),
                                liftInfo.getId(), liftInfo.getElevatorInfo().getLiftNum());
                    }).setNegativeButton("取消", (dialog, which) -> dialog.dismiss()).show());

            holder.llDetail.setOnClickListener(v -> {

                if (liftInfo.getPropertyFlg().equals("3")) {
                    new AlertDialog.Builder(NewLiftCompleteActivity.this)
                            .setMessage("此计划已被物业拒绝，请修改计划")
                            .setTitle("提示")
                            .setPositiveButton("确认", (dialog, which) -> dialog.dismiss()).show();
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("enter_type", "add");
                intent.putExtra("lift", liftInfo);
                intent.putExtra("showImage",false);

                intent.setClass(NewLiftCompleteActivity.this, NewMaintenanceActivity.class);


                startActivity(intent);

            });
            return convertView;
        }
    }

    private String getTypeName(String type) {

        switch (type) {
            case "hm":
                return "半月保";
            case "m": return "月保";
            case "s": return "季度保";
            case "hy": return "半年保";
            case "y":  return "年保";
        }
        return "未知类型";

    }


    /**
     * 用于优化ListView item中的控件的获取
     */
    private static class ViewHolder {

        LinearLayout llLeftDays;
        TextView tvDay;

        TextView tvLiftCode;
        TextView tvLiftAdd;

        TextView tvPlanTitle;
        TextView tvPlanState;

        TextView tvPlanType;

        TextView tvEdit;
        TextView tvDel;

        LinearLayout llDetail;

        FrameLayout flState;

        LinearLayout llPlanInfo;

        ImageView ivVideo;
    }


    /**
     * 删除维保计划
     *
     * @param userId
     * @param token
     * @param id
     */
    private void deletePlan(String userId, String token, String id, final String num) {

        String server = getConfig().getServer() + NetConstant.URL_DELETE_PLAN;

        NetTask netTask = new NetTask(server, PlanActivity.getReportPlanRequest(userId, token, id, "",
                "",getConfig().getBranchId())) {
            @Override
            protected void onResponse(NetTask task, String result) {

                String path = Utils.getSdPath() + "/chorstar/maintenance/" + num + "/";
                Utils.deleteFiles(path);
                Intent intent = new Intent(NewLiftCompleteActivity.this, NewLiftCompleteActivity.class);
                startActivity(intent);
                finish();
            }
        };
        addTask(netTask);
    }

}
