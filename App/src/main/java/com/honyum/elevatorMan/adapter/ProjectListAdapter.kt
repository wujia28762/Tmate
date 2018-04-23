package com.honyum.elevatorMan.adapter

import android.content.Context
import android.content.Intent
import com.honyum.elevatorMan.R
import com.honyum.elevatorMan.activity.common.EveListInfoActivity
import com.honyum.elevatorMan.constant.IntentConstant
import com.honyum.elevatorMan.net.ProjectInfoResponseBody

/**
 * Created by Star on 2017/10/20.
 */
class ProjectListAdapter(val datas: List<ProjectInfoResponseBody>?, context: Context?, layoutId: Int = R.layout.item_project_name) : BaseListViewAdapter<ProjectInfoResponseBody>(datas, context, layoutId) {

    override fun bindData(holder: BaseViewHolder?, t: ProjectInfoResponseBody?, index: Int) {

        holder?.setText(R.id.tv_name,t?.name)
                ?.setOnClickListener(R.id.ll_project_info,{
                    var intent = Intent(mContext, EveListInfoActivity::class.java)
                    intent.putExtra(IntentConstant.INTENT_DATA, t?.id)
                    mContext.startActivity(intent)})



    }

}