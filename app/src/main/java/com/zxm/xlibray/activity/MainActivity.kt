package com.zxm.xlibray.activity

import android.app.Activity
import android.os.Bundle
import com.steven.baselibrary.App
import com.steven.baselibrary.widget.MyToast
import com.zxm.xlibray.R
import com.zxm.xlibray.base.BaseActivity
import java.util.*

class MainActivity : BaseActivity() {
    override fun setLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private val timer = Timer()
    private var isQuit = false

    override fun onBackPressed() {
        if (!isQuit) {
            isQuit = true
            MyToast.getInstance().showTip(this,"再按一次退出App")
            val task = object : TimerTask() {
                override fun run() {
                    isQuit = false
                }
            }
            timer.schedule(task, 2000)
        } else {
            App.getInstance().exit()
        }
    }
}
