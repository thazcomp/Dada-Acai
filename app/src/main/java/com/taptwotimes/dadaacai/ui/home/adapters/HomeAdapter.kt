package com.taptwotimes.dadaacai.ui.home.adapters

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.taptwotimes.dadaacai.R
import com.taptwotimes.dadaacai.databinding.ItemHomeBinding
import com.taptwotimes.dadaacai.model.AcaiProductHome
import com.taptwotimes.dadaacai.model.CrepeProductHome
import com.taptwotimes.dadaacai.model.ProductHome
import com.taptwotimes.dadaacai.model.Topping
import com.taptwotimes.dadaacai.ui.home.HomeViewModel

class HomeAdapter(
    val context: Context,
    val viewModel: HomeViewModel,
    val viewLifecycleOwner: LifecycleOwner,
    val homeItemList: ArrayList<ProductHome>,
    val topOptions: ArrayList<Topping>,
    val bottomOptions: ArrayList<Topping>
) :
    RecyclerView.Adapter<HomeAdapter.ItemHomeViewHolder>() {

    class ItemHomeViewHolder(
        val context: Context,
        val viewModel: HomeViewModel,
        val viewLifecycleOwner: LifecycleOwner,
        val binding: ItemHomeBinding,
        val topList: ArrayList<Topping>,
        val bottomList: ArrayList<Topping>
    ) : RecyclerView.ViewHolder(binding.root) {

        private var acaiAlertDialog: AlertDialog? = null
        private var crepeAlertDialog: AlertDialog? = null
        private lateinit var dialogView: View
        private lateinit var builder: AlertDialog.Builder
        private lateinit var player: MediaPlayer

        fun bind(
            item: ProductHome
        ) {
            binding.tvTitle.text = item.title
            binding.tvSub.text = item.subtitle
            when (item) {
                is AcaiProductHome -> {
                    if (acaiAlertDialog == null) {
                        createAcaiCustomAlertDialog(context, topList, bottomList, item)
                    }
                    observeAcaiProduct()
                    binding.cvBordas.visibility = View.GONE
                }

                is CrepeProductHome -> {
                    if (crepeAlertDialog == null) {
                        createCrepeCustomAlertDialog(context, topList, bottomList, item)
                    }
                    observeCrepeProduct()
                    binding.cvBordas.visibility = View.VISIBLE
                }
            }

            item.image?.let {
                binding.ivCopo.setImageDrawable(
                    AppCompatResources.getDrawable(binding.root.context, it)
                )
            }

            binding.clCard1.setOnClickListener {
                showCustomAlertDialog()
            }
        }

        private fun observeAcaiProduct() {
            viewModel.selecteAcaiTopping1.observe(viewLifecycleOwner) { response ->
                response?.let {
                    binding.tvToppingName1.text = response?.name
                    binding.tvToppingPrice1.text = response?.price
                    binding.tvToppingPrice1.visibility = View.VISIBLE
                    binding.tvToppingName1.visibility = View.VISIBLE
                } ?: run {
                    binding.tvToppingPrice1.visibility = View.GONE
                    binding.tvToppingName1.visibility = View.GONE
                }
            }

            viewModel.selecteAcaiTopping2.observe(viewLifecycleOwner) { response ->
                response?.let {
                    binding.tvToppingName2.text = response?.name
                    binding.tvToppingPrice2.text = response?.price
                    binding.tvToppingPrice2.visibility = View.VISIBLE
                    binding.tvToppingName2.visibility = View.VISIBLE
                } ?: run {
                    binding.tvToppingPrice2.visibility = View.GONE
                    binding.tvToppingName2.visibility = View.GONE
                }
            }

            viewModel.selecteAcaiTopping3.observe(viewLifecycleOwner) { response ->
                response?.let {
                    binding.tvToppingName3.text = response?.name
                    binding.tvToppingPrice3.text = response?.price
                    binding.tvToppingPrice3.visibility = View.VISIBLE
                    binding.tvToppingName3.visibility = View.VISIBLE
                    binding.tvAdicionar.visibility = View.GONE
                } ?: run {
                    binding.tvToppingPrice3.visibility = View.GONE
                    binding.tvToppingName3.visibility = View.GONE
                    binding.tvAdicionar.visibility = View.VISIBLE
                }
            }
        }

        private fun observeCrepeProduct() {
            viewModel.selecteCrepeTopping1.observe(viewLifecycleOwner) { response ->
                response?.let {
                    binding.tvToppingName1.text = response?.name
                    binding.tvToppingPrice1.text = response?.price
                    binding.tvToppingPrice1.visibility = View.VISIBLE
                    binding.tvToppingName1.visibility = View.VISIBLE
                } ?: run {
                    binding.tvToppingPrice1.visibility = View.GONE
                    binding.tvToppingName1.visibility = View.GONE
                }
            }

            viewModel.selecteCrepeTopping2.observe(viewLifecycleOwner) { response ->
                response?.let {
                    binding.tvToppingName2.text = response?.name
                    binding.tvToppingPrice2.text = response?.price
                    binding.tvToppingPrice2.visibility = View.VISIBLE
                    binding.tvToppingName2.visibility = View.VISIBLE
                    binding.tvAdicionar.visibility = View.GONE
                } ?: run {
                    binding.tvToppingPrice2.visibility = View.GONE
                    binding.tvToppingName2.visibility = View.GONE
                    binding.tvAdicionar.visibility = View.VISIBLE
                }
            }
            binding.tvToppingPrice3.visibility = View.GONE
            binding.tvToppingName3.visibility = View.GONE
        }

        private fun createAcaiCustomAlertDialog(
            context: Context,
            topList: ArrayList<Topping>,
            bottomList: ArrayList<Topping>,
            item: AcaiProductHome
        ) {
            builder = AlertDialog.Builder(context)
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            dialogView = inflater.inflate(R.layout.custom_select_topping, null)

            // Customizando o AlertDialog
            val title = dialogView.findViewById<TextView>(R.id.tvTitle)
            val sub = dialogView.findViewById<TextView>(R.id.tvCategoria)
            val sub2 = dialogView.findViewById<TextView>(R.id.tvCategoria2)
            val recycler = dialogView.findViewById<RecyclerView>(R.id.rvCategoria)
            val recycler2 = dialogView.findViewById<RecyclerView>(R.id.rvCategoria2)
            val copo = dialogView.findViewById<ImageView>(R.id.ivCopo)
            val btOk = dialogView.findViewById<AppCompatButton>(R.id.btOk)

            var adapterTop: CategoryAdapter? = null
            var adapterBottom: CategoryAdapter? = null

            title.text = "Toppings"
            sub.text = "Coberturas"
            sub2.text = "Frutas"

            adapterTop = CategoryAdapter(
                topList,
                viewModel,
                item
            )
            adapterBottom = CategoryAdapter(
                bottomList,
                viewModel,
                item
            )

            setRecyclerView(recycler, recycler2, adapterTop, adapterBottom)

            builder.setView(dialogView)
            acaiAlertDialog = builder.create()

            btOk.setOnClickListener {
                acaiAlertDialog?.dismiss()
            }

        }

        private fun createCrepeCustomAlertDialog(
            context: Context,
            topList: ArrayList<Topping>,
            bottomList: ArrayList<Topping>,
            item: CrepeProductHome
        ) {
            builder = AlertDialog.Builder(context)
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            dialogView = inflater.inflate(R.layout.custom_select_topping, null)

            // Customizando o AlertDialog
            val title = dialogView.findViewById<TextView>(R.id.tvTitle)
            val sub = dialogView.findViewById<TextView>(R.id.tvCategoria)
            val sub2 = dialogView.findViewById<TextView>(R.id.tvCategoria2)
            val recycler = dialogView.findViewById<RecyclerView>(R.id.rvCategoria)
            val recycler2 = dialogView.findViewById<RecyclerView>(R.id.rvCategoria2)
            val copo = dialogView.findViewById<ImageView>(R.id.ivCopo)
            val btOk = dialogView.findViewById<AppCompatButton>(R.id.btOk)

            var adapterTop: CategoryAdapter? = null
            var adapterBottom: CategoryAdapter? = null

            title.text = "Recheios"
            title.text = "Recheios"
            sub.text = "Salgados"
            sub2.text = "Doces"

            adapterTop = CategoryAdapter(
                topList,
                viewModel,
                item
            )
            adapterBottom = CategoryAdapter(
                bottomList,
                viewModel,
                item
            )

            setRecyclerView(recycler, recycler2, adapterTop, adapterBottom)

            builder.setView(dialogView)
            acaiAlertDialog = builder.create()

            btOk.setOnClickListener {
                acaiAlertDialog?.dismiss()
            }

        }

        private fun setRecyclerView(
            recycler: RecyclerView,
            recycler2: RecyclerView,
            adapterTop: CategoryAdapter?,
            adapterBottom: CategoryAdapter?
        ) {
            recycler.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = adapterTop
            }
            recycler2.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = adapterBottom
            }

        }

        private fun showCustomAlertDialog() {

            acaiAlertDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            acaiAlertDialog?.show()
        }

//        private fun createCheckboxListener(
//            product: ProductHome,
//            context: Context,
//            copo: ImageView
//        ): MaterialCheckBox.OnCheckedStateChangedListener {
//            return MaterialCheckBox.OnCheckedStateChangedListener { checkBox, state ->
//                if (checkBox.isChecked) {
//                    if (selectionCounter < maxCounter) {
//                        selectionCounter++
//                        if (product is AcaiProductHome) {
//                            animateCopinho(copo, context)
//                        } else {
//                            copo.visibility = View.GONE
//                        }
//                    }
//                } else {
//                    if (selectionCounter > 0) {
//                        selectionCounter--
//                        if (product is AcaiProductHome) {
//                            animateCopinho(copo, context)
//                        } else {
//                            copo.visibility = View.GONE
//                        }
//                    }
//                }
//            }
//        }
//
//        private fun animateCopinho(copo: ImageView, context: Context) {
//            when (selectionCounter) {
//                0 -> {
//                    copo.visibility = View.GONE
//                }
//
//                1 -> {
//                    copo.visibility = View.VISIBLE
//                    Glide.with(context)
//                        .load(context.getDrawable(R.drawable.copinho_parte_baixo))
//                        .into(copo)
//                }
//
//                2 -> {
//                    copo.visibility = View.VISIBLE
//                    Glide.with(context)
//                        .load(context.getDrawable(R.drawable.copinho_parte_meio))
//                        .into(copo)
//                }
//
//                3 -> {
//                    copo.visibility = View.VISIBLE
//                    Glide.with(context)
//                        .load(context.getDrawable(R.drawable.copinho_parte_topo))
//                        .into(copo)
//                }
//            }
//            playSound(R.raw.plaft, 0)
//        }
//
//        fun playSound(sound: Int, time: Long) {
//            val handler = Handler(context.mainLooper)
//            handler.postDelayed({
//                player = MediaPlayer.create(context, sound)
//                player.start()
//            }, time)
//        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHomeViewHolder {
        val binding = ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemHomeViewHolder(
            context,
            viewModel,
            viewLifecycleOwner,
            binding,
            topOptions,
            bottomOptions
        )
    }

    override fun getItemCount(): Int {
        return homeItemList.size
    }

    override fun onBindViewHolder(holder: ItemHomeViewHolder, position: Int) {

        holder.bind(homeItemList[position])
    }

}