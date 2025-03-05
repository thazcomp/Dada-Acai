package com.taptwotimes.dadaacai.ui.cart

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.taptwotimes.dadaacai.R
import com.taptwotimes.dadaacai.databinding.FragmentCartBinding
import com.taptwotimes.dadaacai.model.FirebaseCartItem
import com.taptwotimes.dadaacai.ui.cart.adapter.CartListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private val viewModel:CartViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCart()

        viewModel.cart.observe(viewLifecycleOwner){
            createAdapter()
        }

    }

    private fun createAdapter() {
        val listview = binding.list
        context?.let{ c ->
            listview.adapter = CartListAdapter(listaAcai(), c)
        }
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private fun listaAcai(): ArrayList<FirebaseCartItem>{
        return viewModel.cart.value!!
    }

}