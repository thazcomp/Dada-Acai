package com.taptwotimes.dadaacai.ui.base

import android.media.MediaPlayer
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity: AppCompatActivity() {

    private lateinit var player:MediaPlayer

    internal fun playSound(sound: Int, time:Long) {
        val handler = Handler(this.mainLooper)
        handler.postDelayed({
            player = MediaPlayer.create(this, sound)
            player.start()
        }, time)
    }
}