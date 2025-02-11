package com.taptwotimes.dadaacai.ui.cart.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import com.taptwotimes.dadaacai.databinding.ItemCartBinding
import com.taptwotimes.dadaacai.model.CartItemAcai

class CartListAdapter(private val list:List<CartItemAcai>,
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
        val currentItem = getItem(position) as CartItemAcai
        loadContent(currentItem)

        return view
    }

    private fun loadContent(currentItem: CartItemAcai) {
        binding.tvTitle.text = currentItem.title
        binding.tvTop1.text = currentItem.top1
        binding.tvTop2.text = currentItem.top2
        binding.tvTop3.text = currentItem.top3
        binding.tvPrice.text = currentItem.total
        loadImage(currentItem.image)
    }

    private fun loadImage(image:Drawable) {
        Glide.with(context).load(image).into(binding.ivImage)
    }

}