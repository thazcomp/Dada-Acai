package com.taptwotimes.dadaacai.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.taptwotimes.dadaacai.data.preferences.ProductPrefs
import com.taptwotimes.dadaacai.databinding.CustomToppingBinding
import com.taptwotimes.dadaacai.model.AcaiProductHome
import com.taptwotimes.dadaacai.model.CrepeProductHome
import com.taptwotimes.dadaacai.model.ProductHome
import com.taptwotimes.dadaacai.model.Topping
import com.taptwotimes.dadaacai.ui.home.HomeViewModel

class ExpadableCategoryAdapter(
    open val toppings: ArrayList<Topping>,
    open val viewModel:HomeViewModel,
    open val product:ProductHome,
    open val refresh: () -> Unit
): RecyclerView.Adapter<ExpadableCategoryAdapter.InnerViewHolder>() {

    class InnerViewHolder(
        open val refresh: () -> Unit,
        open val product: ProductHome,
        open val binding: CustomToppingBinding,
        open val viewModel: HomeViewModel
    ) :
        RecyclerView.ViewHolder(binding.root) {

        open fun bind(item: Topping) {
            binding.tvName.text = item.name
            binding.tvValor.text = item.price

            binding.mcbCheckBox.setOnClickListener {
                if (binding.mcbCheckBox.isChecked) {
                    item.isChecked = true
                    binding.mcbCheckBox.isClickable = true
                    when (product) {
                        is AcaiProductHome -> {
                            addAcaiTopping(item)
                        }

                        is CrepeProductHome -> {
                            addCrepeTopping(item)
                        }
                    }
                } else {
                    when (product) {
                        is AcaiProductHome -> {
                            removeAcaiTopping(item)
                        }

                        is CrepeProductHome -> {
                            removeCrepeTopping(item)
                        }
                    }
                }
            }

            when (product) {
                is AcaiProductHome -> {
                    if (ProductPrefs.getAcaiSelectionCounter() == 3) {
                        binding.mcbCheckBox.isClickable = false
                        setItemChecked(item)
                    } else {
                        binding.mcbCheckBox.isClickable = true
                        setItemChecked(item)
                    }
                }

                is CrepeProductHome -> {
                    if (ProductPrefs.getCrepeSelectionCounter() == 2) {
                        binding.mcbCheckBox.isClickable = false
                        setItemChecked(item)
                    } else {
                        binding.mcbCheckBox.isClickable = true
                        setItemChecked(item)
                    }
                }
            }
        }

        private fun setItemChecked(item: Topping) {
            if (item.isChecked) {
                binding.mcbCheckBox.isChecked = true
                binding.mcbCheckBox.isClickable = true
            } else {
                binding.mcbCheckBox.isChecked = false
            }
        }

        fun addAcaiTopping(item: Topping) {
            if (!ProductPrefs.hasAcaiTopping1()) {
                ProductPrefs.setAcaiTopping1(item)
                viewModel.setSelectedAcaiTopping1(item)
            } else {
                if (!ProductPrefs.hasAcaiTopping2()) {
                    ProductPrefs.setAcaiTopping2(item)
                    viewModel.setSelectedAcaiTopping2(item)
                } else {
                    if (!ProductPrefs.hasAcaiTopping3()) {
                        ProductPrefs.setAcaiTopping3(item)
                        viewModel.setSelectedAcaiTopping3(item)
                        refresh()
                    }
                }
            }
            ProductPrefs.increaseAcaiSelectionCounter()
        }

        fun removeAcaiTopping(item: Topping) {
            if (ProductPrefs.hasAcaiTopping3()) {
                removeAcaiTopping3(item)
            }else{
                if (ProductPrefs.hasAcaiTopping2()) {
                    removeAcaiTopping2(item)
                } else {
                    removeAcaiTopping1(item)
                }
            }
            ProductPrefs.decreaseAcaiSelectionCounter()
        }

        private fun removeAcaiTopping3(item:Topping) {
            if(ProductPrefs.getAcaiTopping3()!!.name == item.name){
                ProductPrefs.setAcaiTopping3(null)
                viewModel.clearSelectedAcaiTopping3()
            }else if(ProductPrefs.hasAcaiTopping2()) {
                if(ProductPrefs.getAcaiTopping2()!!.name == item.name){
                    ProductPrefs.setAcaiTopping2(null)
                    viewModel.clearSelectedAcaiTopping2()
                }
            }else if(ProductPrefs.hasAcaiTopping1()) {
                if(ProductPrefs.getAcaiTopping1()!!.name == item.name){
                    ProductPrefs.setAcaiTopping1(null)
                    viewModel.clearSelectedAcaiTopping1()
                }
            }
            item.isChecked = false
            refresh()
        }

        private fun removeAcaiTopping2(item:Topping) {
            if(ProductPrefs.hasAcaiTopping2()) {
                if(ProductPrefs.getAcaiTopping2()!!.name == item.name){
                    ProductPrefs.setAcaiTopping2(null)
                    viewModel.clearSelectedAcaiTopping2()
                }
            }else if(ProductPrefs.hasAcaiTopping1()) {
                if(ProductPrefs.getAcaiTopping1()!!.name == item.name){
                    ProductPrefs.setAcaiTopping1(null)
                    viewModel.clearSelectedAcaiTopping1()
                }
            }
            item.isChecked = false
            refresh()
        }

        private fun removeAcaiTopping1(item:Topping) {
            if(ProductPrefs.hasAcaiTopping1()) {
                if(ProductPrefs.getAcaiTopping1()!!.name == item.name){
                    ProductPrefs.setAcaiTopping1(null)
                    viewModel.clearSelectedAcaiTopping1()
                }
            }
            item.isChecked = false
            refresh()
        }

        fun addCrepeTopping(item: Topping) {
            if (!ProductPrefs.hasCrepeTopping1()) {
                ProductPrefs.setCrepeTopping1(item)
                viewModel.setSelectedCrepeTopping1()
            } else {
                if (!ProductPrefs.hasCrepeTopping2()) {
                    ProductPrefs.setCrepeTopping2(item)
                    viewModel.setSelectedCrepeTopping2()
                    refresh()
                }
            }
            ProductPrefs.increaseCrepeSelectionCounter()
        }

        fun removeCrepeTopping(item: Topping) {
            if (ProductPrefs.hasCrepeTopping2()) {
                removeCrepeTopping2(item)
            } else {
                removeCrepeTopping1(item)
            }
            ProductPrefs.decreaseCrepeSelectionCounter()
        }

        private fun removeCrepeTopping1(item: Topping) {
            if (ProductPrefs.hasCrepeTopping1()) {
                if(ProductPrefs.getCrepeTopping1()!!.name == item.name){
                    ProductPrefs.setCrepeTopping1(null)
                    viewModel.clearSelectedCrepeTopping1()
                }
            }
            item.isChecked = false
            refresh()
        }

        private fun removeCrepeTopping2(item: Topping) {
            if(ProductPrefs.getCrepeTopping2()!!.name == item.name){
                ProductPrefs.setCrepeTopping2(null)
                viewModel.clearSelectedCrepeTopping2()
            }else if (ProductPrefs.hasCrepeTopping1()) {
                if(ProductPrefs.getCrepeTopping1()!!.name == item.name){
                    ProductPrefs.setCrepeTopping1(null)
                    viewModel.clearSelectedCrepeTopping1()
                }
            }
            item.isChecked = false
            refresh()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {
        val binding =
            CustomToppingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InnerViewHolder(refresh, product, binding, viewModel)
    }

    override fun getItemCount(): Int {
        return toppings.size
    }

    override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
        holder.bind(toppings[position])
    }
}