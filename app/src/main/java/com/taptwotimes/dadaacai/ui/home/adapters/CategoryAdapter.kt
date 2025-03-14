package com.taptwotimes.dadaacai.ui.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.taptwotimes.dadaacai.R
import com.taptwotimes.dadaacai.data.preferences.ProductPrefs
import com.taptwotimes.dadaacai.databinding.CustomToppingBinding
import com.taptwotimes.dadaacai.databinding.ExpandableSelectToppingBinding
import com.taptwotimes.dadaacai.model.AcaiProductHome
import com.taptwotimes.dadaacai.model.CategoryItem
import com.taptwotimes.dadaacai.model.CrepeProductHome
import com.taptwotimes.dadaacai.model.ProductHome
import com.taptwotimes.dadaacai.model.Topping
import com.taptwotimes.dadaacai.ui.home.HomeViewModel

class CategoryAdapter(
    open val list: ArrayList<CategoryItem>,
    open val viewModel: HomeViewModel,
    open val product: ProductHome,
) : RecyclerView.Adapter<CategoryAdapter.ToppingViewHolder>() {

    class ToppingViewHolder(
        open val refresh: () -> Unit,
        open val product: ProductHome,
        open val binding: ExpandableSelectToppingBinding,
        open val viewModel: HomeViewModel
    ) :
        RecyclerView.ViewHolder(binding.root) {

        open fun bind(item: CategoryItem) {
            binding.tvCategoria.text = item.name
            val adapter = ExpadableCategoryAdapter(
                item.toppings,
                viewModel,
                product,
                refresh
            )
            binding.rvCategoria.apply {
                layoutManager = LinearLayoutManager(context)
                this.adapter = adapter

            }
            binding.ivArrowDown.setOnClickListener {
                if (binding.rvCategoria.visibility == View.GONE) {
                    binding.rvCategoria.visibility = View.VISIBLE
//                    binding.ivArrowDown.setImageResource(R.drawable.arrow_drop_up) // Troca a imagem da seta para cima
                } else {
                    // Esconde o RecyclerView
                    binding.rvCategoria.visibility = View.GONE
//                    binding.ivArrowDown.setImageResource(R.drawable.arrow_drop_down) // Troca a imagem da seta para baixo
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToppingViewHolder {
        val binding =
            ExpandableSelectToppingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToppingViewHolder({
            notifyDataSetChanged()
        }, product, binding, viewModel)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ToppingViewHolder, position: Int) {
        holder.bind(list[position])
    }
}