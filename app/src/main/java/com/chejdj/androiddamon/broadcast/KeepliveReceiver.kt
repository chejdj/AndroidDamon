package com.chejdj.androiddamon.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.chejdj.androiddamon.manager.KeepLiveManager

class KeepliveReceiver : BroadcastReceiver() {
    val TAG: String = "KeepliveReceiver"
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action.equals(Intent.ACTION_SCREEN_OFF)) {
            Log.d(TAG, "screen_off")
            KeepLiveManager.INSTANCE.startKeepLiveActivity()
        } else if (intent?.action.equals(Intent.ACTION_USER_PRESENT)) {
            Log.d(TAG, "screen_on")
            KeepLiveManager.INSTANCE.finishKeepLiveActivity()
        }
    }
}