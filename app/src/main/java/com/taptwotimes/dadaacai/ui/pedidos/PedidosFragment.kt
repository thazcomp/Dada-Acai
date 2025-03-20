package com.taptwotimes.dadaacai.ui.pedidos

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.taptwotimes.dadaacai.databinding.FragmentPedidosBinding
import com.taptwotimes.dadaacai.model.FirebaseCartItem
import com.taptwotimes.dadaacai.model.Pedido
import com.taptwotimes.dadaacai.ui.base.BaseFragment
import com.taptwotimes.dadaacai.ui.cart.CartViewModel
import com.taptwotimes.dadaacai.ui.cart.adapter.CartAdapter
import com.taptwotimes.dadaacai.ui.pedidos.adapters.PedidosAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PedidosFragment:BaseFragment() {

    private lateinit var binding: FragmentPedidosBinding
    private val viewModel:PedidosViewModel by viewModels()
    private val cartViewModel:CartViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPedidosBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.pedidos.observe(viewLifecycleOwner){
            createAdapter()
        }

        viewModel.getPedidos()

    }

    val myLinearLayoutManager = object : LinearLayoutManager(activity) {
        override fun canScrollHorizontally(): Boolean {
            return false
        }
    }

    private fun createAdapter() {
        context?.let{ c ->
            binding.list.apply {
                layoutManager = myLinearLayoutManager
                adapter = PedidosAdapter(
                    getLista(),
                    cartViewModel,
                    context
                )
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun getLista(): ArrayList<Pedido>{
        return viewModel.pedidos.value!!
    }
}