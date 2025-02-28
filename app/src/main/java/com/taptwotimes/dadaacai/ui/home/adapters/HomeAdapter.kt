package com.taptwotimes.dadaacai.ui.home.adapters

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.taptwotimes.dadaacai.R
import com.taptwotimes.dadaacai.databinding.ItemHomeBinding
import com.taptwotimes.dadaacai.model.AcaiProductHome
import com.taptwotimes.dadaacai.model.CrepeProductHome
import com.taptwotimes.dadaacai.model.ProductHome
import com.taptwotimes.dadaacai.model.Topping

class HomeAdapter(val homeItemList:ArrayList<ProductHome>,
                  val toppings: ArrayList<Topping>):
    RecyclerView.Adapter<HomeAdapter.ItemHomeViewHolder>() {

    class ItemHomeViewHolder(val binding:ItemHomeBinding,
                             val toppings: ArrayList<Topping>)
        :RecyclerView.ViewHolder(binding.root){

        fun bind(item:ProductHome){
            binding.tvTitle.text = item.title
            binding.tvSub.text = item.subtitle

            item.image?.let{
                binding.ivCopo.setImageDrawable(
                    AppCompatResources.getDrawable(binding.root.context, it))
            }

            binding.tvPreco.text = "R$ ${item.basePrice}"
            binding.clCard1.setOnClickListener {
                showCustomAlertDialog(binding.root.context, toppings)
            }
        }

        private fun showCustomAlertDialog(context: Context, toppings: ArrayList<Topping>) {
            val builder = AlertDialog.Builder(context)
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val dialogView = inflater.inflate(R.layout.custom_select_topping, null)

            // Customizando o AlertDialog
            val title = dialogView.findViewById<TextView>(R.id.tvTitle)
            val sub = dialogView.findViewById<TextView>(R.id.tvCategoria)
            val sub2 = dialogView.findViewById<TextView>(R.id.tvCategoria2)
            val recycler = dialogView.findViewById<RecyclerView>(R.id.rvCategoria)
            val recycler2 = dialogView.findViewById<RecyclerView>(R.id.rvCategoria2)

            title.text = "Toppings"
            sub.text = "Coberturas"
            recycler.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = CategoryAdapter(toppings)
            }

            sub2.text = "Frutas"
            recycler2.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = CategoryAdapter(toppings)
            }

            builder.setView(dialogView)
                .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }

            val alertDialog = builder.create()

            // Removendo a borda padrão do AlertDialog e adicionando a do CardView
            alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            alertDialog.show()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHomeViewHolder {
        val binding = ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemHomeViewHolder(binding, toppings)
    }

    override fun getItemCount(): Int {
        return homeItemList.size
    }

    override fun onBindViewHolder(holder: ItemHomeViewHolder, position: Int) {
        holder.bind(homeItemList[position])
    }

}