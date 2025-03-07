package com.taptwotimes.dadaacai.ui.cart

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.taptwotimes.dadaacai.R
import com.taptwotimes.dadaacai.databinding.FragmentCartBinding
import com.taptwotimes.dadaacai.model.CartItemAcai
import com.taptwotimes.dadaacai.ui.cart.adapter.CartListAdapter

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding

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
        createAdapter()
    }

    private fun createAdapter() {
        val listview = binding.list
        context?.let{
            listview.adapter = CartListAdapter(listaAcai(), it)
        }
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private fun listaAcai(): List<CartItemAcai>{
        var list = listOf<CartItemAcai>()
        context?.getDrawable(R.drawable.acai1)?.let{ image ->
            list = listOf(
                CartItemAcai(0,
                    image,
                    getString(R.string.acai_completo),
                    getString(R.string.leite_condensado),
                    getString(R.string.pacoca),
                    getString(R.string.morango),
                    getString(R.string.total18)
                ),
                CartItemAcai(0,
                    image,
                    getString(R.string.acai_completo),
                    getString(R.string.pacoca),
                    getString(R.string.morango),
                    " ",
                    getString(R.string.total16)
                ),
                CartItemAcai(0,
                    image,
                    getString(R.string.acai_completo),
                    getString(R.string.leite_condensado),
                    " ",
                    " ",
                    getString(R.string.total14)
                )
            )
        }
        return list
    }

}