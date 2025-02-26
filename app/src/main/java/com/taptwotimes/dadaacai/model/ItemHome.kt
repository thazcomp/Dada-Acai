package com.taptwotimes.dadaacai.model
import android.graphics.drawable.Drawable

data class ItemHome(
    val title:String,
    val subtitle:String,
    val options:ArrayList<Options>,
    val image:Int?,
    val totalPrice:Double
)
