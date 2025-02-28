package com.taptwotimes.dadaacai.ui.home.adapters

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.common.io.Resources
import com.taptwotimes.dadaacai.R
import com.taptwotimes.dadaacai.databinding.CustomToppingBinding
import com.taptwotimes.dadaacai.databinding.ItemHomeBinding
import com.taptwotimes.dadaacai.model.AcaiProductHome
import com.taptwotimes.dadaacai.model.CrepeProductHome
import com.taptwotimes.dadaacai.model.ProductHome
import com.taptwotimes.dadaacai.model.Topping
import com.taptwotimes.dadaacai.ui.home.HomeActivity
import com.taptwotimes.dadaacai.ui.home.HomeFragment
import com.taptwotimes.dadaacai.ui.home.HomeViewModel

class HomeAdapter(val context: Context,
                  val viewModel: HomeViewModel,
                  val homeItemList:ArrayList<ProductHome>,
                  val topOptions: ArrayList<Topping>,
                  val bottomOptions: ArrayList<Topping>):
    RecyclerView.Adapter<HomeAdapter.ItemHomeViewHolder>() {

    class ItemHomeViewHolder(val context: Context,
                             val viewModel: HomeViewModel,
                             val binding:ItemHomeBinding,
                             val topList: ArrayList<Topping>,
                             val bottomList: ArrayList<Topping>)
        :RecyclerView.ViewHolder(binding.root){

        var selectionCounter: Int = 0
        val maxCounter: Int = 3
        var position = 0
        private lateinit var player: MediaPlayer

        fun bind(item:ProductHome){
            binding.tvTitle.text = item.title
            binding.tvSub.text = item.subtitle

            item.image?.let{
                binding.ivCopo.setImageDrawable(
                    AppCompatResources.getDrawable(binding.root.context, it))
            }

            binding.tvPreco.text = "R$ ${item.basePrice}"
            binding.clCard1.setOnClickListener {
                showCustomAlertDialog(context, topList, bottomList, item)
            }
        }

        private fun showCustomAlertDialog(
            context: Context,
            topList: ArrayList<Topping>,
            bottomList: ArrayList<Topping>,
            product: ProductHome
        ) {
            val builder = AlertDialog.Builder(context)
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val dialogView = inflater.inflate(R.layout.custom_select_topping, null)

            // Customizando o AlertDialog
            val title = dialogView.findViewById<TextView>(R.id.tvTitle)
            val sub = dialogView.findViewById<TextView>(R.id.tvCategoria)
            val sub2 = dialogView.findViewById<TextView>(R.id.tvCategoria2)
            val recycler = dialogView.findViewById<RecyclerView>(R.id.rvCategoria)
            val recycler2 = dialogView.findViewById<RecyclerView>(R.id.rvCategoria2)
            val copo = dialogView.findViewById<ImageView>(R.id.ivCopo)
            val btOk = dialogView.findViewById<AppCompatButton>(R.id.btOk)

            when(product){
                is AcaiProductHome -> {
                    title.text = "Toppings"
                    sub.text = "Coberturas"
                    sub2.text = "Frutas"
                }
                is CrepeProductHome -> {
                    title.text = "Recheios"
                    sub.text = "Salgados"
                    sub2.text = "Doces"
                }
            }

            recycler.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = CategoryAdapter(topList, createCheckboxListener(product, context, copo))
            }.also {
                viewModel.addSelectedTopping((recycler.adapter as CategoryAdapter).getSelectedItem(0))
            }
            recycler2.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = CategoryAdapter(bottomList, createCheckboxListener(product,context, copo))
            }
            builder.setView(dialogView)
            val alertDialog = builder.create()

            // Removendo a borda padrão do AlertDialog e adicionando a do CardView
            alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            alertDialog.show()

            btOk.setOnClickListener {
                alertDialog.dismiss()
            }
        }

        private fun createCheckboxListener(product: ProductHome, context: Context, copo:ImageView): MaterialCheckBox.OnCheckedStateChangedListener {
            return MaterialCheckBox.OnCheckedStateChangedListener { checkBox, state ->
                if(checkBox.isChecked){
                    if(selectionCounter<maxCounter){
                        selectionCounter++
                        if(product is AcaiProductHome){
                            animateCopinho(copo, context)
                        }else{
                            copo.visibility = View.GONE
                        }
                    }
                }else{
                    if(selectionCounter>0){
                        selectionCounter--
                        if(product is AcaiProductHome){
                            animateCopinho(copo, context)
                        }else{
                            copo.visibility = View.GONE
                        }
                    }
                }
            }
        }

        private fun animateCopinho(copo:ImageView, context: Context) {
            when(selectionCounter){
                0 -> {
                    copo.visibility = View.GONE
                }
                1 -> {
                    copo.visibility = View.VISIBLE
                    Glide.with(context)
                        .load(context.getDrawable(R.drawable.copinho_parte_baixo))
                        .into(copo)
                }
                2 -> {
                    copo.visibility = View.VISIBLE
                    Glide.with(context)
                        .load(context.getDrawable(R.drawable.copinho_parte_meio))
                        .into(copo)
                }
                3 -> {
                    copo.visibility = View.VISIBLE
                    Glide.with(context)
                        .load(context.getDrawable(R.drawable.copinho_parte_topo))
                        .into(copo)
                }
            }
            playSound(R.raw.plaft, 0)
        }

        fun playSound(sound: Int, time:Long) {
            val handler = Handler(context.mainLooper)
            handler.postDelayed({
                player = MediaPlayer.create(context, sound)
                player.start()
            }, time)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHomeViewHolder {
        val binding = ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemHomeViewHolder(context,viewModel, binding, topOptions, bottomOptions)
    }

    override fun getItemCount(): Int {
        return homeItemList.size
    }

    override fun onBindViewHolder(holder: ItemHomeViewHolder, position: Int) {
        holder.bind(homeItemList[position])
    }

}