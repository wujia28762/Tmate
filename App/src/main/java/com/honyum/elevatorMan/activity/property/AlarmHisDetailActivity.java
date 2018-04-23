package com.honyum.elevatorMan.activity.property;

import android.content.Intent;
import android.widget.TextView;

import com.honyum.elevatorMan.R;
import com.honyum.elevatorMan.activity.worker.AlarmProcessDetailActivity;
import com.honyum.elevatorMan.base.BaseActivityWraper;
import com.honyum.elevatorMan.data.AlarmInfo;
import com.honyum.elevatorMan.data.AlarmInfo1;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Star on 2017/6/15.
 */

public class AlarmHisDetailActivity extends BaseActivityWraper {

    @BindView(R.id.tv_project)
    TextView tvProject;
    @BindView(R.id.tv_lift_add)
    TextView tvLiftAdd;
    @BindView(R.id.tv_lift_code)
    TextView tvLiftCode;
    @BindView(R.id.tv_lift_date)
    TextView tvLiftDate;
    @BindView(R.id.tv_lift_saved)
    TextView tvLiftSaved;
    @BindView(R.id.tv_lift_injured)
    TextView tvLiftInjured;
    @BindView(R.id.tv_processDetail)
    TextView tvProcessDetail;

    private AlarmInfo mAlarmInfo1;

    @Override
    public String getTitleString() {
        return getString(R.string.save_detail);
    }

    @Override
    protected void initView() {

        mAlarmInfo1 = getIntent("Info");
        tvProject.setText(mAlarmInfo1.getCommunityInfo().getName());
        tvLiftAdd.setText(mAlarmInfo1.getCommunityInfo().getAddress());
        tvLiftCode.setText(mAlarmInfo1.getElevatorInfo().getLiftNum());
        tvLiftDate.setText(mAlarmInfo1.getAlarmTime());
        tvLiftSaved.setText(mAlarmInfo1.getSavedCount() + "");
        tvLiftInjured.setText(mAlarmInfo1.getInjureCount() + "");
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_company_save_detail;
    }




    @OnClick(R.id.tv_processDetail)
    public void onViewClicked() {
        Intent it = new Intent(AlarmHisDetailActivity.this, AlarmProcessDetailActivity.class);
        it.putExtra("Id",mAlarmInfo1.getId());
        startActivity(it);
    }
}
