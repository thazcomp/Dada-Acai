package com.taptwotimes.dadaacai.ui.home

import android.os.Bundle
import com.taptwotimes.dadaacai.databinding.ActivityHomeBinding
import com.taptwotimes.dadaacai.ui.base.BaseActivity

class HomeActivity: BaseActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}