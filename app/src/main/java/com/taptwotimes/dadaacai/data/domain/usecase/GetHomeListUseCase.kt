package com.taptwotimes.dadaacai.data.domain.usecase

import com.taptwotimes.dadaacai.model.ItemHome

fun interface GetHomeListUseCase : suspend () -> ArrayList<ItemHome>