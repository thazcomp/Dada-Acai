package com.taptwotimes.dadaacai.ui.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.taptwotimes.dadaacai.R
import com.taptwotimes.dadaacai.databinding.FragmentHomeBinding
import com.taptwotimes.dadaacai.model.ItemHome
import com.taptwotimes.dadaacai.model.Options
import com.taptwotimes.dadaacai.model.Topping
import com.taptwotimes.dadaacai.ui.home.adapters.HomeAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private var position: Boolean = false

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        viewModel.getHome()

        binding = FragmentHomeBinding.inflate(layoutInflater)
        observeItemHome()
        binding.ivEsquerda.setOnClickListener {
            if (position) {
                binding.rvRecycler.getCarouselLayoutManager().scrollToPosition(0)
                position = false
            } else {
                binding.rvRecycler.getCarouselLayoutManager().scrollToPosition(1)
                position = true
            }
        }

        binding.ivDireita.setOnClickListener {
            if (position) {
                binding.rvRecycler.getCarouselLayoutManager().scrollToPosition(0)
                position = false
            } else {
                binding.rvRecycler.getCarouselLayoutManager().scrollToPosition(1)
                position = true
            }
        }


        return binding.root
    }

    private fun observeItemHome() {
        viewModel.response.observe(viewLifecycleOwner){ response ->
            binding.rvRecycler.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = HomeAdapter(response)
                set3DItem(false)
                setIntervalRatio(.7f)
                setOrientation(RecyclerView.HORIZONTAL)
            }
        }
    }
}