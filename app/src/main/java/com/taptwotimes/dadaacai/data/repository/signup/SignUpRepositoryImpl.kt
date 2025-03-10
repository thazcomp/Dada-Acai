package com.taptwotimes.dadaacai.data.repository.signup

import android.util.Log
import com.example.coxinhaminha.model.User
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.taptwotimes.dadaacai.R
import com.taptwotimes.dadaacai.model.AcaiProductHome
import com.taptwotimes.dadaacai.model.BebidasProductHome
import com.taptwotimes.dadaacai.model.BoloProductHome
import com.taptwotimes.dadaacai.model.CrepeProductHome
import com.taptwotimes.dadaacai.model.FirebaseCartItem
import com.taptwotimes.dadaacai.model.ProductHome
import com.taptwotimes.dadaacai.model.Topping
import kotlinx.coroutines.tasks.await

class SignUpRepositoryImpl:SignUpRepository {

    val db = Firebase.firestore
    val usersCollection = db.collection("Users")

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
        return users[0]
    }
}