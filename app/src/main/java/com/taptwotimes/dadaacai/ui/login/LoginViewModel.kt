package com.taptwotimes.dadaacai.ui.login

import androidx.lifecycle.ViewModel
import com.example.coxinhaminha.model.User
import com.example.coxinhaminha.model.UserLogin
import com.taptwotimes.dadaacai.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase): ViewModel() {

    suspend fun doLogin(userLogin: UserLogin):User?{
        return loginUseCase.doLogin(userLogin)
    }
}

data class UserLoginUiState(
    val user: User? = null,
    val isLoading:Boolean = false,
    val isError:Boolean = false
)