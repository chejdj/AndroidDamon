package com.chejdj.androiddamon

import android.app.Application
import com.chejdj.androiddamon.activity.KeepLiveActivity

class MyApplication : Application() {
    var liveActivity :KeepLiveActivity?=null
    override fun onCreate() {
        super.onCreate()
        _self=this
    }
    companion object {
        var _self:MyApplication? =null
        fun getApplication():MyApplication= _self!!
    }
}