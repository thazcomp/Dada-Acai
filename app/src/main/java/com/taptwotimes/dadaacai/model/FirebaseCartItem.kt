package com.taptwotimes.dadaacai.model

import android.graphics.drawable.Drawable
import android.media.Image

data class FirebaseCartItem(
    val itemName: String,
    val toppings: ArrayList<String>,
    val totalPrice: String,
)
