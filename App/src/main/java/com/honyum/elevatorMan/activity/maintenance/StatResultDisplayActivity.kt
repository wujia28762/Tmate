package com.honyum.elevatorMan.activity.company

import android.app.AlertDialog
import android.app.Dialog
import android.content.res.Configuration
import android.graphics.Color
import android.support.v7.widget.Toolbar
import com.honyum.elevatorMan.R
import com.honyum.elevatorMan.base.BaseActivityWraper
import yalantis.com.sidemenu.interfaces.Resourceble
import yalantis.com.sidemenu.interfaces.ScreenShotable
import yalantis.com.sidemenu.util.ViewAnimator
import android.graphics.drawable.BitmapDrawable
import android.view.animation.AccelerateInterpolator
import android.os.Bundle
import yalantis.com.sidemenu.model.SlideMenuItem
import android.widget.LinearLayout
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.view.*
import android.widget.TextView
import com.honyum.elevatorMan.base.Config.COMPANY
import com.honyum.elevatorMan.base.Config.MANAGER
import com.honyum.elevatorMan.data.CommunityCountInfo
import com.honyum.elevatorMan.data.StatResult
import com.honyum.elevatorMan.fragment.ContentFragmentNew
import com.honyum.elevatorMan.utils.DialogUtil
import com.honyum.elevatorMan.view.ChartTableView
import io.codetail.animation.ViewAnimationUtils
import kotlinx.android.synthetic.main.activity_stat_display.*
import org.jetbrains.anko.act
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk25.coroutines.onClick


/**
 * Created by star on 2018/4/16.
 */
class StatResultDisplayActivity : BaseActivityWraper() {

    override fun getTitleString(): String {
        return "统计查看"
    }

    override fun initView() {
        contentFragment = ContentFragmentNew.newInstance(R.drawable.content_music)
        supportFragmentManager.beginTransaction()
                .replace(R.id.content_frame, contentFragment)
                .commit()
        supportActionBar?.hide()

//        drawerLayout = findViewById(R.id.drawer_layout) as DrawerLayout
//        drawerLayout!!.setScrimColor(Color.TRANSPARENT)
//        linearLayout = findViewById(R.id.left_drawer) as LinearLayout
//        linearLayout!!.setOnClickListener { drawerLayout!!.closeDrawers() }


       // setActionBar()
        //createMenuList()
       // viewAnimator = ViewAnimator<SlideMenuItem>(this, list, contentFragment, drawerLayout, this)
    }

    override fun getLayoutID(): Int {
        return R.layout.activity_stat_display
    }

    //private var drawerLayout: DrawerLayout? = null
   // private var drawerToggle: ActionBarDrawerToggle? = null
    private val list = ArrayList<SlideMenuItem>()
    private var contentFragment: ContentFragmentNew? = null
    private var viewAnimator: ViewAnimator<*>? = null
    private var res = R.drawable.content_music
    private var linearLayout: LinearLayout? = null





}