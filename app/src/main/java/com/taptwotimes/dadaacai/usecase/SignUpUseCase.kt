package com.taptwotimes.dadaacai.usecase

import com.taptwotimes.dadaacai.data.preferences.UserPrefs
import com.taptwotimes.dadaacai.data.repository.home.HomeRepository
import com.taptwotimes.dadaacai.data.repository.signup.SignUpRepository
import com.taptwotimes.dadaacai.model.ProductHome
import com.taptwotimes.dadaacai.model.Topping
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class SignUpUseCase @Inject constructor(private val signUpRepository: SignUpRepository) {

    suspend fun getUser(id:String, success:()->Unit){
        val user = signUpRepository.getUser(id)
        user?.let{
            UserPrefs.setUserId(it.id)
            UserPrefs.setUserName(it.nome)
            UserPrefs.setUserEmail(it.email)
            success()
        }
    }
}