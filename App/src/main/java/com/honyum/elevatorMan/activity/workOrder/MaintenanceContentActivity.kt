package com.honyum.elevatorMan.activity.workOrder

import android.app.Dialog
import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.View.GONE
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.baidu.location.BDLocation
import com.baidu.location.BDLocationListener
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.honyum.elevatorMan.R
import com.honyum.elevatorMan.adapter.MaintenanceContentListAdapter
import com.honyum.elevatorMan.base.BaseActivityWraper
import com.honyum.elevatorMan.base.ListItemCallback
import com.honyum.elevatorMan.data.MaintenanceContenInfo
import com.honyum.elevatorMan.data.WorkOrderInfo
import com.honyum.elevatorMan.net.*
import com.honyum.elevatorMan.net.base.NetConstant
import com.honyum.elevatorMan.net.base.NetTask
import com.honyum.elevatorMan.net.base.RequestBean
import com.honyum.elevatorMan.utils.*
import kotlinx.android.synthetic.main.activity_maintenance_content.*
import kotlinx.android.synthetic.main.layout_pro_plan_item.*
import org.jetbrains.anko.act
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity
import java.text.SimpleDateFormat


class MaintenanceContentActivity : BaseActivityWraper(), ListItemCallback<MaintenanceContenInfo> {
    override fun performItemCallback(data: MaintenanceContenInfo?) {
        for (info in list_content) {
            if (info.state == "0") {
                btn_check.isChecked = false
                btn_check.text = "全选"
                break
            } else {
                btn_check.isChecked = true
                btn_check.text = "取消全选"
            }
        }
    }


    private var list_content: MutableList<MaintenanceContenInfo> = ArrayList<MaintenanceContenInfo>()

    private var list_maintItem_submit: ArrayList<MaintenanceContenInfo> = ArrayList<MaintenanceContenInfo>()

    private var adapter: MaintenanceContentListAdapter? = null

    var datas = java.util.ArrayList<MutableMap<String, Any>>()

    private var dialog: Dialog? = null

    var list_pic: ArrayList<String>? = java.util.ArrayList<String>()

    //var list_content:MutableList<String> = ArrayList<String>()
    var workOrderInfo: WorkOrderInfo = WorkOrderInfo()


    var is_pic_success: Int = 1

    override fun getLayoutID(): Int {
        return R.layout.activity_maintenance_content
    }

    override fun getTitleString(): String {
        return "维保内容"
    }

    var lat = 0.0
    var lng = 0.0

    private lateinit var mLocationClient: LocationClient
    private lateinit var mBDLocationListener: BDLocationListener

    private fun submitSignOut() {
        MapUtils(this).setPositionListener(object : MapUtils.PositionListener {
            override fun receivePosition(latitude: Double?, longitude: Double?) {
                if (latitude != null && longitude != null) {
                    lat = latitude
                    lng = longitude
                    addAttendanceMaintenance("1", workOrderInfo)
                }
            }

            override fun receivePositionAddress(address: String?) {
            }

        })
    }


    fun checkSubmit(): Boolean {
        for (maintItem in list_maintItem_submit) {
            if (maintItem.picNum > 0) {
                if (maintItem.datas == null) {
                    showToast("明细的图片尚未拍摄，无法提交！")
                    return false
                } else if (maintItem.datas.size < maintItem.picNum) {
                    showToast(maintItem.content + "需要上传" + maintItem.picNum + "张图片")
                    return false
                }
            }
        }
        return true
    }

    private fun isMainWorker():Boolean
    {
        return workOrderInfo.assistantId == config.userId
    }
    override fun initView() {
        var bundle = intent.extras
        workOrderInfo = bundle.getSerializable("workOrderInfo") as WorkOrderInfo
        if (isMainWorker())
        getMaintItem(workOrderInfo)
        // showDalog()

        getAttendance(workOrderInfo)

        if (workOrderInfo.realMaintainEndDate.isBlank())
            tv_submit.text = "签退"
        else if(workOrderInfo.realMaintainEndDate.isNotBlank()&&!isMainWorker())
            tv_submit.visibility =GONE
        btn_check.onClick {
            if (btn_check.isChecked) {
                btn_check.text = "取消全选"
                for (info in list_content) {
                    info.state = "1"
                }
                adapter?.notifyDataSetChanged()
            } else {
                btn_check.text = "全选"
                for (info in list_content) {
                    info.state = "0"
                }
                adapter?.notifyDataSetChanged()
            }
        }
        adapter = MaintenanceContentListAdapter(list_content, act)
        lv_mentenance_content.adapter = adapter
        tv_submit.onClick {
            list_content
                    .filter { it.state == "1" }
                    .forEach { list_maintItem_submit.add(it) }
            if (tv_submit.text == "签退") {
                if (isMainWorker()) {
                    if (checkSubmit())
                        submitSignOut()
                    return@onClick
                }
                else
                {
                    submitSignOut()
                    return@onClick
                }


            }

            /* if(list_maintItem_submit.size==0){
                 showToast("请选择维保项")
                 return@onClick
             }*/

            if (!checkSubmit()) {
                return@onClick
            }

            var hasImage = 0
            list_maintItem_submit.forEach {
                if (it.picNum > 0) {
                    hasImage += it.picNum
//                    if (SharedPreferenceUtil.get(it.id+config.userId,it.id)!=null) {
//                        it.datas = (SharedPreferenceUtil.get(it.id+config.userId,it.id) as java.util.ArrayList<Map<String, Any>>)
//                    }
                }

            }
            if (hasImage > 0) {
                var node: ImageNode? = ImageNode()
                var head = node
                for (maintItem in list_maintItem_submit.indices) {
                    if (list_maintItem_submit.get(maintItem).datas != null) {

                        var index = 0
                        if (list_maintItem_submit.get(maintItem).picNum > 0)
                            while (index < list_maintItem_submit.get(maintItem).picNum) {
                                //index图片个数 miantItem list下标
                                node?.img = Utils.imgToStrByBase64(list_maintItem_submit.get(maintItem).datas.get(index).get("path").toString())
                                node?.next = ImageNode()
                                node = node?.next
                                index++
//                            if (node == null) {
//                                node = ImageNode()
//                                node.img = Utils.imgToStrByBase64(datas.get(index).get("path").toString())
//                                node.next = ImageNode()
//                                node = node.next
//                            }
//                            else
//                            {
//                                node.img = Utils.imgToStrByBase64(datas.get(index).get("path").toString())
//                                node = node.next
//                            }
                            }
                    }
                }
                var upImage = UploadImageManager()
                upImage.getImages(this@MaintenanceContentActivity, head, { datas ->
                    var datas1 = datas
                    var mPicNum = 0
                    list_maintItem_submit.forEachIndexed { index, value ->
                        var urls = ""
                        if (value.picNum > 0)
                            mPicNum = value.picNum
                        while (mPicNum-- > 0) {
                            urls += datas1?.url + ","
                            datas1 = datas1?.next
                        }
                        if (urls.isNotEmpty()) {
                            urls = urls.subSequence(0, urls.length - 1).toString()
                            list_maintItem_submit[index].pic = urls
                        }

                    }
                    successMaintWorkOrder(workOrderInfo)

                })
            } else {
                successMaintWorkOrder(workOrderInfo)
            }
        }
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
    private fun requestUploadImage(base64: String?, index: Int, maintItem: Int, list_pic: ArrayList<String>?) {
        //showToast("**********"+base64)
        val task = object : NetTask(config.server + NetConstant.UP_LOAD_IMG,
                getImageRequestBean(config.userId, config.token, base64)) {
            override fun onResponse(task: NetTask, result: String) {
                val response = UploadImageResponse.getUploadImageResponse(result)
                if (response.head != null && response.head.rspCode == "0") {

                    val url = response.body.url
                    list_pic?.add(url)

                    list_maintItem_submit.get(maintItem).list_pic = list_pic

                    var pic: String = ""

                    for (pic_index in list_pic!!.indices) {
                        if (pic_index + 1 == list_pic?.size) {
                            pic = pic + list_pic?.get(pic_index)
                        } else {
                            pic = pic + list_pic?.get(pic_index) + ","
                        }
                    }
                    list_maintItem_submit.get(maintItem).pic = pic

                    Log.e("index", index.toString() + "=====" + maintItem)
                    //index图片个数 miantItem list下标
                    if (maintItem == list_maintItem_submit.size - 1) {
                        if (index == list_maintItem_submit.get(maintItem).datas.size - 1) {
                            successMaintWorkOrder(workOrderInfo)
                        }
                    }
                }
            }
        }
        addTask(task)
    }

    //提交
    private fun successMaintWorkOrder(workOrderInfo: WorkOrderInfo) {
        var maintItemInfoRequest = SuccessMaintWorkOrderInfoRequest()
        var body = maintItemInfoRequest.MaintWorkOrderInfoBody()
        body.workOrderId = workOrderInfo.id
        body.items = list_maintItem_submit
        maintItemInfoRequest.body = body

        maintItemInfoRequest.head = NewRequestHead().setaccessToken(config.token).setuserId(config.userId)
        var server = config.server + NetConstant.SUCCESS_MAINT_WORK_ORDER
        var netTask = object : NetTask(server, maintItemInfoRequest) {
            override fun onResponse(task: NetTask?, result: String?) {
                list_content.forEach {
                    SharedPreferenceUtil.cleanShardFile(it.id)
                }

                showToast("提交成功")
                finish()


                // showSignOutDalog(workOrderInfo)
                //addAttendanceMaintenance("1", workOrderInfo)
            }
        }
        addTask(netTask)
    }


    private fun showUpdateTimeDialog() {

    }

    //上传维保结果 URL_REPORT_MAIN
    private fun finishMain(workOrderInfo: WorkOrderInfo) {
        val sDateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        val date = sDateFormat.format(java.util.Date())
        var contractInfoRquest = ContractInfoRequest()
        var body = contractInfoRquest.ContractInfoBody()
        body.id = workOrderInfo.elevatorInfo.id
        body.mainTime = date
        contractInfoRquest.body = body
        contractInfoRquest.head = NewRequestHead().setaccessToken(config.token).setuserId(config.userId)
        var server = config.server + NetConstant.URL_REPORT_MAIN
        var netTask = object : NetTask(server, contractInfoRquest) {
            override fun onResponse(task: NetTask?, result: String?) {
                /* var response = SignInfoResponse.getSignInfoResponse(result)
                 if(response.body.size==0){
                     showSignDalog(workOrderInfo)
                 }*/
            }
        }
        addTask(netTask)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            var bundle: Bundle = data!!.extras
            datas = bundle.getSerializable("datas") as java.util.ArrayList<MutableMap<String, Any>>
            list_content[requestCode].datas = datas
            adapter?.notifyDataSetChanged()
        }
    }

    private fun getMaintItem(workOrderInfo: WorkOrderInfo) {
        var maintItemInfoRequest = MaintItemInfoRequest()
        var body = maintItemInfoRequest.MaintItemInfoInfoBody()
        body.workOrderId = workOrderInfo.id
        maintItemInfoRequest.body = body
        maintItemInfoRequest.head = NewRequestHead().setaccessToken(config.token).setuserId(config.userId)
        var server = config.server + NetConstant.GET_MAINT_ITEM
        var netTask = object : NetTask(server, maintItemInfoRequest) {
            override fun onResponse(task: NetTask?, result: String?) {
                var response = MaintenanceContentInfoResponse.getRepairInfoResponse(result)
                list_content = response.body
                if (list_content.isNotEmpty()) {
                    list_content.forEach {
                        if (SharedPreferenceUtil.get(it.id + config.userId, it.id) != null) {
                            it.datas = (SharedPreferenceUtil.get(it.id + config.userId, it.id) as java.util.ArrayList<Map<String, Any>>)
                        }
                    }
                    adapter = MaintenanceContentListAdapter(list_content, act)
                    lv_mentenance_content.adapter = adapter
                }
            }
        }
        addTask(netTask)
    }

    //获取签到状态
    private fun getAttendance(workOrderInfo: WorkOrderInfo) {
        var contractInfoRquest = ContractInfoRequest()
        var body = contractInfoRquest.ContractInfoBody()
        body.orderId = workOrderInfo.id
        contractInfoRquest.body = body
        contractInfoRquest.head = NewRequestHead().setaccessToken(config.token).setuserId(config.userId)
        var server = config.server + NetConstant.GET_ATTENDANCE_MAINTENANCE
        var netTask = object : NetTask(server, contractInfoRquest) {
            override fun onResponse(task: NetTask?, result: String?) {
                var response = SignInfoResponse.getSignInfoResponse(result)
                if (response.body.size == 0) {
                    showSignDalog(workOrderInfo)
                }
                /*list_content = response.body
                adapter = MaintenanceContentListAdapter(list_content,act)
                lv_mentenance_content.adapter = adapter*/
            }
        }
        addTask(netTask)
    }

    //签到
    private fun addAttendanceMaintenance(state: String, workOrderInfo: WorkOrderInfo) {
        var addAttendanceMaintance = AddAttendanceInfoRequest()
        var body = addAttendanceMaintance.SignInfo()
        body.orderId = workOrderInfo.id
        body.branchId = config.branchId
        body.state = state
        body.elevatorId = workOrderInfo.elevatorInfo.id
        body.lat = lat
        body.lng = lng
        addAttendanceMaintance.body = body
        addAttendanceMaintance.head = NewRequestHead().setaccessToken(config.token).setuserId(config.userId)
        var server = config.server + NetConstant.ADD_ATTENDANCE_MAINTENANCE
        var netTask = object : NetTask(server, addAttendanceMaintance) {
            override fun onResponse(task: NetTask?, result: String?) {
                if (state.equals("0")) {
                    showToast("签到成功")
                    if (dialog?.isShowing!!)
                        dialog?.dismiss()
                } else if (state.equals("1")) {
                    //finishMain(workOrderInfo)
                    showToast("签退成功")
                    dialog?.dismiss()
                    var response = UpdateWorkOrderInfoResponse.getResponse<UpdateWorkOrderInfoResponse>(result, UpdateWorkOrderInfoResponse::class.java)

                    if (isMainWorker()) {
                        if (response.body != null) {
                            showToast("请调整下一次维保工单信息！")
                            startActivity<UpdateWorkOrderDetailActivity>("workOrderInfo" to response?.body?.orderInfo!!, "persons" to response.body.persons)
                            tv_submit.text = "提交"
                        } else {
                            showToast("获取下一个工单信息失败！")
                        }
                    }
                    else
                    {
                        tv_submit.visibility = GONE
                        finish()
                    }


                    //finish()
                }
            }
        }
        addTask(netTask)
    }


    //签到对话框
    private fun showSignDalog(workOrderInfo: WorkOrderInfo) {
        var view: View = View.inflate(act, R.layout.sign_in_dialog, null)
        view.find<TextView>(R.id.tv_sign_in).onClick {

            MapUtils(this@MaintenanceContentActivity).setPositionListener(object : MapUtils.PositionListener {
                override fun receivePosition(latitude: Double?, longitude: Double?) {
                    if (latitude != null && longitude != null) {
                        lat = latitude
                        lng = longitude
                        addAttendanceMaintenance("0", workOrderInfo)
                    }
                }

                override fun receivePositionAddress(address: String?) {
                    showToast("定位成功！获取地址为：" + address)
                }

            })
            /* *//*val intent = Intent()
            intent.setClass(act, AddRepairWorkOrderActivity::class.java)
            startActivity(intent)*/
//            mLocationClient = LocationClient(applicationContext)
//            mBDLocationListener = MyBDLocationListener()
//            // 注册监听
//            mLocationClient.registerLocationListener(mBDLocationListener)
//
//            getLocation()
//
//            if (lat != 0.0 && lng !== 0.0) {
//                addAttendanceMaintenance("0", workOrderInfo)
//            }
            //showToast("签到成功")
            //dialog?.dismiss()
        }
        view.find<ImageView>(R.id.iv_cancel).onClick {
            finish()
        }


        dialog = Dialog(act)
        dialog!!.setCanceledOnTouchOutside(false)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setContentView(view)
        dialog?.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        dialog = null
    }

    //签退对话框
    private fun showSignOutDalog(workOrderInfo: WorkOrderInfo) {
        var view: View = View.inflate(act, R.layout.sign_out_dialog, null)
        view.find<TextView>(R.id.tv_sign_out).onClick {
            /* *//*val intent = Intent()
            intent.setClass(act, AddRepairWorkOrderActivity::class.java)
            startActivity(intent)*/
            mLocationClient = LocationClient(applicationContext)
            mBDLocationListener = MyBDLocationListener()
            // 注册监听
            mLocationClient.registerLocationListener(mBDLocationListener)

            getLocation()

            if (lat != 0.0 && lng !== 0.0) {
                //addAttendanceMaintenance("1", workOrderInfo)
            }

            //showToast("签到成功")
            //dialog?.dismiss()
        }
        dialog = Dialog(act)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setContentView(view)
        dialog?.show()
    }


    /**
     * 获得所在位置经纬度及详细地址
     */
    fun getLocation() {
        // 声明定位参数
        val option = LocationClientOption()
        option.locationMode = LocationClientOption.LocationMode.Hight_Accuracy// 设置定位模式 高精度
        option.coorType = "bd09ll"// 设置返回定位结果是百度经纬度 默认gcj02
        option.scanSpan = 5000// 设置发起定位请求的时间间隔 单位ms
        option.setIsNeedAddress(true)// 设置定位结果包含地址信息
        option.setNeedDeviceDirect(true)// 设置定位结果包含手机机头 的方向
        // 设置定位参数
        mLocationClient.locOption = option
        // 启动定位
        mLocationClient.start()
    }

    //获取位置信息
    private inner class MyBDLocationListener : BDLocationListener {
        override fun onReceiveLocation(bdLocation: BDLocation?) {
            // 非空判断
            if (bdLocation != null) {
                // 根据BDLocation 对象获得经纬度以及详细地址信息
                lat = bdLocation.latitude
                lng = bdLocation.longitude
                val address = bdLocation.addrStr
                if (mLocationClient.isStarted) {
                    // 获得位置之后停止定位
                    mLocationClient.stop()
                }
                //RequestProjectInfo(x,y)
            }
        }
    }

}
