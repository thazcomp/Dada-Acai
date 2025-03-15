package com.taptwotimes.dadaacai.data.repository.home

import com.example.coxinhaminha.model.User
import com.taptwotimes.dadaacai.model.Address
import com.taptwotimes.dadaacai.model.ProductHome
import com.taptwotimes.dadaacai.model.Topping

interface HomeRepository {
    suspend fun getHome(): ArrayList<ProductHome>
    suspend fun getToppings(id: String): java.util.ArrayList<Topping>
    suspend fun getToppings(id: String, name:String, category:String): java.util.ArrayList<Topping>
    suspend fun saveToCart(id:Int, product: ProductHome, toppings:ArrayList<String>)
    suspend fun getUser(id: String): User
    suspend fun getUserAddress(id: String): Address
    suspend fun isReviwed(id: String, success: (Boolean) -> Unit)
}