package com.honyum.elevatorMan.activity.common

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import com.honyum.elevatorMan.R
import com.honyum.elevatorMan.base.BaseActivityWraper
import com.honyum.elevatorMan.net.UploadPageRequest
import com.honyum.elevatorMan.net.base.NetTask
import com.honyum.elevatorMan.net.base.NewRequestHead
import com.honyum.elevatorMan.net.base.RequestBean
import kotlinx.android.synthetic.main.activity_to_do_list.*
import org.jetbrains.anko.appcompat.v7.Appcompat
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.textColor

/**
 * Created by Star on 2017/12/14.
 */
public class ToDoListActivity : BaseActivityWraper() {

    companion object {
        var currLastNode: String? = null
        var currTask: String? = null
    }

    override fun onDestroy() {
        super.onDestroy()
        currLastNode = null
        currTask = null
    }

    private lateinit var todoFragment: Fragment
    private lateinit var doneFragment: Fragment

    private var currFragment: Fragment? = null

    override fun getTitleString(): String {
        return "待办事项"
    }


    override fun initView() {


        todoFragment = ToDoFragment()
        doneFragment = DoneFragment()
        switchAndChangeTextColor()
        tv_todo.onClick {
            switchAndChangeTextColor()

        }
        tv_done.onClick {
            switchAndChangeTextColor(false)
        }

    }

    private fun switchAndChangeTextColor(first: Boolean = true) {
        val oriColor = ContextCompat.getColor(this,R.color.black)
        val changeColor =  ContextCompat.getColor(this,R.color.titleblue)

        if (first) {
            tv_todo.textColor = changeColor
            switchFragment(todoFragment).commit()
            tv_done.textColor = oriColor
            return
        }
        tv_todo.textColor = oriColor
        switchFragment(doneFragment).commit()
        tv_done.textColor = changeColor

    }

    override fun getLayoutID(): Int {
        return R.layout.activity_to_do_list
    }

    private fun switchFragment(targetFragment: Fragment): FragmentTransaction {
        var fragmentTransaction = supportFragmentManager.beginTransaction()
        if (!targetFragment.isAdded) {
            currFragment?.let {
                fragmentTransaction.hide(currFragment)
            }
            fragmentTransaction.add(R.id.fragment, targetFragment, targetFragment::class.java.simpleName)
        } else {
            fragmentTransaction.hide(currFragment).show(targetFragment)
        }
        currFragment = targetFragment
        return fragmentTransaction

    }
}