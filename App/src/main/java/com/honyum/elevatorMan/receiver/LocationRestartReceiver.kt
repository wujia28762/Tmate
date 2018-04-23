package com.honyum.elevatorMan.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.honyum.elevatorMan.base.BaseFragmentActivity

class LocationRestartReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        (context as BaseFragmentActivity).startLocationService()
    }
}
