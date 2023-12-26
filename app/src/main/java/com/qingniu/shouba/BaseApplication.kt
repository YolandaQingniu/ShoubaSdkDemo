package com.qingniu.shouba

import android.app.Application
import android.content.Context
import com.qingniu.shouba.util.BleHelper

class BaseApplication : Application() {

    companion object {
        lateinit var instance: Context
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        BleHelper.initQNPlugin()
        BleHelper.initQNScalePlugin()
    }
}