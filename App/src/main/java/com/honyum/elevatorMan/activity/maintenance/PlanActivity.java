package com.honyum.elevatorMan.activity.maintenance;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
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
import com.honyum.elevatorMan.net.ContactResponse;
import com.honyum.elevatorMan.net.ContractInfoDetailRequest;
import com.honyum.elevatorMan.net.ContractInfoDetailResponse;
import com.honyum.elevatorMan.net.LiftInfoRequest;
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
import com.honyum.elevatorMan.utils.Utils;
import com.honyum.elevatorMan.utils.ViewUtils;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlanActivity extends WorkerBaseActivity {
    private DatePicker datePicker;
    private TimePicker timePicker;
    Date date;
    String s1;
    String s2;
    private boolean isTimePass;
    private View dialogLayout;
    private AlertDialog alertDialog;
    private Dialog selectedWorkDialog;
    private int currIndex = -1;
    private String mainPlanId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        initTitleBar(getIntent());
        initView(getIntent());
    }


    /**
     * 初始化标题栏
     */
    private void initTitleBar(Intent intent) {
        getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        setExitFlag(false);

        String type = intent.getStringExtra("enter_type");
        String title = "";
        if (type.equals("add")) {
            title = getString(R.string.make_plan);
        } else if (type.equals("modify")) {
            title = getString(R.string.modify_plan);
        } else {
            title = "计划详情";
        }
        initTitleBar(title, R.id.title_plan, R.drawable.back_normal,
                backClickListener);
    }

    /**
     * 初始化界面
     *
     * @param intent
     */
    private void initView(final Intent intent) {
        if (null == intent) {
            return;
        }


        if (intent.hasExtra("lift")) {
            final LiftInfo liftInfo = (LiftInfo) intent.getSerializableExtra("lift");
            fillData(intent, liftInfo);
        } else {
            if (intent.hasExtra("mainPlanId")) {
                mainPlanId = intent.getStringExtra("mainPlanId");
                requestPlanById(mainPlanId);
            }
        }
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
                fillData1(response.getBody().getMaintenance());
            }
        };
        addTask(netTask);
    }

    private void fillData1(MaintenanceInfo response) {
        //维保日期处理
        final TextView tvPlanDate = (TextView) findViewById(R.id.tv_plan_date);
        final TextView tvPlanType = (TextView) findViewById(R.id.tv_plan_type);
        tvPlanDate.setText(response.getPlanTime());
        tvPlanType.setText(response.getMainType());

        ((TextView) findViewById(R.id.tv_lift_code)).setText(response.getElevatorInfo().getLiftNum());
        ((TextView) findViewById(R.id.tv_lift_add)).setText(response.getElevatorInfo().getAddress());
        findViewById(R.id.ll_calendar).setVisibility(View.GONE);
        findViewById(R.id.ll_type).setVisibility(View.GONE);

        TextView textView = ( (TextView)findViewById(R.id.btn_submit));

        textView.setText("立刻处理");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlanActivity.this,ToDoDetailDealActivity.class);
                intent.putExtra("currId",mainPlanId);
                intent.putExtra("url",getConfig().getMaintenanceUrl());
                finish();
                startActivity(intent);
            }
        });


    }

    private void fillData(Intent intent, LiftInfo liftInfo) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = new Date(System.currentTimeMillis());
        String s = sdf.format(d).toString();
        ((TextView) findViewById(R.id.tv_lift_code)).setText(liftInfo.getNum());
        ((TextView) findViewById(R.id.tv_lift_add)).setText(liftInfo.getAddress());
        dialogLayout = LayoutInflater.from(this).inflate(R.layout.dia_datetime_layout, null);
        datePicker = (DatePicker) dialogLayout.findViewById(R.id.datePicker);
        timePicker = (TimePicker) dialogLayout.findViewById(R.id.timePicker);
        ViewUtils.resizePikcer(datePicker);
        ViewUtils.resizePikcer(timePicker);
        //维保日期处理
        final TextView tvPlanDate = (TextView) findViewById(R.id.tv_plan_date);
        final TextView tvPlanType = (TextView) findViewById(R.id.tv_plan_type);

        if (liftInfo.hasPlan()) {
            tvPlanDate.setText(liftInfo.getPlanMainTime());
            tvPlanType.setText(liftInfo.getPlanType());

        } else {
            tvPlanDate.setText(s);
            tvPlanType.setText("半月保");
        }
        date = new Date();

        //使用dialog组合日期和时间控件。
        findViewById(R.id.ll_date).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                timePicker.setIs24HourView(true);
                timePicker.setCurrentHour(date.getHours() + 1);
                timePicker.setCurrentMinute(0);
                int minute = timePicker.getCurrentMinute();
                s2 = "  " + (timePicker.getCurrentHour()) + ":" + (minute < 10 ? "0" + minute : minute);
                timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

                    @Override
                    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                        s2 = ("  " + hourOfDay + ":" + (minute < 10 ? "0" + minute : minute));
                    }
                });
                alertDialog.show();

            }
        });
        alertDialog = new AlertDialog.Builder(this,R.style.dialogStyle).setTitle("选择时间").setView(dialogLayout).setPositiveButton("确定",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int arg1) {
                        s1 = (datePicker.getYear() + "-" + (datePicker.getMonth() + 1) + "-" + datePicker.getDayOfMonth());
                        String dateString = s1 + s2;
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        Date d = new Date();
                        try {
                            isTimePass = false;
                            d = sdf.parse(dateString);
                            long t = d.getTime();
                            long cl = System.currentTimeMillis();

                            if (cl > t) {
                                isTimePass = false;
                            } else {
                                isTimePass = true;
                            }
                            tvPlanDate.setText(dateString);
                            dialog.dismiss();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int arg1) {
                dialog.dismiss();
            }
        }).create();
        //end 组合控件

        //日期设置后回调接口
        final DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Date date = new Date(year - 1900, monthOfYear, dayOfMonth);
                tvPlanDate.setText(Utils.dateToString(date));
            }
        };

//        //点击日期时修改
//        findViewById(R.id.ll_date).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTime(Utils.stringToDate(tvPlanDate.getText().toString()));
//                DatePickerDialog datePickerDialog = new DatePickerDialog(PlanActivity.this, dateSetListener,
//                        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
//                        calendar.get(Calendar.DATE)) {
//                    @Override
//                    protected void onStop() {
//                    }
//                };
//                DatePicker datePicker = datePickerDialog.getDatePicker();
//                datePicker.setSpinnersShown(false);
//
//                datePicker.setCalendarViewShown(true);
//                datePickerDialog.show();
//            }
//        });


        //维保计划类型选择
        findViewById(R.id.tv_plan_type).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popTypeSelector(tvPlanType);
            }
        });

        //维保类型维保内容帮助
        findViewById(R.id.tv_type_help).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = LiftInfo.stringToType(tvPlanType.getText().toString());
                Intent intent = new Intent(PlanActivity.this, MainHelpActivity.class);
                intent.putExtra("type", type);
                startActivity(intent);
            }
        });


        //维保计划提交
        findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = tvPlanDate.getText().toString();
                String type = LiftInfo.stringToType(tvPlanType.getText().toString());

                reportPlan(getConfig().getUserId(), getConfig().getToken(), liftInfo.getId(), date,
                        type, intent.getStringExtra("enter_type"));
            }
        });
    }

    private void requestRepairWork(String name, String param, ListView list) {
        RepairSelectedWorkerRequest request = new RepairSelectedWorkerRequest();
        RepairSelectedWorkerRequest.RepairSelectedWorkerBody body = request.new RepairSelectedWorkerBody();
        body.setName(name);
        body.set_process_task_param(param);
        body.setBranchId(getConfig().getBranchId());
        //  body.remark = et_remark.text.toString()
        request.setBody(body);
        request.setHead(new NewRequestHead().setaccessToken(getConfig().getToken()).setuserId(getConfig().getUserId()));

        String server = getConfig().getNewServer() + NetConstant.SELECTUSERDATAGRID;
        NetTask netTask = new NetTask(server, request) {
            @Override
            protected void onResponse(NetTask task, String result) {
                ContactResponse response = ContactResponse.getContactResponse(result);
                contracts = response.getBody().getDataGrid();

                list.setAdapter(new ContractInnerAdapter(contracts, PlanActivity.this, R.layout.item_contract_inner));
                list.setOnItemClickListener((parent, view, position, id) -> {
                    transDefault();
                    contracts.get(position).setSelected(true);
                    currIndex = position;
                    ((ContractInnerAdapter) list.getAdapter()).notifyDataSetChanged();
                });

            }
        };
        addTask(netTask);
    }

    private void transDefault() {
        for (ContactDataGrideInfo info : contracts) {
            info.setSelected(false);
        }

    }

    List<ContactDataGrideInfo> contracts;

    private void initDialog(String decode, String id) {

        selectedWorkDialog = new Dialog(this);
        selectedWorkDialog.setCanceledOnTouchOutside(true);
        selectedWorkDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        selectedWorkDialog.setContentView(R.layout.dialog_select_pointer);
        ListView list = (ListView) selectedWorkDialog.findViewById(R.id.list_data);
        EditText numText = (EditText) selectedWorkDialog.findViewById(R.id.name);
        requestRepairWork("", decode, list);
        selectedWorkDialog.findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestRepairWork(numText.getText().toString(), decode, list);
            }
        });


        selectedWorkDialog.findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currIndex == -1) {
                    showToast("请选择指派人！");
                    return;
                }
                requestPointWorker(id, decode, contracts.get(currIndex).getId());
            }
        });
        selectedWorkDialog.show();
    }

    private void requestPointWorker(String id, String process_task_param, String s) {
        UploadProcessRequest uploadProcessRequest = new UploadProcessRequest();
        UploadProcessRequest.UploadProcessRequestBody body = uploadProcessRequest.new UploadProcessRequestBody();
        uploadProcessRequest.setHead(new NewRequestHead().setaccessToken(getConfig().getToken()).setuserId(getConfig().getUserId()));
        body.set_process_isLastNode("0");
        body.set_process_task_param(process_task_param);
        body.set_process_path("同意");
        body.set_processSelectUserId(s);
        //body._process_approve_source
        body.set_processSelectUserId(s);
        body.setId(id);
        body.setBranchId(getConfig().getBranchId());

        uploadProcessRequest.setBody(body);

        String server = getConfig().getServer()+getConfig().getMaintenanceUrl() + NetConstant.COMMITMAINPLAN;

        NetTask netTask = new NetTask(server, uploadProcessRequest) {
            @Override
            protected void onResponse(NetTask task, String result) {
                if (selectedWorkDialog.isShowing())
                    selectedWorkDialog.dismiss();
                showToast("提交成功！");
                finish();
            }
        };

        addTask(netTask);
    }

    /**
     * 维保类型选择弹出框选择接口
     */
    class TypeSelector implements View.OnClickListener {

        private AlertDialog mDialog;
        private TextView mTextView;

        public TypeSelector(AlertDialog dialog, TextView textView) {
            mDialog = dialog;
            mTextView = textView;
        }

        @Override
        public void onClick(View v) {
            mDialog.dismiss();
            switch (v.getId()) {
                case R.id.ll_semi_month:
                    mTextView.setText("半月保");
                    break;
                case R.id.ll_month:
                    mTextView.setText("月保");
                    break;
                case R.id.ll_season:
                    mTextView.setText("季度保");
                    break;
                case R.id.ll_semi_year:
                    mTextView.setText("半年保");
                    break;
                case R.id.ll_year:
                    mTextView.setText("年保");
                    break;
            }
        }
    }

    /**
     * 维保类型选择
     *
     * @param textView
     */
    private void popTypeSelector(TextView textView) {
        View view = View.inflate(this, R.layout.layout_plan_type, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.dialogStyle);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
//        params.width = WindowManager.LayoutParams.MATCH_PARENT;


        View.OnClickListener listener = new TypeSelector(dialog, textView);

        view.findViewById(R.id.ll_semi_month).setOnClickListener(listener);
        view.findViewById(R.id.ll_month).setOnClickListener(listener);
        view.findViewById(R.id.ll_season).setOnClickListener(listener);
        view.findViewById(R.id.ll_semi_year).setOnClickListener(listener);
        view.findViewById(R.id.ll_year).setOnClickListener(listener);


        List<TextView> textViews = new ArrayList<>(5);
        textViews.add((TextView) view.findViewById(R.id.tv_half_month));
        textViews.add((TextView) view.findViewById(R.id.tv_star_month));
        textViews.add((TextView) view.findViewById(R.id.tv_star_quarter));
        textViews.add((TextView) view.findViewById(R.id.tv_star_half_year));
        textViews.add((TextView) view.findViewById(R.id.tv_star_year));


        List<ImageView> imageViews = new ArrayList<>(5);
        imageViews.add((ImageView) view.findViewById(R.id.iv_half_month));
        imageViews.add((ImageView) view.findViewById(R.id.iv_star_month));
        imageViews.add((ImageView) view.findViewById(R.id.iv_star_quarter));
        imageViews.add((ImageView) view.findViewById(R.id.iv_star_half_year));
        imageViews.add((ImageView) view.findViewById(R.id.iv_star_year));


        String selectedString = textView.getText() + "";

        int selectedIndex = 0;
        if (!TextUtils.isEmpty(selectedString)) {
            for (int i = 0; i < textViews.size(); i++) {
                if (selectedString.equals(textViews.get(i).getText() + "")) {
                    selectedIndex = i;
                    break;
                }
            }
            imageViews.get(selectedIndex).setBackgroundResource(R.drawable.star_selected);
            textViews.get(selectedIndex).setTextColor(getResources().getColor(R.color.title_bg_color));

        }
        dialog.show();
    }

    /**
     * 获取提交维保计划的bean
     *
     * @param userId
     * @param token
     * @param id
     * @param planDate
     * @param planType
     * @return
     */
    public static RequestBean getReportPlanRequest(String userId, String token, String id, String planDate,
                                                   String planType, String branchId) {
        ReportPlanRequest request = new ReportPlanRequest();
        RequestHead head = new RequestHead();
        ReportPlanRequest.ReportPlanReqBody body = request.new ReportPlanReqBody();

        head.setUserId(userId);
        head.setAccessToken(token);

        body.setId(id);
        body.setPlanTime(planDate);
        body.setMainType(planType);
        body.setBranchId(branchId);

        request.setHead(head);
        request.setBody(body);

        return request;
    }

    /**
     * 上传维保计划
     *
     * @param userId
     * @param token
     * @param id
     * @param planDate
     * @param planType
     */
    private void reportPlan(String userId, String token, String id, String planDate,
                            String planType, String type) {

        String server = "";
        if (type.equals("add")) {

            server = getConfig().getServer()+getConfig().getMaintenanceUrl() + NetConstant.URL_REPORT_PLAN;
        } else if (type.equals("modify")) {

            server = getConfig().getServer()+getConfig().getMaintenanceUrl() + NetConstant.URL_MODIFY_PLAN;
        }
        NetTask netTask = new NetTask(server, getReportPlanRequest(userId, token, id, planDate,
                planType, getConfig().getBranchId())) {
            @Override
            protected void onResponse(NetTask task, String result) {
                PlanResponse response = PlanResponse.getResponse(result, PlanResponse.class);
                try {
                    initDialog(URLDecoder.decode(response.getBody().get_process_task_param(), "UTF-8"), response.getBody().getId());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                //   initDialog()
                // showToast("维保计划提交成功，请及时到记录上传里面完成您的维保计划");
                // finish();

                //Intent liftIntent = new Intent(PlanActivity.this, MyLiftActivity.class);
                //liftIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //startActivity(liftIntent);
            }
        };
        addTask(netTask);
    }
}
