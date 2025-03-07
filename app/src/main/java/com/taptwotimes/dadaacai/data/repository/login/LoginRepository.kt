package com.taptwotimes.dadaacai.data.repository.login

import android.content.Context
import com.example.coxinhaminha.model.User
import com.example.coxinhaminha.model.UserLogin
import com.taptwotimes.dadaacai.data.results.APIResult
import com.taptwotimes.dadaacai.ui.base.BaseActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    suspend fun doLogin(email:String,
                        pass:String,
                        activity: BaseActivity,
                        success:()->Unit)
}