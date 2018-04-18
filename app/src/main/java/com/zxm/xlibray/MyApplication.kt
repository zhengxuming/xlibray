package com.zxm.xlibray

import android.content.Context
import android.support.multidex.MultiDex
import com.steven.baselibrary.App

class MyApplication: App() {

    override fun onCreate() {
        super.onCreate()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}