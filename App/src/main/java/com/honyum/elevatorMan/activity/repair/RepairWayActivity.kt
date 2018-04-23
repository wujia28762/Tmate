package com.honyum.elevatorMan.activity.repair

import android.content.Intent
import com.honyum.elevatorMan.R
import com.honyum.elevatorMan.activity.common.ShowElevatInfoActivity
import com.honyum.elevatorMan.base.BaseActivityWraper
import com.uuzuche.lib_zxing.activity.CaptureActivity
import com.uuzuche.lib_zxing.activity.CodeUtils
import kotlinx.android.synthetic.main.activity_repair_way.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivityForResult

class RepairWayActivity : BaseActivityWraper() {

    val REQUEST_CODE = 10

    override fun getLayoutID(): Int {
        return R.layout.activity_repair_way
    }

    override fun getTitleString(): String {
        return "报修"
    }

    override fun initView() {
        linear_scan.onClick {
            startActivityForResult<CaptureActivity>(REQUEST_CODE)
            finish()
        }

        linear_code.onClick {
            val intent = Intent()
            intent.setClass(applicationContext, RepairRescueCodeActivity::class.java)
            startActivity(intent)
            finish()
        }

        linear_lift_num.onClick {
            val intent = Intent()
            intent.setClass(applicationContext, RepairLiftNumActivity::class.java)
            startActivity(intent)
            finish()
        }

        linear_lift.onClick {
            val intent = Intent()
            intent.setClass(applicationContext, RepairLiftActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                val bundle = data!!.getExtras() ?: return
                if (bundle.getInt(CodeUtils.RESULT_TYPE) === CodeUtils.RESULT_SUCCESS) {
                    val result = bundle.getString(CodeUtils.RESULT_STRING)
                    val s = result.split("liftNum=".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
                    if (s.size > 1) {
                        val it = Intent(this, ShowElevatInfoActivity::class.java)
                        it.putExtra("info", s[s.size - 1])
                        startActivity(it)

                    } else {
                        showToast("扫描失败！")
                    }

                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) === CodeUtils.RESULT_FAILED) {

                    showToast("扫描失败！")
                }
            }
        }
    }



}
