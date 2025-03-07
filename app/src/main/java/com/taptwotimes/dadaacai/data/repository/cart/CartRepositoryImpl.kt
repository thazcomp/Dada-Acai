package com.taptwotimes.dadaacai.data.repository.cart

import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.firestore
import com.taptwotimes.dadaacai.data.preferences.UserPrefs
import com.taptwotimes.dadaacai.model.FirebaseCartItem
import com.taptwotimes.dadaacai.model.Topping
import kotlinx.coroutines.tasks.await

class CartRepositoryImpl:CartRepository {

    val db = Firebase.firestore
    val usersCollection = db.collection("Users")

    @Suppress("UNCHECKED_CAST")
    override suspend fun getCart(): ArrayList<FirebaseCartItem> {
        var userId = "User"
        UserPrefs.getUserId()?.let{
            userId = it
        }
        val cartId = "Cart"
        val cartSnapshot = usersCollection.document(userId).collection(cartId).get().await()

        return cartSnapshot.documents.map { cartDocument ->
            FirebaseCartItem(
                id = cartDocument.getLong("id")?.toInt() ?: -1,
                itemName = cartDocument.getString("itemName") ?: "",
                toppings = (cartDocument.get("toppings") as ArrayList<String>),
                totalPrice = cartDocument.getString("totalPrice") ?: ""
            )
        } as ArrayList<FirebaseCartItem>
    }

    override suspend fun delete(id: Int) {
        val userId = "User"
        val cartId = "Cart"
        val cartSnapshot = usersCollection.document(userId).collection(cartId).get().await()
        val writeBatch = db.batch()
        cartSnapshot.documents.map { cartDocument ->
            val documentId:Int = (cartDocument.get("id") as Long).toInt()
            if(documentId.equals(id)){
                writeBatch.delete(cartDocument.reference)
            }
        }
        writeBatch.commit().await()
    }

    override suspend fun clean() {
        val userId = "User"
        val cartId = "Cart"
        val cartSnapshot = usersCollection.document(userId).collection(cartId).get().await()
        val writeBatch = db.batch()
        cartSnapshot.documents.map { cartDocument ->
            writeBatch.delete(cartDocument.reference)
        }
        writeBatch.commit().await()
    }

}