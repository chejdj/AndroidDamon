package com.chejdj.androiddamon.accountsync

import android.accounts.AbstractAccountAuthenticator
import android.accounts.Account
import android.accounts.AccountAuthenticatorResponse
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.IBinder

class AuthenticationService : Service() {
    private var mAuthenticator: AccountAuthenticator? = null
    override fun onBind(intent: Intent?): IBinder {
        return mAuthenticator!!.iBinder
    }

    override fun onCreate() {
        super.onCreate()
        if (mAuthenticator == null) {
            mAuthenticator = AccountAuthenticator(applicationContext)
        }
    }

    companion object {
        class AccountAuthenticator(context: Context) : AbstractAccountAuthenticator(context) {
            override fun getAuthTokenLabel(authTokenType: String?): String? {
                return null //To change body of created functions use File | Settings | File Templates.
            }

            override fun confirmCredentials(
                response: AccountAuthenticatorResponse?,
                account: Account?,
                options: Bundle?
            ): Bundle? {
                return null//To change body of created functions use File | Settings | File Templates.
            }

            override fun updateCredentials(
                response: AccountAuthenticatorResponse?,
                account: Account?,
                authTokenType: String?,
                options: Bundle?
            ): Bundle? {
                return null
            }

            override fun getAuthToken(
                response: AccountAuthenticatorResponse?,
                account: Account?,
                authTokenType: String?,
                options: Bundle?
            ): Bundle? {
                return null //To change body of created functions use File | Settings | File Templates.
            }

            override fun hasFeatures(
                response: AccountAuthenticatorResponse?,
                account: Account?,
                features: Array<out String>?
            ): Bundle? {
                return null//To change body of created functions use File | Settings | File Templates.
            }

            override fun editProperties(response: AccountAuthenticatorResponse?, accountType: String?): Bundle? {
                return null //To change body of created functions use File | Settings | File Templates.
            }

            override fun addAccount(
                response: AccountAuthenticatorResponse?,
                accountType: String?,
                authTokenType: String?,
                requiredFeatures: Array<out String>?,
                options: Bundle?
            ): Bundle? {
                return null //To change body of created functions use File | Settings | File Templates.
            }

        }
    }
}