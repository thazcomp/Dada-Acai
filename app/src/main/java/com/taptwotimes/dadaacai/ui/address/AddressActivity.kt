package com.taptwotimes.dadaacai.ui.address

import android.os.Bundle
import androidx.activity.viewModels
import com.taptwotimes.dadaacai.databinding.ActivityAddressBinding
import com.taptwotimes.dadaacai.ui.base.BaseActivity


class AddressActivity: BaseActivity()  {
    private lateinit var binding: ActivityAddressBinding
    private val viewModel:AddressViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddressBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setButtonsClickListener()
    }

    private fun setButtonsClickListener() {
//        binding.btEntrar.setOnClickListener {
//            val intent =  Intent(this, HomeActivity::class.java)
//            startActivity(intent)
//        }
    }

    private fun verifyButtonEnabled() {
        TODO("Not yet implemented")
    }


}