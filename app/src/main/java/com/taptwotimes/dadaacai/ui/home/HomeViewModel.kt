package com.taptwotimes.dadaacai.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taptwotimes.dadaacai.model.AcaiProductHome
import com.taptwotimes.dadaacai.model.CrepeProductHome
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

    private val _topOptions: MutableLiveData<ArrayList<Topping>> = MutableLiveData()
    val topOptions: LiveData<ArrayList<Topping>> = _topOptions

    private val _bottomOptions: MutableLiveData<ArrayList<Topping>> = MutableLiveData()
    val bottomOptions: LiveData<ArrayList<Topping>> = _bottomOptions

    private val _selectedToppings: MutableLiveData<ArrayList<Topping>> = MutableLiveData()
    val selecteToppings: LiveData<ArrayList<Topping>> = _selectedToppings

    fun getHome() = viewModelScope.launch {
        _home.value = homeUseCase.getHome()
    }

    fun getSelectedToppings(id:String) = viewModelScope.launch {
        _selectedToppings.value = homeUseCase.getToppings(id)
    }

    fun addSelectedTopping(topping: Topping) = viewModelScope.launch {
        _selectedToppings.value?.add(topping)
    }

    fun removeSelectedTopping(topping: Topping) = viewModelScope.launch {
        _selectedToppings.value?.remove(topping)
    }

    fun getTopOptions(id:String, name:String, category:String) = viewModelScope.launch {
        _topOptions.value = homeUseCase.getToppings(id, name, category)
    }

    fun clearTopOptions(){
        _topOptions.value = arrayListOf()
    }

    fun getBottomOptions(id:String, name:String, category:String) = viewModelScope.launch {
        _bottomOptions.value = homeUseCase.getToppings(id, name, category)
    }

    fun clearBottomOptions(){
        _bottomOptions.value = arrayListOf()
    }

    fun getSelectedItemValue(index:Int):ProductHome?{
        return _home.value?.get(index)
    }

}