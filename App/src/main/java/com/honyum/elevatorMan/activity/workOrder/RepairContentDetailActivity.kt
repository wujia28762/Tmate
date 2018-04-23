package com.honyum.elevatorMan.activity.workOrder

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.os.Handler
import android.os.Message
import android.provider.MediaStore
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import com.honyum.elevatorMan.R
import com.honyum.elevatorMan.adapter.GridViewAddImgesAdpter
import com.honyum.elevatorMan.base.BaseActivityWraper
import kotlinx.android.synthetic.main.activity_repair_lift_detail.*
import net.bither.util.NativeUtil
import java.io.File
import java.util.HashMap
import kotlin.collections.ArrayList

class RepairContentDetailActivity : BaseActivityWraper() {

    var gridViewAddImgesAdpter: GridViewAddImgesAdpter? = null

    var list_pic:MutableList<String>? = ArrayList<String>()

    // var list_pic:Array<String>? = null
    var datas = java.util.ArrayList<Map<String, Any>>()

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
        return R.layout.activity_repair_content_detail
    }

    override fun getTitleString(): String {
        return "维修"
    }

    override fun initView() {
        gridViewAddImgesAdpter = GridViewAddImgesAdpter(datas, this)

        grid_view.adapter = gridViewAddImgesAdpter

        grid_view.setOnItemClickListener(AdapterView.OnItemClickListener {
            adapterView, view, position, id -> showdialog()
        })
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

}
