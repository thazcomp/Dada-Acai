package com.taptwotimes.dadaacai.data.repository.home

import android.content.Context
import com.taptwotimes.dadaacai.data.results.APIResult
import com.taptwotimes.dadaacai.model.ProductHome
import com.taptwotimes.dadaacai.model.Topping
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getHome(): ArrayList<ProductHome>
    suspend fun getToppings(id: String): java.util.ArrayList<Topping>
    suspend fun getToppings(id: String, name:String, category:String): java.util.ArrayList<Topping>
    suspend fun saveToCart(product: ProductHome, toppings:ArrayList<String>)
}