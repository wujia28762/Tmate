package com.honyum.elevatorMan.activity.repair

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.os.Handler
import android.os.Message
import android.provider.MediaStore
import android.util.Base64
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.hanbang.netsdk.Log
import com.honyum.elevatorMan.R
import com.honyum.elevatorMan.adapter.FaultTypeListAdapter
import com.honyum.elevatorMan.adapter.GridViewAddImgesAdpter
import com.honyum.elevatorMan.base.BaseActivityWraper
import com.honyum.elevatorMan.base.ListItemCallback
import com.honyum.elevatorMan.data.ElevatorInfo1
import com.honyum.elevatorMan.data.FaultTypeInfo
import com.honyum.elevatorMan.data.LiftInfo
import com.honyum.elevatorMan.net.*
import com.honyum.elevatorMan.net.base.NetConstant
import com.honyum.elevatorMan.net.base.NetTask
import com.honyum.elevatorMan.net.base.RequestBean
import com.honyum.elevatorMan.utils.Utils
import kotlinx.android.synthetic.main.activity_repair_lift_detail.*
import net.bither.util.NativeUtil
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList


class RepairLiftDetailActivity : BaseActivityWraper(), ListItemCallback<File> {
    override fun performItemCallback(data: File?) {
        image.visibility = View.VISIBLE
        Glide.with(this)
                .load(data)
                .priority(Priority.HIGH)
                .into(image)
        image.onClick {
            it?.visibility = GONE
        }
    }

    private var type_array: Array<String> = arrayOf("普通", "紧急")

    private var fault_type_list: MutableList<FaultTypeInfo> = ArrayList<FaultTypeInfo>()

    private var lift_num: String = ""

    private val CAMERA_REQ_CODE = 101

    private var img1 = ""

    private var imageUrl = ""

    private var imageUrl1 = ""

    var bitmap1: Bitmap? = null

    var bitmap2: Bitmap? = null

    var code_index: Int = 0

    var type: String = "1"


    var datas = java.util.ArrayList<Map<String, Any>>()

    var elevatorInfo: ElevatorInfo1 = ElevatorInfo1()

    var gridViewAddImgesAdpter: GridViewAddImgesAdpter? = null

    var list_pic: MutableList<String>? = ArrayList<String>()

    // var list_pic:Array<String>? = null

    private var dialog: Dialog? = null
    private val PHOTO_REQUEST_CAREMA = 1// 拍照
    private val PHOTO_REQUEST_GALLERY = 2// 从相册中选择private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
    private var tempFile: File? = null
    private val IMAGE_DIR = Environment.getExternalStorageDirectory().toString() + "/gridview/"
    /* 头像名称 */
    private val PHOTO_FILE_NAME = "temp_photo.jpg"

    internal var handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == -0x55555556) {
                photoPath(msg.obj.toString())
            }
        }
    }

    override fun getLayoutID(): Int {
        return R.layout.activity_repair_lift_detail
    }

    override fun getTitleString(): String {
        return "报修单"
    }

    override fun initView() {

        spinner_repair_type.adapter = ArrayAdapter(this, R.layout.layout_spanner_item, type_array)

        var bundle = intent.extras

        var liftInfo: LiftInfo = bundle.getSerializable("lift") as LiftInfo

        lift_num = liftInfo.id

        tv_lift_num.text = liftInfo.num

        tv_user_name.text = config.userName

        tv_tel.text = config.tel

        tv_company_name.text = config.branchName

        requestFault()

        requestElevator(liftInfo.num)

        gridViewAddImgesAdpter = GridViewAddImgesAdpter(datas, this)

        grid_view.adapter = gridViewAddImgesAdpter

        grid_view.setOnItemClickListener(AdapterView.OnItemClickListener { adapterView, view, position, id ->
            showdialog()
        })

        /*iv_repair_pic1.onClick {
            var intent = Intent(act, PicturePickActivity::class.java)
            intent.putExtra("tag", "image1")
            startActivityForResult(intent, CAMERA_REQ_CODE)
        }
        iv_repair_pic2.onClick {
            var intent = Intent(act, PicturePickActivity::class.java)
            intent.putExtra("tag", "image2")
            startActivityForResult(intent, CAMERA_REQ_CODE)
        }*/

        tv_submit.onClick {
            if (et_description.text.toString()=="") {
                showToast("请输入故障描述")
                return@onClick
            }
//            if (datas.size == 0) {
//                showToast("请上传故障图片")
//                return@onClick
//            }

            /*requestUploadImage(bitmapToBase64(bitmap1), 1)
            requestUploadImage(bitmapToBase64(bitmap2), 2)*/

            if(datas.size>0)
            for (index in datas.indices) {
                requestUploadImage(Utils.imgToStrByBase64(datas.get(index).get("path").toString()), index)
            }
            else
               addRepairDetail(code_index, lift_num)

        }

        spinner_fault_type.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                //showToast(""+spinner_state.selectedItem)
                code_index = i
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {

            }
        }
    }

    //获取电梯信息
    private fun requestElevator(liftNum: String) {
        var elevatorInfoRequest = ElevatorInfoRequest()
        var body = ElevatorInfoRequest().ElevatorInfoBody()
        body.liftNum = liftNum
        elevatorInfoRequest.body = body
        elevatorInfoRequest.head = NewRequestHead().setaccessToken(config.token).setuserId(config.userId)
        var server = config.server + NetConstant.GET_ELEVATOR_LIFT_NUM
        var netTask = object : NetTask(server, elevatorInfoRequest) {
            override fun onResponse(task: NetTask?, result: String?) {
                var response: ElevatorInfoResponse = ElevatorInfoResponse.getElevatorInfoResponse(result)
                elevatorInfo = response.body
                if (elevatorInfo != null) {
                    tv_communityName.text = elevatorInfo.communityName
                    tv_company_name.text = elevatorInfo.branchName
                    // tv_branch_name.text = elevatorInfo.propertyName
                }
            }
        }
        addBackGroundTask(netTask)
    }


    /**
     * 选择图片对话框
     */
    fun showdialog() {
        val localView = LayoutInflater.from(this).inflate(
                R.layout.dialog_add_picture, null)
        val tv_camera = localView.findViewById(R.id.tv_camera) as TextView
        val tv_gallery = localView.findViewById(R.id.tv_gallery) as TextView
        val tv_cancel = localView.findViewById(R.id.tv_cancel) as TextView
        dialog = Dialog(this, R.style.custom_dialog)
        dialog!!.setContentView(localView)
        dialog!!.getWindow()!!.setGravity(Gravity.BOTTOM)
        // 设置全屏
        val windowManager = windowManager
        val display = windowManager.defaultDisplay
        val lp = dialog!!.getWindow()!!.getAttributes()
        lp.width = display.width // 设置宽度
        dialog!!.getWindow()!!.setAttributes(lp)
        dialog!!.show()
        tv_cancel.setOnClickListener { dialog!!.dismiss() }

        tv_camera.setOnClickListener {
            dialog!!.dismiss()
            // 拍照
            camera()
        }

        tv_gallery.setOnClickListener {
            dialog!!.dismiss()
            // 从系统相册选取照片
            gallery()
        }
    }

    /**
     * 拍照
     */
    private fun camera() {
        // 判断存储卡是否可以用，可用进行存储
        if (hasSdcard()) {

            val dir = File(IMAGE_DIR)
            if (!dir.exists()) {
                dir.mkdir()
            }
            tempFile = File(dir,
                    System.currentTimeMillis().toString() + "_" + PHOTO_FILE_NAME)
            //从文件中创建uri
            val uri = Uri.fromFile(tempFile)
            val intent = Intent()
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            intent.action = MediaStore.ACTION_IMAGE_CAPTURE
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
            startActivityForResult(intent, PHOTO_REQUEST_CAREMA)
        } else {
            Toast.makeText(this, "未找到存储卡，无法拍照！", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 判断sdcard是否被挂载
     */
    private fun hasSdcard(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }


    /**
     * 从相册获取2
     */
    fun gallery() {
        val intent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        /*super.onActivityResult(requestCode, resultCode, data)
        when(data?.getStringExtra("tag"))
        {
            "image1"->{
                val photo = data.getStringExtra("result")
                //val bitmap = Utils.getBitmapBySize(photo, 60, 80) ?: return
                bitmap1 = Utils.getBitmapBySize(photo,100,100)
                //iv_repair_pic1.setImageBitmap(bitmap1)
            }
            "image2"->{
                val photo = data.getStringExtra("result")
               // val bitmap = Utils.getBitmapBySize(photo, 60, 80) ?: return
                bitmap2 = Utils.getBitmapBySize(photo,100,100)
                //iv_repair_pic2.setImageBitmap(bitmap2)
            }
        }*/

        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PHOTO_REQUEST_GALLERY) {
                // 从相册返回的数据
                if (data != null) {
                    // 得到图片的全路径
                    val uri = data.data
                    val proj = arrayOf(MediaStore.Images.Media.DATA)
                    //好像是android多媒体数据库的封装接口，具体的看Android文档
                    val cursor = managedQuery(uri, proj, null, null, null)
                    //按我个人理解 这个是获得用户选择的图片的索引值
                    val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                    //将光标移至开头 ，这个很重要，不小心很容易引起越界
                    cursor.moveToFirst()
                    //最后根据索引值获取图片路径
                    val path = cursor.getString(column_index)
                    uploadImage(path)
                }

            } else if (requestCode == PHOTO_REQUEST_CAREMA) {
                if (resultCode != Activity.RESULT_CANCELED) {
                    // 从相机返回的数据
                    if (hasSdcard()) {
                        if (tempFile != null) {
                            uploadImage(tempFile!!.getPath())
                        } else {
                            Toast.makeText(this, "相机异常请稍后再试！", Toast.LENGTH_SHORT).show()
                        }
                        android.util.Log.i("images", "拿到照片path=" + tempFile!!.getPath())
                    } else {
                        Toast.makeText(this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }


    //获取故障类型
    private fun requestFault() {
        var contractInfoRequest = ContractInfoRequest()
        contractInfoRequest.head = NewRequestHead().setaccessToken(config.token).setuserId(config.userId)
        var server = config.server + NetConstant.GET_REPAIR_TYPE_LIST
        var netTask = object : NetTask(server, contractInfoRequest) {
            override fun onResponse(task: NetTask?, result: String?) {
                Log.e("contract_result", result + "==========")
                var response = FaultTypeInfoResponse.getContratInfoResponse(result)
                fault_type_list = response.body
                spinner_fault_type.adapter = FaultTypeListAdapter(fault_type_list, applicationContext)
            }
        }
        addTask(netTask)
    }

    //添加报修单
    private fun addRepairDetail(code_index: Int, num: String) {
        var code: String = fault_type_list.get(code_index).code
        //这里修改为不传故障类型
        code = ""
        var addRepairInfoRequest = AddRepairInfoRequest()
        var body = AddRepairInfoRequest().RepairInfoBody()
        body.elevatorId = num
        body.faultCode = code
        body.description = et_description.text.toString()
        // body.type = spinner_repair_type.selectedItem.toString()

        if (spinner_repair_type.selectedItem.toString().equals("普通")) {
            type = "1"
        } else if (spinner_repair_type.selectedItem.toString().equals("")) {
            type = "2"
        }
        body.type = type

        var pic: String = ""

        for (index in list_pic!!.indices) {
            if (index + 1 == list_pic?.size) {
                pic = pic + list_pic?.get(index)
            } else {
                pic = pic + list_pic?.get(index) + ","
            }
        }

        body.pic = pic

        addRepairInfoRequest.body = body
        addRepairInfoRequest.head = NewRequestHead().setaccessToken(config.token).setuserId(config.userId)
        var server = config.server + NetConstant.ADD_BAOXIU
        var netTask = object : NetTask(server, addRepairInfoRequest) {
            override fun onResponse(task: NetTask?, result: String?) {
                showToast("报修完成")
                finish()
            }
        }
        addTask(netTask)
    }


    private fun getImageRequestBean(userId: String, token: String, path: String?): RequestBean {
        val request = UploadRepairImageRequest()
        request.head = NewRequestHead().setuserId(userId).setaccessToken(token)
        var body = request.UploadRepairImageBody()
        body.img = path
        request.body = body
        return request
    }

    //上传图片
    private fun requestUploadImage(base64: String?, index: Int) {
        //showToast("**********"+base64)
        val task = object : NetTask(config.server + NetConstant.UP_LOAD_IMG,
                getImageRequestBean(config.userId, config.token, base64)) {
            override fun onResponse(task: NetTask, result: String) {
                val response = UploadImageResponse.getUploadImageResponse(result)
                if (response.head != null && response.head.rspCode == "0") {
                    val url = response.body.url
                    list_pic?.add(url)
                    if (index == datas.size - 1) {
                        addRepairDetail(code_index, lift_num)
                    }
                }
            }
        }
        addTask(task)
    }


    private fun bitmapToBase64(bitmap: Bitmap?): String? {
        var result: String? = null
        var baos: ByteArrayOutputStream? = null
        try {
            if (bitmap != null) {
                baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)

                baos!!.flush()
                baos!!.close()

                val bitmapBytes = baos!!.toByteArray()
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                if (baos != null) {
                    baos!!.flush()
                    baos!!.close()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        return result
    }

    /**
     * 上传图片
     *
     * @param path
     */
    private fun uploadImage(path: String) {
        object : Thread() {
            override fun run() {
                if (File(path).exists()) {
                    android.util.Log.d("images", "源文件存在" + path)
                } else {
                    android.util.Log.d("images", "源文件不存在" + path)
                }
                val dir = File(IMAGE_DIR)
                if (!dir.exists()) {
                    dir.mkdir()
                }
                val file = File(dir.toString() + "/temp_photo" + System.currentTimeMillis() + ".jpg")
                NativeUtil.compressBitmap(path, file.getAbsolutePath(), 50)
                if (file.exists()) {
                    android.util.Log.d("images", "压缩后的文件存在" + file.getAbsolutePath())
                } else {
                    android.util.Log.d("images", "压缩后的不存在" + file.getAbsolutePath())
                }
                val message = Message()
                message.what = -0x55555556
                message.obj = file.getAbsolutePath()
                handler.sendMessage(message)
            }
        }.start()
    }

    fun photoPath(path: String) {
        val map = HashMap<String, Any>()
        map.put("path", path)
        datas.add(map)
        gridViewAddImgesAdpter?.notifyDataSetChanged()
    }

}
