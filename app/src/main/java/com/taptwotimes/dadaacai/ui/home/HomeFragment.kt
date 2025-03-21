package com.taptwotimes.dadaacai.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging
import com.taptwotimes.dadaacai.R
import com.taptwotimes.dadaacai.data.preferences.ProductPrefs
import com.taptwotimes.dadaacai.data.preferences.UserPrefs
import com.taptwotimes.dadaacai.databinding.FragmentHomeBinding
import com.taptwotimes.dadaacai.model.AcaiProductHome
import com.taptwotimes.dadaacai.model.BebidasProductHome
import com.taptwotimes.dadaacai.model.BoloProductHome
import com.taptwotimes.dadaacai.model.CrepeProductHome
import com.taptwotimes.dadaacai.model.ProductHome
import com.taptwotimes.dadaacai.model.Topping
import com.taptwotimes.dadaacai.ui.base.BaseFragment
import com.taptwotimes.dadaacai.ui.caddone.CadDoneActivity
import com.taptwotimes.dadaacai.ui.cart.CartFragment
import com.taptwotimes.dadaacai.ui.home.adapters.HomeAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment() {
    private lateinit var binding: FragmentHomeBinding
    private var topOptions: ArrayList<Topping> = arrayListOf()
    private var bottomOptions: ArrayList<Topping> = arrayListOf()
    private var position = 0

    private val viewModel: HomeViewModel by viewModels()

    private var selectedProduct: ProductHome? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        viewModel.isReviwed { reviwed ->
            if(reviwed){
                createHomeLayout()
            }else{
                goToCadDoneActivity()
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        createFirebaseMessageToken()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun createFirebaseMessageToken() {

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
            val token = task.result
            UserPrefs.setUserToken(token)
        })
    }

    private fun goToCadDoneActivity() {
        val intent =  Intent(activity, CadDoneActivity::class.java)
        activity?.startActivity(intent)
    }

    private fun createHomeLayout() {
        viewModel.getHome()
        observeItemHome()

        ProductPrefs.clear()

        binding.ivEsquerda.setOnClickListener {
            if (position > 0) position--
            binding.rvRecycler.getCarouselLayoutManager().scrollToPosition(position)

            topOptions.clear()
            bottomOptions.clear()
            viewModel.clearTopOptions()
            viewModel.clearBottomOptions()
            getToppings()
        }

        binding.ivDireita.setOnClickListener {
            if (position < 3) position++
            binding.rvRecycler.getCarouselLayoutManager().scrollToPosition(position)

            topOptions.clear()
            bottomOptions.clear()
            viewModel.clearTopOptions()
            viewModel.clearBottomOptions()
            getToppings()
        }

        getToppings()
    }

    private fun getToppings() {
        when (position) {
            0 -> { createAcaiToppings() }
            1 -> { createCrepeToppings() }
            2 -> {}
            3 -> {}
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
    }

    private fun createBebidasToppings() {
        topOptions.clear()
        bottomOptions.clear()
        viewModel.clearTopOptions()
        viewModel.clearBottomOptions()
    }

    private fun createCrepeToppings() {
        topOptions.clear()
        bottomOptions.clear()
        viewModel.clearTopOptions()
        viewModel.clearBottomOptions()
        createToppingList("Crepes", "Recheios", "Salgados", "Doces")
    }

    private fun observeItemHome() {

        viewModel.topOptions.observe(viewLifecycleOwner) { response ->
            val auxList = arrayListOf<Topping>()
            for(r in response){ //Verifica dados duplicados
                if(!topOptions.contains(r)){
                    auxList.add(r)
                }
            }
            topOptions.addAll(auxList)
        }

        viewModel.bottomOptions.observe(viewLifecycleOwner) { response ->
            val auxList = arrayListOf<Topping>()
            for(r in response){ //Verifica dados duplicados
                if(!bottomOptions.contains(r)){
                    auxList.add(r)
                }
            }
            bottomOptions.addAll(auxList)
        }

        val myLinearLayoutManager = object : LinearLayoutManager(activity) {
            override fun canScrollHorizontally(): Boolean {
                return false
            }
        }

        viewModel.home.observe(viewLifecycleOwner) { response ->
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
                adapter = HomeAdapter(
                    context,
                    activity?.supportFragmentManager,
                    viewModel,
                    viewLifecycleOwner,
                    list,
                    topOptions,
                    bottomOptions
                )
                set3DItem(false)
                setIntervalRatio(.7f)
                setIsScrollingEnabled(false)
                setOrientation(RecyclerView.HORIZONTAL)
            }
            selectedProduct = response[0]

            when (selectedProduct) {
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

    private fun createToppingList(
        id: String,
        name: String,
        topCategory: String,
        bottomCategory: String
    ) {
        viewModel.getTopOptions(id, name, topCategory)
        viewModel.getBottomOptions(id, name, bottomCategory)
    }
}