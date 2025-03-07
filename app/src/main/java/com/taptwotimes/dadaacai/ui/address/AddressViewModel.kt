package com.taptwotimes.dadaacai.ui.address

import androidx.lifecycle.ViewModel
import com.example.coxinhaminha.model.User
import com.example.coxinhaminha.model.UserLogin
import com.taptwotimes.dadaacai.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(private val loginUseCase: LoginUseCase): ViewModel() {

//    suspend fun doLogin(userLogin: UserLogin):User?{
//        return loginUseCase.doLogin(userLogin)
//    }
}