package com.taptwotimes.dadaacai.ui.signup

import android.Manifest
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.google.firebase.auth.AuthResult
import com.taptwotimes.dadaacai.data.preferences.UserPrefs
import com.taptwotimes.dadaacai.databinding.ActivitySignupBinding
import com.taptwotimes.dadaacai.ui.address.AddressActivity
import com.taptwotimes.dadaacai.ui.base.BaseActivity
import com.taptwotimes.dadaacai.util.CpfUtil
import com.taptwotimes.dadaacai.util.Mask
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

@AndroidEntryPoint
class SignUpActivity: BaseActivity()  {

    private lateinit var binding: ActivitySignupBinding
    private val viewModel:SignUpViewModel by viewModels()

    private var nameError:Boolean = false
    private var emailError:Boolean = false
    private var passError:Boolean = false
    private var cpfError:Boolean = false
    private var phoneError:Boolean = false
    private var hasPhoto:Boolean = false
    private var hasPermissions = false

    private var imageUri: Uri? = null

    private val getContent = registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
        uri?.let {
            imageUri = uri
            binding.ivFoto.setImageURI(uri)
            saveImageToMediaStore(this, uri, "acai_mania_profile_photo.jpg")
            UserPrefs.setUserPhoto(uri.toString())
        }

    }

    fun selectFile() {
        getContent.launch(arrayOf("image/*"))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setButtonsClickListener()
        setTextWatchers()
        binding.tilPhone.editText?.error = "Será realizada uma verificação via WhatsApp"
    }

    private fun setTextWatchers() {
        binding.tilPass.editText?.addTextChangedListener { text ->
            if(greaterThan5(text.toString())){
                showPassError(null)
            }else{
                showPassError("A senha deve ter mais de 5 caracteres")
            }
        }

        binding.tilCpf.editText?.let{
            it.addTextChangedListener(Mask.mask("###.###.###-##", it))
        }

        binding.tilPhone.editText?.let{
            it.addTextChangedListener(Mask.mask("(##)# ####-####", it))
        }
    }

    private fun showPassError(text: String?) {
        binding.tilPass.error = text
    }

    private fun greaterThan5(text:String):Boolean{
        return text.length>5
    }

    private fun greaterThan10(text:String):Boolean{
        return text.length>10
    }

    fun saveImageToMediaStore(context: Context, imageUri: Uri, fileName: String) {
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, context.contentResolver.getType(imageUri)) // Obter o MIME type da Uri
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            }
        }

        val targetUri: Uri? = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        targetUri?.let {
            try {
                val inputStream: InputStream? = context.contentResolver.openInputStream(imageUri)
                val outputStream: OutputStream? = context.contentResolver.openOutputStream(it)

                if (inputStream != null && outputStream != null) {
                    inputStream.use { input ->
                        outputStream.use { output ->
                            input.copyTo(output)
                        }
                    }
                }
                binding.tvPhotoError.visibility = View.GONE
                hasPhoto = true

            } catch (e: Exception) {
                e.printStackTrace()
                binding.tvPhotoError.visibility = View.VISIBLE
                hasPhoto = false
            }
        }
    }

    private fun setButtonsClickListener() {
        binding.btAddress.setOnClickListener {
            verifyNameField(binding.tilName.editText)
            verifyEmailField(binding.tilEmail.editText)
            verifyPassField(binding.tilPass.editText)
            verifyCpfField(binding.tilCpf.editText)
            verifyPhoneField(binding.tilPhone.editText)
            verifyPhoto()

            if(!hasError()){
                viewModel.createUser(binding.tilEmail.editText?.text.toString(),
                                binding.tilPass.editText?.text.toString(),
                    ::goToAdressActivity,
                    ::showError
                )
            }

        }

        val requestPermissions = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { results ->
            results.forEach { (permission, granted) ->
                if (granted) {
                    hasPermissions = true
                } else {
                    hasPermissions = false
                }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            requestPermissions.launch(arrayOf(READ_MEDIA_IMAGES, READ_MEDIA_VISUAL_USER_SELECTED))
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions.launch(arrayOf(READ_MEDIA_IMAGES))
        } else {
            requestPermissions.launch(arrayOf(READ_EXTERNAL_STORAGE))
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 123) // Código de requisição
        }

        binding.ivFoto.setOnClickListener {
            if(hasPermissions){
                selectFile()
            }
        }
    }

    private fun showError(exception: Exception) {

    }

    private fun goToAdressActivity(auth:AuthResult) {
        UserPrefs.setUserId(auth.user!!.uid)
        val intent =  Intent(this, AddressActivity::class.java)
        startActivity(intent)
    }

    private fun verifyPhoto() {
        if(hasPhoto){
            binding.tvPhotoError.visibility = View.GONE
        }else{
            binding.tvPhotoError.visibility = View.VISIBLE
        }
    }

    private fun verifyPhoneField(editText: EditText?) {
        editText?.let {
            if(it.text.toString().length == 15){
                it.error = null
                phoneError = false
                UserPrefs.setUserPhone(it.text.toString())
            }else{
                cpfError = true
                it.error = "Digite um número de celular válido"
            }
        }
    }

    private fun verifyCpfField(editText: EditText?) {
        editText?.let{
            if(CpfUtil.myValidateCPF(it.text.toString())){
                it.error = null
                cpfError = false
                UserPrefs.setUserCpf(it.text.toString())
            }else{
                cpfError = true
                it.error = "Digite um CPF válido"
            }
        }
    }

    private fun verifyPassField(editText: EditText?) {
        editText?.let {
            if(greaterThan5(it.text.toString())){
                it.error = null
                passError = false
            }else{
                passError = true
                showPassError("A senha deve ter mais de 5 caracteres")
            }
        }
    }

    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    private fun verifyEmailField(editText: EditText?) {
        editText?.let{
            if(isValidEmail(it.text.toString())){
                it.error = null
                emailError = false
                UserPrefs.setUserEmail(it.text.toString())
            }else{
                emailError = true
                it.error = "Digite um email válido"
            }
        }
    }

    private fun verifyNameField(name: EditText?) {
        name?.let{
            if(greaterThan10(it.text.toString())){
                it.error = null
                nameError = false
                UserPrefs.setUserName(it.text.toString())
            }else{
                nameError = true
                it.error = "Digite o seu nome completo"
            }
        }
    }

    private fun hasError() :Boolean{
        return (nameError or emailError or passError or cpfError or phoneError or !hasPhoto)
    }

}