package com.taptwotimes.dadaacai.model

import android.graphics.drawable.Drawable
import android.media.Image

data class CartItemAcai(
    val id: Int,
    val image: Drawable,
    val title: String,
    val top1: String?,
    val top2: String?,
    val top3: String?,
    val total: String,
)
