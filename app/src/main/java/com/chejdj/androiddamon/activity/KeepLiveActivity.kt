package com.chejdj.androiddamon.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import com.chejdj.androiddamon.MyApplication

//开启一个像素点的方法
class KeepLiveActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setGravity(Gravity.LEFT and Gravity.TOP)
        val params = window.attributes;
        params.x = 0
        params.y = 0
        params.height = 1
        params.width = 1
        window.attributes = params
        MyApplication.getApplication().liveActivity=this
    }
}