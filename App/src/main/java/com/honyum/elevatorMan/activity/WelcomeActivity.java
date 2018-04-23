package com.honyum.elevatorMan.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.widget.ImageView;

import com.baidu.navisdk.util.common.StringUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.gifdecoder.GifDecoder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.honyum.elevatorMan.R;
import com.honyum.elevatorMan.activity.common.MainGroupActivity;
import com.honyum.elevatorMan.activity.common.MainPage1Activity;
import com.honyum.elevatorMan.activity.company.MainPageActivity;
import com.honyum.elevatorMan.activity.company.MainPageGroupCompanyActivity;
import com.honyum.elevatorMan.base.BaseFragmentActivity;
import com.honyum.elevatorMan.constant.Constant;
import com.honyum.elevatorMan.net.LoginResponse;
import com.honyum.elevatorMan.utils.Utils;

import java.lang.ref.WeakReference;

import cn.jpush.android.api.JPushInterface;

/**
 * 当有记录状态时，直接登陆界面，不进行登陆操作
 *
 * @author changhaozhang
 */
public class WelcomeActivity extends BaseFragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

//        boolean first = getConfig().getIsFirst();
//        if (first) {
//            getConfig().setUnFirst();
//            startActivity(new Intent(WelcomeActivity.this, NavigationActivity.class));
//            finish();
//        } else {
        setContentView(R.layout.activity_welcome);
        ImageView iv = (ImageView) findViewById(R.id.iv_welcome);
        Glide.with(this).load(R.drawable.welcome_page_gif)
                .diskCacheStrategy(DiskCacheStrategy.NONE).listener(new RequestListener<Integer, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, Integer integer, Target<GlideDrawable> target, boolean b) {
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable glideDrawable, Integer integer, Target<GlideDrawable> target, boolean b, boolean b1) {
                GifDrawable drawable = (GifDrawable) glideDrawable;
                GifDecoder decoder = drawable.getDecoder();
                int duration = 0;
                for (int i = 0; i < drawable.getFrameCount(); i++) {
                    duration += decoder.getDelay(i);
                }
                //发送延时消息，通知动画结束
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        checkRemoteVersion(new ICheckVersionCallback() {
                            @Override
                            public void onCheckVersion(int remoteVersion, String url, int isForce, String description) {
                                updateApk(remoteVersion, url, isForce, description);
                            }
                        });
                    }
                }, duration);
                return false;
            }
        })
                .into(new GlideDrawableImageViewTarget(iv, 1));

//        Resources res=getResources();
//        Bitmap bmp= BitmapFactory.decodeResource(res, R.drawable.welcome_page_gif);

//        // 延迟1秒后执行run方法中的页面跳转
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                checkRemoteVersion(new ICheckVersionCallback() {
//                    @Override
//                    public void onCheckVersion(int remoteVersion, String url, int isForce, String description) {
//                        updateApk(remoteVersion, url, isForce, description);
//                    }
//                });
//            }
//        }, 5000);
//        }
    }

    private static class MyHandler extends Handler {
        private WeakReference<WelcomeActivity> mWeakReference;
        public MyHandler(WelcomeActivity activity) {
            mWeakReference = new WeakReference<WelcomeActivity>(activity);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) mHandler.removeCallbacksAndMessages(null);
    }

    /**
     * 根据保存的信息跳转页面
     */
    private void startActivityByToken() {
        String userId = getConfig().getUserId();
        String token = getConfig().getToken();
        String role = getConfig().getRole();
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(token)
                || StringUtils.isEmpty(role)) {
            startLogin(getAlarmId());

        } else if (role.equals(Constant.WORKER)) {
            startWorker(getAlarmId());

        } else if (role.equals(Constant.PROPERTY)) {
            startProperty(false);
        }
        else if (role.equals(Constant.COMPANY)) {
            startCompany(getAlarmId());
        }
            finish();
    }

    private void startCompany(String alarmId) {
        Intent intent = new Intent(WelcomeActivity.this, MainPageGroupCompanyActivity.class);

//        if (!StringUtils.isEmpty(alarmId)) {
//            intent.putExtra("alarm_id", alarmId);
//            intent.setAction(Constant.ACTION_ALARM_RECEIVED);
//        }
        startActivity(intent);
        finish();
    }

    /**
     * 跳转到登陆页面
     */
    private void startLogin(String alarmId) {

        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);

        if (!StringUtils.isEmpty(alarmId)) {
            intent.putExtra("alarm_id", alarmId);
            intent.setAction(Constant.ACTION_ALARM_RECEIVED);
        }
        startActivity(intent);
        //finish();
    }

    /**
     * 更新apk
     *
     * @param remoteVersion
     * @param url
     * @param isForce
     */
    private void updateApk(int remoteVersion, String url, int isForce, String description) {

        //当没有下载url时，不再进行版本的检测
        if (StringUtils.isEmpty(url)) {
            startActivityByToken();
            return;
        }

        if (StringUtils.isEmpty(description)) {
            description = "有新的版本可用";
        }

        int curVersion = Utils.getVersionCode(this.getApplicationContext());
        if (remoteVersion > curVersion) {
            updateApk(url, isForce, description, new IUpdateCancelCallback() {
                @Override
                public void onCancel() {
                    startActivityByToken();
                }
            });
        } else {
            startActivityByToken();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    /**
     * 获取传递过来的报警id，用来处理从微信传递过来的数据
     *
     * @return
     */
    private String getAlarmId() {
        if (null == getIntent()) {
            return "";
        }
        Uri uri = getIntent().getData();
        return uri == null ? "" : uri.getQueryParameter("id");
    }
}
