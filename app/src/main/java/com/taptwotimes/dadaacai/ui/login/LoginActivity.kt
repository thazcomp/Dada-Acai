package com.taptwotimes.dadaacai.ui.login

import android.credentials.CredentialOption
import android.credentials.GetCredentialRequest
import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.taptwotimes.dadaacai.R
import com.taptwotimes.dadaacai.databinding.ActivityLoginBinding
import com.taptwotimes.dadaacai.ui.base.BaseActivity


class LoginActivity: BaseActivity()  {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel:LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        verifyButtonEnabled()
        setButtonsClickListener()
    }

    private fun setButtonsClickListener() {

    }

    private fun verifyButtonEnabled() {
        TODO("Not yet implemented")
    }


}