package com.taptwotimes.dadaacai.model
import android.graphics.drawable.Drawable

data class BebidasProductHome(
    override var title: String?,
    override var subtitle: String?,
    override var image:Int?,
    override var basePrice:String?,
    override var maxTopping:Int? = 6
):ProductHome()
