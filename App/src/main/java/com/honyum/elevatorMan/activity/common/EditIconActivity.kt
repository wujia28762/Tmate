package com.honyum.elevatorMan.activity.common

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.TextView
import com.honyum.elevatorMan.R
import com.honyum.elevatorMan.activity.company.CompanyApplyActivity
import com.honyum.elevatorMan.activity.company.InsuranceLookActivity
import com.honyum.elevatorMan.adapter.BaseListViewAdapter
import com.honyum.elevatorMan.adapter.BaseViewHolder
import com.honyum.elevatorMan.adapter.DragBaseAdapter
import com.honyum.elevatorMan.adapter.ViewHolder
import com.honyum.elevatorMan.base.BaseActivityWraper
import com.honyum.elevatorMan.base.Config
import com.honyum.elevatorMan.base.ListItemCallback
import com.honyum.elevatorMan.base.MyApplication
import com.honyum.elevatorMan.constant.Constant
import com.honyum.elevatorMan.data.IconInfo
import com.honyum.elevatorMan.data.IconInfosTag
import com.honyum.elevatorMan.net.GetApplyResponse
import com.honyum.elevatorMan.net.GetApplyResponseBody
import com.honyum.elevatorMan.net.base.NetConstant
import com.honyum.elevatorMan.net.base.NetTask
import com.honyum.elevatorMan.net.base.RequestBean
import com.honyum.elevatorMan.net.base.RequestHead
import com.honyum.elevatorMan.utils.IconXmlUtil
import com.honyum.elevatorMan.utils.Preference
import com.honyum.elevatorMan.utils.Utils
import com.honyum.elevatorMan.view.DragGridView
import com.honyum.elevatorMan.view.MyGridView
import kotlinx.android.synthetic.main.activity_edit_icon.*
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*


/**
 * Created by Star on 2017/11/28.
 */
class EditIconActivity : BaseActivityWraper(), ListItemCallback<IconInfo> {

    //editMode enable
    private var editMode: Boolean = false


    private lateinit var mIconInfos: List<IconInfosTag>


    private lateinit var mContentInfo: List<IconInfosTag>


    private lateinit var groupList: MutableList<MyGridView>

    lateinit var commonList: MutableList<IconInfo>


    private var commonList1: MutableList<IconInfo> by Preference(act, MyApplication.getInstance().config.userId + Constant.WORK_ICON, ArrayList())


    override fun getTitleString(): String {
        return "功能编辑"
    }

    override fun performItemCallback(data: IconInfo?) {
        data.let {
            modifyIconInfo({ commonList?.remove(it) }, it, false)
            notifyAllAdapter()
        }
    }


    override fun initView() {



        window.decorView.background = null
      //  commonList1 = commonList2
        val iconUtil = IconXmlUtil(ctx)
        mIconInfos = iconUtil.getIconByRoleId(config.roleId)
        mContentInfo = mIconInfos.drop(1)



        tv_edit.onClick {
            editIconEnable(!editMode)
        }
        /**
         *  // init common gridView, if init first, get property from E_DEFAULT_TAG.xml otherwise reading
         *  @see commonList by Preference Delegation
         *
         */
        commonList = commonList1?.toMutableList()
        if (Utils.isEmpty(commonList))
            commonList = mIconInfos[0].iconsInfo
        gl_common.adapter = CommonAdapter(ctx, commonList, gl_common)
        gl_e_common.adapter = ImageOnlyAdapter(commonList, ctx)
        genLayout()
    }


    private fun genLayout() {
        groupList = ArrayList()

        val inflater = LayoutInflater.from(ctx)
        mContentInfo.forEach {
            val view = inflater.inflate(R.layout.item_grideview, null) as LinearLayout
            val funTitle = view.find<TextView>(R.id.fun_title)
            funTitle.text = it.tag
            val curGrid = view.find<MyGridView>(R.id.gl_e_save)
            curGrid.adapter = SaveAdapter(it.iconsInfo, act)
            groupList.add(curGrid)
            ll_parent.addView(view)

        }

    }


    fun notifyAllAdapter() {
        notifyContentAdapter()
        // notifyCommonAdapter()
        (gl_e_common.adapter as ImageOnlyAdapter).notifyDataSetChanged()
        (gl_common.adapter as CommonAdapter).notifyDataSetChanged()
    }

    /**
     * define the status of delete icon using property
     * @see editMode
     */
    private fun editIconEnable(b: Boolean) {

        mContentInfo.forEach {
            it.iconsInfo.forEach {
                it.isEditAble = b
                it.isSelected = false
            }
        }
        commonList?.forEach {
            it.isEditAble = b
        }

        commonList?.forEach {
            modifyIconInfo({}, it, true)
        }

        editMode = !editMode

        if (editMode) {
            ll_common.visibility = GONE
            gl_e_common.visibility = GONE
            ll_ed_common.visibility = VISIBLE
            gl_common.visibility = VISIBLE
        } else {
            ll_common.visibility = VISIBLE
            gl_e_common.visibility = VISIBLE
            ll_ed_common.visibility = GONE
            gl_common.visibility = GONE
        }


        if (editMode) {
            tv_finish.visibility = VISIBLE
            tv_finish.onClick {
                editIconEnable(!editMode)
                commonList1 = commonList
            }
        } else {
            tv_finish.visibility = GONE
        }

        notifyAllAdapter()
    }

    private fun modifyIconInfo(body: () -> Unit, it: IconInfo?, boolean: Boolean) {

        try {
            val index = it?.id?.split("_")
            val indexDataSet: Int = index!![0].toInt()
            val indexItem: Int = index[1].toInt()
            if (indexDataSet < 1) {
                return
            }
            mContentInfo[indexDataSet - 1].iconsInfo[indexItem].let {
                it.isSelected = boolean
                body()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     *  notify All Adapter to refresh editMode
     */
    private fun notifyContentAdapter() {
        groupList.forEach {
            (it.adapter as SaveAdapter).notifyDataSetChanged()
        }
    }


    override fun getLayoutID(): Int {
        return R.layout.activity_edit_icon
    }

    fun requestApply() {
        val server = config.server + NetConstant.GET_APPLIY
        val request = RequestBean()
        val head = RequestHead()
        head.accessToken = config.token
        head.userId = config.userId
        request.head = head

        val netTask = object : NetTask(server, request) {
            override fun onResponse(task: NetTask, result: String) {
                val response = GetApplyResponse.getResponse(result)
                dealResult(response)
            }
        }

        addTask(netTask)
    }

    private fun dealResult(response: GetApplyResponse) {

        if (response.body != null) {
            dealState(response.body)
        }
    }

    fun dealState(body: GetApplyResponseBody) {

        when (body.state) {

            "0" -> {
                val it = Intent(this, CompanyApplyActivity::class.java)
                it.putExtra("data", body)
                startActivity(it)
            }
            "1" -> {
                val it = Intent(this, InsuranceLookActivity::class.java)
                startActivity(it)
            }
            "2" -> {
                val it = Intent(this, CompanyApplyActivity::class.java)
                it.putExtra("data", body)
                startActivity(it)
            }
            else -> {
                val gb = GetApplyResponseBody()
                gb.state = "99"
                val it = Intent(this, CompanyApplyActivity::class.java)
                it.putExtra("data", gb)
                startActivity(it)
            }
        }

    }
}

//in the non-editMode , the common list showing image only
class ImageOnlyAdapter(data: List<IconInfo>?, context: Context?, layoutId: Int = R.layout.item_image) : BaseListViewAdapter<IconInfo>(data, context, layoutId) {
    override fun bindData(holder: BaseViewHolder?, t: IconInfo?, index: Int) {
        holder?.setImageResource(R.id.tv_icon, t!!.image)
//                ?.setSize(R.id.tv_icon, 30, 30)
    }
}

//common adapter
class SaveAdapter(data: List<IconInfo>?, context: Context?, layoutId: Int = R.layout.item_iamge_tag) : BaseListViewAdapter<IconInfo>(data, context, layoutId) {
    override fun bindData(holder: BaseViewHolder?, t: IconInfo?, index: Int) {

        var dwLeft = ContextCompat.getDrawable(mContext, t?.image!!)
        holder?.setText(R.id.tv_icon, t.name)?.setBoundsCompoundDrawables(R.id.tv_icon, dwLeft)?.setOnClickListener(R.id.tv_icon, {
            CommonMainPage.dealViewDispatcher(mContext, t?.action, t?.name)
        }
        )
        // if EditIconActivity in the EditMode, all icon selected status must be specify
        if (t.isEditAble)
            holder?.apply {
                setVisible(R.id.iv_delete, t.isEditAble)
                if (!t.isSelected) {
                    setImageResource(R.id.iv_delete, R.drawable.add_icon)
                    setOnClickListener(R.id.iv_delete, { v ->
                        val info = IconInfo()
                        info.id = t.id
                        info.isSelected = t.isSelected
                        info.action = t.action
                        info.image = t.image
                        info.isEditAble = t.isEditAble
                        info.name = t.name
                        (mContext as EditIconActivity).commonList?.add(info)
                        (mContext as EditIconActivity).notifyAllAdapter()
                        t.isSelected = true
                    })
                    //setBackGround(R.id.)
                } else {
                    setImageResource(R.id.iv_delete, R.drawable.delete_icon)
                    setOnClickListener(R.id.iv_delete, { v ->
                        (mContext as EditIconActivity).commonList?.remove(t)
                        t.isSelected = false
                        (mContext as EditIconActivity).notifyAllAdapter()
                    })

                }
                setBackGround(R.id.tv_icon, R.color.backgray)
            }
        else {
            holder?.setVisible(R.id.iv_delete, t.isEditAble)
            holder?.setBackGround(R.id.tv_icon, R.color.white)
        }
    }
}

class CommonAdapter(context: Context?, data: List<IconInfo>?, gl_common: DragGridView) : DragBaseAdapter<IconInfo>(context, data, gl_common) {


    override fun getLayoutId(): Int {
        return R.layout.item_iamge_tag
    }

    override fun initView(holder: ViewHolder) {
        holder.addView(R.id.tv_icon)
        holder.addView(R.id.iv_delete)
        holder.addView(R.id.fl_back)
    }

    private fun TextView.boundsCompoundDrawables(dwLeft: Drawable) {
        val size = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30.00f, context.displayMetrics).toInt()
        dwLeft.setBounds(0, 0, size, size)//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        this.setCompoundDrawables(null, dwLeft, null, null)//只放左边
    }

    override fun setViewValue(holder: ViewHolder, position: Int) {
        getItem(position).let {
            var dwLeft = ContextCompat.getDrawable(context, it.image!!)
            (holder.getView(R.id.tv_icon) as TextView).text = it.name
            (holder.getView(R.id.tv_icon) as TextView).boundsCompoundDrawables(dwLeft)
            // if EditIconActivity in the EditMode, all icon selected status must be specify
            if (it.isEditAble)
                holder?.apply {
                    holder.getView(R.id.iv_delete).visibility = VISIBLE
                    view.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                        (context as ListItemCallback<IconInfo>).performItemCallback(getItem(position))
                    }
                    (holder.getView(R.id.tv_icon) as TextView).backgroundColor = ContextCompat.getColor(context, R.color.backgray)
                }
            else {
                holder.getView(R.id.iv_delete).visibility = GONE
                view.setOnItemClickListener({ _, _, _, _ ->
                    CommonMainPage.dealViewDispatcher(context, it.action, it.name)
                })

            }
        }

    }

}