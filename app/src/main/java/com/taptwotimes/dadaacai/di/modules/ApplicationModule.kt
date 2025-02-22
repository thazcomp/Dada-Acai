package com.taptwotimes.dadaacai.di.modules

import com.taptwotimes.dadaacai.data.repository.login.LoginRepository
import com.taptwotimes.dadaacai.data.repository.login.LoginRepositoryImpl
import com.taptwotimes.dadaacai.usecase.LoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    fun provideCoroutineScope():CoroutineScope{
        return CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }

    @Provides
    fun provideLoginRepository(): LoginRepository {
        return LoginRepositoryImpl()
    }

    @Provides
    fun provideLoginUseCase(): LoginUseCase {
        return LoginUseCase(provideLoginRepository(), provideCoroutineScope())
    }
}