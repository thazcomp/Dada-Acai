package com.taptwotimes.dadaacai.ui.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
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
                binding.tvPrice1.visibility = View.VISIBLE
                binding.tvResult1.text = item.options[0].toppings[0].name
                binding.tvPrice1.text = "+R$ 0,00"
            }catch (e:IndexOutOfBoundsException){
                binding.tvResult1.text = "Clique para Adicionar"
                binding.tvPrice1.visibility = View.INVISIBLE
                e.printStackTrace()
            }

            //Topping2
            try {
                binding.tvResult2.visibility = View.VISIBLE
                binding.tvPrice2.visibility = View.VISIBLE
                binding.tvResult2.text = item.options[1].toppings[0].name
                binding.tvPrice2.text = "+R$ 0,00"
            }catch (e:IndexOutOfBoundsException){
                binding.tvResult2.visibility = View.GONE
                binding.tvPrice2.visibility = View.GONE
                //Deixa invisivel se o item acima estiver vazio
                e.printStackTrace()
            }

            //Topping3
            try {
                binding.tvResult3.visibility = View.VISIBLE
                binding.tvPrice3.visibility = View.VISIBLE
                binding.tvResult3.text = item.options[2].toppings[0].name
                binding.tvPrice3.text = "+R$ 0,00"
            }catch (e:IndexOutOfBoundsException){
                binding.tvResult3.visibility = View.GONE
                binding.tvPrice3.visibility = View.GONE
                e.printStackTrace()
            }

            //Topping4
            try {
                binding.tvResult4.visibility = View.VISIBLE
                binding.tvPrice4.visibility = View.VISIBLE
                binding.tvResult4.text = item.options[3].toppings[0].name
                binding.tvPrice4.text = "+R$ ${item.options[3].toppings[0].price}"
            }catch (e:IndexOutOfBoundsException){
                binding.tvResult4.visibility = View.GONE
                binding.tvPrice4.visibility = View.GONE
                e.printStackTrace()
            }

            //Topping5
            try {
                binding.tvResult5.visibility = View.VISIBLE
                binding.tvPrice5.visibility = View.VISIBLE
                binding.tvResult5.text = item.options[4].toppings[0].name
                binding.tvPrice5.text = "+R$ ${item.options[4].toppings[0].price}"
            }catch (e:IndexOutOfBoundsException){
                binding.tvResult5.visibility = View.GONE
                binding.tvPrice5.visibility = View.GONE
                e.printStackTrace()
            }

            //Topping6
            try {
                binding.tvResult6.visibility = View.VISIBLE
                binding.tvPrice6.visibility = View.VISIBLE
                binding.tvResult6.text = item.options[5].toppings[0].name
                binding.tvPrice6.text = "+R$ ${item.options[5].toppings[0].price}"
            }catch (e:IndexOutOfBoundsException){
                binding.tvResult6.visibility = View.GONE
                binding.tvPrice6.visibility = View.GONE
                e.printStackTrace()
            }

            if(binding.tvResult6.visibility == View.VISIBLE){
                binding.tvResultFinal.visibility = View.GONE
            }else{
                binding.tvResultFinal.visibility = View.VISIBLE
            }

            binding.ivCopo.setImageDrawable(
                AppCompatResources.getDrawable(binding.root.context, item.image!!))

            binding.tvPreco.text = "R$ ${item.totalPrice}"
            binding.clCard1.setOnClickListener {
                
            }
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