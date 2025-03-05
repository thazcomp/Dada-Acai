package com.taptwotimes.dadaacai.data.repository.cart

import com.taptwotimes.dadaacai.model.FirebaseCartItem

interface CartRepository {
    suspend fun getCart():ArrayList<FirebaseCartItem>
}