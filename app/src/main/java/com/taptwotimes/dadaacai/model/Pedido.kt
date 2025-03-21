package com.taptwotimes.dadaacai.model

data class Pedido (
    var id:String,
    val payment:String,
    val status:String,
    val itens:ArrayList<FirebaseCartItem>
)