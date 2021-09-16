package com.bmiapptask.bmitask.presentation.common.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.gms.ads.MobileAds

open class BaseActivity: AppCompatActivity() {

    open fun getResourceId(): Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    open fun init() {
        setContentView(getResourceId())
        onClickListener()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        MobileAds.initialize(this)
    }

    open fun onClickListener() {}

}