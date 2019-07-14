package com.chejdj.androiddamon.service

import android.app.job.JobInfo
import android.app.job.JobParameters
import android.app.job.JobScheduler
import android.app.job.JobService
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Message
import android.os.SystemClock
import android.support.annotation.RequiresApi
import android.util.Log
import com.chejdj.androiddamon.MainActivity
import com.chejdj.androiddamon.broadcast.KeepliveStaticReceiver
import com.chejdj.androiddamon.utils.LiveState

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
class KeepLiveJobService : JobService() {
    val handler = Handler(Handler.Callback() {
        val parameters = it.obj as JobParameters
        jobFinished(parameters, true)
        if (!LiveState.isApplive(applicationContext, packageName)) {
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        Log.d(TAG, "start work" + SystemClock.currentThreadTimeMillis())
        true
    })

    override fun onStartJob(params: JobParameters?): Boolean {
        val message = Message.obtain()
        message.obj = params
        handler.sendMessage(message)
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        handler.removeCallbacksAndMessages(null)
        return false
    }


    companion object {
        val TAG: String = "KeepLiveJobService"
        val packageName: String = "com.chejdj.androiddamon"
        fun configureJobInfo(): JobInfo {
            //JobId是每一个Job的id
            val jobId = 1
            //指定执行的JobService
            val name = ComponentName(packageName, KeepLiveJobService::class.java.name)
            val builder = JobInfo.Builder(jobId, name)
            //设置任务的间隔时间
            builder.setPeriodic(3000)
            builder.setPersisted(true)
            return builder.build()
        }

        fun startJobService(context: Context) {
            val jobScheudler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
            jobScheudler.schedule(configureJobInfo())
        }
    }
}