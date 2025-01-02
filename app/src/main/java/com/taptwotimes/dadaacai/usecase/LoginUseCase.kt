package com.taptwotimes.dadaacai.usecase

import com.example.coxinhaminha.model.User
import com.example.coxinhaminha.model.UserLogin
import com.taptwotimes.dadaacai.data.repository.login.LoginRepository
import com.taptwotimes.dadaacai.data.results.APIResult
import com.taptwotimes.dadaacai.ui.login.UserLoginUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val loginRepository: LoginRepository, private val viewModelScope: CoroutineScope) {

    suspend fun doLogin(userLogin: UserLogin):User?{
        val user = loginRepository.doLoginFlow(userLogin)
        val uiState = user.map{ u ->
            when(u){
                is APIResult.Error -> UserLoginUiState(isError = true)
                is APIResult.Loading -> UserLoginUiState(isLoading = true)
                is APIResult.Success -> UserLoginUiState(user = u.data)
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UserLoginUiState(isLoading = true)
        )
        return uiState.value.user
    }
}