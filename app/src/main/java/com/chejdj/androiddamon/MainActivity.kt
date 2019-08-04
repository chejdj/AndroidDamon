package com.chejdj.androiddamon

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.chejdj.androiddamon.keepmanager.KeepLiveManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        KeepLiveManager.INSTANCE.startKeepliveService(this)
        KeepLiveManager.INSTANCE.startKeepliveJobServie(this)
        KeepLiveManager.INSTANCE.startKeepliveFromAccountSync(this)
        KeepLiveManager.INSTANCE.startKeepDoubleDamonProcess(this)
    }

}
