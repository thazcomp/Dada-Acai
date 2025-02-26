package com.taptwotimes.dadaacai.data.repository.home

import android.content.Context
import com.taptwotimes.dadaacai.data.results.APIResult
import com.taptwotimes.dadaacai.model.ItemHome
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getHome(): ArrayList<ItemHome>
}