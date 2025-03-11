package com.taptwotimes.dadaacai.usecase

import android.graphics.Bitmap
import com.example.coxinhaminha.model.User
import com.google.firebase.auth.AuthResult
import com.taptwotimes.dadaacai.data.preferences.UserPrefs
import com.taptwotimes.dadaacai.data.repository.home.HomeRepository
import com.taptwotimes.dadaacai.data.repository.signup.SignUpRepository
import com.taptwotimes.dadaacai.model.Address
import com.taptwotimes.dadaacai.model.ProductHome
import com.taptwotimes.dadaacai.model.Topping
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class SignUpUseCase @Inject constructor(private val signUpRepository: SignUpRepository) {

    suspend fun saveAddress(address: Address, success: () -> Unit, error: () -> Unit){
        signUpRepository.saveAddress(address, success, error)
    }

    suspend fun saveUser(user: User, success: () -> Unit, error: () -> Unit){
        signUpRepository.saveUser(user, success, error)
    }

    suspend fun createUser(email:String, pass:String, success: (AuthResult) -> Unit, error: (Exception) -> Unit){
        signUpRepository.createUser(email, pass, success, error)
    }

    suspend fun savePhoto(bitmap: Bitmap, documentId:String){
        signUpRepository.uploadImageAndStoreURL(bitmap, documentId)
    }
}