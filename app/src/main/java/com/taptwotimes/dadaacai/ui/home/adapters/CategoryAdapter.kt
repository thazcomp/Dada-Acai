package com.taptwotimes.dadaacai.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.checkbox.MaterialCheckBox.OnCheckedStateChangedListener
import com.taptwotimes.dadaacai.data.preferences.ProducrPrefs
import com.taptwotimes.dadaacai.databinding.CustomToppingBinding
import com.taptwotimes.dadaacai.model.Topping
import com.taptwotimes.dadaacai.ui.home.HomeViewModel

class CategoryAdapter(
    val list: ArrayList<Topping>,
    val viewModel: HomeViewModel
) :RecyclerView.Adapter<CategoryAdapter.ToppingViewHolder>() {

    class ToppingViewHolder(
        val refresh:() -> Unit,
        val binding: CustomToppingBinding,
                            val viewModel: HomeViewModel) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Topping) {
            binding.tvName.text = item.name
            binding.tvValor.text = item.price
            if(ProducrPrefs.getAcaiSelectionCounter() == 3){
                binding.mcbCheckBox.isEnabled = false
            }
            if(item.isChecked){
                binding.mcbCheckBox.isChecked = true
                addTopping(item)
            }

            binding.mcbCheckBox.addOnCheckedStateChangedListener { checkBox, state ->
                addTopping(item)
            }

        }

        private fun addTopping(item: Topping) {
            if(!ProducrPrefs.hasAcaiTopping1()){
                ProducrPrefs.setAcaiTopping1(item)
                ProducrPrefs.increaseAcaiSelectionCounter()
                viewModel.setSelectedTopping1()
            }else{
                if(!ProducrPrefs.hasAcaiTopping2()){
                    ProducrPrefs.setAcaiTopping2(item)
                    ProducrPrefs.increaseAcaiSelectionCounter()
                    viewModel.setSelectedTopping2()
                }else{
                    if(!ProducrPrefs.hasAcaiTopping3()){
                        ProducrPrefs.setAcaiTopping3(item)
                        ProducrPrefs.increaseAcaiSelectionCounter()
                        viewModel.setSelectedTopping3()
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToppingViewHolder {
        val binding =
            CustomToppingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToppingViewHolder({
            notifyItemRangeChanged(0, list.size)
        },binding, viewModel)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ToppingViewHolder, position: Int) {
        holder.bind(list[position])
    }
}