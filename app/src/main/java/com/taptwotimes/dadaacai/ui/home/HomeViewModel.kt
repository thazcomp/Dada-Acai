package com.taptwotimes.dadaacai.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taptwotimes.dadaacai.model.ProductHome
import com.taptwotimes.dadaacai.model.Topping
import com.taptwotimes.dadaacai.usecase.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeUseCase: HomeUseCase): ViewModel() {

    private val _home: MutableLiveData<ArrayList<ProductHome>> = MutableLiveData()
    val home: LiveData<ArrayList<ProductHome>> = _home

    private val _selectedToppings: MutableLiveData<ArrayList<Topping>> = MutableLiveData()
    val selecteToppings: LiveData<ArrayList<Topping>> = _selectedToppings

    fun getHome() = viewModelScope.launch {
        _home.value = homeUseCase.getHome()
    }

    fun getSelectedToppings(id:String) = viewModelScope.launch {
        _selectedToppings.value = homeUseCase.getToppings(id)
    }

}