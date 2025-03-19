package com.taptwotimes.dadaacai.data.repository.pedidos

import com.taptwotimes.dadaacai.model.Pedido

interface PedidosRepository {

    suspend fun getPedidos():ArrayList<Pedido>
}