package com.honyum.elevatorMan.fragment.workOorder

import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.*
import com.baidu.navisdk.util.common.StringUtils
import com.honyum.elevatorMan.R
import com.honyum.elevatorMan.adapter.MaintenanceContentListAdapter
import com.honyum.elevatorMan.adapter.MaintenanceContentResultAdapter
import com.honyum.elevatorMan.base.BaseFragment
import com.honyum.elevatorMan.base.BaseFragmentActivity
import com.honyum.elevatorMan.constant.Constant
import com.honyum.elevatorMan.data.MaintenanceContenInfo
import com.honyum.elevatorMan.data.MaintenanceTaskInfo
import com.honyum.elevatorMan.data.WorkOrderInfo
import com.honyum.elevatorMan.net.MaintenanceContentInfoResponse
import com.honyum.elevatorMan.net.NewRequestHead
import com.honyum.elevatorMan.net.base.NetConstant
import com.honyum.elevatorMan.net.base.NetTask
import com.honyum.elevatorMan.net.getMaintResultRequest
import com.honyum.elevatorMan.utils.ToastUtils
import com.honyum.elevatorMan.utils.Utils
import kotlinx.android.synthetic.main.activity_maintenance_content.*
import org.jetbrains.anko.act
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.act
import java.lang.ref.WeakReference

class WorkOrderEnclosureFragment : Fragment() {
    private var workOrderInfo: WorkOrderInfo = WorkOrderInfo()
    private var tv_fix_complete: TextView? = null
    private var et_remark: EditText? = null
    private lateinit var list:ListView

    private var list_content:MutableList<MaintenanceContenInfo> = ArrayList<MaintenanceContenInfo>()
    private var iv_before_image: ImageView? = null
    private var iv_after_image: ImageView? = null
    private var iv_after_image1: ImageView? = null

    private var llFullScreen: LinearLayout? = null
    private var ivOverview: ImageView? = null
    //var response: ContractInfoDetailResponse = ContractInfoDetailResponse()
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var bundle: Bundle = arguments
        workOrderInfo = bundle.getSerializable("workOrderInfo") as WorkOrderInfo

        var view:View
        if (workOrderInfo.bizType=="2") {
            view = inflater!!.inflate(R.layout.maint_result, null)
            initView(view)
        }
        else
        {
            view = inflater!!.inflate(R.layout.fragment_maint_result, null)
            initView1(workOrderInfo,view)
        }
        return view
    }
    private var adapter:MaintenanceContentResultAdapter ?= null
    private fun initView1(workOrderInfo:WorkOrderInfo,view: View?) {
        if ("success" == workOrderInfo.statusCode)
        getMaintResult(workOrderInfo)
        else {
            view?.find<TextView>(R.id.tip)?.visibility = VISIBLE
            view?.find<ListView>(R.id.lv_mentenance_content)?.visibility = GONE
        }

    }

    private fun getMaintResult(workOrderInfo:WorkOrderInfo)
    {
        var request = getMaintResultRequest()
        var body = request.getMaintResultBody()
        body.workOrderId = workOrderInfo.id
        var head = NewRequestHead().setaccessToken((act as BaseFragmentActivity).config.token).setuserId((act as BaseFragmentActivity).config.userId)

        request.body = body
        request.head = head

        var netTask = object : NetTask((act as BaseFragmentActivity).config.server+NetConstant.GETWORKORDERMAINTAINITEMBYWORKORDERID,request) {
            override fun onResponse(task: NetTask?, result: String?) {
                var response = MaintenanceContentInfoResponse.getRepairInfoResponse(result)
                list = view?.find(R.id.lv_mentenance_content)!!
                list_content = response.body
                adapter = MaintenanceContentResultAdapter(list_content,act)
                lv_mentenance_content.adapter = adapter
                }

        }
        (act as BaseFragmentActivity).addBackGroundTask(netTask)
    }

    private var pics: List<String>? = null

    private lateinit var et_appearance:EditText
    private lateinit var et_reason:EditText
    private lateinit var et_deal:EditText
    private lateinit var et_protect:EditText
    private lateinit var faultType: Spinner

    private lateinit var tvFaultType: TextView

    private fun initView(view: View) {

        if(!workOrderInfo.statusCode.equals("success"))
        {

            view.findViewById(R.id.title_service_result).visibility = GONE
            view.findViewById(R.id.line).visibility = VISIBLE
            view.findViewById(R.id.tip).visibility = VISIBLE
            view.find<ScrollView>(R.id.scroll_view).visibility = GONE
            return
        }
        view.findViewById(R.id.title_service_result).visibility = GONE
        view.findViewById(R.id.line).visibility = VISIBLE
        pics = workOrderInfo.pic?.split(",")
        et_remark = view?.findViewById(R.id.et_remark) as EditText
        et_remark?.setText(workOrderInfo.result)
        et_remark?.isFocusable = false
        faultType = view?.find(R.id.spinner_fault_type)
        tvFaultType = view?.find(R.id.tv_fault_type)
        faultType.visibility = GONE
        tvFaultType.visibility = VISIBLE
        tvFaultType.text = workOrderInfo.faultCode


        llFullScreen = view?.findViewById(R.id.ll_full_screen) as LinearLayout
        llFullScreen?.setOnClickListener(View.OnClickListener { llFullScreen?.setVisibility(View.GONE) })
        ivOverview = view?.findViewById(R.id.iv_overview) as ImageView
        tv_fix_complete = view?.findViewById(R.id.tv_fix_complete) as TextView
        tv_fix_complete?.setVisibility(View.GONE)



        iv_before_image = view?.findViewById(R.id.iv_image1) as ImageView
        iv_after_image = view?.findViewById(R.id.iv_image2) as ImageView
        iv_after_image1 = view?.findViewById(R.id.iv_image3) as ImageView
        iv_before_image?.setDrawingCacheEnabled(true)
        iv_after_image?.setDrawingCacheEnabled(true)
        iv_after_image1?.setDrawingCacheEnabled(true)

        et_appearance = (view.findViewById(R.id.et_appearance) as EditText)
        et_appearance.setText(workOrderInfo.appearance.toString())
        et_appearance.isFocusable = false

        et_deal = (view.findViewById(R.id.et_deal) as EditText)
        et_deal.setText(workOrderInfo.processResult.toString())
        et_deal.isFocusable = false

        et_protect = (view.findViewById(R.id.et_protect) as EditText)
        et_protect.setText(workOrderInfo.preventiveMeasure.toString())
        et_protect.isFocusable = false

        et_reason = (view.findViewById(R.id.et_reason) as EditText)
        et_reason.setText(workOrderInfo.reason.toString())
        et_reason.isFocusable = false

//        (view.findViewById(R.id.et_appearance) as EditText).isFocusable= false
//        (view.findViewById(R.id.et_reason) as EditText).setText(workOrderInfo.reason)
//        (view.findViewById(R.id.et_reason) as EditText).isFocusable= false
//        (view.findViewById(R.id.et_deal) as EditText).setText(workOrderInfo.processResult)
//        (view.findViewById(R.id.et_deal) as EditText).isFocusable= false
//        (view.findViewById(R.id.et_protect) as EditText).setText(workOrderInfo.preventiveMeasure)
//        (view.findViewById(R.id.et_protect) as EditText).isFocusable= false


        if (workOrderInfo.bizType == "1")
        {
            view.find<ScrollView>(R.id.scroll_view).visibility = GONE
            view.find<LinearLayout>(R.id.ll_maint).visibility = VISIBLE
        }
        else
        {
            view.find<LinearLayout>(R.id.ll_maint).visibility = GONE
            view.find<ScrollView>(R.id.scroll_view).visibility = VISIBLE

        }


        if (pics?.size==3) {
            GetPicture(pics?.get(0)!!, iv_before_image!!).execute()
            GetPicture(pics?.get(1)!!, iv_after_image!!).execute()
            GetPicture(pics?.get(2)!!, iv_after_image1!!).execute()
            iv_before_image?.setOnClickListener(View.OnClickListener { showPreviewImage(iv_before_image!!) })
            iv_after_image?.setOnClickListener(View.OnClickListener { showPreviewImage(iv_after_image!!) })
            iv_after_image1?.setOnClickListener(View.OnClickListener { showPreviewImage(iv_after_image1!!) })

        }
        else
        {
            //ToastUtils.showToast(activity.applicationContext,"图片获取不完整")
        }


    }

    /**
     * 查看预览信息
     *
     * @param image1
     */

    private fun showPreviewImage(image1: ImageView) {
        val filePath = image1.getTag(R.id.file_path) as String
        val bitmap = BitmapFactory.decodeFile(filePath)
        (view?.findViewById(R.id.iv_overview) as ImageView).setImageBitmap(bitmap)
        llFullScreen?.setVisibility(View.VISIBLE)
        image1.isDrawingCacheEnabled = false
        image1.isDrawingCacheEnabled = true
    }


    /**
     * 异步获取图片
     *
     * @author chang
     */
    class GetPicture(private val mUrl: String, imageView: ImageView) : AsyncTask<String, Void, String>() {
        private val mImageView: WeakReference<ImageView>

        init {
            mImageView = WeakReference(imageView)
            //mImageView.setImageResource(R.drawable.icon_img_original);
        }

        override fun doInBackground(vararg arg0: String): String {
            // TODO Auto-generated method stub
            var filePath = ""
            try {
                filePath = Utils.getImage(mUrl)
            } catch (e: Exception) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }

            return filePath
        }

        override fun onPostExecute(result: String) {
            super.onPostExecute(result)
            if (!StringUtils.isEmpty(result)) {
                val bitmap = Utils.getBitmapBySize(result, 80, 80)

                //Bitmap bitmap = Utils.getImageFromFile(new File(result));
                if (bitmap != null) {
                    mImageView.get()!!.setImageBitmap(bitmap)
                    mImageView.get()!!.setTag(R.id.file_path, result)
                } else {
                    mImageView.get()!!.setImageResource(R.drawable.defaut_image)
                }
            }
        }
    }

}