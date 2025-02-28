package com.taptwotimes.dadaacai.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.checkbox.MaterialCheckBox
import com.taptwotimes.dadaacai.databinding.CustomToppingBinding
import com.taptwotimes.dadaacai.model.Topping

class CategoryAdapter(
    val list: ArrayList<Topping>,
    val createCheckboxListener: MaterialCheckBox.OnCheckedStateChangedListener
) :RecyclerView.Adapter<CategoryAdapter.ToppingViewHolder>() {

    class ToppingViewHolder(val binding: CustomToppingBinding, val createCheckboxListener: MaterialCheckBox.OnCheckedStateChangedListener) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Topping) {
            binding.tvName.text = item.name
            binding.tvValor.text = item.price
            binding.mcbCheckBox.addOnCheckedStateChangedListener(createCheckboxListener)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToppingViewHolder {
        val binding =
            CustomToppingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToppingViewHolder(binding, createCheckboxListener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ToppingViewHolder, position: Int) {
        holder.bind(list[position])
    }

    public fun getSelectedItem(position: Int):Topping{
        return list[position]
    }
}