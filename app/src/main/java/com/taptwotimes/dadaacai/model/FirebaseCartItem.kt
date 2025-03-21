package com.taptwotimes.dadaacai.model

import android.graphics.drawable.Drawable
import android.media.Image

data class FirebaseCartItem(
    var id:Int,
    var itemName: String,
    var toppings: ArrayList<String>,
    var totalPrice: String
)
