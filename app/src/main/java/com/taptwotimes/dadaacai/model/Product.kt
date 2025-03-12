package com.taptwotimes.dadaacai.model

data class Product(
    val id: Int,
    val title:String,
    val subtitle:String,
    val value: Double,
    val toppings:ArrayList<Topping>
)
