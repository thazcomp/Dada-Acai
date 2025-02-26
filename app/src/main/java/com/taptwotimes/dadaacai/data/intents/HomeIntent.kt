package com.taptwotimes.dadaacai.data.intents

import com.taptwotimes.dadaacai.model.Product
import com.taptwotimes.dadaacai.model.Topping

sealed class HomeIntent {

    data class AddTopping(val topping: Topping?):HomeIntent()
    data class RemoveTopping(val topping: Topping?):HomeIntent()

    data class AddToCart(val product: Product?):HomeIntent()
    data class RemoveFromCart(val product: Product?):HomeIntent()

    data class GetToppings(val list:ArrayList<Topping>?):HomeIntent()

}