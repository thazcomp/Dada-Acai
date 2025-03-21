package com.taptwotimes.dadaacai.data.repository.signup

import android.graphics.Bitmap
import android.util.Log
import com.example.coxinhaminha.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import com.taptwotimes.dadaacai.R
import com.taptwotimes.dadaacai.data.preferences.UserPrefs
import com.taptwotimes.dadaacai.model.AcaiProductHome
import com.taptwotimes.dadaacai.model.Address
import com.taptwotimes.dadaacai.model.BebidasProductHome
import com.taptwotimes.dadaacai.model.BoloProductHome
import com.taptwotimes.dadaacai.model.CrepeProductHome
import com.taptwotimes.dadaacai.model.FirebaseCartItem
import com.taptwotimes.dadaacai.model.ProductHome
import com.taptwotimes.dadaacai.model.Topping
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream

class SignUpRepositoryImpl : SignUpRepository {

    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore
    val usersCollection = db.collection("Users")

    override suspend fun getUser(id: String): User {
        val collectionId = "Data"

        val dataSnapshot = usersCollection.document(id)
            .collection(collectionId)
            .document("dados")
            .get().await()

        return User(
            id = dataSnapshot.getString("id") ?: "",
            nome = dataSnapshot.getString("name") ?: "",
            email = dataSnapshot.getString("email") ?: "",
            cpf = dataSnapshot.getString("cpf") ?: "",
            phone = dataSnapshot.getString("phone") ?: "",
            token = dataSnapshot.getString("token") ?: ""
        )
    }


    override suspend fun saveAddress(address: Address, success: () -> Unit, error: () -> Unit) {
        val collectionId = "Data"
        val docId = "Endereco"
        usersCollection.document(UserPrefs.getUserId()!!)
            .collection(collectionId)
            .document(docId)
            .set(address)
            .addOnSuccessListener {
                success()
            }
            .addOnFailureListener {
                error()
            }
    }

    override suspend fun saveUser(user: User, success: () -> Unit, error: () -> Unit) {
        val collectionId = "Data"
        val docId = "Dados"
        usersCollection.document(user.id)
            .collection(collectionId)
            .document(docId)
            .set(user)
            .addOnSuccessListener{
                success()
            }
            .addOnFailureListener {
                error()
            }
    }

    override suspend fun createUser(email:String, password:String, success:(AuthResult)->Unit, error:(Exception)->Unit ) {
        auth = Firebase.auth
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(success).addOnFailureListener(error)
    }

    override suspend fun uploadImageAndStoreURL(imageBitmap: Bitmap, documentId: String) {
        val storage = Firebase.storage
        val storageRef = storage.reference
        val imagesRef = storageRef.child("images") // Criando uma pasta 'images'
        val imageRef = imagesRef.child("$documentId.jpg") // Nome do arquivo, pode ser alterado

        val baos = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        try {
            val uploadTask = imageRef.putBytes(data)
            val downloadUrl = uploadTask.await().storage.downloadUrl.await()

            val db = Firebase.firestore
            val docRef = db.collection("Users").document(documentId)
                            .collection("Data").document("Dados")
            val imageData = hashMapOf(
                "photo" to downloadUrl.toString()
            )
            docRef.set(imageData).await()

            println("Image uploaded successfully! URL: ${downloadUrl.toString()}")
        } catch (e: Exception) {
            println("Error uploading image: ${e.message}")
        }
    }
}