package com.taptwotimes.dadaacai.ui.signup

import androidx.lifecycle.ViewModel
import com.example.coxinhaminha.model.User
import com.example.coxinhaminha.model.UserLogin
import com.taptwotimes.dadaacai.usecase.LoginUseCase
import com.taptwotimes.dadaacai.usecase.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val signUpUseCase: SignUpUseCase): ViewModel() {

//    suspend fun doLogin(userLogin: UserLogin):User?{
//        return loginUseCase.doLogin(userLogin)
//    }
}