package com.taptwotimes.dadaacai.ui.cart

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.auth.User
import com.taptwotimes.dadaacai.R
import com.taptwotimes.dadaacai.data.preferences.UserPrefs
import com.taptwotimes.dadaacai.databinding.FragmentCartBinding
import com.taptwotimes.dadaacai.model.FirebaseCartItem
import com.taptwotimes.dadaacai.ui.cart.adapter.CartAdapter
import com.taptwotimes.dadaacai.ui.cart.adapter.CartConfirmAdapter
import com.taptwotimes.dadaacai.ui.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private val viewModel:CartViewModel by viewModels()

    private var confirmAlertDialog: AlertDialog? = null
    private lateinit var dialogView: View
    private lateinit var builder: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)

        binding.btLimpar.setOnClickListener {
            viewModel.clean()
            (binding.list.adapter as CartAdapter).clear()
        }

        binding.btCarrinho.setOnClickListener {
            context?.let {
                createConfirmDialog(it, viewModel.cart.value!!)
            }
        }
        return binding.root
    }

    private fun createConfirmDialog(
        context: Context,
        itens: ArrayList<FirebaseCartItem>
    ) {
        builder = AlertDialog.Builder(context)
        val inflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        dialogView = inflater.inflate(R.layout.custom_dialog_confirm, null)

        val recycler = dialogView.findViewById<RecyclerView>(R.id.list)
        val radioGroup1: RadioGroup = dialogView.findViewById(R.id.radioGroup1)


        UserPrefs.setPaymentMethod("Pix")
        radioGroup1.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radioPix1 -> {
                    UserPrefs.setPaymentMethod("Pix")
                }
                R.id.radioCredito1 -> {
                    UserPrefs.setPaymentMethod("Cartão-Crédito")
                }
                R.id.radioDebito1 -> {
                    UserPrefs.setPaymentMethod("Cartão-Débito")
                }
                R.id.radioDinheiro1 -> {
                    UserPrefs.setPaymentMethod("Dinheiro")
                }
            }
        }


        val name = dialogView.findViewById<TextView>(R.id.tvName)
        name.text = UserPrefs.getUserName()
        val rua = dialogView.findViewById<TextView>(R.id.tvRua)
        rua.text = "${UserPrefs.getUserRua()}, ${UserPrefs.getUserNum()}"
        val bairro = dialogView.findViewById<TextView>(R.id.tvBairro)
        bairro.text = UserPrefs.getUserBairro()
        val comp = dialogView.findViewById<TextView>(R.id.tvComp)
        comp.text = UserPrefs.getUserComp()

        val btPagamento = dialogView.findViewById<AppCompatButton>(R.id.btPagamento)
        btPagamento.setOnClickListener {
            confirmAlertDialog?.dismiss()
            viewModel.cratePedidoFirebase(itens)
            goToHome()
        }

        val btTrocar = dialogView.findViewById<AppCompatButton>(R.id.btTrocar)
        btTrocar.setOnClickListener {
            confirmAlertDialog?.dismiss()
        }

        var adapter: CartConfirmAdapter? = null


        adapter = CartConfirmAdapter(
            itens,
            viewModel,
            context)

        recycler.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }

        builder.setView(dialogView)
        confirmAlertDialog = builder.create()
        confirmAlertDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        confirmAlertDialog?.show()
    }

    private fun goToHome(){
        fragmentManager?.beginTransaction()?.
        replace(R.id.frameLayout, HomeFragment())?.
        commit()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCart()

        viewModel.cart.observe(viewLifecycleOwner){
            createAdapter()
        }

    }

    val myLinearLayoutManager = object : LinearLayoutManager(activity) {
        override fun canScrollHorizontally(): Boolean {
            return false
        }
    }

    private fun createAdapter() {
        context?.let{ c ->
            binding.list.apply {
                layoutManager = myLinearLayoutManager
                adapter = CartAdapter(
                    listaAcai(),
                    viewModel,
                    context
                )
            }
        }
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private fun listaAcai(): ArrayList<FirebaseCartItem>{
        return viewModel.cart.value!!
    }

}