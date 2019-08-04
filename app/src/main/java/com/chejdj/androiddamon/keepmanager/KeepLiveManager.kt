package com.chejdj.androiddamon.keepmanager

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.LocalSocket
import com.chejdj.androiddamon.MyApplication
import com.chejdj.androiddamon.accountsync.SyncService
import com.chejdj.androiddamon.activity.KeepLiveActivity
import com.chejdj.androiddamon.broadcast.KeepliveDynamicReceiver
import com.chejdj.androiddamon.damonjava.LocalService
import com.chejdj.androiddamon.damonjava.RemoteService
import com.chejdj.androiddamon.service.KeepLiveJobService
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
        val keepliveReceive = KeepliveDynamicReceiver()
        val receiverFilter = IntentFilter()
        receiverFilter.addAction(Intent.ACTION_SCREEN_ON)
        receiverFilter.addAction(Intent.ACTION_SCREEN_OFF)
        MyApplication.getApplication().registerReceiver(keepliveReceive, receiverFilter)
    }

    /**
     * 方案一： 通过监控手机息屏和开屏，开启1像素点的Activity的方法提升进程优先级
     */

    fun startKeepLiveActivity() {
        val intent = Intent(MyApplication.getApplication().applicationContext, KeepLiveActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        MyApplication.getApplication().startActivity(intent)
    }

    fun finishKeepLiveActivity() {
        MyApplication.getApplication().liveActivity?.finish()
    }

    /**
     * 方案二：通过开启前台Service，利用Android5的通知Bug提升进程优先级
     */
    fun startKeepliveService(activity: Activity) {
        val intent = Intent(activity, KeepLiveService::class.java)
        activity.startService(intent)
    }

    /**
     * 方案三：利用系统广播实现拉活,这个是静态广播实现，见KeepliveStaticReceiver
     */

    /**
     * 方案四：service返回START_STICKY ，见KeepliveService
     */


    /**
     * 方案五
     * JobSchduler机制拉活
     */
    fun startKeepliveJobServie(activty: Activity) {
        KeepLiveJobService.startJobService(activty.applicationContext)
    }

    /**
     * 方案六
     * 账号同步机制
     */
    fun startKeepliveFromAccountSync(context: Context) {
        SyncService.startAccountAsync(context)
    }


    // TODO 需要学习知识点localSocket,以及如何在C进程中，拉活Java的进程
    /**
     * 方案七：native进程拉活
     */

    /**
     * 方案八：双进程保活
     */

    fun startKeepDoubleDamonProcess(context: Context) {
        context.startService(Intent(context, LocalService::class.java))
        context.startService(Intent(context, RemoteService::class.java))
    }


}