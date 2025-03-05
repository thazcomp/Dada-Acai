package com.taptwotimes.dadaacai

import android.app.Application
import com.taptwotimes.dadaacai.data.preferences.ProductPrefs
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AcaiApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        ProductPrefs.with(this)
        ProductPrefs.clear()
    }

}