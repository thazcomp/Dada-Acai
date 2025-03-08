package com.taptwotimes.dadaacai.ui.signup

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.taptwotimes.dadaacai.data.preferences.UserPrefs
import com.taptwotimes.dadaacai.databinding.ActivitySignupBinding
import com.taptwotimes.dadaacai.ui.address.AddressActivity
import com.taptwotimes.dadaacai.ui.base.BaseActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class SignUpActivity: BaseActivity()  {
    private val TAG: String = "SignUpActivity"
    private lateinit var binding: ActivitySignupBinding
    private var photoFile = File("profile_photo.png")
    private val viewModel:SignUpViewModel by viewModels()

    private var imageUri: Uri? = null

    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            imageUri = uri
            binding.ivFoto.setImageURI(uri)
            saveImageToFile(uri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setButtonsClickListener()
    }

    private fun saveImageToFile(uri: Uri) {
        val inputStream = contentResolver.openInputStream(uri)
        val file = File(filesDir, "selected_image.jpg") // Salva no armazenamento interno

        try {
            val outputStream = FileOutputStream(file)
            inputStream?.copyTo(outputStream)
            inputStream?.close()
            outputStream.close()
            // Agora você tem o arquivo salvo em 'file'
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun setButtonsClickListener() {
        binding.btAddress.setOnClickListener {
            val intent =  Intent(this, AddressActivity::class.java)
            startActivity(intent)
        }

        binding.ivFoto.setOnClickListener {
            pickImage.launch("image/*")
        }
    }

    private fun verifyButtonEnabled() {
        TODO("Not yet implemented")
    }



}