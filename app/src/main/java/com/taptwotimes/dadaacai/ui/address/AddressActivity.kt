package com.taptwotimes.dadaacai.ui.address

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.viewModels
import com.example.coxinhaminha.model.User
import com.taptwotimes.dadaacai.data.preferences.UserPrefs
import com.taptwotimes.dadaacai.databinding.ActivityAddressBinding
import com.taptwotimes.dadaacai.model.Address
import com.taptwotimes.dadaacai.ui.base.BaseActivity
import com.taptwotimes.dadaacai.ui.caddone.CadDoneActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddressActivity: BaseActivity()  {
    private lateinit var binding: ActivityAddressBinding
    private val viewModel:AddressViewModel by viewModels()
    private lateinit var address: Address

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setButtonsClickListener()
    }

    private fun setButtonsClickListener() {
        binding.btAddress.setOnClickListener {
            if(!hasError()){
                viewModel.saveUser(
                    User(UserPrefs.getUserId()!!,
                        UserPrefs.getUserName()!!,
                        UserPrefs.getUserEmail()!!,
                        UserPrefs.getUserCpf()!!,
                        UserPrefs.getUserPhone()!!),
                    ::saveAddress,
                    ::showError
                )
            }
        }
    }

    private fun showError() {

    }

    private fun saveAddress(){
        val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(contentResolver, Uri.parse(UserPrefs.getUserPhoto()!!))
        viewModel.savePhoto(bitmap)
        viewModel.saveAddress(address,
            ::goToCadDoneActivity,
            ::showError)
    }

    private fun goToCadDoneActivity() {
        val intent =  Intent(this, CadDoneActivity::class.java)
        startActivity(intent)
    }

    private fun greaterThan5(text:String):Boolean{
        return text.length>5
    }

    private fun hasError(): Boolean {
        var bairro = ""
        var rua = ""
        var num = ""
        var comp = ""
        if(hasBairro()){
            binding.tilBairro.editText?.error = null
            UserPrefs.setUserBairro(binding.tilBairro.editText?.text.toString())
            bairro = binding.tilBairro.editText?.text.toString()
        }else{
            binding.tilBairro.editText?.error = "Digite o nome do seu Bairro"
        }
        if(hasRua()){
            binding.tilRua.editText?.error = null
            UserPrefs.setUserRua(binding.tilRua.editText?.text.toString())
            rua = binding.tilRua.editText?.text.toString()
        }else{
            binding.tilRua.editText?.error = "Digite o nome da sua Rua"
        }
        if(hasNum()){
            binding.tilNum.editText?.error = null
            UserPrefs.setUserNum(binding.tilNum.editText?.text.toString())
            num = binding.tilNum.editText?.text.toString()
        }else{
            binding.tilNum.editText?.error = "Digite o número da sua casa"
        }

        UserPrefs.setUserComp(binding.tilComp.editText?.text.toString())
        comp = binding.tilComp.editText?.text.toString()
        address = Address(rua, bairro, num, comp)
        return (!hasBairro()) or (!hasRua()) or (!hasNum())
    }

    private fun hasNum(): Boolean {
        return binding.tilNum.editText?.text.toString().isNotEmpty()
    }

    private fun hasRua(): Boolean {
        return greaterThan5(binding.tilRua.editText?.text.toString())
    }

    private fun hasBairro(): Boolean {
        return greaterThan5(binding.tilBairro.editText?.text.toString())
    }

}