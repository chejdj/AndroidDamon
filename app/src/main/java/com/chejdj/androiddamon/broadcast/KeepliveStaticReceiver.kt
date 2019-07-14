package com.chejdj.androiddamon.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.chejdj.androiddamon.activity.KeepLiveActivity
import com.chejdj.androiddamon.utils.LiveState

class KeepliveStaticReceiver : BroadcastReceiver() {
    companion object {
        val packageName: String = "com.chejdj.androiddamon"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (!LiveState.isApplive(context, packageName)) {
            val intent = Intent(context, KeepLiveActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            if (context != null) {
                context.startActivity(intent)
            }
        }
    }

}