package com.honyum.elevatorMan.activity.company;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
import com.honyum.elevatorMan.base.BaseActivityWraper;
import com.honyum.elevatorMan.base.MyApplication;
import com.honyum.elevatorMan.data.MaintRecInfo;
import com.honyum.elevatorMan.net.GetWorkerRequest;
import com.honyum.elevatorMan.net.LookPersonResponse;
import com.honyum.elevatorMan.net.LookPersonResponse.BodyBean.BusyBean;
import com.honyum.elevatorMan.net.base.NetConstant;
import com.honyum.elevatorMan.net.base.NetTask;
import com.honyum.elevatorMan.net.base.NewRequestHead;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Star on 2017/8/11.
 */
public class LookPersonsActivity extends BaseActivityWraper {

    private BaiduMap mMap;
    private MapView mapView;
    private List<BusyBean> busyDatas;
    private List<BusyBean> freeDatas;

    private TextView free_num;

    private TextView busy_num;

    @Override
    public String getTitleString() {
        return "在线情况";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void initView() {
        mapView  = findView(R.id.map);
        free_num = findView(R.id.free_num);
        busy_num = findView(R.id.busy_num);
        mMap = mapView.getMap();
        getWindow().setBackgroundDrawable(null);
        getPersons();
    }

    private void markMap (List<BusyBean> datas,int id)
    {
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory
                .fromResource(id);


        for (BusyBean data : datas) {
            final BusyBean info = data;
            double latitude = info.getLat();
            double longitude = info.getLng();
            LatLng point = new LatLng(latitude, longitude);
            MarkerOptions markerOption = new MarkerOptions().icon(bitmapDescriptor).position(point);
            Marker locationMarker = (Marker) mMap.addOverlay(markerOption);
            Bundle bundle = new Bundle();
            bundle.putSerializable("Info", (Serializable) info);
            locationMarker.setExtraInfo(bundle);
            mMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    mMap.hideInfoWindow();
                }

                @Override
                public boolean onMapPoiClick(MapPoi mapPoi) {
                    return false;
                }
            });
            mMap.setOnMarkerClickListener(arg0 -> {
                LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                View view = inflater.inflate(R.layout.mapdetail_item, null);
                TextView textInfo = (TextView) view.findViewById(R.id.tv_detail_info);
                Button button = (Button) view.findViewById(R.id.bt_forward);
                button.setVisibility(View.GONE);
                final Bundle bundle1 = arg0.getExtraInfo();
                final BusyBean info1 = (BusyBean) bundle1.getSerializable("Info");

//                textInfo.setText(DataTravelUtils.getMiantRecInfoData().get(0).getCommunityName() + "(" + DataTravelUtils.getMiantRecInfoData().size() + ")");
//                double latitude = DataTravelUtils.getMiantRecInfoData().get(0).getLat();
//                double longitude = DataTravelUtils.getMiantRecInfoData().get(0).getLng();


                textInfo.setText(info1.getUserName());
                double latitude1 = info1.getLat();
                double longitude1 = info1.getLng();

                LatLng point1 = new LatLng(latitude1, longitude1);
                InfoWindow mInfoWindow = new InfoWindow(view, point1, 10);
                mMap.showInfoWindow(mInfoWindow);
                return false;
            });
        }


    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_personlist;
    }
    private void getPersons()
    {
        GetWorkerRequest request = new GetWorkerRequest();
        NewRequestHead head = new NewRequestHead();
        head.setaccessToken(getConfig().getToken()).setuserId(getConfig().getUserId());
        GetWorkerRequest.GetWorkerBody body =request.new GetWorkerBody();
        body.setBranchId(getConfig().getBranchId());
        request.setBody(body);
        request.setHead(head);


        NetTask netTask = new NetTask(getConfig().getServer()+NetConstant.GETSTATSTATE,request) {
            @Override
            protected void onResponse(NetTask task, String result) {

                LookPersonResponse response = LookPersonResponse.getResponse(result,LookPersonResponse.class);

                if (response!=null)
                {
                    busyDatas = response.getBody().getBusy();
                    freeDatas = response.getBody().getFree();
                    free_num.setText("休息中："+freeDatas.size());
                    busy_num.setText("忙碌中："+busyDatas.size());
                }
                initMapView();
            }
        };
        addTask(netTask);
    }

    @Override
    protected void preView() {
        super.preView();
        SDKInitializer.initialize(MyApplication.getInstance());
    }

    private void initMapView()
    {
        markMap(busyDatas,R.drawable.work_woker1);
        markMap(freeDatas,R.drawable.select_mine1);
    }
}
