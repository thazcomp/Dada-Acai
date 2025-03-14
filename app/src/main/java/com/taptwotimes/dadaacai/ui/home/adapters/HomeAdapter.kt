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
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.taptwotimes.dadaacai.R
import com.taptwotimes.dadaacai.data.preferences.ProductPrefs
import com.taptwotimes.dadaacai.databinding.ItemHomeBinding
import com.taptwotimes.dadaacai.model.AcaiProductHome
import com.taptwotimes.dadaacai.model.BebidasProductHome
import com.taptwotimes.dadaacai.model.BoloProductHome
import com.taptwotimes.dadaacai.model.CategoryItem
import com.taptwotimes.dadaacai.model.CrepeProductHome
import com.taptwotimes.dadaacai.model.ProductHome
import com.taptwotimes.dadaacai.model.Topping
import com.taptwotimes.dadaacai.ui.cart.CartFragment
import com.taptwotimes.dadaacai.ui.home.HomeActivity
import com.taptwotimes.dadaacai.ui.home.HomeFragment
import com.taptwotimes.dadaacai.ui.home.HomeViewModel

class HomeAdapter(
    val context: Context,
    val fragmentManager:FragmentManager?,
    val viewModel: HomeViewModel,
    val viewLifecycleOwner: LifecycleOwner,
    val homeItemList: ArrayList<ProductHome>,
    val topOptions: ArrayList<Topping>,
    val bottomOptions: ArrayList<Topping>
) :
    RecyclerView.Adapter<HomeAdapter.ItemHomeViewHolder>() {

    class ItemHomeViewHolder(
        val context: Context,
        val fragmentManager:FragmentManager?,
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

                is BoloProductHome -> {
                    binding.cvBordas.visibility = View.GONE
                }

                is BebidasProductHome -> {
                    binding.cvBordas.visibility = View.GONE
                }
            }

            item.image?.let {
                binding.ivCopo.setImageDrawable(
                    AppCompatResources.getDrawable(binding.root.context, it)
                )
            }

            binding.clCard1.setOnClickListener {
                when(item){
                    is AcaiProductHome -> {
                        createAcaiCustomAlertDialog(context, topList, bottomList, item)
                    }
                    is CrepeProductHome -> {
                        createCrepeCustomAlertDialog(context, topList, bottomList, item)
                    }
                }
                showCustomAlertDialog()
            }

            binding.btCarrinho.setOnClickListener {
                viewModel.saveToCart(item, getToppings(item))

                fragmentManager?.beginTransaction()?.
                    replace(R.id.frameLayout, CartFragment())?.
                    commit()

            }
        }

        private fun getToppings(item: ProductHome): java.util.ArrayList<String> {
            val list:ArrayList<String> = arrayListOf()
            when(item){
                is AcaiProductHome -> {
                    if(ProductPrefs.hasAcaiTopping3()){
                        list.add(ProductPrefs.getAcaiTopping3()!!.name)
                    }
                    if(ProductPrefs.hasAcaiTopping2()){
                        list.add(ProductPrefs.getAcaiTopping2()!!.name)
                    }
                    if(ProductPrefs.hasAcaiTopping1()){
                        list.add(ProductPrefs.getAcaiTopping1()!!.name)
                    }
                }
                is CrepeProductHome -> { }
                is BoloProductHome -> { }
                is BebidasProductHome -> { }
            }
            return list
        }

        private fun observeAcaiProduct() {
            viewModel.selecteAcaiTopping1.observe(viewLifecycleOwner) { response ->
                response?.let {
                    binding.tvToppingName1.text = response.name
                    binding.tvToppingPrice1.text = response.price
                    binding.tvToppingPrice1.visibility = View.VISIBLE
                    binding.tvToppingName1.visibility = View.VISIBLE
                } ?: run {
                    binding.tvToppingPrice1.visibility = View.GONE
                    binding.tvToppingName1.visibility = View.GONE
                }
            }

            viewModel.selecteAcaiTopping2.observe(viewLifecycleOwner) { response ->
                response?.let {
                    binding.tvToppingName2.text = response.name
                    binding.tvToppingPrice2.text = response.price
                    binding.tvToppingPrice2.visibility = View.VISIBLE
                    binding.tvToppingName2.visibility = View.VISIBLE
                } ?: run {
                    binding.tvToppingPrice2.visibility = View.GONE
                    binding.tvToppingName2.visibility = View.GONE
                }
            }

            viewModel.selecteAcaiTopping3.observe(viewLifecycleOwner) { response ->
                response?.let {
                    binding.tvToppingName3.text = response.name
                    binding.tvToppingPrice3.text = response.price
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
                    binding.tvToppingName1.text = response.name
                    binding.tvToppingPrice1.text = response.price
                    binding.tvToppingPrice1.visibility = View.VISIBLE
                    binding.tvToppingName1.visibility = View.VISIBLE
                } ?: run {
                    binding.tvToppingPrice1.visibility = View.GONE
                    binding.tvToppingName1.visibility = View.GONE
                }
            }

            viewModel.selecteCrepeTopping2.observe(viewLifecycleOwner) { response ->
                response?.let {
                    binding.tvToppingName2.text = response.name
                    binding.tvToppingPrice2.text = response.price
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
            val recycler = dialogView.findViewById<RecyclerView>(R.id.rvCategoria)
            val copo = dialogView.findViewById<ImageView>(R.id.ivCopo)
            val btOk = dialogView.findViewById<AppCompatButton>(R.id.btOk)

            var adapter: CategoryAdapter? = null
            val list = arrayListOf(CategoryItem("Coberturas", topList), CategoryItem("Frutas", bottomList))

            adapter = CategoryAdapter(
                list,
                viewModel,
                item,
                ::animateCopinho
            )
            setRecyclerView(recycler, adapter)

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

            val recycler = dialogView.findViewById<RecyclerView>(R.id.rvCategoria)
            val btOk = dialogView.findViewById<AppCompatButton>(R.id.btOk)

            var adapter: CategoryAdapter? = null
            val list = arrayListOf(CategoryItem("Coberturas", topList), CategoryItem("Frutas", bottomList))

            adapter = CategoryAdapter(
                list,
                viewModel,
                item,
                ::animateCopinho
            )

            setRecyclerView(recycler, adapter)

            builder.setView(dialogView)
            acaiAlertDialog = builder.create()

            btOk.setOnClickListener {
                acaiAlertDialog?.dismiss()
            }

        }

        private fun setRecyclerView(
            recycler: RecyclerView,
            adapter: CategoryAdapter?
        ) {
            recycler.apply {
                layoutManager = LinearLayoutManager(context)
                this.adapter = adapter
            }
        }

        private fun showCustomAlertDialog() {

            acaiAlertDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            acaiAlertDialog?.show()
        }

        private fun animateCopinho(selectionCounter:Int) {
            val copo = dialogView.findViewById<ImageView>(R.id.ivCopinho)
            when (selectionCounter) {
                0 -> {
                    copo.visibility = View.GONE
                }

                1 -> {
                    Glide.with(context)
                        .load(context.getDrawable(R.drawable.copinho_parte_baixo))
                        .into(copo)
                    copo.visibility = View.VISIBLE
                    fadeOutAndHideImage(copo)
                }

                2 -> {
                    Glide.with(context)
                        .load(context.getDrawable(R.drawable.copinho_parte_meio))
                        .into(copo)
                    copo.visibility = View.VISIBLE
                    fadeOutAndHideImage(copo)
                }

                3 -> {
                    Glide.with(context)
                        .load(context.getDrawable(R.drawable.copinho_parte_topo))
                        .into(copo)
                    copo.visibility = View.VISIBLE
                    fadeOutAndHideImage(copo)
                }
            }
        }

        private fun fadeOutAndHideImage(img: ImageView) {
            val fadeOut = AlphaAnimation(1F, 0F)
            fadeOut.setInterpolator(AccelerateInterpolator())
            fadeOut.setDuration(1000)

            fadeOut.setAnimationListener(object: Animation.AnimationListener {
                override fun onAnimationEnd(animation:Animation) {
                    img.setVisibility(View.GONE)
                }
                override fun onAnimationRepeat(animation:Animation) {}
                override  fun onAnimationStart(animation:Animation) {
                    playSound(R.raw.plaft, 0)
                }
            })
            img.startAnimation(fadeOut)
        }

        fun playSound(sound: Int, time: Long) {
            val handler = Handler(context.mainLooper)
            handler.postDelayed({
                player = MediaPlayer.create(context, sound)
                player.start()
            }, time)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHomeViewHolder {
        val binding = ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemHomeViewHolder(
            context,
            fragmentManager,
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