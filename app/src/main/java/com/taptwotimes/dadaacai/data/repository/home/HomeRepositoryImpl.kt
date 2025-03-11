package com.taptwotimes.dadaacai.data.repository.home

import android.util.Log
import com.example.coxinhaminha.model.User
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.taptwotimes.dadaacai.R
import com.taptwotimes.dadaacai.data.preferences.UserPrefs
import com.taptwotimes.dadaacai.model.AcaiProductHome
import com.taptwotimes.dadaacai.model.BebidasProductHome
import com.taptwotimes.dadaacai.model.BoloProductHome
import com.taptwotimes.dadaacai.model.CrepeProductHome
import com.taptwotimes.dadaacai.model.FirebaseCartItem
import com.taptwotimes.dadaacai.model.ProductHome
import com.taptwotimes.dadaacai.model.Topping
import kotlinx.coroutines.tasks.await

class HomeRepositoryImpl:HomeRepository {

    val db = Firebase.firestore
    val usersCollection = db.collection("Users")
    val productsCollection = db.collection("Products")

    override suspend fun getHome(): ArrayList<ProductHome> {
        val itemList = arrayListOf<ProductHome>()
        try {
            val productsSnapshot = productsCollection.get().await()
            var document = productsSnapshot.documents[0]
            var data = document.data

            itemList.add(
                AcaiProductHome(
                    title = data?.get("title") as String,
                    subtitle = data?.get("subtitle") as String?,
                    image = R.drawable.acai4,
                    basePrice = data?.get("basePrice") as String
                )
            )

            document = productsSnapshot.documents[1]
            data = document.data
            itemList.add(BebidasProductHome(
                title = data?.get("title") as String,
                subtitle = data?.get("subtitle") as String,
                image = R.drawable.bebidas,
                basePrice = data?.get("basePrice") as String
            ))

            document = productsSnapshot.documents[2]
            data = document.data
            itemList.add(
                BoloProductHome(
                    title = data?.get("title") as String,
                    subtitle = data?.get("subtitle") as String?,
                    image = R.drawable.bolo,
                    basePrice = data?.get("basePrice") as String
                )
            )

            document = productsSnapshot.documents[3]
            data = document.data
            itemList.add(
                CrepeProductHome(
                    title = data?.get("title") as String,
                    subtitle = data?.get("subtitle") as String?,
                    image = R.drawable.crepe2,
                    basePrice = data?.get("basePrice") as String
                )
            )

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

    override suspend fun getToppings(id: String, name:String, category:String): java.util.ArrayList<Topping> {
        val toppingsCollection = productsCollection.document(id)
            .collection(name)
            .document("Categorias")
            .collection(category)

        val toppinsSnapshot = toppingsCollection.get().await()
        return  toppinsSnapshot.documents.map { toppingDocument ->
            Topping(
                name = toppingDocument.getString("name") ?: "",
                price = toppingDocument.getString("price") ?: ""
            )
        } as ArrayList<Topping>
    }

    override suspend fun saveToCart(id: Int, product: ProductHome, toppings:ArrayList<String>){
        val cartItem = FirebaseCartItem(id, product.title, toppings, product.basePrice)

        db.collection("Users").document(UserPrefs.getUserId()!!).collection("Cart").add(cartItem)
            .addOnSuccessListener {
                Log.d("Firestore", "DocumentSnapshot successfully written!")
            }.addOnFailureListener { e->
                Log.w("Firestore", "Error writing document", e)
            }
    }

    override suspend fun getUser(id: String): User {
        val collectionId = "Data"

        val dataSnapshot = usersCollection.document(id).collection(collectionId).get().await()
        val users = dataSnapshot.documents.map { dataDocument ->
            User(
                id = dataDocument.getString("id") ?: "",
                nome = dataDocument.getString("name") ?: "",
                email = dataDocument.getString("email") ?: "",
                cpf = dataDocument.getString("cpf") ?: "",
                phone = dataDocument.getString("phone") ?: ""
            )
        }
        val user = users[0]
        return user
    }
}