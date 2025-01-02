package com.taptwotimes.dadaacai.data.repository.login

import com.example.coxinhaminha.model.User
import com.example.coxinhaminha.model.UserLogin
import com.taptwotimes.dadaacai.data.results.APIResult
import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    fun doLoginFlow(userLogin: UserLogin): Flow<APIResult<User>>
}