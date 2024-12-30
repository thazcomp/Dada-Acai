package com.taptwotimes.dadaacai.ui.login

import android.os.Bundle
import com.taptwotimes.dadaacai.databinding.ActivityLoginBinding
import com.taptwotimes.dadaacai.ui.BaseActivity

class LoginActivity:BaseActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}