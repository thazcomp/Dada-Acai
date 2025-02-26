package com.taptwotimes.dadaacai.usecase

import android.content.Context
import com.taptwotimes.dadaacai.data.repository.home.HomeRepository
import com.taptwotimes.dadaacai.data.results.APIResult
import com.taptwotimes.dadaacai.model.ItemHome
import com.taptwotimes.dadaacai.ui.home.GetHomeUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import kotlin.contracts.Returns

class HomeUseCase @Inject constructor(private val homeRepository: HomeRepository, private val viewModelScope: CoroutineScope) {

    fun getHome():ArrayList<ItemHome> {
        val home = homeRepository.getHome()
        return home
    }
}