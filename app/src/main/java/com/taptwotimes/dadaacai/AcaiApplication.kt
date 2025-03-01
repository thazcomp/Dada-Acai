package com.taptwotimes.dadaacai

import android.app.Application
import com.taptwotimes.dadaacai.data.preferences.ProducrPrefs
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AcaiApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        ProducrPrefs.with(this)
        ProducrPrefs.clear()
    }

}