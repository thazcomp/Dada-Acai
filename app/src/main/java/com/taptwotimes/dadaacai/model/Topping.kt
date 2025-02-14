package com.taptwotimes.dadaacai.model

data class Topping(
    val name:String,
    val price:Double,
    val viewValue:String = "$name $price"
)
