package com.honyum.elevatorMan.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ZoomControls;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.honyum.elevatorMan.R;
import com.honyum.elevatorMan.activity.company.EMentenanceListActivity;
import com.honyum.elevatorMan.base.BaseFragmentActivity;
import com.honyum.elevatorMan.base.Config;
import com.honyum.elevatorMan.base.MyApplication;
import com.honyum.elevatorMan.data.CommunityCountInfo;
import com.honyum.elevatorMan.data.StatMapDetail;
import com.honyum.elevatorMan.data.StatResult;
import com.honyum.elevatorMan.net.NewRequestHead;
import com.honyum.elevatorMan.net.ProjectCountInfoResponse;
import com.honyum.elevatorMan.net.StatInfoRequest;
import com.honyum.elevatorMan.net.StatInfoResponse;
import com.honyum.elevatorMan.net.StatMapInfoResponse;
import com.honyum.elevatorMan.net.base.NetTask;
import com.honyum.elevatorMan.utils.DialogUtil;
import com.honyum.elevatorMan.utils.Utils;
import com.honyum.elevatorMan.view.ChartTableView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yalantis.com.sidemenu.interfaces.ScreenShotable;

import static com.honyum.elevatorMan.net.base.NetConstant.GETCOMMUNITY;
import static com.honyum.elevatorMan.net.base.NetConstant.GETDEPARTMENT;
import static com.honyum.elevatorMan.net.base.NetConstant.GETINDEXALARMREPORT;
import static com.honyum.elevatorMan.net.base.NetConstant.GETINDEXALARMREPORTELEVATORLINE;
import static com.honyum.elevatorMan.net.base.NetConstant.GETINDEXBAOXIUREPORT;
import static com.honyum.elevatorMan.net.base.NetConstant.GETINDEXBAOXIUREPORTELEVATORLINE;
import static com.honyum.elevatorMan.net.base.NetConstant.GETINDEXWEIXIUGONGLINE;
import static com.honyum.elevatorMan.net.base.NetConstant.GETINDEXWORKORDERREPORT;
import static com.honyum.elevatorMan.net.base.NetConstant.GETINDEXWORKORDERREPORTELEVATORLINE;
import static com.honyum.elevatorMan.net.base.NetConstant.GETSTATUS;

/**
 * Created by Konstantin on 22.12.2014.
 */
public class ContentFragmentNew extends Fragment implements ScreenShotable {
    public static final String CLOSE = "close";
    public static final String ORDER = "订单查看";
    public static final String FIX = "维修查看";
    public static final String PERSON = "人员查看";
    public static final String ALARM = "报警查看";
    public static Map<Integer, String> tagMap = new HashMap<>();

    static {
        tagMap.put(0, CLOSE);
        tagMap.put(1, ORDER);
        tagMap.put(2, FIX);
        tagMap.put(3, PERSON);
        tagMap.put(4, ALARM);
    }

    public static ContentFragmentNew contentFragment;

    private View containerView;
    protected ImageView mImageView;
    protected int res;
    private Bitmap bitmap;
    private MapView baiduMap;
    private Marker locationMarker;

    private TextView date;
    private TextView department;

    private TextView state;

    private TextView table;
    private String currTag = ContentFragmentNew.FIX;
    private ImageButton btn_back;
    private TextView title;
    private TextView month;
    private TextView fix;
    private TextView person;
    private TextView alarm;
    private TextView order;
    private TextView project;


    public static ContentFragmentNew newInstance(int resId) {
        contentFragment = new ContentFragmentNew();
        Bundle bundle = new Bundle();
        bundle.putInt(Integer.class.getName(), resId);
        contentFragment.setArguments(bundle);
        return contentFragment;
    }

    private LinearLayout panel;

    private void showProjectPanel(List<CommunityCountInfo> info, TextView iamge) {
        ScrollView sll = new ScrollView(getActivity());
        panel = new LinearLayout(getActivity());
        LinearLayout.LayoutParams panelParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        panel.setOrientation(LinearLayout.VERTICAL);
        panel.setLayoutParams(panelParams);

        int size = (int) getResources().getDimension(R.dimen.x20);
        panel.setPadding(size, size, size, size);
        addProjectViews(info, panel, iamge);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.dialogStyle);
        sll.addView(panel);
        dialog1 = builder.setView(sll).create();
        dialog1.show();
        dialog1.setCanceledOnTouchOutside(true);
    }

    private void addProjectViews(List<CommunityCountInfo> info, LinearLayout panel, TextView image) {


        for (CommunityCountInfo info1 : info) {
            View child = LayoutInflater.from(getActivity()).inflate(R.layout.item_project, null);
            TextView childView = (TextView) child.findViewById(R.id.tv_project);
            childView.setText(info1.getName());
            child.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialog1.isShowing())
                        dialog1.dismiss();
                }
            });
            childView.setOnClickListener(v -> {
                if (dialog1.isShowing()) {
                    image.setTag(info1);
                    getMapDetailInfo(currTag);
                    dialog1.dismiss();
                    if (image.getTag(R.id.index)!=null) {
                        try {
                            image.setText(info1.getName().split("-")[2]+"/"+info1.getName().split("-")[1]);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                        else
                    image.setText(info1.getName());
                }
            });
            panel.addView(child);
        }
    }

    private Dialog dialog1;

    public void changeView(List<StatResult> infos) {
        // String tabTag = tagMap.get(tag);
//        switch (tag)
//        {
//            case CLOSE:
//                getActivity().finish();
//                break;
//            case ALARM:
//                markMap(getInfo(tag));
//                break;
//                //获取报警，展示
//            case ORDER:
//                break;
//                //获取报警，展示
//            case FIX:
//                break;
//                //获取报警，展示
//            case PERSON:
//                break;
//                //获取报警，展示
//        }
//        String type = "1";
//        if (tabIndex/2 == 1)
//            type = "2";
        mMap.clear();
 //       markMap(infos);

    }

    public List<StatResult> getInfo(String type) {
        List<StatResult> datas = new ArrayList<>();

        if (type.equals(ORDER) || type.equals(PERSON)) {
            ///// test data

            StatResult data1 = new StatResult();
            data1.setCancel(10);
            data1.setDealing(11);
            data1.setFinsh(12);
            data1.setTitle("一区");
            data1.setTotal(34);
            data1.setUnDeal(1);
            data1.setLng(116.350238);
            data1.setLat(40.06024);
            datas.add(data1);
        } else {

            StatResult data2 = new StatResult();
            data2.setCancel(10);
            data2.setDealing(11);
            data2.setFinsh(12);
            data2.setTitle("一区");
            data2.setTotal(34);
            data2.setUnDeal(1);
            data2.setLng(116.429565);
            data2.setLat(39.950786);
            datas.add(data2);
        }

        //end test data
        return datas;

    }

    private void markMap(List<StatMapDetail> datas) {


        //mBaiduMap.hideInfoWindow();
        if (null == mMap) {
            return;
        }

        if (null == datas) {
            return;
        }

        mMap.clear();
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory
                .fromResource(R.drawable.deattask);

        for (StatMapDetail info : datas) {

                LatLng point = new LatLng(info.getLat(), info.getLng());
                MarkerOptions markerOption = new MarkerOptions().icon(bitmapDescriptor).position(point);
                locationMarker = (Marker) mMap.addOverlay(markerOption);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Info", (Serializable) info);
                locationMarker.setExtraInfo(bundle);
                mMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker arg0) {
                        LayoutInflater inflater = LayoutInflater.from(getActivity());
                        View view = inflater.inflate(R.layout.mapdetail_item, null);
                        TextView textInfo = (TextView) view.findViewById(R.id.tv_detail_info);

                        Button forward = (Button) view.findViewById(R.id.bt_forward);
                        final Bundle bundle = arg0.getExtraInfo();
                        final StatMapDetail info1 = (StatMapDetail) bundle.getSerializable("Info");

//                textInfo.setText(DataTravelUtils.getMiantRecInfoData().get(0).getCommunityName() + "(" + DataTravelUtils.getMiantRecInfoData().size() + ")");
//                double latitude = DataTravelUtils.getMiantRecInfoData().get(0).getLat();
//                double longitude = DataTravelUtils.getMiantRecInfoData().get(0).getLng();


                        textInfo.setText(info1.getPropertyCode()+"/"+info1.getCommunityName() + info1.getBuildingCode() + "号楼"
                                + info1.getUnitCode() + "单元");
                        LatLng point = new LatLng(info1.getLat(), info1.getLng());
                        InfoWindow mInfoWindow = new InfoWindow(view, point, 10);
                        forward.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                mMap.hideInfoWindow();
                                switch (currTag)
                                {
                                    case ContentFragmentNew.FIX:
                                    {
                                        Intent intent = new Intent(getActivity(), EMentenanceListActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("Id",  info1.getId());
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                        break;
                                    }
                                    case ContentFragmentNew.ORDER:
                                    {
                                        Intent intent = new Intent(getActivity(), EMentenanceListActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("Id",  info1.getId());
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                        break;
                                    }
                                    case ContentFragmentNew.ALARM:
                                    {
                                        Intent intent = new Intent(getActivity(), EMentenanceListActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("Id",  info1.getId());
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                        break;
                                    }


                                }

                            }
                        });
                        mMap.showInfoWindow(mInfoWindow);
                        return false;
                    }
                });


        }

    }
    private void markMapPerson(ArrayList<StatMapDetail> datas) {

        mMap.setOnMapClickListener(
                new BaiduMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        mMap.hideInfoWindow();
                    }

                    @Override
                    public boolean onMapPoiClick(MapPoi mapPoi) {
                        return false;
                    }
                }
        );
        //mBaiduMap.hideInfoWindow();

        if (null == mMap) {
            return;
        }

        if (null == datas) {
            return;
        }

        mMap.clear();
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory
                .fromResource(R.drawable.deattask);

        for (StatMapDetail info : datas) {


            LatLng point = new LatLng(info.getLat(), info.getLng());
            MarkerOptions markerOption = new MarkerOptions().icon(bitmapDescriptor).position(point);
            locationMarker = (Marker) mMap.addOverlay(markerOption);
            Bundle bundle = new Bundle();
            bundle.putSerializable("Info", (Serializable) info);
            locationMarker.setExtraInfo(bundle);
            mMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker arg0) {
                    LayoutInflater inflater = LayoutInflater.from(getActivity());
                    View view = inflater.inflate(R.layout.mapdetail_item, null);
                    TextView textInfo = (TextView) view.findViewById(R.id.tv_detail_info);

                    Button forward = (Button) view.findViewById(R.id.bt_forward);
                    final Bundle bundle = arg0.getExtraInfo();
                    final StatMapDetail info1 = (StatMapDetail) bundle.getSerializable("Info");

//                textInfo.setText(DataTravelUtils.getMiantRecInfoData().get(0).getCommunityName() + "(" + DataTravelUtils.getMiantRecInfoData().size() + ")");
//                double latitude = DataTravelUtils.getMiantRecInfoData().get(0).getLat();
//                double longitude = DataTravelUtils.getMiantRecInfoData().get(0).getLng();


                    textInfo.setText(info1.getUserName());
                    LatLng point = new LatLng(info1.getLat(), info1.getLng());
                    InfoWindow mInfoWindow = new InfoWindow(view, point, 10);
                    forward.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mMap.hideInfoWindow();
                        }
                    });
                    mMap.showInfoWindow(mInfoWindow);
                    return false;
                }
            });


        }

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.containerView = view.findViewById(R.id.container);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        SDKInitializer.initialize(MyApplication.getInstance());
        super.onCreate(savedInstanceState);
        res = getArguments().getInt(Integer.class.getName());
    }
    private void getProjectList(TextView project)
    {
        BaseFragmentActivity activity = ((BaseFragmentActivity)getActivity());
        StatInfoRequest request =new StatInfoRequest();
        NewRequestHead head = new NewRequestHead().setaccessToken(activity.getConfig().getToken()).setuserId(activity.getConfig().getUserId());
        StatInfoRequest.StatInfoRequestBody body = new StatInfoRequest.StatInfoRequestBody();
        body.setBranchId(activity.getConfig().getBranchId());
        body.setRoleId(activity.getConfig().getRoleId());
        body.setDepartId(((CommunityCountInfo)department.getTag()).getId());
        request.setBody(body);
        request.setHead(head);

        String url = activity.getConfig().getServer() + GETCOMMUNITY;

        NetTask netTask = new NetTask(url,request) {
            @Override
            protected void onResponse(NetTask task, String result) {
                ProjectCountInfoResponse response = ProjectCountInfoResponse.getResponse(result,ProjectCountInfoResponse.class);
                showProjectPanel(response.getBody(),project);
            }
        };
        activity.addTask(netTask);
    }
    private void getDepartMentList(TextView department,Boolean isStatus)
    {
        BaseFragmentActivity activity = ((BaseFragmentActivity)getActivity());
        StatInfoRequest request =new StatInfoRequest();
        NewRequestHead head = new NewRequestHead().setaccessToken(activity.getConfig().getToken()).setuserId(activity.getConfig().getUserId());
        StatInfoRequest.StatInfoRequestBody body = new StatInfoRequest.StatInfoRequestBody();
        body.setBranchId(activity.getConfig().getBranchId());
        body.setRoleId(activity.getConfig().getRoleId());
        request.setBody(body);
        request.setHead(head);
        String url = "";
        if (isStatus) {
            url = activity.getConfig().getServer() + GETSTATUS;
            switch (currTag)
            {
                case ContentFragmentNew.FIX:
                {
                    body.setStatusType("baoxiu");

                    break;
                }
                case ContentFragmentNew.PERSON:
                {
                    body.setStatusType("weixiugong");

                    break;
                }case ContentFragmentNew.ORDER:
            {
                body.setStatusType("workOrder");

                break;
            }
            case ContentFragmentNew.ALARM:
            {
                body.setStatusType("alarm");

                break;
            }
            }
        }
        else
        {
//        if (Config.COMPANY.equals(activity.getConfig().getRoleId()))
//        {
//            url = activity.getConfig().getServer() + GETDEPARTMENT;
//        }
//        else if (Config.MANAGER.equals(activity.getConfig().getRoleId())) {
//           url = activity.getConfig().getServer() + GETCOMMUNITY;
//        }
            url = activity.getConfig().getServer() + GETDEPARTMENT;
        }

        NetTask netTask = new NetTask(url,request) {
            @Override
            protected void onResponse(NetTask task, String result) {
                ProjectCountInfoResponse response = ProjectCountInfoResponse.getResponse(result,ProjectCountInfoResponse.class);
                showProjectPanel(response.getBody(),department);
            }
        };
        activity.addTask(netTask);
    }


    public void initData()
    {

    }
    private BaiduMap mMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        baiduMap = (MapView) rootView.findViewById(R.id.baidu_map);
        btn_back = (ImageButton)rootView.findViewById(R.id.btn_back);
        btn_back.setOnClickListener(v -> getActivity().finish());
        mMap = baiduMap.getMap();

        LinearLayout titleLayout = (LinearLayout) rootView.findViewById(R.id.title);
        titleLayout.setBackground(ContextCompat.getDrawable(getActivity(),R.color.transparent));
        title = (TextView) rootView.findViewById(R.id.tv_title);
        title.setVisibility(View.INVISIBLE);
        btn_back.setVisibility(View.VISIBLE);
        btn_back.setImageResource(R.drawable.list_delete_icon);
        date = (TextView) rootView.findViewById(R.id.date);
        department = (TextView) rootView.findViewById(R.id.department);
        project = (TextView) rootView.findViewById(R.id.project);
        state = (TextView) rootView.findViewById(R.id.state);
        table = (TextView) rootView.findViewById(R.id.table);
        fix = (TextView)rootView.findViewById(R.id.fix);
        person = (TextView)rootView.findViewById(R.id.person);
        alarm = (TextView)rootView.findViewById(R.id.alarm);
        order = (TextView)rootView.findViewById(R.id.order);
        initPageData();
        changeTitle(currTag, fix);
        getMapDetailInfo(currTag);
        clearMapImage();
        return rootView;
    }

    private void initPageData() {

        date.setOnClickListener(v -> {
            ///获取数据 并回调  //showProjectPanel(date)
            List<CommunityCountInfo> datas = getTimeByType();
            showProjectPanel(datas,date);
        });
        CommunityCountInfo info = new CommunityCountInfo() ;
        info.setId(Utils.dateToString(new Date()));
        info.setName(Utils.dateToString(new Date()));
        date.setTag(info);
        date.setTag(R.id.index,"time");
        date.setText(info.getName().split("-")[2]+"/"+info.getName().split("-")[1]);

        department.setOnClickListener(v -> {

            getDepartMentList(department,false);
         //
        });
        CommunityCountInfo depInfo = new CommunityCountInfo() ;

        depInfo.setId("all");
        depInfo.setName("全部");

        department.setTag(depInfo);
        department.setText(depInfo.getName());



        project.setOnClickListener(v -> {

            getProjectList(project);
            //
        });
        CommunityCountInfo proInfo = new CommunityCountInfo() ;

        proInfo.setId("all");
        proInfo.setName("全部");

        project.setTag(proInfo);
        project.setText(proInfo.getName());


        state.setOnClickListener(v -> {
            getDepartMentList(state,true);
            ///获取数据 并回调  //showProjectPanel(date)
        });
        CommunityCountInfo stateInfo = new CommunityCountInfo();
        stateInfo.setId("all");
        stateInfo.setName("全部");
        state.setTag(stateInfo);
        state.setText(stateInfo.getName());

        table.setOnClickListener(v -> {
            ///获取数据 并回调  //showStatTableDialog()
            getTableInfo(currTag);
        });
        table.setTag(new CommunityCountInfo());
        table.setText("统计表");

        fix.setOnClickListener(v -> {
            ///获取数据 并回调  //changeView(date)
            currTag = ContentFragmentNew.FIX;
            changeTitle(currTag,v);
            getMapDetailInfo(currTag);
        });

        person.setOnClickListener(v -> {
            ///获取数据 并回调  //changeView(date)
            currTag = ContentFragmentNew.PERSON;
            changeTitle(currTag, v);
            getMapDetailInfo(currTag);
        });

        alarm.setOnClickListener(v -> {
            ///获取数据 并回调  //changeView(date)
            currTag = ContentFragmentNew.ALARM;
            changeTitle(currTag, v);
            getMapDetailInfo(currTag);
        });

        order.setOnClickListener(v -> {
            ///获取数据 并回调  //changeView(date)
            currTag = ContentFragmentNew.ORDER;
            changeTitle(currTag, v);
            getMapDetailInfo(currTag);
        });
    }

    public void clearMapImage()
    {
        // 隐藏百度的LOGO
        View child = baiduMap.getChildAt(1);
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)) {
            child.setVisibility(View.INVISIBLE);
        }

        // 不显示地图上比例尺
        baiduMap.showScaleControl(false);

        // 不显示地图缩放控件（按钮控制栏）
        baiduMap.showZoomControls(false);
    }
    public void changeTitle(String titleText, View v)
    {
        resetStateColor();
        initPageData();
        title.setText(titleText);
        if (v instanceof TextView) {
            ((TextView) v).setTextColor(ContextCompat.getColor(MyApplication.getInstance(), R.color.title_bg_color));
            v.setBackground(ContextCompat.getDrawable(MyApplication.getInstance(),R.drawable.corner_stroke_solid));
        }
    }
    public void resetStateColor()
    {
        CommunityCountInfo info = new CommunityCountInfo();
        info.setId("all");
        info.setName("全部");
        state.setTag(info);
        state.setText("全部");
        fix.setTextColor(ContextCompat.getColor(MyApplication.getInstance(),R.color.grey));
        fix.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.corner_grey_stroke_soild));
        alarm.setTextColor(ContextCompat.getColor(MyApplication.getInstance(),R.color.grey));
        alarm.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.corner_grey_stroke_soild));
        person.setTextColor(ContextCompat.getColor(MyApplication.getInstance(),R.color.grey));
        person.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.corner_grey_stroke_soild));
        order.setTextColor(ContextCompat.getColor(MyApplication.getInstance(),R.color.grey));
        order.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.corner_grey_stroke_soild));

    }
    @NonNull
    private List<CommunityCountInfo> getTimeByType() {
      //  BaseFragmentActivity activity = ((BaseFragmentActivity)getActivity());
        List<CommunityCountInfo> datas = new ArrayList<>();
        Date date = new Date();
        String today = Utils.dateToString(date);
        if (currTag.equals(FIX)||currTag.equals(ALARM)) {

            CommunityCountInfo data = new CommunityCountInfo();
            data.setId(today);
            data.setName(today);

            CommunityCountInfo data1 = new CommunityCountInfo();
            data1.setId(Utils.dateDayInterval(-1));
            data1.setName(Utils.dateDayInterval(-1));
            CommunityCountInfo data2 = new CommunityCountInfo();
            data2.setId(Utils.dateDayInterval(-2));
            data2.setName(Utils.dateDayInterval(-2));
            CommunityCountInfo data3 = new CommunityCountInfo();
            data3.setId(Utils.dateDayInterval(-3));
            data3.setName(Utils.dateDayInterval(-3));
            CommunityCountInfo data4 = new CommunityCountInfo();
            data4.setId(Utils.dateDayInterval(-4));
            data4.setName(Utils.dateDayInterval(-4));

            datas.add(data4);
            datas.add(data3);
            datas.add(data2);
            datas.add(data1);
            datas.add(data);
        }
        else if(currTag.equals(PERSON))
        {
            CommunityCountInfo data = new CommunityCountInfo();
            data.setId(Utils.dateToString(date));
            data.setName(Utils.dateToString(date));
            datas.add(data);
        }
        else if (currTag.equals(ORDER))
        {
            CommunityCountInfo data = new CommunityCountInfo();
            data.setId(today);
            data.setName(today);

            CommunityCountInfo data1 = new CommunityCountInfo();
            data1.setId(Utils.dateDayInterval(1));
            data1.setName(Utils.dateDayInterval(1));
            CommunityCountInfo data2 = new CommunityCountInfo();
            data2.setId(Utils.dateDayInterval(2));
            data2.setName(Utils.dateDayInterval(2));
            CommunityCountInfo data3 = new CommunityCountInfo();
            data3.setId(Utils.dateDayInterval(-1));
            data3.setName(Utils.dateDayInterval(-1));
            CommunityCountInfo data4 = new CommunityCountInfo();
            data4.setId(Utils.dateDayInterval(-2));
            data4.setName(Utils.dateDayInterval(-2));
            datas.add(data4);
            datas.add(data3);
            datas.add(data);
            datas.add(data1);
            datas.add(data2);
        }
        return datas;
    }

    private Dialog dialog2;

    public void showStatTableDialog(ArrayList<StatResult> body) {
        DialogUtil dialog = new DialogUtil();
        View tableView = View.inflate(getActivity(), R.layout.dialog_table_data, null);
        ChartTableView table1 = (ChartTableView) tableView.findViewById(R.id.chartTableView_visitSummary);

        table1.setOnClickListener(v -> {
            if (dialog2.isShowing())
                dialog2.dismiss();
        });


        //ArrayList<StatResult> list = new ArrayList<StatResult>();
        table1.setDataList(body);
        dialog2 = dialog.CustomDialog(getActivity(), tableView);
    }

    @Override
    public void takeScreenShot() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Bitmap bitmap = Bitmap.createBitmap(containerView.getWidth(),
                        containerView.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                containerView.draw(canvas);
                ContentFragmentNew.this.bitmap = bitmap;
            }
        };

        thread.start();

    }

    private void getTableInfo(String type)
    {
        BaseFragmentActivity activity = ((BaseFragmentActivity)getActivity());
        String url  =  activity.getConfig().getServer()+activity.getConfig().getReportUrl();
        switch (type)
        {
            case ContentFragment.ORDER: url = url+GETINDEXWORKORDERREPORT; break;
            case ContentFragment.FIX: url = url+GETINDEXBAOXIUREPORT; break;
            case ContentFragment.ALARM: url = url+GETINDEXALARMREPORT; break;
        }

        StatInfoRequest request =new StatInfoRequest();
        NewRequestHead head = new NewRequestHead().setaccessToken(activity.getConfig().getToken()).setuserId(activity.getConfig().getUserId());
        StatInfoRequest.StatInfoRequestBody body = new StatInfoRequest.StatInfoRequestBody();
        body.setBranchId(activity.getConfig().getBranchId());
        String id = ((CommunityCountInfo)department.getTag()).getId();
        body.setCommunityId(id);
        body.setDepartId(id);
        String time = ((CommunityCountInfo)date.getTag()).getId();
        body.setQueryTime(time);
        body.setRoleId(activity.getConfig().getRoleId());
        request.setBody(body);
        request.setHead(head);

        NetTask netTask = new NetTask(url,request) {
            @Override
            protected void onResponse(NetTask task, String result) {
                StatInfoResponse response = StatInfoResponse.getResponse(result,StatInfoResponse.class);
                if (response!=null&&response.getBody().size()>0)
                //changeView(response.getBody());
                    showStatTableDialog(response.getBody());
            }
        };
        activity.addTask(netTask);

    }
    private void getMapDetailInfo(String type)
    {
        BaseFragmentActivity activity = ((BaseFragmentActivity)getActivity());
        String url  =  activity.getConfig().getServer()+activity.getConfig().getReportUrl();
        switch (type)
        {
            case ContentFragment.ORDER: url = url+GETINDEXWORKORDERREPORTELEVATORLINE; break;
            case ContentFragment.FIX: url = url+GETINDEXBAOXIUREPORTELEVATORLINE; break;
            case ContentFragment.ALARM: url = url+GETINDEXALARMREPORTELEVATORLINE; break;
            case ContentFragment.PERSON: url = url+GETINDEXWEIXIUGONGLINE; break;
        }


        StatInfoRequest request =new StatInfoRequest();
        NewRequestHead head = new NewRequestHead().setaccessToken(activity.getConfig().getToken()).setuserId(activity.getConfig().getUserId());
        StatInfoRequest.StatInfoRequestBody body = new StatInfoRequest.StatInfoRequestBody();
        body.setBranchId(activity.getConfig().getBranchId());
        String id = ((CommunityCountInfo)department.getTag()).getId();
        body.setDepartId(id);
        String id1 = ((CommunityCountInfo)project.getTag()).getId();
        body.setCommunityId(id1);
        body.setDepartId(id);
        body.setStatus(((CommunityCountInfo)state.getTag()).getId());

        String time = ((CommunityCountInfo)date.getTag()).getId();
       // String time = Utils.dateToString(new Date());
        body.setQueryTime(time);
        body.setRoleId(activity.getConfig().getRoleId());
        request.setBody(body);
        request.setHead(head);

        NetTask netTask = new NetTask(url,request) {
            @Override
            protected void onResponse(NetTask task, String result) {
                StatMapInfoResponse response = StatMapInfoResponse.getResponse(result,StatMapInfoResponse.class);
                if (type.equals(ContentFragment.PERSON))
                {
                    markMapPerson(response.getBody());
                    return;
                }
              //  StatMapInfoResponse response = StatMapInfoResponse.getResponse(result,StatMapInfoResponse.class);

                if (response!=null)

                switch (type)
                {
                    case ContentFragment.ORDER: {
                        markMap(response.getBody());
                        break;
                    }
                    case ContentFragment.FIX: {
                        markMap(response.getBody());
                        break;
                    }
                    case ContentFragment.ALARM: {
                        markMap(response.getBody());
                        break;
                    }
                }

            }
        };
        activity.addTask(netTask);

    }
    public String getType()
    {
        switch (currTag)
        {
            case FIX:
                return "baoxiu";
            case ORDER:
                return "workOrder";
            case PERSON:
                return "weixiugong";
            case ALARM:
                return "alarm";
        }
        return "";
    }
    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }

}