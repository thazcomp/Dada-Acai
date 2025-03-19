package com.taptwotimes.dadaacai.model

import android.graphics.drawable.Drawable
import android.media.Image

data class FirebaseCartItem(
    val id:Int,
    val itemName: String,
    val toppings: ArrayList<String>,
    val totalPrice: String
)
