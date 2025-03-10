package com.taptwotimes.dadaacai.ui.address

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.taptwotimes.dadaacai.data.preferences.UserPrefs
import com.taptwotimes.dadaacai.databinding.ActivityAddressBinding
import com.taptwotimes.dadaacai.ui.base.BaseActivity
import com.taptwotimes.dadaacai.ui.caddone.CadDoneActivity


class AddressActivity: BaseActivity()  {
    private lateinit var binding: ActivityAddressBinding
    private val viewModel:AddressViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setButtonsClickListener()
    }

    private fun setButtonsClickListener() {
        binding.btAddress.setOnClickListener {
            if(!hasError()){
                goToCadDoneActivity()
            }
        }
    }

    private fun goToCadDoneActivity() {
        val intent =  Intent(this, CadDoneActivity::class.java)
        startActivity(intent)
    }

    private fun greaterThan5(text:String):Boolean{
        return text.length>5
    }

    private fun hasError(): Boolean {

        if(hasBairro()){
            binding.tilBairro.editText?.error = null
            UserPrefs.setUserBairro(binding.tilBairro.editText?.text.toString())
        }else{
            binding.tilBairro.editText?.error = "Digite o nome do seu Bairro"
        }
        if(hasRua()){
            binding.tilRua.editText?.error = null
            UserPrefs.setUserRua(binding.tilRua.editText?.text.toString())
        }else{
            binding.tilRua.editText?.error = "Digite o nome da sua Rua"
        }
        if(hasNum()){
            binding.tilNum.editText?.error = null
            UserPrefs.setUserNum(binding.tilNum.editText?.text.toString())
        }else{
            binding.tilNum.editText?.error = "Digite o número da sua casa"
        }

        UserPrefs.setUserComp(binding.tilComp.editText?.text.toString())
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