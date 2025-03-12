package com.taptwotimes.dadaacai.data.repository.signup

import android.content.Context
import android.graphics.Bitmap
import com.example.coxinhaminha.model.User
import com.google.firebase.auth.AuthResult
import com.taptwotimes.dadaacai.data.results.APIResult
import com.taptwotimes.dadaacai.model.Address
import com.taptwotimes.dadaacai.model.ProductHome
import com.taptwotimes.dadaacai.model.Topping
import kotlinx.coroutines.flow.Flow

interface SignUpRepository {
    suspend fun getUser(id: String): User
    suspend fun saveAddress(address: Address, success:()->Unit, error:()->Unit)
    suspend fun saveUser(user: User, success:()->Unit, error:()->Unit)
    suspend fun createUser(email:String, password:String, success:(AuthResult)->Unit, error:(Exception)->Unit )
    suspend fun uploadImageAndStoreURL(imageBitmap: Bitmap, documentId: String)
}