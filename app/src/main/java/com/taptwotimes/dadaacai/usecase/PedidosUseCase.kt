package com.taptwotimes.dadaacai.usecase

import com.taptwotimes.dadaacai.data.repository.pedidos.PedidosRepository
import com.taptwotimes.dadaacai.model.Pedido
import javax.inject.Inject

class PedidosUseCase @Inject constructor(private val repository: PedidosRepository) {

    suspend fun getPedidos():ArrayList<Pedido>{
        return repository.getPedidos()
    }

}