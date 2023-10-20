package com.bmiapptask.bmitask.presentation.bmi_details

import android.content.Intent
import android.net.Uri
import com.bmiapptask.bmitask.presentation.common.ui.BaseActivity
import com.bmiapptask.bmitask.utils.AppConstants
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.activity_bmi_details.*
import com.bmiapptask.dmytrodyshuk.R
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import androidx.core.content.FileProvider

class BmiDetailsActivity: BaseActivity() {

    private var userName: String? = null
    private var userWeight: String = ""
    private var userHeight: String = ""
    private var userGender: String = ""
    private var calculatedBmi: Float = 0F

    override fun getResourceId() = R.layout.activity_bmi_details

    override fun init() {
        super.init()
        getIntentExtra()
        setUsername()
        calculateBmi()
        setCalculatedBmi()
        initAdb()
    }

    override fun onClickListener() {
        super.onClickListener()
        llRate.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.google.android.play.games"));
            startActivity(intent)
        }

        llShare.setOnClickListener {
            saveScreenshotToInternalStorage()
            shareScreenshot()
        }
    }

    private fun getIntentExtra() {
        userName = intent.getStringExtra(AppConstants.USER_NAME)
        userWeight = intent.getStringExtra(AppConstants.WEIGHT) ?: ""
        userHeight = intent.getStringExtra(AppConstants.HEIGHT) ?: ""
        userGender = intent.getStringExtra(AppConstants.GENDER) ?: ""
    }

    private fun calculateBmi() {
        calculatedBmi = ((userWeight.toFloat() / ((userHeight.toFloat() / 100) * 2)))
    }

    private fun setCalculatedBmi() {
        val stringCalculatedBmi = calculatedBmi.toString()
        val subStringBmiBeforeDat = stringCalculatedBmi.substring(0, stringCalculatedBmi.indexOf("."))
        val subStringBmiAfterDat = stringCalculatedBmi.substring(stringCalculatedBmi.indexOf("."))
        subStringBmiBeforeDat.trim()
        subStringBmiAfterDat.trim()

        tvBmiBeforeDat.text = subStringBmiBeforeDat
        tvBmiAfterDat.text = subStringBmiAfterDat
    }

    private fun setUsername() {
        when {
            calculatedBmi < 18.5F -> tvGreetingText.text = "Hello $userName, you are underweight"
            calculatedBmi > 25F -> tvGreetingText.text = "Hello $userName, you are overweight"
            else -> tvGreetingText.text = "Hello $userName, you are normal"
        }
    }

    private fun initAdb() {
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }

    private fun getScreenshot(view: View): Bitmap? {
        val bitmap = Bitmap.createBitmap(
            view.width,
            view.height, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    private fun saveScreenshotToInternalStorage() {
        try {
            val cachePath = File(this.cacheDir, "images")
            cachePath.mkdirs()
            val stream = FileOutputStream("$cachePath/image.png")
            getScreenshot(window.decorView.rootView)?.compress(Bitmap.CompressFormat.PNG, 100, stream)
            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun shareScreenshot() {
        val imagePath = File(this.cacheDir, "images")
        val newFile = File(imagePath, "image.png")
        val contentUri = FileProvider.getUriForFile(this, "com.bmiapptask.bmitask.fileprovider", newFile)

        if (contentUri != null) {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            shareIntent.setDataAndType(contentUri, contentResolver.getType(contentUri))
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri)
            startActivity(Intent.createChooser(shareIntent, "Choose an app"))
        }
    }

}