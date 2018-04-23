package com.honyum.elevatorMan.base;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.baidu.navisdk.util.common.StringUtils;
import com.honyum.elevatorMan.constant.Constant;
import com.honyum.elevatorMan.utils.Utils;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 保存配置信息，如一页显示信息条数
 *
 * @author LiuJiane
 */
public class Config implements Serializable {

    public static Map<String,Integer> unReadMap = new HashMap<>();
    public static final String WORKER = "30d9b180-80b4-4707-bc82-686560e694a7";
    public static final String COMPANY = "ba83c794-0055-47a6-963e-a1460624ca81";
    public static final String MANAGER = "8ce9d93f-3157-4509-a1ca-e20dc593dd9b";


    private SharedPreferences mPreferences;

    private Editor mEditor;

    private int CurrDelay = 0;

    public static String currLastNode;
    public static String currTask;
    public static String isFinish;
    public static String userId;



  // public static String baseUrl = "http://10.10.4.18:8080";
 //  public static String baseUrl = "http://192.168.5.108:8080";
    //public static String baseUrl = "http://192.168.5.103:8080";
   //public static String baseUrl = "http://10.10.4.20:8080";
    public static String baseUrl = "http://www.chorstar.com:8081";
 //public static String baseUrl = "http://192.168.0.81:8080";
 //public static String baseUrl = "http://www.chorstar.net";

    public Config(Context context) {
        mPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
    }

    /**
     * 设置区域
     *
     * @param region
     */
    public void setRegion(String region) {
        if (StringUtils.isEmpty(region)) {
            return;
        }
        mEditor.putString("region", region);
        mEditor.commit();
    }

    /**
     * 获取区域
     *
     * @return
     */
    public String getRegion() {
        return mPreferences.getString("region", Constant.CHINA);
    }

    /**
     * 获取服务器地址
     *
     * @return
     */
    public String getServer() {

        return baseUrl+"/mobile";
        // return "http://www.chorstar.com:8081/mobile";

//        String region = getRegion();
//        String server = Constant.SERVER_LIST.get(region);
//
//        if (StringUtils.isEmpty(server)) {
//            server = Constant.SERVER_LIST.get(Constant.BEIJING);
//        }
//
//        return server;
    }
    /**
     * 获取服务器地址
     *
     * @return
     */
    public String getMaintenanceServer() {

        return baseUrl+"/mobile/maintenance";
      // return "http://www.chorstar.com:8081/mobile";

//        String region = getRegion();
//        String server = Constant.SERVER_LIST.get(region);
//
//        if (StringUtils.isEmpty(server)) {
//            server = Constant.SERVER_LIST.get(Constant.BEIJING);
//        }
//
//        return server;
    }

    public String getNewServer() {

        return baseUrl+"/mobile/process";
        // return "http://www.chorstar.com:8081/mobile";

//        String region = getRegion();
//        String server = Constant.SERVER_LIST.get(region);
//
//        if (StringUtils.isEmpty(server)) {
//            server = Constant.SERVER_LIST.get(Constant.BEIJING);
//        }
//
//        return server;
    }
    public String getContractUrl()
    {
        return "/contract";
    }
    public String getReportUrl(){return "/report";}
    public String getMaintenanceUrl()
    {
        return "/maintenance";
    }
    public String getPCServer() {

        return baseUrl;
        // return "http://www.chorstar.com:8081/mobile";

//        String region = getRegion();
//        String server = Constant.SERVER_LIST.get(region);
//
//        if (StringUtils.isEmpty(server)) {
//            server = Constant.SERVER_LIST.get(Constant.BEIJING);
//        }
//
//        return server;
    }

    /**
     * 保存用户id
     *
     * @param userId
     */
    public void setUserId(String userId) {
        mEditor.putString("user_id", userId);
        mEditor.commit();
    }

    /**
     * 保存当前项目的lat
     *
     * @param lat
     */
    public void setLat(String lat) {
        mEditor.putString("Reslat", lat);
        mEditor.commit();
    }



    /**
     * 保存当前项目的 lng
     *
     * @param lng
     */
    public void setLng(String lng) {
        mEditor.putString("Reslng", lng);
        mEditor.commit();
    }



    /**
     * 保存当前项目的lat
     *
     * @param
     */
    public String getLat() {


        return mPreferences.getString("Reslat", "0");
    }


    public int getLocationUpload()
    {
        return  mPreferences.getInt("LocationUpload",300);

    }

    public int getLocationUploadTask()
    {
        return  mPreferences.getInt("LocationUploadTask",15);

    }

    public void setLocationUpload(int LocationUpload)
    {
        mEditor.putInt("LocationUpload", LocationUpload);
        mEditor.commit();
    }

    public void setCurrDelay(int CurrDelay)
    {
        mEditor.putInt("CurrDelay", CurrDelay);
        mEditor.commit();
    }
    public int getCurrDelay()
    {
        return mPreferences.getInt("CurrDelay",15);
    }
    public void setLocationUploadTask(int LocationUploadTask)
    {
        mEditor.putInt("LocationUploadTask", LocationUploadTask);
        mEditor.commit();
    }



    /**
     * 保存当前项目的 lng
     *
     * @param
     */
    public String getLng() {
        return mPreferences.getString("Reslng", "0");
    }
    /**
     * 获取用户id
     *
     * @return
     */
    public String getUserId() {
        return mPreferences.getString("user_id", "");
    }

    /**
     * 保存用户token
     */
    public void setToken(String token) {
        mEditor.putString("token", token);
        mEditor.commit();
    }

    /**
     * 获取用户token
     *
     * @return
     */
    public String getToken() {
        return mPreferences.getString("token", "test");
    }

    /**
     * 设置头像的url地址
     *
     * @param url
     */
    public void setIconUrl(String url) {
        mEditor.putString("icon", url);
        mEditor.commit();
    }

    /**
     * 获取头像的url地址
     *
     * @return
     */
    public String getIconUrl() {
        return mPreferences.getString("icon", "");
    }

    /**
     * 保存纬度信息
     *
     * @param latitude
     */
    public void setLatitude(double latitude) {
        mEditor.putString("lat", "" + latitude);
        mEditor.commit();
    }

    /**
     * 获取纬度信息
     *
     * @return
     */
    public double getLatitude() {
        String latitude = mPreferences.getString("lat", "0");
        return Utils.getDouble(latitude);
    }

    /**
     * 保存经度信息
     *
     * @param longitude
     */
    public void setLongitude(double longitude) {
        mEditor.putString("long", "" + longitude);
        mEditor.commit();
    }

    /**
     * 获取经度信息
     *
     * @return
     */
    public double getLongitude() {
        String longitude = mPreferences.getString("long", "0");
        return Utils.getDouble(longitude);
    }

    /**
     * 保存当前用户角色
     *
     * @param role
     */
    public void setRole(String role) {
        mEditor.putString("role", role);
        mEditor.commit();
    }

    /**
     * 获取当前用户角色
     *
     * @return
     */
    public String getRole() {
        return mPreferences.getString("role", Constant.PROPERTY);
    }

    /**
     * 设置当前报警事件的id
     *
     * @param alarmId
     */
    public void setAlarmId(String alarmId) {
        mEditor.putString("alarm_id", alarmId);
        mEditor.commit();
    }

    /**
     * 获取当前处理报警事件的id
     *
     * @return
     */
    public String getAlarmId() {
        return mPreferences.getString("alarm_id", "");
    }

    /**
     * 设置当前报警事件的状态
     *
     * @param state
     */
    public void setAlarmState(String state) {
        mEditor.putString("alarm_state", state);
        mEditor.commit();
    }

    /**
     * 获取当前处理报警事件的状态
     *
     * @return
     */
    public String getAlarmState() {
        return mPreferences.getString("alarm_state", "");
    }


    /**
     * 保存用户名相关信息
     *
     * @param userName
     * @return
     */
    public void setUserName(String userName) {
        mEditor.putString("user_name", userName);
        mEditor.commit();
    }

    /**
     * 获取用户名相关信息
     */
    public String getUserName() {
        return mPreferences.getString("user_name", "");
    }

    public void setPassword(String password) {
        mEditor.putString("pwd", password);
        mEditor.commit();
    }

    public String getPassword() {
        return mPreferences.getString("pwd", "");
    }

    /**
     * 用户年龄的处理
     *
     * @param age
     */
    public void setAge(int age) {
        mEditor.putInt("age", age);
        mEditor.commit();
    }

    public int getAge() {
        return mPreferences.getInt("age", 18);
    }

    /**
     * 用户操作证号的处理
     */
    public void setOperationCard(String card) {
        mEditor.putString("card", card);
        mEditor.commit();
    }

    public String getOperationCard() {
        return mPreferences.getString("card", "");
    }

    /**
     * 用户性别的处理
     */
    public void setSex(int sex) {
        mEditor.putInt("sex", sex);
        mEditor.commit();
    }

    public int getSex() {
        return mPreferences.getInt("sex", 1);
    }

    /**
     * 电话号码的处理
     */
    public void setTel(String tel) {
        mEditor.putString("tel", tel);
        mEditor.commit();
    }

    public String getTel() {
        return mPreferences.getString("tel", "");
    }

    /**
     * 公司名称
     */
    public void setBranchName(String branchName) {
        mEditor.putString("branch", branchName);
        mEditor.commit();
    }

    public String getBranchName() {
        return mPreferences.getString("branch", "");
    }

    /**
     * 公司id
     */
    public void setBranchId(String branchId) {
        mEditor.putString("branchId", branchId);
        mEditor.commit();
    }

    public String getBranchId() {
        return mPreferences.getString("branchId", "");
    }


    /**
     * 保存错误代码
     *
     * @param errorCode
     */
    public void setErrorCode(String errorCode) {
        mEditor.putString("error_code", errorCode);
        mEditor.commit();
    }

    public String getErrorCode() {
        return mPreferences.getString("error_code", "");
    }

    /**
     * 保存用户的姓名
     *
     * @param name
     */
    public void setName(String name) {
        mEditor.putString("name", name);
        mEditor.commit();
    }

    /**
     * 获取用户的姓名
     *
     * @return
     */
    public String getName() {
        return mPreferences.getString("name", "");
    }

    /**
     * 设置电梯的初始日期，测试使用
     *
     * @param date
     */
    public void setInitDate(String date) {
        mEditor.putString("init_date", date);
        mEditor.commit();
    }

    /**
     * 获取电梯的初始日期，测试使用
     *
     * @return
     */
    public Date getInitDate() {
        String date = mPreferences.getString("init_date", "2015-09-01");
        return Utils.stringToDate(date);
    }

    /**
     * 设置是否显示视频接口
     *
     * @param enable
     */
    public void setVideoEnable(boolean enable) {
        mEditor.putBoolean("video", enable);
        mEditor.commit();
    }

    /**
     * 获取是否显示视频接口
     *
     * @return
     */
    public boolean getVideoEnable() {
        return mPreferences.getBoolean("video", false);
    }


    /**
     * 设置是否显示知识库
     *
     * @param enable
     */
    public void setKnowledge(boolean enable) {
        mEditor.putBoolean("knowledge", enable);
        mEditor.commit();
    }

    /**
     * 获取是否显示知识库
     *
     * @return
     */
    public boolean getKnowledgeEnable() {
        return mPreferences.getBoolean("knowledge", false);
    }

    /**
     * 设置接警时间
     *
     * @param seconds
     */
    public void setAlarmWaitTime(int seconds) {
        mEditor.putInt("alarm_wait", seconds);
        mEditor.commit();
    }

    public void setUnFirst() {
        mEditor.putBoolean("first", false);
        mEditor.commit();
    }

    public boolean getIsFirst() {
        return mPreferences.getBoolean("first", true);
    }

    /**
     * 获取接警时间，默认120秒
     *
     * @return
     */
    public int getAlarmWaitTime() {
        return mPreferences.getInt("alarm_wait", 300);
    }

    public void setWProvince(String province) {
        mEditor.putString("wprovince", province);
        mEditor.commit();
    }

    public String getWProvince() {
        return mPreferences.getString("wprovince", "");
    }

    public void setWCity(String city) {
        mEditor.putString("wcity", city);
        mEditor.commit();
    }

    public String getWCity() {
        return mPreferences.getString("wcity", "");
    }

    public void setWDistrict(String district) {
        mEditor.putString("wdistrict", district);
        mEditor.commit();
    }

    public String getWDistrict() {
        return mPreferences.getString("wdistrict", "");
    }

    public void setWAddress(String address) {
        mEditor.putString("waddress", address);
    }

    public String getWAddress() {
        return mPreferences.getString("waddress", "");
    }

    public void setHDistrict(String district) {
        mEditor.putString("hdistrict", district);
        mEditor.commit();
    }


    public void setHProvince(String province) {
        mEditor.putString("hprovince", province);
        mEditor.commit();
    }

    public String getHProvince() {
        return mPreferences.getString("hprovince", "");
    }

    public void setHCity(String city) {
        mEditor.putString("hcity", city);
        mEditor.commit();
    }

    public String getHCity() {
        return mPreferences.getString("hcity", "");
    }

    public String getHDistrict() {
        return mPreferences.getString("hdistrict", "");
    }

    public void setHAddress(String address) {
        mEditor.putString("haddress", address);
        mEditor.commit();
    }

    public String getHAddress() {
        return mPreferences.getString("haddress", "");
    }

    public String getSign() {
        return mPreferences.getString("sign_url", "");
    }

    public void setSign(String sign) {
        mEditor.putString("sign_url", sign);
        mEditor.commit();
    }


    public String getPwd() {
        return mPreferences.getString("Pwd", "");
    }

    public void setPwd(String sign) {
        mEditor.putString("Pwd", sign);
        mEditor.commit();
    }
    public void setRoleId(String roleId,Boolean reload)
    {
        mEditor.putString("role_id", roleId);
        if (!reload)
        mEditor.putBoolean("role_type", false);
        mEditor.commit();
    }
    public String getRealRole()
    {
        return mPreferences.getString("role_id", "");
    }
    public boolean getRoleType()
    {
        return mPreferences.getBoolean("role_type",false);
    }
    public void setRoleType(boolean change)
    {
        mEditor.putBoolean("role_type", change).commit();
    }
    public String getRoleId() {
        String s = mPreferences.getString("role_id", "");

        boolean change = mPreferences.getBoolean("role_type",false);
        if (2 == s.split(",").length)
        {
            if (change)
            return s.split(",")[0];
            else
                return s.split(",")[1];
        }
            return s;
    }

}
