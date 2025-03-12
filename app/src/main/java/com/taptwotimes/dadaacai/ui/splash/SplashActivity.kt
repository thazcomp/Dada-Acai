package com.taptwotimes.dadaacai.ui.splash

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.AnimationDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.taptwotimes.dadaacai.R
import com.taptwotimes.dadaacai.databinding.ActivitySplashBinding
import com.taptwotimes.dadaacai.ui.base.BaseActivity
import com.taptwotimes.dadaacai.ui.main.MainActivity


class SplashActivity: BaseActivity(){

    private lateinit var binding:ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        playSound(R.raw.som_double_click, 850)
        playSound(R.raw.som_magic_shine, 2400)

        val animation:AnimationDrawable = binding.ivSplash.background as AnimationDrawable
        animation.start().also {
            goToMainActivity()
        }
    }

    private fun goToMainActivity() {
        val handler = Handler(this@SplashActivity.mainLooper)

        handler.postDelayed({
            val i = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(i)
            finish()
        }, 3000)
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {

    }

}