package com.chejdj.androiddamon.damonjava

import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import com.chejdj.androiddamon.RemoteChat

class LocalService : Service() {
    private var myBinder: MyBinder? = null
    private var myServiceConnection: MyServiceConnection? = null
    private val TAG: String = "LocalService"
    override fun onCreate() {
        super.onCreate()
        if (myBinder == null) {
            myBinder = MyBinder()
        }
        if (myServiceConnection == null) {
            myServiceConnection = MyServiceConnection()
        }
    }

    override fun onBind(intent: Intent?): MyBinder? {
        return myBinder
    }


    inner class MyBinder : RemoteChat.Stub() {
        override fun getProcessName(): String {
            Log.d(TAG, "local service  process name :" + processName)
            return processName
        }
    }

    inner class MyServiceConnection : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.d(TAG, "connect successful!!")
            Log.d(TAG, "remote service process name is:" + (service as RemoteChat).processName)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d(TAG, "remote service disconnect!!")
            val intent = Intent(this@LocalService, RemoteService::class.java)
            this@LocalService.startService(intent)
            this@LocalService.bindService(intent, myServiceConnection, Context.BIND_IMPORTANT)
        }
    }
}