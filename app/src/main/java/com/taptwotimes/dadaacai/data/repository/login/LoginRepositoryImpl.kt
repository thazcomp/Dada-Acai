package com.taptwotimes.dadaacai.data.repository.login

import android.content.Context
import com.example.coxinhaminha.model.User
import com.example.coxinhaminha.model.UserLogin
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.taptwotimes.dadaacai.data.preferences.UserPrefs
import com.taptwotimes.dadaacai.data.results.APIResult
import com.taptwotimes.dadaacai.model.Address
import com.taptwotimes.dadaacai.ui.base.BaseActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Singleton
import kotlin.coroutines.coroutineContext

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