package com.taptwotimes.dadaacai.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taptwotimes.dadaacai.data.preferences.ProducrPrefs
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

    private val _selectedTopping1: MutableLiveData<Topping?> = MutableLiveData()
    val selecteTopping1: LiveData<Topping?> = _selectedTopping1

    private val _selectedTopping2: MutableLiveData<Topping?> = MutableLiveData()
    val selecteTopping2: LiveData<Topping?> = _selectedTopping2

    private val _selectedTopping3: MutableLiveData<Topping?> = MutableLiveData()
    val selecteTopping3: LiveData<Topping?> = _selectedTopping3

    fun getHome() = viewModelScope.launch {
        _home.value = homeUseCase.getHome()
    }

    fun setSelectedTopping1() = viewModelScope.launch {
        ProducrPrefs.getAcaiTopping1()?.let{
            _selectedTopping1.value = it
        }?:run{
            _selectedTopping1.value = null
        }
    }

    fun setSelectedTopping2() = viewModelScope.launch {
        ProducrPrefs.getAcaiTopping2()?.let{
            _selectedTopping2.value = it
        }?:run{
            _selectedTopping2.value = null
        }
    }

    fun setSelectedTopping3() = viewModelScope.launch {
        ProducrPrefs.getAcaiTopping3()?.let{
            _selectedTopping3.value = it
        }?:run{
            _selectedTopping3.value = null
        }
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