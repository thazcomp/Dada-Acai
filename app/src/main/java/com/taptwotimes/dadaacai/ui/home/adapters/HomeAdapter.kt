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

class HomeAdapter(val homeItemList:ArrayList<ProductHome>):
    RecyclerView.Adapter<HomeAdapter.ItemHomeViewHolder>() {

    class ItemHomeViewHolder(val binding:ItemHomeBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item:ProductHome){
            binding.tvTitle.text = item.title
            binding.tvSub.text = item.subtitle

            if(item is AcaiProductHome){
                createAcaiProduct(item)
            }else if(item is CrepeProductHome){
                createCrepeProduct(item)
            }
            item.image?.let{
                binding.ivCopo.setImageDrawable(
                    AppCompatResources.getDrawable(binding.root.context, it))
            }

            binding.tvPreco.text = "R$ ${item.basePrice}"
            binding.clCard1.setOnClickListener {
                showCustomAlertDialog(binding.root.context)
            }
        }

        private fun createAcaiProduct(item:ProductHome) {
            //Topping1
            try {
                binding.tvPrice1.visibility = View.VISIBLE
                binding.tvResult1.text = item.coberturas?.get(0)?.name
                binding.tvPrice1.text = item.coberturas?.get(0)?.price
            }catch (e:IndexOutOfBoundsException){
                binding.tvResult1.text = "Clique para Adicionar"
                binding.tvPrice1.visibility = View.INVISIBLE
                e.printStackTrace()
            }

            //Topping2
            try {
                binding.tvResult2.visibility = View.VISIBLE
                binding.tvPrice2.visibility = View.VISIBLE
                binding.tvResult2.text = item.coberturas?.get(1)?.name
                binding.tvPrice2.text = item.coberturas?.get(1)?.price
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
                binding.tvResult3.text = item.coberturas?.get(2)?.name
                binding.tvPrice3.text = item.coberturas?.get(2)?.price
            }catch (e:IndexOutOfBoundsException){
                binding.tvResult3.visibility = View.GONE
                binding.tvPrice3.visibility = View.GONE
                e.printStackTrace()
            }

            if(isTopping3Selected(binding)){
                binding.tvResultFinal.visibility = View.GONE
            }else{
                binding.tvResultFinal.visibility = View.VISIBLE
            }
        }

        private fun createCrepeProduct(item:ProductHome) {
            //Topping1
            try {
                binding.tvPrice1.visibility = View.VISIBLE
                binding.tvResult1.text = item.coberturas?.get(0)?.name
                binding.tvPrice1.text = item.coberturas?.get(0)?.price
            }catch (e:IndexOutOfBoundsException){
                binding.tvResult1.text = "Clique para Adicionar"
                binding.tvPrice1.visibility = View.INVISIBLE
                e.printStackTrace()
            }

            //Topping2
            try {
                binding.tvResult2.visibility = View.VISIBLE
                binding.tvPrice2.visibility = View.VISIBLE
                binding.tvResult2.text = item.coberturas?.get(1)?.name
                binding.tvPrice2.text = item.coberturas?.get(1)?.price
            }catch (e:IndexOutOfBoundsException){
                binding.tvResult2.visibility = View.GONE
                binding.tvPrice2.visibility = View.GONE
                //Deixa invisivel se o item acima estiver vazio
                e.printStackTrace()
            }

            //Topping3
            binding.tvResult3.visibility = View.GONE
            binding.tvPrice3.visibility = View.GONE

            if(isTopping2Selected(binding)){
                binding.tvResultFinal.visibility = View.GONE
            }else{
                binding.tvResultFinal.visibility = View.VISIBLE
            }

        }

        fun isTopping2Selected(binding:ItemHomeBinding):Boolean = binding.tvResult2.visibility == View.VISIBLE
        fun isTopping3Selected(binding:ItemHomeBinding):Boolean = binding.tvResult3.visibility == View.VISIBLE


        private fun showCustomAlertDialog(context: Context) {
            val builder = AlertDialog.Builder(context)
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val dialogView = inflater.inflate(R.layout.custom_select_topping, null)

            // Customizando o AlertDialog
            val title = dialogView.findViewById<TextView>(R.id.tvTitle)
            val sub = dialogView.findViewById<TextView>(R.id.tvCategoria)
            val sub2 = dialogView.findViewById<TextView>(R.id.tvCategoria2)
            val recycler = dialogView.findViewById<RecyclerView>(R.id.rvCategoria)
            val recycler2 = dialogView.findViewById<RecyclerView>(R.id.rvCategoria2)

            title.text = "Coberturas"
            sub.text = "Toppings"
            recycler.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = CategoryAdapter(createToppingList())
            }

            sub2.text = "Frutas"
            recycler2.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = CategoryAdapter(createFruitList())
            }

            builder.setView(dialogView)
                .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }

            val alertDialog = builder.create()

            // Removendo a borda padrão do AlertDialog e adicionando a do CardView
            alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            alertDialog.show()
        }

        private fun createToppingList(): ArrayList<Topping> {
            val condimentos:ArrayList<Topping> = arrayListOf()
            condimentos.add(
                Topping(
                    "Leite Condensado", "0,00"
                )
            )
            condimentos.add(
                Topping(
                    "Paçoca", "0,00"
                )
            )
            return condimentos
        }

        private fun createFruitList(): ArrayList<Topping> {
            val frutas:ArrayList<Topping> = arrayListOf()
            frutas.add(
                Topping(
                    "Banana", "0,00"
                )
            )
            frutas.add(Topping(
                "Morango", "0,00"
            ))
            return frutas
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