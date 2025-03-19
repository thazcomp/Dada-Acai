package com.taptwotimes.dadaacai.data.repository.pedidos

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.taptwotimes.dadaacai.data.preferences.UserPrefs
import com.taptwotimes.dadaacai.model.FirebaseCartItem
import com.taptwotimes.dadaacai.model.Pedido
import kotlinx.coroutines.tasks.await

class PedidosRepositoryImpl: PedidosRepository {

    val db = Firebase.firestore
    val usersCollection = db.collection("Users")
    val pedidosCollection = db.collection("Pedidos")

    override suspend fun getPedidos(): ArrayList<Pedido> {
        var userId = UserPrefs.getUserId()
        val cartId = "Pedidos"
        val cartSnapshot = usersCollection.document(userId!!)
            .collection(cartId).get().await()

        return cartSnapshot.documents.map { pedidoDoc ->
            Pedido(
                id = pedidoDoc.id,
                payment = pedidoDoc.getString("payment") ?: "",
                status = pedidoDoc.getString("status") ?: "",
                itens = (pedidoDoc.get("itens") as ArrayList<FirebaseCartItem>)
            )
        } as ArrayList<Pedido>
    }
}