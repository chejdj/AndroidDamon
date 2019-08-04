package com.chejdj.androiddamon.accountsync

import android.accounts.Account
import android.accounts.AccountManager
import android.app.Service
import android.content.*
import android.os.Bundle
import android.os.IBinder
import android.os.SystemClock
import android.util.Log
import com.chejdj.androiddamon.MainActivity
import com.chejdj.androiddamon.activity.KeepLiveActivity
import com.chejdj.androiddamon.utils.LaunchUtils
import com.chejdj.androiddamon.utils.LiveState

class SyncService : Service() {
    override fun onCreate() {
        super.onCreate()
        synchronized(syncObject) {
            if (syncAdapter == null) {
                syncAdapter = SyncAdapter(applicationContext, true)
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder {
        return syncAdapter!!.syncAdapterBinder
    }

    companion object {
        private val syncObject = Object()
        private var syncAdapter: SyncAdapter? = null
        private val ACCOUNT_TYPE: String = "com.chejdj.androiddamon"
        private val SYNC_AUTHOR: String = "com.chejdj.androiddamon.provider"

        class SyncAdapter(context: Context, autoInitialize: Boolean) :
            AbstractThreadedSyncAdapter(context, autoInitialize) {
            override fun onPerformSync(
                account: Account?,
                extras: Bundle?,
                authority: String?,
                provider: ContentProviderClient?,
                syncResult: SyncResult?
            ) {
                Log.d("AndroidDamon", "sync time at" + SystemClock.elapsedRealtime())
                if (!LiveState.isApplive(context, "com.chejdj.androiddamon")) {
                    Log.d("AndroidDamon", "准备启动APP")
                    LaunchUtils.launchAppActivity(
                        "com.chejdj.androiddamon",
                        "com.chejdj.androiddamon.MainActivity",
                        context
                    )
                }
            }

        }

        fun startAccountAsync(context: Context) {
            val accountManager = context.getSystemService(Context.ACCOUNT_SERVICE) as AccountManager
            var account: Account
            val accounts = accountManager.getAccountsByType(ACCOUNT_TYPE)
            if (accounts.size > 0) {
                account = accounts[0]
            } else {
                account = Account("chejdj", ACCOUNT_TYPE)
            }
            if (accountManager.addAccountExplicitly(account, null, null)) {
                val syncInternal: Long = 60
                Log.d("AndroidDamon", "start service")
                ContentResolver.setIsSyncable(account, SYNC_AUTHOR, 1)
                ContentResolver.setSyncAutomatically(account, SYNC_AUTHOR, true) //自动同步
                ContentResolver.addPeriodicSync(account, SYNC_AUTHOR, Bundle(), syncInternal)
            }
        }
    }
}