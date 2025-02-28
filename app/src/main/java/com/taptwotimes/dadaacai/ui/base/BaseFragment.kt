package com.taptwotimes.dadaacai.ui.base

import android.media.MediaPlayer
import android.os.Handler
import androidx.fragment.app.Fragment

open class BaseFragment: Fragment() {
    private lateinit var player: MediaPlayer

    internal fun playSound(sound: Int, time:Long) {
        this.activity?.mainLooper?.let{
            val handler = Handler(it)
            handler.postDelayed({
                player = MediaPlayer.create(this.activity, sound)
                player.start()
            }, time)
        }
    }
}