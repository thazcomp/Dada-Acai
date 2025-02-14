package com.taptwotimes.dadaacai.ui.signup

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.taptwotimes.dadaacai.databinding.ActivitySignupBinding
import com.taptwotimes.dadaacai.ui.base.BaseActivity
import com.taptwotimes.dadaacai.ui.home.HomeActivity


class SignUpActivity: BaseActivity()  {
    private lateinit var binding: ActivitySignupBinding
    private val viewModel:SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setButtonsClickListener()
    }

    private fun setButtonsClickListener() {
//        binding.btEntrar.setOnClickListener {
//            val intent =  Intent(this, HomeActivity::class.java)
//            startActivity(intent)
//        }
    }

    private fun verifyButtonEnabled() {
        TODO("Not yet implemented")
    }


}