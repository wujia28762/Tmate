package com.honyum.elevatorMan.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.honyum.elevatorMan.R;
import com.honyum.elevatorMan.activity.company.NHFixOrderListActivity;
import com.honyum.elevatorMan.base.MyApplication;
import com.honyum.elevatorMan.data.StatResult;
import com.honyum.elevatorMan.data.newdata.CompanyRepairInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.OnClick;
import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * Created by Konstantin on 22.12.2014.
 */
public class ContentFragment extends Fragment implements ScreenShotable {
    public static final String CLOSE = "close";
    public static final String ORDER = "订单查看";
    public static final String FIX = "维修查看";
    public static final String PERSON = "人员查看";
    public static final String ALARM = "报警查看";
    public static Map<Integer,String> tagMap = new HashMap<>();
    static
    {
        tagMap.put(0,CLOSE);
        tagMap.put(1,ORDER);
        tagMap.put(2,FIX);
        tagMap.put(3,PERSON);
        tagMap.put(4,ALARM);
    }
    public static ContentFragment contentFragment;

    private View containerView;
    protected ImageView mImageView;
    protected int res;
    private Bitmap bitmap;
    private MapView baiduMap;
    private Marker locationMarker;

    public static ContentFragment newInstance(int resId) {
        contentFragment = new ContentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Integer.class.getName(), resId);
        contentFragment.setArguments(bundle);
        return contentFragment;
    }

    public void changeView(String tag)
    {
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
        markMap(getInfo(tag));

    }
    public List<StatResult> getInfo(String type)
    {
        List<StatResult> datas = new ArrayList<>();

        if (type.equals(ORDER)||type.equals(PERSON)) {
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
        }
        else {

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
        return  datas;

    }

    private void markMap(List<StatResult> datas) {


        //mBaiduMap.hideInfoWindow();
        if (null == mMap) {
            return;
        }

        if (null == datas) {
            return;
        }


        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory
                .fromResource(R.drawable.deattask);

        for (StatResult info : datas) {

            double latitude = info.getLat();
            double longitude = info.getLng();
            LatLng point = new LatLng(latitude, longitude);
            MarkerOptions markerOption = new MarkerOptions().icon(bitmapDescriptor).position(point);
            locationMarker = (Marker) mMap.addOverlay(markerOption);
        }
        mMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker arg0) {
                //showAppToast("点击了MAKER");
                return false;
            }
        });
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
    private BaiduMap mMap;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        baiduMap = (MapView)rootView.findViewById(R.id.baidu_map);
        mMap = baiduMap.getMap();
        return rootView;
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
                ContentFragment.this.bitmap = bitmap;
            }
        };

        thread.start();

    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }
}