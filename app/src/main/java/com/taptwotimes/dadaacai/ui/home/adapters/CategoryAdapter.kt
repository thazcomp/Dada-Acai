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

            binding.mcbCheckBox.setOnClickListener {
                if(binding.mcbCheckBox.isChecked){
                    item.isChecked = true
                    binding.mcbCheckBox.isClickable = true
                    addTopping(item)
                }else{
                    removeTopping()
                }
            }

            if(ProducrPrefs.getAcaiSelectionCounter() == 3){
                binding.mcbCheckBox.isClickable = false
                if(item.isChecked){
                    binding.mcbCheckBox.isChecked = true
                    binding.mcbCheckBox.isClickable = true
                }else{
                    binding.mcbCheckBox.isChecked = false
                }
            }else {
                binding.mcbCheckBox.isClickable = true
                if(item.isChecked){
                    binding.mcbCheckBox.isChecked = true
                    binding.mcbCheckBox.isClickable = true
                }else{
                    binding.mcbCheckBox.isChecked = false
                }
            }
        }

        private fun addTopping(item: Topping) {
            if(!ProducrPrefs.hasAcaiTopping1()){
                ProducrPrefs.setAcaiTopping1(item)
                viewModel.setSelectedTopping1()
            }else{
                if(!ProducrPrefs.hasAcaiTopping2()){
                    ProducrPrefs.setAcaiTopping2(item)
                    viewModel.setSelectedTopping2()
                }else{
                    if(!ProducrPrefs.hasAcaiTopping3()){
                        ProducrPrefs.setAcaiTopping3(item)
                        viewModel.setSelectedTopping3()
                        refresh()
                    }
                }
            }
            ProducrPrefs.increaseAcaiSelectionCounter()
        }

        private fun removeTopping() {
            if(ProducrPrefs.hasAcaiTopping3()){
                ProducrPrefs.setAcaiTopping3(null)
                viewModel.setSelectedTopping3()
                refresh()
            }else{
                if(ProducrPrefs.hasAcaiTopping2()){
                    ProducrPrefs.setAcaiTopping2(null)
                    viewModel.setSelectedTopping2()
                }else{
                    if(ProducrPrefs.hasAcaiTopping1()){
                        ProducrPrefs.setAcaiTopping1(null)
                        viewModel.setSelectedTopping1()
                    }
                }
            }
            ProducrPrefs.decreaseAcaiSelectionCounter()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToppingViewHolder {
        val binding =
            CustomToppingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToppingViewHolder({
            notifyDataSetChanged()
        },binding, viewModel)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ToppingViewHolder, position: Int) {
        holder.bind(list[position])
    }
}