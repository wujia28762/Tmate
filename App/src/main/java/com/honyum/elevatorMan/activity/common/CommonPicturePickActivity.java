package com.honyum.elevatorMan.activity.common;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import com.baidu.navisdk.util.common.StringUtils;
import com.honyum.elevatorMan.R;
import com.honyum.elevatorMan.base.BaseFragmentActivity;
import com.honyum.elevatorMan.constant.IntentConstant;
import com.honyum.elevatorMan.net.SendChatRequest;
import com.honyum.elevatorMan.net.base.NetConstant;
import com.honyum.elevatorMan.net.base.NetTask;
import com.honyum.elevatorMan.net.base.RequestHead;
import com.honyum.elevatorMan.utils.Utils;

import java.io.File;

public class CommonPicturePickActivity extends BaseFragmentActivity {


    public static final int PICK_TAG = 2;

    private final int RESULT_OK = -1;

    private final int RESULT_CANCEL = 0;

    private static final int CAMERA_REQ_CODE = 1;

    private static final int PICKER_REQ_CODE = 2;
   // private String mAlarmId;


    private Intent intent;
    private Uri uri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_icon_pop);
        initView(getIntent());
    }

    /**
     * 初始化视图
     * @param intent
     */
    private void initView(final Intent intent) {

//        if (getIntent().getBooleanExtra("CameraOnly",false))
//            findViewById(R.id.tv_pick).setVisibility(View.GONE);
        //mAlarmId = getIntent().getStringExtra("AlarmId");
        //拍摄照片
        findViewById(R.id.tv_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dirPath = Utils.getTempPath();
                if (StringUtils.isEmpty(dirPath)) {
                    showToast("没有找到SD卡，请检查之后再试!");
                    return;
                }

                //目录不存在，创建目录
                File dirFile = new File(dirPath);
                if (!dirFile.exists()) {
                    dirFile.mkdirs();
                }

                //文件存在，删除文件
                String fileName = intent.getStringExtra("tag") + ".jpg";
                String filePath = dirPath + "/" + fileName;
                File imgFile = new File(filePath);
                if (imgFile.exists()) {
                    imgFile.delete();
                }

                uri = Uri.fromFile(imgFile);

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, CAMERA_REQ_CODE);
            }
        });

        //选取图片
        findViewById(R.id.tv_pick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICKER_REQ_CODE);
            }
        });


        //取消
        findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCEL);
                finish();
            }
        });
    }
//    /**
//     * 发送聊天信息
//     *
//     * @param chatType 聊天类型
//     * @param content  聊天内容
//     */
//    private void sendChat(int chatType, String content) {
//
//
//        String server = getConfig().getServer() + NetConstant.SEND_CHAT;
//
//        SendChatRequest request = new SendChatRequest();
//        RequestHead head = new RequestHead();
//        SendChatRequest.RequestBody body = request.new RequestBody();
//
//        head.setAccessToken(getConfig().getToken());
//        head.setUserId(getConfig().getUserId());
//
//        body.setAlarmId(mAlarmId);
//        body.setType(chatType + "");
//        body.setUserName(getConfig().getName());
//
//
//        body.setContent(content);
//
//
//        request.setHead(head);
//        request.setBody(body);
//
//        NetTask netTask = new NetTask(server, request) {
//            @Override
//            protected void onResponse(NetTask task, String result) {
//                setResult(RESULT_OK, intent);
//                finish();
//            }
//        };
//
//        addTask(netTask);
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //如果不进行拍照，取消结束此页面
        if (resultCode != Activity.RESULT_OK) {
            setResult(RESULT_CANCEL);
            finish();
            return;
        }

        //拍摄照片
        if (requestCode == CAMERA_REQ_CODE) {
            String tag = getIntent().getStringExtra("tag");
            String dir = Utils.getTempPath();
            String fileName = tag + ".jpg";

            Bitmap bitmap = Utils.getBitmapBySize(dir + "/" + fileName, 600, 800);

            //删除之前的图片文件
            File file = new File(dir, fileName);
            if(file!=null)
            file.delete();
            Utils.saveBitmapWithQuality(bitmap, dir, fileName, 60);
            intent = new Intent();
            intent.putExtra("tag", tag);
            intent.putExtra(IntentConstant.INTENT_DATA, dir + "/" + fileName);
            setResult(PICK_TAG,intent);
            finish();

        }

        //选取照片
        if (requestCode == PICKER_REQ_CODE) {

            String tag = getIntent().getStringExtra("tag");

            String filePath = Utils.getRealPathFromUri(this.getApplicationContext(), data.getData());
            Bitmap bitmap = Utils.getBitmapBySize(filePath, 600, 800);

            //删除之前的图片文件
            String dir = Utils.getTempPath();
            String fileName = tag + ".jpg";

            File file = new File(dir, fileName);
            if(file!=null)
            file.delete();

            Utils.saveBitmapWithQuality(bitmap, dir, fileName, 50);
            intent = new Intent();
            intent.putExtra("tag", tag);
            intent.putExtra(IntentConstant.INTENT_DATA, dir + "/" + fileName);
            setResult(PICK_TAG,intent);
            finish();
            //requestUploadImage(Utils.imgToStrByBase64(dir + "/" + fileName));
        }
    }
}
