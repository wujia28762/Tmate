package com.honyum.elevatorMan.activity.company;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.honyum.elevatorMan.R;
import com.honyum.elevatorMan.activity.maintenance.MaintenanceServiceActivity;
import com.honyum.elevatorMan.adapter.BaseListViewAdapter;
import com.honyum.elevatorMan.adapter.BaseViewHolder;
import com.honyum.elevatorMan.base.BaseActivityWraper;
import com.honyum.elevatorMan.base.ListItemCallback;
import com.honyum.elevatorMan.data.MaintenanceServiceInfo;
import com.honyum.elevatorMan.data.MaintenanceTaskInfo;
import com.honyum.elevatorMan.data.mydata.NHorderAndTask;
import com.honyum.elevatorMan.net.MaintenanceServiceResponse;
import com.honyum.elevatorMan.net.MaintenanceServiceTaskRequest;
import com.honyum.elevatorMan.net.MiantenanceServiceTaskResponse;
import com.honyum.elevatorMan.net.NHMprojectSelectRequest;
import com.honyum.elevatorMan.net.base.NetConstant;
import com.honyum.elevatorMan.net.base.NetTask;
import com.honyum.elevatorMan.net.base.NewRequestHead;
import com.honyum.elevatorMan.net.base.RequestBean;
import com.honyum.elevatorMan.view.RefreshLayout;
import com.honyum.elevatorMan.view.RefreshListView;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicDefaultFooter;
import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler2;
import in.srain.cube.views.ptr.util.PtrLocalDisplay;


public class NHMProjectSelectActivity extends BaseActivityWraper implements ListItemCallback<MaintenanceServiceInfo>, RefreshLayout.OnRefreshListener,
        RefreshLayout.OnLoadListener {


    private int currIdex = 1;
    private RefreshLayout swipeLayout;
    private NHMentenanceListAdapter adapter;
    private List<MaintenanceServiceInfo> msInfoList = new ArrayList<>();
    ListView listView;
    private PtrClassicFrameLayout ptrClassicLayout;

    @Override
    public String getTitleString() {
        return "维保订单";
    }
    private RequestBean getRequestBean(String userId, String token,boolean isRefresh) {

        NHMprojectSelectRequest request = new NHMprojectSelectRequest();
        request.setHead(new NewRequestHead().setuserId(userId).setaccessToken(token));
        NHMprojectSelectRequest.NHMprojectSelectRequestBody body = request.new NHMprojectSelectRequestBody();
        if (isRefresh)
            body.setPage(1);
        else
        body.setPage(currIdex);
        body.setRows(10);
        request.setBody(body);
        return request;
    }
    private void requestMaintenServiceInfo(Boolean isRefresh) {
        NetTask task = new NetTask(getConfig().getServer() + NetConstant.URL_MAINT_SERVICE,
                getRequestBean(getConfig().getUserId(), getConfig().getToken(),isRefresh)) {
            @Override
            protected void onResponse(NetTask task, String result) {
                Log.e("TAG", "onResponse: " + result);
                MaintenanceServiceResponse response = MaintenanceServiceResponse.getMaintServiceInfoResponse(result);
                if (response.getBody() == null || response.getBody().size() == 0) {
                    getWindow().getDecorView().setBackgroundColor(Color.WHITE);
                    return;
                }
                currIdex ++;
                if (isRefresh) {
                    msInfoList.clear();
                    currIdex = 2;
                }
                msInfoList.addAll(response.getBody());
                adapter.notifyDataSetChanged();


            }

                //Log.e("!!!!!!!!!!!!!!", "onResponse: "+ msInfoList.get(0).getMainttypeId());
        };
        addTask(task);
    }

    private void fillView() {

    }
    @Override
    public void onRefresh() {
        swipeLayout.post(new Runnable() {

            @Override
            public void run() {
                // 更新数据  更新完后调用该方法结束刷新
                requestMaintenServiceInfo(true);

            }
        });
    }


    /**
     * 加载更多
     */
    @Override
    public void onLoad() {
        swipeLayout.post(new Runnable() {
            @Override
            public void run() {
                // 更新数据  更新完后调用该方法结束刷新
                requestMaintenServiceInfo(false);
            }
        });
    }
        @Override
    protected void initView() {
            ptrClassicLayout = (PtrClassicFrameLayout) findViewById(R.id.ptr_classic_frame_layout);
            listView = findView(R.id.lv_data);
            adapter = new NHMentenanceListAdapter(msInfoList, NHMProjectSelectActivity.this);
            listView.setAdapter(adapter);
            initPtrClass();

            requestMaintenServiceInfo(true);
        }

    private void initPtrClass() {
        /**
         * 经典 风格的头部实现
         */
        final PtrClassicDefaultHeader header = new PtrClassicDefaultHeader(this);
        header.setPadding(0, PtrLocalDisplay.dp2px(15), 0, 0);

        PtrClassicDefaultFooter footer = new PtrClassicDefaultFooter(this);
        footer.setPadding(0, 0, 0, PtrLocalDisplay.dp2px(15));

        ptrClassicLayout.setHeaderView(header);
        ptrClassicLayout.addPtrUIHandler(header);

        ptrClassicLayout.setFooterView(footer);
        ptrClassicLayout.addPtrUIHandler(footer);
        //mPtrFrame.setKeepHeaderWhenRefresh(true);//刷新时保持头部的显示，默认为true
        //mPtrFrame.disableWhenHorizontalMove(true);//如果是ViewPager，设置为true，会解决ViewPager滑动冲突问题。
        ptrClassicLayout.setPtrHandler(new PtrHandler2() {
            @Override
            public boolean checkCanDoLoadMore(PtrFrameLayout frame, View content, View footer) {
                // 默认实现，根据实际情况做改动

                return PtrDefaultHandler2.checkContentCanBePulledUp(frame, content, footer);
            }

            /**
             * 加载更多的回调
             * @param frame
             */
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        requestMaintenServiceInfo(false);
                        ptrClassicLayout.refreshComplete();
                    }
                }, 2000);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                // 默认实现，根据实际情况做改动
                return PtrDefaultHandler2.checkContentCanBePulledDown(frame, content, header);
            }

            /**
             * 下拉刷新的回调
             * @param frame
             */
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        requestMaintenServiceInfo(true);
                        ptrClassicLayout.refreshComplete();
                    }
                }, 1000);
            }
        });
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_nh_project_select;
    }

    @Override
    public void performItemCallback(MaintenanceServiceInfo data) {
        Intent it = new Intent(this, MaintenanceServiceActivity.class);
        it.putExtra("currInfo",data);
        startActivity(it);
    }
}
class NHMentenanceListAdapter extends BaseListViewAdapter<MaintenanceServiceInfo> {

    public NHMentenanceListAdapter(List datas, Context context) {
        super(datas, context, R.layout.company_order_item);
    }


    @Override
    public void bindData(BaseViewHolder holder, final MaintenanceServiceInfo o, int index) {
        holder.setText(R.id.tv_taskcode, (o.getVillaInfo().getVillaCode()==null?(""):(o.getVillaInfo().getVillaCode()+"/"))+o.getVillaInfo().getCellName()).setText(R.id.tv_person, o.getMainttypeInfo().getName())
                .setVisible(R.id.tv_state,false).setVisible(R.id.right_arrow,true)
                .setText(R.id.tv_num, "编号："+o.getCode()).setText(R.id.tv_time, o.getCreateTime()).setOnClickListener(R.id.nhorder_detail, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ListItemCallback)mContext).performItemCallback(o);

            }
        });
    }
}
