package com.taptwotimes.dadaacai

import android.app.Application
import com.google.firebase.Firebase
import com.google.firebase.initialize
import com.google.firebase.messaging.messaging
import com.taptwotimes.dadaacai.data.preferences.ProductPrefs
import com.taptwotimes.dadaacai.data.preferences.UserPrefs
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AcaiApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        UserPrefs.with(this)
        ProductPrefs.with(this)
        ProductPrefs.clear()
        Firebase.messaging.isAutoInitEnabled = true
        Firebase.initialize(this)
    }

}