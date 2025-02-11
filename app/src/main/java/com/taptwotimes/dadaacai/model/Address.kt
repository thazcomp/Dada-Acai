package com.taptwotimes.dadaacai.model

data class Address(
    val id: Int,
    val rua: String,
    val bairro:String,
    val numero:String,
    val complemento:String?,
    val cep: String,
)
