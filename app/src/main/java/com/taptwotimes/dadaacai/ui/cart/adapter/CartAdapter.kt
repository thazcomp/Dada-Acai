package com.taptwotimes.dadaacai.ui.cart.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.taptwotimes.dadaacai.R
import com.taptwotimes.dadaacai.databinding.ItemCartBinding
import com.taptwotimes.dadaacai.model.FirebaseCartItem
import com.taptwotimes.dadaacai.model.ProductHome
import com.taptwotimes.dadaacai.ui.cart.CartViewModel
import com.taptwotimes.dadaacai.ui.home.HomeViewModel

class CartAdapter(val list:ArrayList<FirebaseCartItem>,
                  val viewModel: CartViewModel,
                  private val context: Context
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(
        open val context: Context,
        open val list:ArrayList<FirebaseCartItem>,
        open val refresh: () -> Unit,
        open val binding: ItemCartBinding,
        open val viewModel: CartViewModel
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item:FirebaseCartItem, position: Int){
            loadContent(item, position)
        }

        private fun loadContent(currentItem: FirebaseCartItem, position: Int) {
            if(currentItem.itemName != ""){
                binding.tvTitle.text = currentItem.itemName

                try{
                    if(currentItem.toppings[0] != ""){
                        binding.tvTop1.visibility = View.VISIBLE
                        binding.tvTop1.text = currentItem.toppings[0]
                    }else{
                        binding.tvTop1.visibility = View.GONE
                    }
                }catch (e:IndexOutOfBoundsException){
                    binding.tvTop1.visibility = View.GONE
                }

                try{
                    if(currentItem.toppings[1] != ""){
                        binding.tvTop2.visibility = View.VISIBLE
                        binding.tvTop2.text = currentItem.toppings[1]
                    }else{
                        binding.tvTop2.visibility = View.GONE
                    }
                }catch (e:IndexOutOfBoundsException){
                    binding.tvTop2.visibility = View.GONE
                }

                try{
                    if(currentItem.toppings[2] != ""){
                        binding.tvTop3.visibility = View.VISIBLE
                        binding.tvTop3.text = currentItem.toppings[2]
                    }else{
                        binding.tvTop3.visibility = View.GONE
                    }
                }catch (e:IndexOutOfBoundsException){
                    binding.tvTop3.visibility = View.GONE
                }

                binding.tvPrice.text = currentItem.totalPrice
                loadImage(AppCompatResources.getDrawable(context, R.drawable.acai1)!!)

                binding.ivDelete.setOnClickListener {
                    val itemsToRemove = list.filter { it.id == currentItem.id }
                    list.removeAll(itemsToRemove)
                    itemsToRemove.forEach { viewModel.delete(it.id) }
                    refresh()
                }
            }
        }

        private fun loadImage(image: Drawable) {
            Glide.with(context).load(image).into(binding.ivImage)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(context, list,
            refresh = ::refresh,
            binding,
            viewModel
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

    fun refresh(){
        notifyDataSetChanged()
    }

    fun clear(){
        list.clear()
        notifyDataSetChanged()
    }
}