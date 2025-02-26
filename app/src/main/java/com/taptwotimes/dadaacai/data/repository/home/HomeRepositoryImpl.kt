package com.taptwotimes.dadaacai.data.repository.home

import android.content.Context
import androidx.appcompat.content.res.AppCompatResources
import com.taptwotimes.dadaacai.R
import com.taptwotimes.dadaacai.data.results.APIResult
import com.taptwotimes.dadaacai.model.ItemHome
import com.taptwotimes.dadaacai.model.Options
import com.taptwotimes.dadaacai.model.Topping
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepositoryImpl:HomeRepository {

    override fun getHome(): ArrayList<ItemHome> {
        return createItemList()
    }

    fun createItemList():ArrayList<ItemHome> {
        val itemList = arrayListOf<ItemHome>()
        itemList.add(
            ItemHome(
                "Açaí Mania",
                "Refresque o seu dia com o melhor Açaí da cidade!",
                arrayListOf<Options>(
                    Options(
                        "Topping 1:", arrayListOf<Topping>(
                            Topping("Leite Condensado", 0.00)
                        )
                    ),
                    Options(
                        "Topping 2:", arrayListOf<Topping>(
                            Topping("Morango", 0.00)
                        )
                    ),
                    Options(
                        "Topping 1:", arrayListOf<Topping>(
                            Topping("Paçoca", 0.00)
                        )
                    )
                ),
                R.drawable.acai4,
                15.00
            ),

            )

        itemList.add(
            ItemHome(
                "Crepe Mania",
                "Subtitulo!!",
                arrayListOf<Options>(
                    Options(
                        "Recheio:", arrayListOf<Topping>(
                            Topping("Presunto e Queijo", 0.00)
                        )
                    ),
                    Options(
                        "B. Recheadas:", arrayListOf<Topping>(
                            Topping("B. Recheadas: Sim", 5.00)
                        )
                    )
                ),
                R.drawable.crepe2,
                15.00
            ),

            )
        return itemList
    }
}