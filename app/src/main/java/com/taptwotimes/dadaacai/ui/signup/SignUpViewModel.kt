package com.taptwotimes.dadaacai.ui.signup

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coxinhaminha.model.User
import com.example.coxinhaminha.model.UserLogin
import com.google.firebase.auth.AuthResult
import com.taptwotimes.dadaacai.data.preferences.UserPrefs
import com.taptwotimes.dadaacai.usecase.LoginUseCase
import com.taptwotimes.dadaacai.usecase.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val signUpUseCase: SignUpUseCase): ViewModel() {

    fun createUser(email:String, password:String, success: (AuthResult) -> Unit, error: (Exception) -> Unit) = viewModelScope.launch{
        signUpUseCase.createUser(email, password, success, error)
    }
}