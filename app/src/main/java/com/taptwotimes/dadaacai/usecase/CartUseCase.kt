package com.taptwotimes.dadaacai.usecase

import com.taptwotimes.dadaacai.data.repository.cart.CartRepository
import com.taptwotimes.dadaacai.model.FirebaseCartItem
import javax.inject.Inject

class CartUseCase @Inject constructor(private val repository: CartRepository) {

    suspend fun getCartList():ArrayList<FirebaseCartItem> {
        return repository.getCart()
    }

    suspend fun clean() {
        return repository.cleanCart()
    }

    suspend fun delete(id:Int){
        return repository.delete(id)
    }

    suspend fun createPedido(itens: ArrayList<FirebaseCartItem>) {
        return repository.createPedidos(itens).also {
            clean()
        }
    }
}