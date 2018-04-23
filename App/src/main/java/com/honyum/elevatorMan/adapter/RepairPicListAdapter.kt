package com.honyum.elevatorMan.adapter

import android.content.Context
import com.bumptech.glide.Glide
import com.honyum.elevatorMan.R
import com.honyum.elevatorMan.base.ListItemCallback

/**
 * Created by Star on 2017/10/20.
 */
class RepairPicListAdapter(val datas: List<String>?, context: Context?, layoutId: Int = R.layout.repair_pic_item) : BaseListViewAdapter<String>(datas, context, layoutId) {
    override fun bindData(holder: BaseViewHolder?, t: String?, index: Int) {
        Glide.with(mContext).load(t).into(holder?.getView(R.id.iv_pic))
        holder?.setOnClickListener(R.id.iv_pic,{v->
            (mContext as ListItemCallback<String>).performItemCallback(t)
        })
        // holder?.setImageResource()
              /* ?.setOnClickListener(R.id.linear_item,{
                   *//* val intent = Intent()
                    //获取intent对象
                    intent.setClass(mContext, ContractDetailActivity::class.java)
                    var bundle = Bundle()
                   bundle.putString("code",t?.id)
                   intent.putExtras(bundle)
                    mContext.startActivity(intent)*//*
                })*/
    }
}