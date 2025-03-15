package com.taptwotimes.dadaacai.model

data class Pedido (
    val payment:String,
    val itens:ArrayList<FirebaseCartItem>
)