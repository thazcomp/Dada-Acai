package com.taptwotimes.dadaacai.data.repository.home

import android.content.Context
import com.taptwotimes.dadaacai.data.results.APIResult
import com.taptwotimes.dadaacai.model.ProductHome
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getHome(): ArrayList<ProductHome>
}