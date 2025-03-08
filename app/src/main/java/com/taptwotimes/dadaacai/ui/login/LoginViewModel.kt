package com.taptwotimes.dadaacai.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taptwotimes.dadaacai.ui.base.BaseActivity
import com.taptwotimes.dadaacai.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase): ViewModel() {

    fun doLogin(email:String, pass:String, activity: BaseActivity, success:() -> Unit, error:() -> Unit) = viewModelScope.launch {
        loginUseCase.doLogin(email, pass, activity, success, error)
    }
}