package com.taptwotimes.dadaacai.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.taptwotimes.dadaacai.databinding.FragmentHomeBinding
import com.taptwotimes.dadaacai.model.AcaiProductHome
import com.taptwotimes.dadaacai.model.Category
import com.taptwotimes.dadaacai.model.CrepeProductHome
import com.taptwotimes.dadaacai.model.ProductHome
import com.taptwotimes.dadaacai.model.Topping
import com.taptwotimes.dadaacai.ui.home.adapters.HomeAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private var position: Boolean = false
    private var options:ArrayList<Topping> = arrayListOf()
    private var index:Int = 0
    private var itemCount = 2

    private val viewModel: HomeViewModel by viewModels()

    private var selectedProduct:ProductHome? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        viewModel.getHome()

        binding = FragmentHomeBinding.inflate(layoutInflater)
        observeItemHome()

        binding.ivEsquerda.setOnClickListener {
            getToppings()
            if (position) {
                binding.rvRecycler.getCarouselLayoutManager().scrollToPosition(0)
                position = false
            } else {
                binding.rvRecycler.getCarouselLayoutManager().scrollToPosition(1)
                position = true
            }
        }

        binding.ivDireita.setOnClickListener {
            getToppings()
            if (position) {
                binding.rvRecycler.getCarouselLayoutManager().scrollToPosition(0)
                position = false
            } else {
                binding.rvRecycler.getCarouselLayoutManager().scrollToPosition(1)
                position = true
            }
        }

        binding.rvRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    options.clear()
                    viewModel.clearOptions()
                    getToppings()
                }
            }
        })


        return binding.root
    }

    private fun getToppings() {
        if(index==(itemCount-1)){
            index--
        }else{
            index++
        }
        selectedProduct = viewModel.getSelectedItemValue(index)

        if(selectedProduct is CrepeProductHome){
            options.clear()
            createCrepeToppings()
        }else if(selectedProduct is AcaiProductHome){
            options.clear()
            createAcaiToppings()
        }
    }

    private fun createAcaiToppings() {
        options.clear()
        viewModel.clearOptions()
        createToppingList("Acai", "Coberturas", "Cobertura")
        createToppingList("Acai", "Coberturas","Frutas")
    }

    private fun createCrepeToppings() {
        options.clear()
        viewModel.clearOptions()
        createToppingList("Crepes", "Recheios", "Salgados")
        createToppingList("Crepes", "Recheios", "Doces")
    }

    private fun observeItemHome() {

        viewModel.options.observe(viewLifecycleOwner){ response ->
            options.addAll(response)
        }

        viewModel.home.observe(viewLifecycleOwner){ response ->
            binding.rvRecycler.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = HomeAdapter(response, options)
                set3DItem(false)
                setIntervalRatio(.7f)
                setOrientation(RecyclerView.HORIZONTAL)
            }
            selectedProduct = response[0]

            when(selectedProduct){
                is AcaiProductHome -> {
                    createAcaiToppings()
                }
                is CrepeProductHome -> {
                    createCrepeToppings()
                }
            }
        }

    }

    private fun createToppingList(id:String, name:String, category:String){
        viewModel.getOptions(id, name, category)
    }
}