package com.taptwotimes.dadaacai.data.repository.home

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.taptwotimes.dadaacai.R
import com.taptwotimes.dadaacai.model.AcaiProductHome
import com.taptwotimes.dadaacai.model.CrepeProductHome
import com.taptwotimes.dadaacai.model.ProductHome
import com.taptwotimes.dadaacai.model.Topping
import kotlinx.coroutines.tasks.await

class HomeRepositoryImpl:HomeRepository {

    val db = Firebase.firestore
    val productsCollection = db.collection("Products")


    override suspend fun getHome(): ArrayList<ProductHome> {
        val itemList = arrayListOf<ProductHome>()
        try {
            val querySnapshot = productsCollection.get().await()
            var data = querySnapshot.documents[0].data

            itemList.add(AcaiProductHome(
                title = data?.get("title") as String?,
                subtitle = data?.get("subtitle") as String?,
                coberturas = createAcaiOptions(),
                image = R.drawable.acai4,
                basePrice = data?.get("basePrice") as String?
            ))

            data = querySnapshot.documents[1].data
            itemList.add(CrepeProductHome(
                title = data?.get("title") as String?,
                subtitle = data?.get("subtitle") as String?,
                coberturas = createCrepeOptions(),
                image = R.drawable.crepe2,
                basePrice = data?.get("basePrice") as String?
            ))

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return itemList
    }

    fun createAcaiOptions():ArrayList<Topping>{
        val topping = arrayListOf<Topping>(
            Topping("Leite Condensado", "0,00"),
            Topping("Morango", "0,00"),
            Topping("Paçoca", "0,00")
        )
        return topping
    }

    fun createCrepeOptions():ArrayList<Topping>{
        val topping = arrayListOf<Topping>(
            Topping("Frango vom Queijo", "0,00"),
            Topping("Bordas Rechedas: Sim", "5,00"),
        )
        return topping
    }
}