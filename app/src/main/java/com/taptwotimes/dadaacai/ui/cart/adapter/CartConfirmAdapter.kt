package com.taptwotimes.dadaacai.ui.cart.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.taptwotimes.dadaacai.databinding.ItemPedidosCartBinding
import com.taptwotimes.dadaacai.model.FirebaseCartItem
import com.taptwotimes.dadaacai.ui.cart.CartViewModel

class CartConfirmAdapter(val list:ArrayList<FirebaseCartItem>,
                         val viewModel: CartViewModel,
                         private val context: Context
) : RecyclerView.Adapter<CartConfirmAdapter.ViewHolder>() {

    class ViewHolder(
        open val context: Context,
        open val list:ArrayList<FirebaseCartItem>,
        open val refresh: () -> Unit,
        open val binding: ItemPedidosCartBinding,
        open val viewModel: CartViewModel
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item:FirebaseCartItem, position: Int){
            binding.tvTitle.text = item.itemName
            binding.tvPrice.text = item.totalPrice
            try{
                binding.tvTop3.visibility = View.VISIBLE
                binding.tvTop3.text = item.toppings[2]
            }catch (e:IndexOutOfBoundsException){
                binding.tvTop3.visibility = View.GONE
            }

            try{
                binding.tvTop2.visibility = View.VISIBLE
                binding.tvTop2.text = item.toppings[1]
            }catch (e:IndexOutOfBoundsException){
                binding.tvTop2.visibility = View.GONE
            }

            try{
                binding.tvTop1.visibility = View.VISIBLE
                binding.tvTop1.text = item.toppings[0]
            }catch (e:IndexOutOfBoundsException){
                binding.tvTop1.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPedidosCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(context, list, ::refresh, binding, viewModel)
    }

    private fun refresh() {
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

}