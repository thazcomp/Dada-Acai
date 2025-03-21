package com.taptwotimes.dadaacai.ui.cart

import android.util.Log
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
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
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

    fun sendNotificationToMe(token: String, titulo: String, corpo: String) = viewModelScope.launch {
        //Tratar envio da notificação pelo Backend
    }

    fun sendNotificationToAdm(titulo: String, corpo: String) = viewModelScope.launch {
        //Tratar envio da notificação pelo Backend
    }

}