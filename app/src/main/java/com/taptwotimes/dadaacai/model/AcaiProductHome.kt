package com.taptwotimes.dadaacai.model

data class AcaiProductHome(
    override var title: String?,
    override var subtitle: String?,
    override var coberturas:ArrayList<Topping>?,
    override var image:Int?,
    override var basePrice: String?,
    override var maxTopping:Int? = 3
):ProductHome()
