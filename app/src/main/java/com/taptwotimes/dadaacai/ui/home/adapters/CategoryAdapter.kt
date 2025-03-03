package com.taptwotimes.dadaacai.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.taptwotimes.dadaacai.data.preferences.ProducrPrefs
import com.taptwotimes.dadaacai.databinding.CustomToppingBinding
import com.taptwotimes.dadaacai.model.AcaiProductHome
import com.taptwotimes.dadaacai.model.CrepeProductHome
import com.taptwotimes.dadaacai.model.ProductHome
import com.taptwotimes.dadaacai.model.Topping
import com.taptwotimes.dadaacai.ui.home.HomeViewModel

class CategoryAdapter(
    open val list: ArrayList<Topping>,
    open val viewModel: HomeViewModel,
    open val product:ProductHome
) :RecyclerView.Adapter<CategoryAdapter.ToppingViewHolder>() {

    class ToppingViewHolder(
        open val refresh:() -> Unit,
        open val product:ProductHome,
        open val binding: CustomToppingBinding,
        open val viewModel: HomeViewModel) :
        RecyclerView.ViewHolder(binding.root) {

        open fun bind(item: Topping) {
            binding.tvName.text = item.name
            binding.tvValor.text = item.price

            binding.mcbCheckBox.setOnClickListener {
                if (binding.mcbCheckBox.isChecked) {
                    item.isChecked = true
                    binding.mcbCheckBox.isClickable = true
                    when(product){
                        is AcaiProductHome -> {
                            addAcaiTopping(item)
                        }is CrepeProductHome -> {
                            addCrepeTopping(item)
                        }
                    }
                } else {
                    when(product){
                        is AcaiProductHome -> {
                            removeAcaiTopping(item)
                        }is CrepeProductHome -> {
                            removeCrepeTopping(item)
                        }
                    }
                }
            }

            if (ProducrPrefs.getAcaiSelectionCounter() == 3) {
                binding.mcbCheckBox.isClickable = false
                setItemChecked(item)
            } else {
                binding.mcbCheckBox.isClickable = true
                setItemChecked(item)
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
            if (!ProducrPrefs.hasAcaiTopping1()) {
                ProducrPrefs.setAcaiTopping1(item)
                viewModel.setSelectedAcaiTopping1()
            } else {
                if (!ProducrPrefs.hasAcaiTopping2()) {
                    ProducrPrefs.setAcaiTopping2(item)
                    viewModel.setSelectedAcaiTopping2()
                } else {
                    if (!ProducrPrefs.hasAcaiTopping3()) {
                        ProducrPrefs.setAcaiTopping3(item)
                        viewModel.setSelectedAcaiTopping3()
                        refresh()
                    }
                }
            }
            ProducrPrefs.increaseAcaiSelectionCounter()
        }

        fun removeAcaiTopping(item: Topping) {
            if (ProducrPrefs.hasAcaiTopping3()) {
                ProducrPrefs.setAcaiTopping3(null)
                viewModel.setSelectedAcaiTopping3()
                refresh()
            } else {
                if (ProducrPrefs.hasAcaiTopping2()) {
                    ProducrPrefs.setAcaiTopping2(null)
                    viewModel.setSelectedAcaiTopping2()
                } else {
                    if (ProducrPrefs.hasAcaiTopping1()) {
                        ProducrPrefs.setAcaiTopping1(null)
                        viewModel.setSelectedAcaiTopping1()
                    }
                }
            }
            item.isChecked = false
            ProducrPrefs.decreaseAcaiSelectionCounter()
        }


        fun addCrepeTopping(item: Topping) {
            if (!ProducrPrefs.hasCrepeTopping1()) {
                ProducrPrefs.setCrepeTopping1(item)
                viewModel.setSelectedCrepeTopping1()
            } else {
                if (!ProducrPrefs.hasCrepeTopping2()) {
                    ProducrPrefs.setCrepeTopping2(item)
                    viewModel.setSelectedCrepeTopping2()
                    refresh()
                }
            }
            ProducrPrefs.increaseAcaiSelectionCounter()
        }

        fun removeCrepeTopping(item: Topping) {
            if (ProducrPrefs.hasCrepeTopping2()) {
                ProducrPrefs.setCrepeTopping2(null)
                viewModel.setSelectedCrepeTopping2()
                refresh()
            } else {
                if (ProducrPrefs.hasCrepeTopping1()) {
                    ProducrPrefs.setCrepeTopping1(null)
                    viewModel.setSelectedCrepeTopping1()
                }
            }
            item.isChecked = false
            ProducrPrefs.decreaseAcaiSelectionCounter()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToppingViewHolder {
        val binding =
            CustomToppingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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