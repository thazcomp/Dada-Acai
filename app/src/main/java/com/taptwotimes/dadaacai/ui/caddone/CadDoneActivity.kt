package com.taptwotimes.dadaacai.ui.caddone

import android.annotation.SuppressLint
import android.os.Bundle
import com.taptwotimes.dadaacai.databinding.ActivityCaddoneBinding
import com.taptwotimes.dadaacai.ui.base.BaseActivity

class CadDoneActivity:BaseActivity() {

    private lateinit var binding:ActivityCaddoneBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCaddoneBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    @Deprecated("Deprecated in Java")
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {

    }
}