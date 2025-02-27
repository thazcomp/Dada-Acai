package com.taptwotimes.dadaacai.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taptwotimes.dadaacai.model.ProductHome
import com.taptwotimes.dadaacai.usecase.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeUseCase: HomeUseCase): ViewModel() {

    private val _response: MutableLiveData<ArrayList<ProductHome>> = MutableLiveData()
    val response: LiveData<ArrayList<ProductHome>> = _response

    fun getHome() = viewModelScope.launch {
        _response.value = homeUseCase.getHome()
    }

}