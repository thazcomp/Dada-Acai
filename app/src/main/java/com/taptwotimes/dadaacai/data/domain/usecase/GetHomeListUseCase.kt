package com.taptwotimes.dadaacai.data.domain.usecase

import com.taptwotimes.dadaacai.model.ProductHome

fun interface GetHomeListUseCase : suspend () -> ArrayList<ProductHome>