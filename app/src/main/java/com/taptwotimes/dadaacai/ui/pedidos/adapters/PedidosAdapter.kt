package com.taptwotimes.dadaacai.ui.pedidos.adapters

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.taptwotimes.dadaacai.R
import com.taptwotimes.dadaacai.data.preferences.UserPrefs
import com.taptwotimes.dadaacai.databinding.ItemPedidosMiniBinding
import com.taptwotimes.dadaacai.model.FirebaseCartItem
import com.taptwotimes.dadaacai.model.Pedido
import com.taptwotimes.dadaacai.ui.cart.CartViewModel
import com.taptwotimes.dadaacai.ui.cart.adapter.CartConfirmAdapter
import com.taptwotimes.dadaacai.ui.pedidos.PedidosViewModel

class PedidosAdapter(val list:ArrayList<Pedido>,
                     val cartViewModel:CartViewModel,
                     val context: Context
):RecyclerView.Adapter<PedidosAdapter.PedidoViewHolder>() {

    private var confirmAlertDialog: AlertDialog? = null
    private lateinit var dialogView: View
    private lateinit var builder: AlertDialog.Builder

    class PedidoViewHolder(
        val createDialog: (Pedido) -> Unit,
        val binding:ItemPedidosMiniBinding,
        val cartViewModel:CartViewModel,
    ):RecyclerView.ViewHolder(binding.root){

        fun bind(item: Pedido){
            loadContent(item)
        }

        private fun loadContent(item: Pedido) {
            binding.tvId.text = item.id
            binding.tvStatusTitle.text = "STATUS: ${item.status}"
            binding.tvName.text = UserPrefs.getUserName()
            binding.tvEndereco.text = "${UserPrefs.getUserRua()},${UserPrefs.getUserNum()} - ${UserPrefs.getUserBairro()}"
            binding.tvPrice.text = item.payment

            binding.btDetalhes.setOnClickListener {
                createDialog(item)
            }
        }

    }

    private fun createConfirmDialog(item: Pedido) {

        builder = AlertDialog.Builder(context)
        val inflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        dialogView = inflater.inflate(R.layout.custom_dialog_readonly, null)

        val recycler = dialogView.findViewById<RecyclerView>(R.id.list)
        val metodo: TextView = dialogView.findViewById(R.id.tvPay)
        metodo.text = item.payment


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
        }

        val btTrocar = dialogView.findViewById<AppCompatButton>(R.id.btTrocar)
        btTrocar.setOnClickListener {
            confirmAlertDialog?.dismiss()
        }

        var adapter: CartConfirmAdapter? = null
        val itens:ArrayList<FirebaseCartItem> = item.itens

        adapter = CartConfirmAdapter(
            itens,
            cartViewModel,
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidoViewHolder {
        val binding = ItemPedidosMiniBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PedidoViewHolder(
            ::createConfirmDialog,
            binding,
            cartViewModel
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: PedidoViewHolder, position: Int) {
        holder.bind(list[position])
    }
}