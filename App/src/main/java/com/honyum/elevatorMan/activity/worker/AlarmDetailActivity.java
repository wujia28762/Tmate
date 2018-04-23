package com.honyum.elevatorMan.activity.worker;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.honyum.elevatorMan.R;
import com.honyum.elevatorMan.base.BaseFragmentActivity;
import com.honyum.elevatorMan.data.AlarmInfo;

public class AlarmDetailActivity extends BaseFragmentActivity {

    private TextView tv_processDetail;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_detail);
        initTitleBar();
        initView();
    }

    private void initTitleBar() {
        initTitleBar("报警详情", R.id.title_detail, R.drawable.back_normal, backClickListener);
    }

    private void initView() {
        final String id = getIntent().getStringExtra("Id");
        tv_processDetail = (TextView) findViewById(R.id.tv_processDetail);
        tv_processDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(AlarmDetailActivity.this, AlarmProcessDetailActivity.class);
                it.putExtra("Id",id);
                startActivity(it);
            }
        });
        Intent intent = getIntent();
        if(null == intent) {
            return;
        }
        ((TextView) findViewById(R.id.tv_project)).setText(intent.getStringExtra("project"));
        ((TextView) findViewById(R.id.tv_lift_add)).setText(intent.getStringExtra("add"));
        ((TextView) findViewById(R.id.tv_lift_code)).setText(intent.getStringExtra("code"));
        ((TextView) findViewById(R.id.tv_lift_date)).setText(intent.getStringExtra("date"));
        ((TextView) findViewById(R.id.tv_lift_saved)).setText(intent.getStringExtra("saved"));
        ((TextView) findViewById(R.id.tv_lift_injured)).setText(intent.getStringExtra("injured"));

    }

}
