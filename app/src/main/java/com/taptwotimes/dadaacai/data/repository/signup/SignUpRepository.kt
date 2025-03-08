package com.taptwotimes.dadaacai.data.repository.signup

import android.content.Context
import com.example.coxinhaminha.model.User
import com.taptwotimes.dadaacai.data.results.APIResult
import com.taptwotimes.dadaacai.model.ProductHome
import com.taptwotimes.dadaacai.model.Topping
import kotlinx.coroutines.flow.Flow

interface SignUpRepository {
    suspend fun getUser(id: String): User
}