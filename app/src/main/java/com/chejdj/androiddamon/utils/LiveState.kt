package com.chejdj.androiddamon.utils

import android.app.ActivityManager
import android.content.Context

class LiveState {
    companion object {
        /**
         * 判断APP存活
         */
        fun isApplive(context: Context?, packageName: String?): Boolean {
            val activityManager = context!!.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val processInfos = activityManager.runningAppProcesses
            for (processInfo in processInfos) {
                if (processInfo.processName.equals(packageName)) {
                    return true
                }
            }
            return false
        }

        /**
         * 判断Activity活动
         */
        fun isActivitylive(context: Context, activityName: String): Boolean {
            val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val list = am.getRunningTasks(30)
            for (entity in list) {
                if (entity.topActivity.toString().equals(activityName)) {
                    return true
                }
            }
            return false
        }

        /**
         * 判断Activity是否在前台,在当前Task的顶部
         */
        fun isForegroundActivity(context: Context, activityName: String): Boolean {
            val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val list = am.getRunningTasks(1)
            val liveActivtyName = list.get(0).topActivity.toString();
            if (liveActivtyName != null && liveActivtyName.equals(activityName)) {
                return true
            }
            return false
        }

        /**
         * 判断Servie存活,判断存活的在Android 5和Android5以下可以使用
         */
        fun isServielive(context: Context, serviceName: String): Boolean {
            val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val list = am.getRunningServices(30)
            for (entity in list) {
                if (entity.service.className.equals(serviceName)) {
                    return true
                }
            }
            return false
        }

    }
}