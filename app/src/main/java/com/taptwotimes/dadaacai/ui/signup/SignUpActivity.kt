package com.taptwotimes.dadaacai.ui.signup

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
import java.io.File
import java.io.FileOutputStream
import java.util.Objects


class SignUpActivity: BaseActivity()  {
    private val TAG: String = "SignUpActivity"
    private lateinit var binding: ActivitySignupBinding
    private val viewModel:SignUpViewModel by viewModels()

    val galleryLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent(),
        fun(it: Uri?) {
            val galleryUri = it
            try {
                binding.ivFoto.setImageURI(galleryUri)
                galleryUri?.let{
                    UserPrefs.setUserPhoto(it)
                }
                savePic(binding.ivFoto, "profile", "profile_photo")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setButtonsClickListener()
    }

    private fun setButtonsClickListener() {
        binding.btAddress.setOnClickListener {
            val intent =  Intent(this, AddressActivity::class.java)
            startActivity(intent)
        }

        binding.ivFoto.setOnClickListener {
            pickImage()
        }
    }

    private fun verifyButtonEnabled() {
        TODO("Not yet implemented")
    }

    private fun savePic(imageView: ImageView, subfolder: String, pictureName: String): String? {
        // subfolder: Pflanzen, Rezepte, ..
        val drawable = imageView.drawable as BitmapDrawable
        val bitmap = drawable.bitmap
        try {
            val file = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                    .toString() + "/Pictures/[APP_NAME]/" + subfolder + "/",
                "$pictureName.jpg"
            )

            if (!file.exists()) {
                Log.d(TAG, "Folder '$file' doesn't exist, creating it...")
                val rv: Boolean = Objects.requireNonNull(file.parentFile).mkdir()
                Log.d(TAG, "Folder creation " + (if (rv) "success" else "failed"))
            } else {
                Log.d(TAG, "Folder already exists.")
            }

            val out = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            out.flush()
            out.close()
            return file.absolutePath
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
    }

    private fun pickImage() {
        galleryLauncher.launch("image/*")
    }

}