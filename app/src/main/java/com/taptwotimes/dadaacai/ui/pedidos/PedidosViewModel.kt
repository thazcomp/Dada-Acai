package com.taptwotimes.dadaacai.ui.pedidos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taptwotimes.dadaacai.model.Pedido
import com.taptwotimes.dadaacai.usecase.PedidosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PedidosViewModel  @Inject constructor(private val useCase: PedidosUseCase): ViewModel()  {

    private val _pedidos:MutableLiveData<ArrayList<Pedido>> = MutableLiveData()
    val pedidos:LiveData<ArrayList<Pedido>> =  _pedidos

    fun getPedidos() = viewModelScope.launch {
        _pedidos.value = useCase.getPedidos()
    }
}