package com.taptwotimes.dadaacai.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.taptwotimes.dadaacai.databinding.CustomToppingBinding
import com.taptwotimes.dadaacai.databinding.ItemHomeBinding
import com.taptwotimes.dadaacai.model.Category
import com.taptwotimes.dadaacai.model.Topping

class CategoryAdapter(val list:ArrayList<Topping>):
    RecyclerView.Adapter<CategoryAdapter.ToppingViewHolder>() {


    class ToppingViewHolder(val binding: CustomToppingBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item:Topping){
            binding.tvName.text = item.name
            binding.tvValor.text = item.price
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToppingViewHolder {
        val binding = CustomToppingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToppingViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ToppingViewHolder, position: Int) {
        holder.bind(list[position])
    }
}