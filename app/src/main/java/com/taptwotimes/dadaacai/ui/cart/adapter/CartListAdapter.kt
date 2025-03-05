package com.taptwotimes.dadaacai.ui.cart.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.appcompat.content.res.AppCompatResources
import com.bumptech.glide.Glide
import com.taptwotimes.dadaacai.R
import com.taptwotimes.dadaacai.databinding.ItemCartBinding
import com.taptwotimes.dadaacai.model.FirebaseCartItem
import com.taptwotimes.dadaacai.ui.cart.CartViewModel

class CartListAdapter(private val list:ArrayList<FirebaseCartItem>,
                      val viewModel: CartViewModel,
                      private val context: Context
): BaseAdapter() {

    lateinit var binding: ItemCartBinding

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view:View? = convertView
        if(view == null){
            binding = ItemCartBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
            view = binding.root
        }
        val currentItem = getItem(position) as FirebaseCartItem
        loadContent(currentItem)

        return view
    }

    private fun loadContent(currentItem: FirebaseCartItem) {
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
                notifyDataSetChanged()
            }
        }
    }

    private fun loadImage(image:Drawable) {
        Glide.with(context).load(image).into(binding.ivImage)
    }

    fun clear(){
        list.clear()
        notifyDataSetChanged()
    }

}