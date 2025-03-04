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
import com.taptwotimes.dadaacai.model.BebidasProductHome
import com.taptwotimes.dadaacai.model.BoloProductHome
import com.taptwotimes.dadaacai.model.CrepeProductHome
import com.taptwotimes.dadaacai.model.ProductHome
import com.taptwotimes.dadaacai.model.Topping
import com.taptwotimes.dadaacai.ui.base.BaseFragment
import com.taptwotimes.dadaacai.ui.home.adapters.HomeAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment() {
    private lateinit var binding: FragmentHomeBinding
    private var topOptions:ArrayList<Topping> = arrayListOf()
    private var bottomOptions:ArrayList<Topping> = arrayListOf()
    private var index:Int = 0
    private var itemCount = 2
    private var position = 0

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
            if(position>0) position--
            binding.rvRecycler.getCarouselLayoutManager().scrollToPosition(position)
        }

        binding.ivDireita.setOnClickListener {
            getToppings()
            if(position<3) position++
            binding.rvRecycler.getCarouselLayoutManager().scrollToPosition(position)
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

    private fun createBoloToppings() {
        topOptions.clear()
        bottomOptions.clear()
        viewModel.clearTopOptions()
        viewModel.clearBottomOptions()
        createToppingList("Acai", "Coberturas", "Cobertura", "Frutas")
    }

    private fun createBebidasToppings() {
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

        val myLinearLayoutManager = object : LinearLayoutManager(activity) {
            override fun canScrollHorizontally(): Boolean {
                return false
            }
        }

        viewModel.home.observe(viewLifecycleOwner){ response ->
            binding.rvRecycler.apply {
                val acai = response.get(0) as AcaiProductHome
                val bebidas = response.get(1) as BebidasProductHome
                val bolo = response.get(2) as BoloProductHome
                val crepe = response.get(3) as CrepeProductHome
                val list = arrayListOf<ProductHome>()
                list.add(0, acai)
                list.add(1, crepe)
                list.add(2, bolo)
                list.add(3, bebidas)
                layoutManager = myLinearLayoutManager
                adapter = HomeAdapter(context, viewModel, viewLifecycleOwner, list, topOptions, bottomOptions)
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
                is BoloProductHome -> {
                    createBoloToppings()
                }
                is BebidasProductHome -> {
                    createBebidasToppings()
                }
            }
        }

    }

    private fun createToppingList(id:String, name:String, topCategory:String, bottomCategory: String){
        viewModel.getTopOptions(id, name, topCategory)
        viewModel.getBottomOptions(id, name, bottomCategory)
    }
}