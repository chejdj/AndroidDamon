package com.chejdj.androiddamon.manager

import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import com.chejdj.androiddamon.MyApplication
import com.chejdj.androiddamon.activity.KeepLiveActivity
import com.chejdj.androiddamon.broadcast.KeepliveReceiver
import com.chejdj.androiddamon.service.KeepLiveService

/*
 编译期常量，使用const修饰符
 1. 位于顶层或者object声明或 companion object的一个成员
 2. 以String或原生类型值初始化
 3. 没有自定义getter

 使用object修饰的类为静态类

 !!表示当前对象不为null的时候执行,如果对象为null报错异常
 ？表示任何情况下都不会报异常,在声明一个变量的时候，加？代表这个变量类型支持为null的值

 Android无法获取SCREEN_ON和SCREEN_OFF广播，需要动态注册
 */
class KeepLiveManager private constructor() {
    companion object {
        val INSTANCE = SingleInstance.INSTANCE
    }

    private object SingleInstance {
        val INSTANCE = KeepLiveManager()
    }
    init {
        val keepliveReceive = KeepliveReceiver()
        val receiverFilter = IntentFilter()
        receiverFilter.addAction(Intent.ACTION_SCREEN_ON)
        receiverFilter.addAction(Intent.ACTION_SCREEN_OFF)
        MyApplication.getApplication().registerReceiver(keepliveReceive,receiverFilter)
    }

    fun startKeepLiveActivity() {
        val intent = Intent(MyApplication.getApplication().applicationContext, KeepLiveActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        MyApplication.getApplication().startActivity(intent)
    }

    fun finishKeepLiveActivity() {
        MyApplication.getApplication().liveActivity?.finish()
    }
    fun startKeepliveService(activity: Activity){
        val intent = Intent(activity,KeepLiveService::class.java)
        activity.startService(intent)
    }
}