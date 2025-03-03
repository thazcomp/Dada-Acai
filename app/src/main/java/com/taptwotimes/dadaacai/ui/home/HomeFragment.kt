package com.taptwotimes.dadaacai.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.taptwotimes.dadaacai.data.preferences.ProducrPrefs
import com.taptwotimes.dadaacai.databinding.FragmentHomeBinding
import com.taptwotimes.dadaacai.model.AcaiProductHome
import com.taptwotimes.dadaacai.model.CrepeProductHome
import com.taptwotimes.dadaacai.model.ProductHome
import com.taptwotimes.dadaacai.model.Topping
import com.taptwotimes.dadaacai.ui.base.BaseFragment
import com.taptwotimes.dadaacai.ui.home.adapters.HomeAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment() {
    private lateinit var binding: FragmentHomeBinding
    private var position: Boolean = false
    private var topOptions:ArrayList<Topping> = arrayListOf()
    private var bottomOptions:ArrayList<Topping> = arrayListOf()
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

        ProducrPrefs.clear()

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
                    topOptions.clear()
                    bottomOptions.clear()
                    viewModel.clearTopOptions()
                    viewModel.clearBottomOptions()
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
            topOptions.clear()
            bottomOptions.clear()
            createCrepeToppings()
        }else if(selectedProduct is AcaiProductHome){
            topOptions.clear()
            bottomOptions.clear()
            createAcaiToppings()
        }
    }

    private fun createAcaiToppings() {
        topOptions.clear()
        bottomOptions.clear()
        viewModel.clearTopOptions()
        viewModel.clearBottomOptions()
        createToppingList("Acai", "Coberturas", "Cobertura", "Frutas")
    }

    private fun createCrepeToppings() {
        topOptions.clear()
        bottomOptions.clear()
        viewModel.clearTopOptions()
        viewModel.clearBottomOptions()
        createToppingList("Crepes", "Recheios", "Salgados", "Doces")
    }

    private fun observeItemHome() {

        viewModel.topOptions.observe(viewLifecycleOwner){ response ->
            topOptions.addAll(response)
        }

        viewModel.bottomOptions.observe(viewLifecycleOwner){ response ->
            bottomOptions.addAll(response)
        }

        viewModel.selecteAcaiTopping1.observe(viewLifecycleOwner){ response ->
        }

        viewModel.home.observe(viewLifecycleOwner){ response ->
            binding.rvRecycler.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = HomeAdapter(context, viewModel, viewLifecycleOwner, response, topOptions, bottomOptions)
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

    private fun createToppingList(id:String, name:String, topCategory:String, bottomCategory: String){
        viewModel.getTopOptions(id, name, topCategory)
        viewModel.getBottomOptions(id, name, bottomCategory)
    }
}