package com.chejdj.androiddamon.utils

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import java.lang.Exception

class LaunchUtils {
    companion object {
        fun launchAppActivity(packageName: String, componentName: String, context: Context) {
            val cn = ComponentName(packageName, componentName)
            val intent = Intent()
            try {
                intent.setComponent(cn)
                if (context != null) {
                    context.startActivity(intent)
                } else {
                    throw Exception()
                }
            } catch (e: Exception) {
                Log.e("KeepLiveManager", "launch app fail")
            }
        }
    }
}