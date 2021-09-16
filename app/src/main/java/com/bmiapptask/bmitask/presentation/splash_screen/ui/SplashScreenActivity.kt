package com.bmiapptask.bmitask.presentation.splash_screen.ui

import android.content.Intent
import com.bmiapptask.dmytrodyshuk.R
import com.bmiapptask.bmitask.presentation.common.ui.BaseActivity
import com.bmiapptask.bmitask.presentation.main_activity.ui.MainActivity

class SplashScreenActivity: BaseActivity() {

    override fun getResourceId() = R.layout.activity_splash_screen

    override fun init() {
        super.init()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}