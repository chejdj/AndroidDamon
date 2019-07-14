package com.chejdj.androiddamon.service


import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log

class KeepLiveService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            startForeground(MONITOR_SERVICE_ID, Notification())
        } else {
            val innerIntent = Intent(this, InnerService::class.java)
            val notification = Notification.Builder(this).setContentTitle("hello").build()
            startService(innerIntent)
            startForeground(MONITOR_SERVICE_ID, notification)
            Log.d(TAG, "start OutService")
        }
        return Service.START_STICKY
    }

    companion object {
        val MONITOR_SERVICE_ID = -1001
        val TAG: String = "KeepLiveService"

        class InnerService : Service() {
            override fun onBind(intent: Intent?): IBinder? {
                return null
            }

            override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
                val notification = Notification.Builder(this).setContentTitle("hello").build()
                startForeground(MONITOR_SERVICE_ID, notification)
                stopSelf()
                Log.d(TAG, "start InnerService")
                return super.onStartCommand(intent, flags, startId)
            }
        }
    }
}