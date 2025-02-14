package com.taptwotimes.dadaacai.data.repository.login

import com.example.coxinhaminha.model.User
import com.example.coxinhaminha.model.UserLogin
import com.taptwotimes.dadaacai.data.results.APIResult
import com.taptwotimes.dadaacai.model.Address
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Singleton

@Singleton
class LoginRepositoryImpl(): LoginRepository {

    override fun doLoginFlow(userLogin: UserLogin): Flow<APIResult<User>> {
        return flow { emit(APIResult.Success(User(1, "name1", "email1", "1", arrayListOf())))}
    }

}