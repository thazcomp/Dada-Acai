package com.taptwotimes.dadaacai.data.repository.cart

import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.firestore
import com.taptwotimes.dadaacai.model.FirebaseCartItem
import com.taptwotimes.dadaacai.model.Topping
import kotlinx.coroutines.tasks.await

class CartRepositoryImpl:CartRepository {

    val db = Firebase.firestore
    val usersCollection = db.collection("Users")

    override suspend fun getCart(): ArrayList<FirebaseCartItem> {
        val userId = "User"
        val cartId = "Cart"
        val cartSnapshot = usersCollection.document(userId).collection(cartId).get().await()

        return cartSnapshot.documents.map { cartDocument ->
            FirebaseCartItem(
                itemName = cartDocument.getString("itemName") ?: "",
                toppings = (cartDocument.get("toppings") as ArrayList<String>),
                totalPrice = cartDocument.getString("totalPrice") ?: ""
            )
        } as ArrayList<FirebaseCartItem>
    }

}