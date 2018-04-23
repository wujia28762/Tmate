package com.honyum.elevatorMan.activity.maintenance;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.honyum.elevatorMan.R;
import com.honyum.elevatorMan.activity.company.EMentenanceHisListActivity;
import com.honyum.elevatorMan.base.BaseFragmentActivity;
import com.honyum.elevatorMan.data.LiftInfo;
import com.honyum.elevatorMan.net.EleRecordRequest;
import com.honyum.elevatorMan.net.ElevatorInfoDetailResponse;
import com.honyum.elevatorMan.net.NewRequestHead;
import com.honyum.elevatorMan.net.base.NetConstant;
import com.honyum.elevatorMan.net.base.NetTask;

public class LiftDetailActivity extends BaseFragmentActivity {


    private TextView tv_workers;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lift_detail);
        initTitleBar();
        initView();
    }

    private void initTitleBar() {
        initTitleBar("电梯详情", R.id.title_lift_detail,
                R.drawable.back_normal, backClickListener);
    }

    private void initView() {
        Intent intent = getIntent();
        LiftInfo info = (LiftInfo) intent.getSerializableExtra("info");
        if (null == info) {
            return;
        }

        ((TextView)findViewById(R.id.tv_code)).setText(info.getNum());
        ((TextView)findViewById(R.id.tv_project)).setText(info.getCommunityName());
        ((TextView)findViewById(R.id.tv_address)).setText(info.getAddress());
        ((TextView)findViewById(R.id.tv_brand)).setText(info.getBrand());
        ((TextView)findViewById(R.id.tv_worker)).setText(getConfig().getName());
        ((TextView)findViewById(R.id.tv_tel)).setText(getConfig().getTel());

        tv_workers = (TextView)findViewById(R.id.tv_workers);

        findViewById(R.id.tv_look_his).setOnClickListener(v -> {
            Intent intent1 = new Intent(LiftDetailActivity.this, EMentenanceHisListActivity.class);
            intent1.putExtra("Id", info.getId());
            startActivity(intent1);
        });

        requestEleInfo(info.getId());
    }

    //获取电梯详情
    public void requestEleInfo(String eleNum) {
        String server = getConfig().getServer() + NetConstant.GET_ELEVATOR_BY_ELEVATOR_ID;
        EleRecordRequest er = new EleRecordRequest();
        er.setHead(new NewRequestHead().setaccessToken(getConfig().getToken()).setuserId(getConfig().getUserId()));
        er.setBody(er.new EleRecordRequestBody().setElevatorId(eleNum.trim()));
        NetTask netTask = new NetTask(server, er) {
            @Override
            protected void onResponse(NetTask task, String result) {

                if (!result.equals("{}")) {
                    ElevatorInfoDetailResponse response = ElevatorInfoDetailResponse.getElevatorInfoDetailResponse(result);
                    if (response.getBody().getList().size()>0){
                        String str = "";
                        for (int i=0;i<response.getBody().getList().size();i++){
                            if(i==response.getBody().getList().size()-1){
                                str = str+response.getBody().getList().get(i).getUserName();
                            }else {
                                str = str+response.getBody().getList().get(i).getUserName()+"、";
                            }
                        }

                        tv_workers.setText(str);
                    }
                    }
                    else {
                       // showToast("未获取到当前编号的电梯信息");
                        //finish();
                    }
                }
        };
        addTask(netTask);
    }

}
