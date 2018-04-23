package com.honyum.elevatorMan.activity.workOrder;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.baidu.navisdk.util.common.StringUtils;
import com.honyum.elevatorMan.R;
import com.honyum.elevatorMan.activity.common.CommonPicturePickActivity;
import com.honyum.elevatorMan.adapter.FaultTypeListAdapter;
import com.honyum.elevatorMan.base.BaseFragmentActivity;
import com.honyum.elevatorMan.constant.IntentConstant;
import com.honyum.elevatorMan.data.FaultTypeInfo;
import com.honyum.elevatorMan.data.WorkOrderInfo;
import com.honyum.elevatorMan.net.ContractInfoRequest;
import com.honyum.elevatorMan.net.FaultTypeInfoResponse;
import com.honyum.elevatorMan.net.MaintenanceServiceFinishRequest;
import com.honyum.elevatorMan.net.UploadImageRequest;
import com.honyum.elevatorMan.net.UploadImageResponse;
import com.honyum.elevatorMan.net.base.NetConstant;
import com.honyum.elevatorMan.net.base.NetTask;
import com.honyum.elevatorMan.net.base.NewRequestHead;
import com.honyum.elevatorMan.net.base.RequestBean;
import com.honyum.elevatorMan.net.base.Response;
import com.honyum.elevatorMan.utils.ImageNode;
import com.honyum.elevatorMan.utils.UploadImageManager;
import com.honyum.elevatorMan.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Star on 2017/6/10.
 */


public class MaintenanceFinishActivity extends BaseFragmentActivity {

    private ImageView mImageView1;
    private ImageView mImageView2;
    private ImageView mImageView3;
    private String currId = "";
    private TextView tv_fix_complete;
    ImageNode first = new ImageNode();

    ImageNode sec = new ImageNode();

    ImageNode thir = new ImageNode();
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv_result;

    private Spinner spinner_fault_type;
    private String bi = "";
    private String ai = "";
    private String ci = "";
    private String mPublicPath = "";
    //临时文件，处理照片的压缩
    private String mTempFile = "";

    private WorkOrderInfo workOrderInfo;
    private EditText etAppearance;
    private EditText etReason;
    private EditText etDeal;
    private EditText etProtect;
    private int code_index = 0;
    private String key2;
    private String key;
    private String key3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maint_result);
        initPublicPath();
        initTitle();
        initView();
        requestFault();

    }

    //获取故障类型
    private void requestFault() {
        ContractInfoRequest contractInfoRequest = new ContractInfoRequest();
        contractInfoRequest.setHead(new NewRequestHead().setaccessToken(getConfig().getToken()).setuserId(getConfig().getUserId()));
        String server = getConfig().getServer() + NetConstant.GET_REPAIR_TYPE_LIST;
//        NetTask netTask = new NetTask(server, contractInfoRequest) {
//            override fun onResponse(task: NetTask?, result: String?) {
//                Log.e("contract_result", result + "==========")
//                var response = FaultTypeInfoResponse.getContratInfoResponse(result)
//                fault_type_list = response.body
//                spinner_fault_type.adapter = FaultTypeListAdapter(fault_type_list, act)
//            }
//        }
        NetTask netTask = new NetTask(server, contractInfoRequest) {
            @Override
            protected void onResponse(NetTask task, String result) {
                FaultTypeInfoResponse response = FaultTypeInfoResponse.getContratInfoResponse(result);
                fault_type_list = response.getBody();
                spinner_fault_type.setAdapter(new FaultTypeListAdapter(fault_type_list, MaintenanceFinishActivity.this, R.layout.layout_fault_type_spanner_item));
            }
        };
        addTask(netTask);
    }

    private List<FaultTypeInfo> fault_type_list = new ArrayList<>();

    /**
     * 初始化照片存储路径
     *
     * @param
     */
    private void initPublicPath() {
        Intent it = getIntent();
        //currId = it.getStringExtra("Id");
        workOrderInfo = (WorkOrderInfo) it.getSerializableExtra(IntentConstant.INTENT_DATA);
        currId = workOrderInfo.getId();
        String sdPath = Utils.getSdPath();

        if (null == sdPath) {
            return;
        }

        //LiftInfo liftInfo = (LiftInfo) intent.getSerializableExtra("lift");
        mPublicPath = sdPath + "/chorstar/maintenancetask/" + currId + "/";
    }

    /**
     * 初始化标题
     */
    private void initTitle() {

        initTitleBar("维修结果", R.id.title_service_result,
                R.drawable.back_normal, backClickListener);
    }

    /**
     * 调用系统相机之后返回
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data==null)
            return;
        String dirPath = data.getStringExtra(IntentConstant.INTENT_DATA);

        if (StringUtils.isEmpty(dirPath)) {
            showToast("请检查SD卡");
            return;
        }
        if (1 == requestCode) {
            saveImageView(requestCode, dirPath,mImageView1,R.id.btn_del_1,first);
            //requestUploadImage(imgPath1, imgPath1, requestCode);

        } else if (2 == requestCode) {
            saveImageView(requestCode, dirPath,mImageView2,R.id.btn_del_2,sec);
//            imageView = mImageView2;
//            delButton = (Button) findViewById(R.id.btn_del_2);
//            delButton.setTag(R.id.index,requestCode);
//            showImage(requestCode,dirPath, imageView, delButton);
//            String imgPath2 = (String) mImageView2.getTag(R.id.file_path);
//            Log.e("TAG", "onActivityResult: " + imgPath2);
//            //requestUploadImage(imgPath2, imgPath2, requestCode);
//            sec.setImg(Utils.imgToStrByBase64(imgPath2));
//            bi = sec.getImg();

        } else if (3 == requestCode) {
            saveImageView(requestCode, dirPath,mImageView3,R.id.btn_del_3,thir);
//            imageView = mImageView3;
//            delButton = (Button) findViewById(R.id.btn_del_3);
//            delButton.setTag(R.id.index,requestCode);
//            showImage(dirPath, imageView, delButton);
//            String imgPath3 = (String) mImageView3.getTag(R.id.file_path);
//            Log.e("TAG", "onActivityResult: " + imgPath3);
//            thir.setImg(Utils.imgToStrByBase64(imgPath3));
//            //requestUploadImage(imgPath3, imgPath3, requestCode);
//            ci = thir.getImg();
        }


    }

    /**
     * 显示图片，并存入缓存，存取规则：KEY = requestCode+getConfig().getUserId()+workOrderInfo.getId()
     * @param requestCode
     * @param dirPath
     * @param
     * @param deleIndex
     * @param node
     */
    private void saveImageView(int requestCode, String dirPath,ImageView imageView1,int deleIndex,ImageNode node) {
        Button delButton;
        delButton = (Button) findViewById(deleIndex);
        delButton.setTag(R.id.index,requestCode);
        showImage(requestCode,dirPath, imageView1, delButton);
        String imgPath1 = (String) imageView1.getTag(R.id.file_path);
        saveImageData(requestCode+getConfig().getUserId()+workOrderInfo.getId(),imgPath1);
        Log.e("TAG", "onActivityResult: " + imgPath1);
        node.setImg(Utils.imgToStrByBase64(imgPath1));
        switch(requestCode)
        {
            case 1: ai =node.getImg();
            break;
            case 2:bi = node.getImg();
            break;
            case 3:ci = node.getImg();
            break;
        }
    }

    private void requestMaintOrderProcessWorkerFinish() {
        NetTask task = new NetTask(getConfig().getServer() + NetConstant.URL_MAINT_TASK_FINISH,
                getRequestBean(getConfig().getUserId(), getConfig().getToken())) {
            @Override
            protected void onResponse(NetTask task, String result) {
                Response response = Response.getResponse(result);
                if (response.getHead() != null && response.getHead().getRspCode().equals("0")) {
                    showAppToast(getString(R.string.sucess));
                    finish();
                }
                //Log.e("!!!!!!!!!!!!!!", "onResponse: "+ msInfoList.get(0).getMainttypeId());
            }
            //Log.e("!!!!!!!!!!!!!!", "onResponse: "+ msInfoList.get(0).getMainttypeId());;
        };
        addTask(task);
    }

    private RequestBean getRequestBean(String userId, String token) {

        MaintenanceServiceFinishRequest request = new MaintenanceServiceFinishRequest();
        request.setHead(new NewRequestHead().setaccessToken(token).setuserId(userId));
        //request.setBody(request.new MaintenanceServiceFinishBody().setMaintOrderProcessId(currId).setMaintUserFeedback(et_remark.getText().toString().trim()).setAfterImg(ai).setBeforeImg(bi).setAfterImg1(ci));
        return request;
    }

    /**
     * 显示拍摄的照片并
     *
     * @param dirPath
     * @param imageView
     * @param delButton
     */
    private void showImage(int index,String dirPath, final ImageView imageView,
                           final Button delButton) {

//        Bitmap bitmap = Utils.getBitmapBySize(dirPath, 600, 800);
//
//        //清理临时目录
//        initTempFile();
//
//        FileOutputStream outputStream = null;
//
//        File dirFile = new File(dirPath);
//
//        //如果目录存在，则删除，重新创建，保证同一个电梯在同一时间同一个照片位置只有一个照片目录
//        if (dirFile.exists()) {
//            Utils.deleteFiles(dirFile);
//        } else {
//            dirFile.mkdirs();
//        }
//
//        String fileName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".jpg";
//
//        final String filePath = dirPath + fileName;
//
//        try {
//            outputStream = new FileOutputStream(filePath);
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 30, outputStream);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } finally {
//
//            try {
//                outputStream.flush();
//                outputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }


        Bitmap showImage = Utils.getBitmapBySize(dirPath, 120, 160);
        imageView.setImageBitmap(showImage);
        imageView.setTag(R.id.file_path, dirPath);
        imageView.setOnClickListener(overViewClickListener);

        delButton.setVisibility(View.VISIBLE);
        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(dirPath);
                file.delete();

                delButton.setVisibility(View.GONE);

                if ((int) delButton.getTag(R.id.index) == 1) {
                    ai = "";
                    deleteImageData("1"+getConfig().getUserId()+workOrderInfo.getId());

                } else if ((int) delButton.getTag(R.id.index) == 2) {
                    bi = "";
                    deleteImageData("2"+getConfig().getUserId()+workOrderInfo.getId());
                } else if ((int) delButton.getTag(R.id.index) == 3) {
                    ci = "";
                    deleteImageData("3"+getConfig().getUserId()+workOrderInfo.getId());
                }
                imageView.setTag(R.id.file_path, "");
                imageView.setImageResource(R.drawable.defaut_image);
                imageView.setOnClickListener(imageViewClickListener);
            }
        });
    }


    /**
     * 将图片转换成BASE64
     *
     * @param imgPath
     * @return
     */
    public static String imgToStrByBase64(String imgPath) {
        if (StringUtils.isEmpty(imgPath)) {
            return "";
        }
        File file = new File(imgPath);
        if (!file.exists()) {
            return "";
        }

        Bitmap bitmap = BitmapFactory.decodeFile(imgPath);

        return Utils.imgToStrByBase64(bitmap);
    }

    UploadImageManager upimage;

    /**
     * 初始化view
     */
    private void initView() {

        spinner_fault_type = (Spinner) findViewById(R.id.spinner_fault_type);
        spinner_fault_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                code_index = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        //tv_result = (TextView) findViewById(R.id.tv_result);
        tv1.setText("检验合格证");
        tv2.setText("维修后1");
        tv3.setText("维修后2");
        //tv_result.setText("本次维修结果");

        upimage = new UploadImageManager();

        first.setNext(sec);
        sec.setNext(thir);

        mImageView1 = (ImageView) findViewById(R.id.iv_image1);
        mImageView2 = (ImageView) findViewById(R.id.iv_image2);
        mImageView3 = (ImageView) findViewById(R.id.iv_image3);
        mImageView1.setOnClickListener(imageViewClickListener);
        mImageView2.setOnClickListener(imageViewClickListener);
        mImageView3.setOnClickListener(imageViewClickListener);
        mImageView1.setTag(R.id.index, 1);
        mImageView2.setTag(R.id.index, 2);
        mImageView3.setTag(R.id.index, 3);

        etAppearance = (EditText) findViewById(R.id.et_appearance);
        etReason = (EditText) findViewById(R.id.et_reason);
        etDeal = (EditText) findViewById(R.id.et_deal);
        etProtect = (EditText) findViewById(R.id.et_protect);

        button1= (Button) findViewById(R.id.btn_del_1);
        button2 = (Button) findViewById(R.id.btn_del_2);
        button3 = (Button) findViewById(R.id.btn_del_3);


        button1.setTag(R.id.index, 1);
        button2.setTag(R.id.index, 2);
        button3.setTag(R.id.index, 3);

        key = "1" + getConfig().getUserId() + workOrderInfo.getId();
        String path1 =  getImageData(key);
        key2 = "2" + getConfig().getUserId() + workOrderInfo.getId();
        String path2=  getImageData(key2);
        key3 = "3" + getConfig().getUserId() + workOrderInfo.getId();
        String path3=  getImageData(key3);

        if (!TextUtils.isEmpty(path1))
        loadPicture(path1, mImageView1, button1);
        if (!TextUtils.isEmpty(path2))
        loadPicture(path2, mImageView2, button2);
        if (!TextUtils.isEmpty(path3))
        loadPicture(path3, mImageView3, button3);


        tv_fix_complete = (TextView) findViewById(R.id.tv_fix_complete);
        tv_fix_complete.setText("提交维修结果");
        tv_fix_complete.setOnClickListener((View v) -> {
            if (ai.equals("")|| bi.equals("") || ci.equals("")) {
                //success_repair_work_order(workOrderInfo);
                showAppToast("请拍摄完整的图片！");
                return;
            }
            upimage.getImages(this, first, (ImageNode datas) ->
                    {
                        ai = first.getUrl();
                        bi = sec.getUrl();
                        ci = thir.getUrl();
                        success_repair_work_order(workOrderInfo);
                        return null;
                    }
            );

            //requestMaintOrderProcessWorkerFinish();
        });
    }
    Button button1;
    Button button2;
    Button button3;
    private void cleanImageCache()
    {

        deleteImageData(key);
        deleteImageData(key2);
        deleteImageData(key3);
    }
    private void success_repair_work_order(WorkOrderInfo workOrderInfo) {
        String code = fault_type_list.get(code_index).getName();
        ContractInfoRequest contractInfoRequest = new ContractInfoRequest();
        ContractInfoRequest.ContractInfoBody body = contractInfoRequest.new ContractInfoBody();
        body.setWorkOrderId(workOrderInfo.getId());
        body.setPic(ai + "," + bi + "," + ci);
        body.setAppearance(etAppearance.getText().toString());
        body.setReason(etReason.getText().toString());
        body.setPreventiveMeasure(etProtect.getText().toString());
        body.setProcessResult(etDeal.getText().toString());
        body.setFaultCode(code);
        //body.setResult(et_remark.getText().toString());
        contractInfoRequest.setBody(body);
        contractInfoRequest.setHead(new NewRequestHead().setaccessToken(getConfig().getToken()).setuserId(getConfig().getUserId()));
        String server = getConfig().getServer() + NetConstant.SUCCESS_BAOXIU_WORK_ORDER;
        NetTask netTask = new NetTask(server, contractInfoRequest) {
            @Override
            protected void onResponse(NetTask task, String result) {
                showToast("成功！");
                cleanImageCache();
                finish();
            }
        };
        addTask(netTask);
    }

    /**
     * 加载之前拍摄的照片
     *
     *
     * @param imageView
     * @param delButton
     */
    private void loadPicture(String filePath, final ImageView imageView, final Button delButton) {
//        File file = new File(dirPath);
//        if (!file.exists()) {
//            return;
//        }
//        File[] files = file.listFiles();
//        if (null == files || 0 == files.length) {
//            return;
//        }
//
//        final String filePath = files[0].getAbsolutePath();

        Bitmap bitmap = Utils.getBitmapBySize(filePath, 60, 80);
        imageView.setImageBitmap(bitmap);
        imageView.setTag(R.id.file_path, filePath);
        if ((int) imageView.getTag(R.id.index) == 1) {
            ai = filePath;
            first.setImg(Utils.imgToStrByBase64(filePath));
        } else if ((int) imageView.getTag(R.id.index) == 2) {
            bi = filePath;
            sec.setImg(Utils.imgToStrByBase64(filePath));
        } else if ((int) imageView.getTag(R.id.index) == 3) {
            ci = filePath;
            thir.setImg(Utils.imgToStrByBase64(filePath));
        }
        imageView.setOnClickListener(overViewClickListener);

        delButton.setVisibility(View.VISIBLE);
        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(filePath);
                file.delete();

                delButton.setVisibility(View.GONE);

                if ((int) delButton.getTag(R.id.index) == 1) {
                    ai = "";
                    first.setImg("");
                    deleteImageData(key);
                } else if ((int) delButton.getTag(R.id.index) == 2) {
                    bi = "";
                    sec.setImg("");
                    deleteImageData(key2);
                } else if ((int) delButton.getTag(R.id.index) == 3) {
                    ci = "";
                    thir.setImg("");
                    deleteImageData(key3);
                }
                imageView.setImageResource(R.drawable.defaut_image);
                imageView.setOnClickListener(imageViewClickListener);
            }
        });
    }

    /**
     * 拍照之后点击照片查看照片预览
     */
    private View.OnClickListener overViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v instanceof ImageView) {
                String filePath = (String) v.getTag(R.id.file_path);
                Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                ((ImageView) findViewById(R.id.iv_overview)).setImageBitmap(bitmap);

                final LinearLayout llFullScreen = (LinearLayout) findViewById(R.id.ll_full_screen);
                llFullScreen.setVisibility(View.VISIBLE);
                llFullScreen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        llFullScreen.setVisibility(View.GONE);
                    }
                });

            }
        }
    };

    /**
     * 初始化临时文件
     */
    private void initTempFile() {

        String sdPath = Utils.getSdPath();

        if (null == sdPath) {
            return;
        }

        String tempPath = sdPath + "/chorstar/maintenancetask/temp/";
        mTempFile = tempPath + "original.jpg";
        File tempFile = new File(mTempFile);

        //文件存在，删除
        if (tempFile.exists()) {
            tempFile.delete();
            return;
        }

        //目录不存在，创建目录
        File pathFile = new File(tempPath);
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }
    }

    /**
     * 点击照片位置时进行拍照
     */
    private View.OnClickListener imageViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //初始化临时目录
            initTempFile();
            Intent intent = new Intent(MaintenanceFinishActivity.this, CommonPicturePickActivity.class);
            intent.putExtra("tag", "maintfinish"+v.getTag(R.id.index));
            intent.putExtra("CameraOnly",true);
            startActivityForResult(intent, (Integer) v.getTag(R.id.index));


//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//            File out = new File(mTempFile);
//            Uri uri = Uri.fromFile(out);
//
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//            startActivityForResult(intent, (Integer) v.getTag(R.id.index));
        }
    };


    private RequestBean getImageRequestBean(String userId, String token, String path) {
        UploadImageRequest request = new UploadImageRequest();
        request.setHead(new NewRequestHead().setuserId(userId).setaccessToken(token));
        request.setBody(request.new UploadImageBody().setImg(path));
        return request;
    }


    private void requestUploadImage(final String path, final String realPath, final int request) {
        NetTask task = new NetTask(getConfig().getServer() + NetConstant.UP_LOAD_IMG,
                getImageRequestBean(getConfig().getUserId(), getConfig().getToken(), path)) {
            @Override
            protected void onResponse(NetTask task, String result) {
                UploadImageResponse response = UploadImageResponse.getUploadImageResponse(result);
                if (response.getHead() != null && response.getHead().getRspCode().equals("0")) {
                    String url = response.getBody().getUrl();
                    showAppToast(getString(R.string.sucess));
                    if (request == 1) {
                        ai = url;
                        saveImageData(realPath, url);
                    } else if (request == 2) {
                        saveImageData(realPath, url);
                        bi = url;
                    } else if (request == 3) {
                        saveImageData(realPath, url);
                        ci = url;
                    }
                }
                //Log.e("!!!!!!!!!!!!!!", "onResponse: "+ msInfoList.get(0).getMainttypeId());

            }
        };
        addTask(task);
    }
}
