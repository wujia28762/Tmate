package com.honyum.elevatorMan.utils

import android.text.TextUtils
import com.honyum.elevatorMan.base.BaseFragmentActivity
import com.honyum.elevatorMan.net.NewRequestHead
import com.honyum.elevatorMan.net.UploadImageResponse
import com.honyum.elevatorMan.net.UploadRepairImageRequest
import com.honyum.elevatorMan.net.base.NetConstant
import com.honyum.elevatorMan.net.base.NetTask
import com.honyum.elevatorMan.net.base.RequestBean

/**
 * Created by star on 2018/3/16.
 */
class UploadImageManager {

    fun getImages(context: BaseFragmentActivity, datas: ImageNode?, nextDeal: (datas: ImageNode?) -> Unit){

        if (datas != null) {
            requestUploadImage(context,datas,nextDeal,datas)
        }
        else
            context.showToast("图片上传错误！")
    }

    //上传图片
    private fun requestUploadImage(context:BaseFragmentActivity,datas: ImageNode,nextDeal:(datas:ImageNode)->Unit,head: ImageNode) {
        //showToast("**********"+base64)
        val task = object : NetTask(context.config.server + NetConstant.UP_LOAD_IMG,
                getImageRequestBean(context.config.userId, context.config.token, datas.img)) {
            override fun onResponse(task: NetTask, result: String) {
                val response = UploadImageResponse.getUploadImageResponse(result)
                if (response.head != null && response.head.rspCode == "0") {
                    val url = response.body.url
                    datas.url = url
                    if (datas.hasNext()&&!TextUtils.isEmpty(datas.next?.img))
                        requestUploadImage(context,datas.next!!,nextDeal,head)
                    else {
                        nextDeal(head)
                    }

                }
            }
        }
        context.addTask(task)
    }

    private fun getImageRequestBean(userId: String, token: String, path: String?): RequestBean {
        val request = UploadRepairImageRequest()
        request.head = NewRequestHead().setuserId(userId).setaccessToken(token)
        var body = request.UploadRepairImageBody()
        body.img = path
        request.body = body
        return request
    }

}

class ImageNode {
    var url: String? = null
    var img: String? = null
    var next: ImageNode? = null

    fun hasNext(): Boolean {
        return next != null
    }

}