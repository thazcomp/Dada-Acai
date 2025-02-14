package com.taptwotimes.dadaacai.ui.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.taptwotimes.dadaacai.databinding.ItemHomeBinding
import com.taptwotimes.dadaacai.model.ItemHome

class HomeAdapter(val homeItemList:ArrayList<ItemHome>):
    RecyclerView.Adapter<HomeAdapter.ItemHomeViewHolder>() {

    class ItemHomeViewHolder(val binding:ItemHomeBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item:ItemHome){
            binding.tvTitle.text = item.title
            binding.tvSub.text = item.subtitle
            //Topping1
            try {
                binding.tvTopping1.text = item.options[0].title
                binding.tvResult1.text = item.options[0].toppings[0].viewValue
            }catch (e:IndexOutOfBoundsException){
                binding.clCard1.visibility = View.GONE
                e.printStackTrace()
            }

            //Topping2
            try {
                binding.tvTopping2.text = item.options[1].title
                binding.tvResult2.text = item.options[1].toppings[0].viewValue
            }catch (e:IndexOutOfBoundsException){
                binding.clCard2.visibility = View.GONE
                e.printStackTrace()
            }

            //Topping3
            try {
                binding.tvTopping3.text = item.options[2].title
                binding.tvResult3.text = item.options[2].toppings[0].viewValue
            }catch (e:IndexOutOfBoundsException){
                binding.clCard3.visibility = View.GONE
                e.printStackTrace()
            }

            binding.ivCopo.setImageDrawable(item.image)
            binding.tvPreco.text = "R$ ${item.totalPrice}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHomeViewHolder {
        val binding = ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemHomeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return homeItemList.size
    }

    override fun onBindViewHolder(holder: ItemHomeViewHolder, position: Int) {
        holder.bind(homeItemList[position])
    }

}