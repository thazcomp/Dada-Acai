package com.taptwotimes.dadaacai.usecase

import com.taptwotimes.dadaacai.data.repository.cart.CartRepository
import com.taptwotimes.dadaacai.data.repository.home.HomeRepository
import com.taptwotimes.dadaacai.model.FirebaseCartItem
import com.taptwotimes.dadaacai.model.ProductHome
import com.taptwotimes.dadaacai.model.Topping
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class CartUseCase @Inject constructor(private val repository: CartRepository) {

    suspend fun getCartList():ArrayList<FirebaseCartItem> {
        return repository.getCart()
    }

    suspend fun clean() {
        return repository.clean()
    }

    suspend fun delete(id:Int){
        return repository.delete(id)
    }

    suspend fun createPedido(itens: ArrayList<FirebaseCartItem>) {
        return repository.createPedidos(itens)
    }
}