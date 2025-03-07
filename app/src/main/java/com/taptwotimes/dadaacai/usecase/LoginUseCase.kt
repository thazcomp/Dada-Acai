package com.taptwotimes.dadaacai.usecase

import com.taptwotimes.dadaacai.data.preferences.UserPrefs
import com.taptwotimes.dadaacai.data.repository.login.LoginRepository
import com.taptwotimes.dadaacai.ui.base.BaseActivity
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val loginRepository: LoginRepository) {

    suspend fun doLogin(email:String, senha:String, activity: BaseActivity, success:()->Unit){
        loginRepository.doLogin(email, senha, activity, success)
    }
}