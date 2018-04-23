package com.honyum.elevatorMan.utils;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.honyum.elevatorMan.activity.common.ShowElevatInfoActivity;

/**
 * 获取地图坐标工具
 */

public class MapUtils {
    private LocationClient mLocationClient;
    private BDLocationListener mBDLocationListener;
    private PositionListener positionListener;


    public void setPositionListener(PositionListener positionListener) {
        this.positionListener = positionListener;
    }


    public interface PositionListener {
        void receivePosition(Double latitude,Double longitude);
        void receivePositionAddress(String address);
    }



    public MapUtils(Context context){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 设置定位模式 高精度
        option.setCoorType("bd09ll");// 设置返回定位结果是百度经纬度 默认gcj02
        option.setScanSpan(5000);// 设置发起定位请求的时间间隔 单位ms
        option.setIsNeedAddress(true);// 设置定位结果包含地址信息
        option.setNeedDeviceDirect(true);// 设置定位结果包含手机机头 的方向

        mLocationClient = new LocationClient(context.getApplicationContext());
        mLocationClient.setLocOption(option);
        mBDLocationListener = new MyBDLocationListener();
        // 注册监听
        mLocationClient.registerLocationListener(mBDLocationListener);

        mLocationClient.start();
    }
    private class MyBDLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            // 非空判断
            if (bdLocation != null) {
                // 根据BDLocation 对象获得经纬度以及详细地址信息
                if (positionListener==null )
                {
                    return ;
                }
                positionListener.receivePosition(bdLocation.getLatitude(),bdLocation.getLongitude());
                positionListener.receivePositionAddress(bdLocation.getAddrStr());
                if (mLocationClient.isStarted()) {
                    // 获得位置之后停止定位
                    mLocationClient.stop();
                }
                if (mLocationClient != null) {
                    mLocationClient.unRegisterLocationListener(mBDLocationListener);
                }
            }
        }
    }
}
