package com.taptwotimes.dadaacai.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taptwotimes.dadaacai.data.preferences.ProducrPrefs
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

    private val _selectedAcaiTopping1: MutableLiveData<Topping?> = MutableLiveData()
    val selecteAcaiTopping1: LiveData<Topping?> = _selectedAcaiTopping1

    private val _selectedCrepeTopping1: MutableLiveData<Topping?> = MutableLiveData()
    val selecteCrepeTopping1: LiveData<Topping?> = _selectedCrepeTopping1

    private val _selectedAcaiTopping2: MutableLiveData<Topping?> = MutableLiveData()
    val selecteAcaiTopping2: LiveData<Topping?> = _selectedAcaiTopping2

    private val _selectedCrepeTopping2: MutableLiveData<Topping?> = MutableLiveData()
    val selecteCrepeTopping2: LiveData<Topping?> = _selectedCrepeTopping2

    private val _selectedAcaiTopping3: MutableLiveData<Topping?> = MutableLiveData()
    val selecteAcaiTopping3: LiveData<Topping?> = _selectedAcaiTopping3

    fun getHome() = viewModelScope.launch {
        _home.value = homeUseCase.getHome()
    }

    fun setSelectedAcaiTopping1() = viewModelScope.launch {
        ProducrPrefs.getAcaiTopping1()?.let{
            _selectedAcaiTopping1.value = it
        }?:run{
            _selectedAcaiTopping1.value = null
        }
    }

    fun setSelectedAcaiTopping2() = viewModelScope.launch {
        ProducrPrefs.getAcaiTopping2()?.let{
            _selectedAcaiTopping2.value = it
        }?:run{
            _selectedAcaiTopping2.value = null
        }
    }

    fun setSelectedAcaiTopping3() = viewModelScope.launch {
        ProducrPrefs.getAcaiTopping3()?.let{
            _selectedAcaiTopping3.value = it
        }?:run{
            _selectedAcaiTopping3.value = null
        }
    }

    fun setSelectedCrepeTopping1() = viewModelScope.launch {
        ProducrPrefs.getCrepeTopping1()?.let{
            _selectedCrepeTopping1.value = it
        }?:run{
            _selectedCrepeTopping1.value = null
        }
    }

    fun setSelectedCrepeTopping2() = viewModelScope.launch {
        ProducrPrefs.getCrepeTopping2()?.let{
            _selectedCrepeTopping2.value = it
        }?:run{
            _selectedCrepeTopping2.value = null
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