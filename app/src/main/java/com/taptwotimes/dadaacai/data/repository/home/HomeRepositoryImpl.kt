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
            val productsSnapshot = productsCollection.get().await()
            var document = productsSnapshot.documents[0]
            var data = document.data

            itemList.add(
                AcaiProductHome(
                    title = data?.get("title") as String?,
                    subtitle = data?.get("subtitle") as String?,
                    image = R.drawable.acai4,
                    basePrice = data?.get("basePrice") as String?
                )
            )

            document = productsSnapshot.documents[1]
            data = document.data
            itemList.add(CrepeProductHome(
                title = data?.get("title") as String?,
                subtitle = data?.get("subtitle") as String?,
                image = R.drawable.crepe2,
                basePrice = data?.get("basePrice") as String?
            ))

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return itemList
    }

    override suspend fun getToppings(id: String): java.util.ArrayList<Topping> {
        val toppingsCollection = productsCollection.document(id)
            .collection("Coberturas")
            .document("Categorias")
            .collection("Cobertura")

        val toppinsSnapshot = toppingsCollection.get().await()
        return  toppinsSnapshot.documents.map { toppingDocument ->
            Topping(
                name = toppingDocument.getString("name") ?: "",
                price = toppingDocument.getString("price") ?: ""
            )
        } as ArrayList<Topping>
    }
}