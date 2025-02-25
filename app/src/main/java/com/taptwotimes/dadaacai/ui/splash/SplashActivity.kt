package com.taptwotimes.dadaacai.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Handler
import com.taptwotimes.dadaacai.databinding.ActivitySplashBinding
import com.taptwotimes.dadaacai.ui.base.BaseActivity
import com.taptwotimes.dadaacai.ui.main.MainActivity


class SplashActivity: BaseActivity(){

    private lateinit var binding:ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val animation:AnimationDrawable = binding.ivSplash.background as AnimationDrawable
        animation.start().also {
            val handler = Handler(this@SplashActivity.mainLooper)

            handler.postDelayed({
                val i = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(i)
                finish()
            }, 3000)
        }

    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {

    }


}