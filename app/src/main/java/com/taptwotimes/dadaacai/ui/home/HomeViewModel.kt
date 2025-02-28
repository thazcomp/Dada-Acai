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

    private val _options: MutableLiveData<ArrayList<Topping>> = MutableLiveData()
    val options: LiveData<ArrayList<Topping>> = _options

    private val _selectedToppings: MutableLiveData<ArrayList<Topping>> = MutableLiveData()
    val selecteToppings: LiveData<ArrayList<Topping>> = _selectedToppings

    private val _selectedItemType: MutableLiveData<ProductHome> = MutableLiveData()
    val selectedItemType: LiveData<ProductHome> = _selectedItemType

    fun getHome() = viewModelScope.launch {
        _home.value = homeUseCase.getHome()
    }

    fun getSelectedToppings(id:String) = viewModelScope.launch {
        _selectedToppings.value = homeUseCase.getToppings(id)
    }

    fun getOptions(id:String) = viewModelScope.launch {
        _options.value = homeUseCase.getToppings(id)
    }

    fun getOptions(id:String, name:String, category:String) = viewModelScope.launch {
        _options.value = homeUseCase.getToppings(id, name, category)
    }

    fun getItemType(type:Any) = viewModelScope.launch {
        when(type){
            is AcaiProductHome -> { _selectedItemType.value = type }
            is CrepeProductHome -> {  _selectedItemType.value = type }
        }
    }

    fun getSelectedItemValue(index:Int):ProductHome?{
        return _home.value?.get(index)
    }

    fun clearOptions(){
        _options.value = arrayListOf()
    }

}