package com.honyum.elevatorMan.activity.contract

import android.os.Bundle
import com.honyum.elevatorMan.R
import com.honyum.elevatorMan.base.BaseActivityWraper
import kotlinx.android.synthetic.main.activity_lift_message.*

class ContractEnclosureActivity : BaseActivityWraper() {

    override fun getLayoutID(): Int {
        return R.layout.activity_contract_enclosure
    }

    override fun getTitleString(): String {
        return "附件详情"
    }

    override fun initView() {
        var bundle:Bundle = intent.extras
        webView.loadUrl(bundle.getString("url"))
    }
}
