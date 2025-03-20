package com.taptwotimes.dadaacai.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taptwotimes.dadaacai.data.preferences.ProductPrefs
import com.taptwotimes.dadaacai.data.preferences.UserPrefs
import com.taptwotimes.dadaacai.model.FirebaseCartItem
import com.taptwotimes.dadaacai.usecase.CartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val cartUseCase: CartUseCase):ViewModel() {

    private val _cart:MutableLiveData<ArrayList<FirebaseCartItem>> = MutableLiveData()
    val cart:LiveData<ArrayList<FirebaseCartItem>> = _cart

    fun getCart() = viewModelScope.launch {
        _cart.value = cartUseCase.getCartList()
    }

    fun clean() = viewModelScope.launch {
        ProductPrefs.clearCartCounter()
        cartUseCase.clean()
    }

    fun delete(id:Int) = viewModelScope.launch {
        ProductPrefs.decreaseCartCounter()
        cartUseCase.delete(id)
    }

    fun cratePedidoFirebase(itens:ArrayList<FirebaseCartItem>) = viewModelScope.launch {
        cartUseCase.createPedido(itens)
        cartUseCase.clean()
    }
}