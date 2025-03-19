package com.taptwotimes.dadaacai.data.repository.cart

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.firestore
import com.taptwotimes.dadaacai.data.preferences.UserPrefs
import com.taptwotimes.dadaacai.model.FirebaseCartItem
import com.taptwotimes.dadaacai.model.Pedido
import com.taptwotimes.dadaacai.model.Topping
import kotlinx.coroutines.tasks.await

class CartRepositoryImpl : CartRepository {

    val db = Firebase.firestore
    val usersCollection = db.collection("Users")
    val pedidosCollection = db.collection("Pedidos")

    @Suppress("UNCHECKED_CAST")
    override suspend fun getCart(): ArrayList<FirebaseCartItem> {
        var userId = UserPrefs.getUserId()
        val cartId = "Cart"
            val cartSnapshot = usersCollection.document(userId!!).collection(cartId).get().await()

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
        val userId = UserPrefs.getUserId()!!
        val cartId = "Cart"
        val cartSnapshot = usersCollection.document(userId).collection(cartId).get().await()
        val writeBatch = db.batch()
        cartSnapshot.documents.map { cartDocument ->
            val documentId: Int = (cartDocument.get("id") as Long).toInt()
            if (documentId.equals(id)) {
                writeBatch.delete(cartDocument.reference)
            }
        }
        writeBatch.commit().await()
    }

    override suspend fun clean() {
        val userId = UserPrefs.getUserId()!!
        val cartId = "Cart"
        val cartSnapshot = usersCollection.document(userId).collection(cartId).get().await()
        val writeBatch = db.batch()
        cartSnapshot.documents.map { cartDocument ->
            writeBatch.delete(cartDocument.reference)
        }
        writeBatch.commit().await()
    }

    override suspend fun createPedidos(itens: ArrayList<FirebaseCartItem>) {
        val pedido = Pedido("", UserPrefs.getPaymentMethod()!!, "RECEBIDO", itens)
        usersCollection.document(UserPrefs.getUserId()!!)
            .collection("Pedidos")
            .add(pedido)
            .addOnSuccessListener {
                pedido.id = it.id
                val atualizacoes = mapOf("id" to it.id)
                usersCollection.document(UserPrefs.getUserId()!!)
                    .collection("Pedidos")
                    .document(it.id)
                    .update(atualizacoes)
                pedidosCollection.document(it.id).set(pedido)
            }.addOnFailureListener { e ->
                Log.w("Firestore", "Error writing document", e)
            }
    }
}