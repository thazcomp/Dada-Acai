package com.taptwotimes.dadaacai.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.taptwotimes.dadaacai.R
import com.taptwotimes.dadaacai.databinding.FragmentHomeBinding
import com.taptwotimes.dadaacai.model.ItemHome
import com.taptwotimes.dadaacai.model.Options
import com.taptwotimes.dadaacai.model.Topping
import com.taptwotimes.dadaacai.ui.home.adapters.HomeAdapter

class HomeFragment : Fragment() {

    private lateinit var binding:FragmentHomeBinding
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var adapter:RecyclerView.Adapter<HomeAdapter.ItemHomeViewHolder>
    private lateinit var itemList:ArrayList<ItemHome>
    private var position:Boolean = false

    fun createItemList(){
        activity?.let{
            itemList = arrayListOf<ItemHome>()
            itemList.add(
                ItemHome(
                    "Açaí Mania",
                    "Refresque o seu dia com o melhor Açaí da cidade!",
                    arrayListOf<Options>(
                        Options("Topping 1:", arrayListOf<Topping>(
                            Topping("Leite Condensado", 0.00)
                        )),
                        Options("Topping 2:", arrayListOf<Topping>(
                            Topping("Morango", 0.00)
                        )),
                        Options("Topping 1:", arrayListOf<Topping>(
                            Topping("Paçoca", 0.00)
                        ))
                    ),
                    AppCompatResources.getDrawable(it, R.drawable.acai4),
                    15.00
                ),

            )

            itemList.add(
                ItemHome(
                    "Crepe Mania",
                    "Subtitulo!!",
                    arrayListOf<Options>(
                        Options("Recheio:", arrayListOf<Topping>(
                            Topping("Presunto e Queijo", 0.00)
                        )),
                        Options("B. Recheadas:", arrayListOf<Topping>(
                            Topping("B. Recheadas: Sim", 5.00)
                        ))
                    ),
                    AppCompatResources.getDrawable(it, R.drawable.crepe2),
                    15.00
                ),

                )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        createItemList()
        binding = FragmentHomeBinding.inflate(layoutInflater)
        binding.rvRecycler.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = HomeAdapter(itemList)
            set3DItem(false)
            setIntervalRatio(.7f)
            setOrientation(RecyclerView.HORIZONTAL)
        }

        binding.ivEsquerda.setOnClickListener {
            if(position){
                binding.rvRecycler.getCarouselLayoutManager().scrollToPosition(0)
                position = false
            }else{
                binding.rvRecycler.getCarouselLayoutManager().scrollToPosition(1)
                position = true
            }
        }

        binding.ivDireita.setOnClickListener {
            if(position){
                binding.rvRecycler.getCarouselLayoutManager().scrollToPosition(0)
                position = false
            }else{
                binding.rvRecycler.getCarouselLayoutManager().scrollToPosition(1)
                position = true
            }
        }


        return binding.root
    }
}