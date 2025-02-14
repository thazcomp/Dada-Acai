package com.example.coxinhaminha.model

import com.taptwotimes.dadaacai.model.Address

data class User(
    val id: Int,
    val nome:String,
    val email:String,
    val firebaseId:String,
    val enderecos:ArrayList<Address>
)