package com.example.coxinhaminha.model

import com.taptwotimes.dadaacai.model.Address

data class User(
    val id: String,
    val nome:String,
    val email:String,
    val cpf:String,
    val phone:String,
    var token:String
)