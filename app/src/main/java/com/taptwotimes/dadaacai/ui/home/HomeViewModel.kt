package com.taptwotimes.dadaacai.ui.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coxinhaminha.model.User
import com.taptwotimes.dadaacai.data.domain.usecase.GetHomeListUseCase
import com.taptwotimes.dadaacai.data.intents.HomeIntent
import com.taptwotimes.dadaacai.data.intents.HomeScreenUiState
import com.taptwotimes.dadaacai.data.results.APIResult
import com.taptwotimes.dadaacai.model.ItemHome
import com.taptwotimes.dadaacai.usecase.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeUseCase: HomeUseCase): ViewModel() {

    private val _response: MutableLiveData<ArrayList<ItemHome>> = MutableLiveData()
    val response: LiveData<ArrayList<ItemHome>> = _response

    fun getHome() = viewModelScope.launch {
        _response.value = homeUseCase.getHome()
    }

}

data class GetHomeUiState(
    val home: ArrayList<ItemHome> = arrayListOf(),
    val isLoading:Boolean = false,
    val isError:Boolean = false
)