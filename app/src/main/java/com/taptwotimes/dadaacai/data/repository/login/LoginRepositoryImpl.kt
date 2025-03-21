package com.taptwotimes.dadaacai.data.repository.login

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.taptwotimes.dadaacai.data.preferences.UserPrefs
import com.taptwotimes.dadaacai.ui.base.BaseActivity
import javax.inject.Singleton

@Singleton
class LoginRepositoryImpl() : LoginRepository {

    private lateinit var auth: FirebaseAuth

    override suspend fun doLogin(email: String, pass: String, activity: BaseActivity, success:()->Unit, error:() -> Unit) {
        auth = Firebase.auth
        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    task.result.user?.let {
                        UserPrefs.setUserId(it.uid)
                        success()
                    }
                }else{
                    error()
                }
            }
    }

}