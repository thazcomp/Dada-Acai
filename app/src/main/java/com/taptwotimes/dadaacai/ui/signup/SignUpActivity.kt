package com.taptwotimes.dadaacai.ui.signup

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import com.taptwotimes.dadaacai.data.preferences.UserPrefs
import com.taptwotimes.dadaacai.databinding.ActivitySignupBinding
import com.taptwotimes.dadaacai.ui.address.AddressActivity
import com.taptwotimes.dadaacai.ui.base.BaseActivity
import com.taptwotimes.dadaacai.util.CpfUtil
import com.taptwotimes.dadaacai.util.Mask
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class SignUpActivity: BaseActivity()  {

    private lateinit var binding: ActivitySignupBinding
    private var nameError:Boolean = false
    private var emailError:Boolean = false
    private var passError:Boolean = false
    private var cpfError:Boolean = false
    private var phoneError:Boolean = false
    private var hasPhoto:Boolean = false

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

    private fun saveImageToFile(uri: Uri) {
        val inputStream = contentResolver.openInputStream(uri)
        val file = File(filesDir, "selected_image.jpg") // Salva no armazenamento interno

        try {
            val outputStream = FileOutputStream(file)
            inputStream?.copyTo(outputStream)
            inputStream?.close()
            outputStream.close()
            binding.tvPhotoError.visibility = View.GONE
            hasPhoto = true
            // Agora você tem o arquivo salvo em 'file'
        } catch (e: IOException) {
            hasPhoto = false
            e.printStackTrace()
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
                goToAdressActivity()
            }

        }
        binding.ivFoto.setOnClickListener {
            pickImage.launch("image/*")
        }
    }

    private fun goToAdressActivity() {
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