package com.taptwotimes.dadaacai.ui.main

import android.content.Intent
import android.os.Bundle
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.taptwotimes.dadaacai.ui.base.BaseActivity
import com.taptwotimes.dadaacai.ui.home.HomeActivity
import com.taptwotimes.dadaacai.ui.login.LoginActivity

class MainActivity: BaseActivity(){

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth

        val currentUser = auth.currentUser
        if(currentUser != null){
            goToHome()
        }else{
            goToLogin()
        }
    }

    private fun goToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun goToHome() {
        startActivity(Intent(this, HomeActivity::class.java))
    }
}