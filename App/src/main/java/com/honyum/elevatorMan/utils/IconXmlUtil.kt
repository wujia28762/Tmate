package com.honyum.elevatorMan.utils

import android.content.Context
import com.honyum.elevatorMan.base.Config
import com.honyum.elevatorMan.constant.Constant.COMPANY
import com.honyum.elevatorMan.constant.Constant.WORKER
import com.honyum.elevatorMan.data.IconInfosTag

/**
 * Created by Star on 2017/12/6.
 */
class IconXmlUtil {
    //using XML parser to get all kinds of icon config
    private var parser: IconsParser

    private lateinit var mIconInfos: List<IconInfosTag>
    /**
     * icon tag container using in block
     * init to init icon data lists
     */
    // private var tagList: List<String>

    private var mContext: Context

    constructor(mContext: Context) {
        this.mContext = mContext.applicationContext
        parser = IconsParser(mContext)
        //tagList = listOf(currXml)

    }

    private fun selectCurrXml(roleId: String): String {
        return when (roleId) {
            Config.WORKER -> "work_icons.xml"
            Config.COMPANY -> "company_icons.xml"
            Config.MANAGER-> "manager_icons.xml"
            else -> "work_icons.xml"
        }
    }

    fun getIconByRoleId(roleId: String): List<IconInfosTag> {
        parser = IconsParser(mContext)
        //init E_SAVE_TAG data list and gridView
        val xmlIs = mContext.assets.open(selectCurrXml(roleId))
        mIconInfos = parser.getIconsInfo(xmlIs)
        xmlIs.close()
        return mIconInfos;
    }


}