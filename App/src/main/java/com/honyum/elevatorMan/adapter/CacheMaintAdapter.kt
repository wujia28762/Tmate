package com.honyum.elevatorMan.adapter

import android.content.Context
import android.content.Intent
import com.honyum.elevatorMan.R
import com.honyum.elevatorMan.activity.common.EveListInfoActivity
import com.honyum.elevatorMan.constant.IntentConstant
import com.honyum.elevatorMan.data.CacheMaint
import com.honyum.elevatorMan.net.ProjectInfoResponseBody

/**
 * Created by Star on 2017/10/20.
 */
class CacheMaintAdapter(val datas: List<CacheMaint>?, context: Context?, layoutId: Int = R.layout.item_cache_dialog) : BaseListViewAdapter<CacheMaint>(datas, context, layoutId) {

    override fun bindData(holder: BaseViewHolder?, t: CacheMaint?, index: Int) {

        holder?.setText(R.id.plan_time,t?.planTime)
        if(t?.mainType.equals("hm")){
            holder?.setText(R.id.plan_type,"半月保")
        }else if(t?.mainType.equals("m")){
            holder?.setText(R.id.plan_type,"月保")

        }else if(t?.mainType.equals("s")){
            holder?.setText(R.id.plan_type,"季保")

        }else if(t?.mainType.equals("y")){
            holder?.setText(R.id.plan_type,"年保")

        } else if(t?.mainType.equals("hy")){
            holder?.setText(R.id.plan_type,"半年保")

        }




    }

}