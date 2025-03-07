package com.taptwotimes.dadaacai.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.taptwotimes.dadaacai.databinding.ActivityLoginBinding
import com.taptwotimes.dadaacai.ui.base.BaseActivity
import com.taptwotimes.dadaacai.ui.home.HomeActivity
import com.taptwotimes.dadaacai.ui.signup.SignUpActivity
import dagger.hilt.android.AndroidEntryPoint

class LoginActivity : BaseActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var auth: FirebaseAuth
    private val uid: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setButtonsClickListener()
    }

    private fun setButtonsClickListener() {
        binding.btEntrar.setOnClickListener {
            auth.signInWithEmailAndPassword(getLoginString(), getPassString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                    }
                }
        }

        binding.tvCriarConta.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun verifyButtonEnabled() {
        TODO("Not yet implemented")
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {

    }

    private fun getLoginString(): String {
        return binding.etEmail.text.toString()
    }

    private fun getPassString(): String {
        return binding.etPass.text.toString()
    }

}