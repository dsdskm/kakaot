package com.kakaot.pocketd

import android.app.Application
import android.content.Context

class PkAppClass : Application() {

    override fun onCreate() {
        super.onCreate()
        AppContext = applicationContext
    }

    companion object {

        lateinit var AppContext: Context

    }
}