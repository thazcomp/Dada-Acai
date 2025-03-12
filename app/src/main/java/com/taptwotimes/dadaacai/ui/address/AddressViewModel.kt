package com.taptwotimes.dadaacai.ui.address

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coxinhaminha.model.User
import com.example.coxinhaminha.model.UserLogin
import com.taptwotimes.dadaacai.data.preferences.UserPrefs
import com.taptwotimes.dadaacai.model.Address
import com.taptwotimes.dadaacai.usecase.LoginUseCase
import com.taptwotimes.dadaacai.usecase.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(private val signupUseCase: SignUpUseCase): ViewModel() {

    fun saveAddress(address: Address, success:()->Unit, error:()->Unit) = viewModelScope.launch {
        signupUseCase.saveAddress(address, success, error)
    }

    fun saveUser(user:User, success:()->Unit, error:()->Unit) = viewModelScope.launch {
        signupUseCase.saveUser(user, success, error)
    }

    fun savePhoto(bitmap: Bitmap) = viewModelScope.launch {
        signupUseCase.savePhoto(bitmap, UserPrefs.getUserId()!!)
    }
}