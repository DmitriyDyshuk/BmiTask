package com.bmiapptask.bmitask.presentation.main_activity.ui

import android.content.Context
import android.content.Intent
import android.view.View
import com.bmiapptask.dmytrodyshuk.R
import com.bmiapptask.bmitask.presentation.bmi_details.BmiDetailsActivity
import com.bmiapptask.bmitask.presentation.common.ui.BaseActivity
import com.bmiapptask.bmitask.presentation.main_activity.model.Parameter
import com.bmiapptask.bmitask.utils.AppConstants
import com.bmiapptask.bmitask.utils.view.ViewUtils
import kotlinx.android.synthetic.main.activity_main.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class MainActivity : BaseActivity() {

    private val weightArray = ArrayList<Parameter>()
    private val heightArray = ArrayList<Parameter>()
    private val genderArray = ArrayList<Parameter>()

    private var mInterstitialAd: InterstitialAd? = null

    override fun getResourceId() = R.layout.activity_main

    override fun init() {
        super.init()
        initParametersAdapter()
        initInterstitialAd()
    }

    override fun onClickListener() {
        super.onClickListener()
        btnCalculate.setOnClickListener {
            if (!etName.text.isNullOrEmpty()) {
                if (mInterstitialAd != null) {
                    mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
                        override fun onAdDismissedFullScreenContent() {
                            showBmiDetailsActivity(this@MainActivity)
                        }

                        override fun onAdFailedToShowFullScreenContent(adError: AdError?) {}

                        override fun onAdShowedFullScreenContent() {
                            mInterstitialAd = null
                        }
                    }
                    mInterstitialAd?.show(this)
                }
            } else {
                tvError.visibility = View.VISIBLE
            }
        }
    }

    private fun initParametersAdapter() {
        for (i in 20..150) {
            weightArray.add(Parameter("$i"))
        }

        for (i in 120..200) {
            heightArray.add(Parameter("$i"))
        }

        genderArray.add(0, Parameter("Male"))
        genderArray.add(1, Parameter("Female"))

        ViewUtils.initParametersAdapter(this.rvWeight, weightArray, this)
        ViewUtils.initParametersAdapter(this.rvHeight, heightArray, this)
        ViewUtils.initParametersAdapter(this.rvGender, genderArray, this)
    }

    private fun getCurrentItem(recyclerView: RecyclerView): Int {
        return (recyclerView.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
    }

    private fun initInterstitialAd() {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd
                //showBmiDetailsActivity(intent)
            }
        })

    }

    private fun showBmiDetailsActivity(intent: Context) {
        val userName = etName.text.toString()
        val bmiDetailsActivityIntent = Intent(intent, BmiDetailsActivity::class.java)
        bmiDetailsActivityIntent.putExtra(AppConstants.USER_NAME, userName)
        bmiDetailsActivityIntent.putExtra(AppConstants.WEIGHT, weightArray[getCurrentItem(rvWeight) + 1].title)
        bmiDetailsActivityIntent.putExtra(AppConstants.HEIGHT, heightArray[getCurrentItem(rvHeight) + 1].title)
        bmiDetailsActivityIntent.putExtra(AppConstants.GENDER, genderArray[getCurrentItem(rvGender) + 1].title)
        startActivity(bmiDetailsActivityIntent)
    }

}