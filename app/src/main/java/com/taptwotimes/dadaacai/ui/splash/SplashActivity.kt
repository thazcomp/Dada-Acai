package com.taptwotimes.dadaacai.ui.splash

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Handler
import com.taptwotimes.dadaacai.databinding.ActivitySplashBinding
import com.taptwotimes.dadaacai.ui.base.BaseActivity
import com.taptwotimes.dadaacai.ui.main.MainActivity


class SplashActivity: BaseActivity(){

    private lateinit var binding:ActivitySplashBinding
    private val MSG_CONTINUE: Int = 1234
    private val DELAY: Long = 2000

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
            }, 5000)
        }

    }


}