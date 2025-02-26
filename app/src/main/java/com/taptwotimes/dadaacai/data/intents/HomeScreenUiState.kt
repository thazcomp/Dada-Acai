package com.taptwotimes.dadaacai.data.intents

import com.taptwotimes.dadaacai.model.ItemHome

data class HomeScreenUiState(
    val home:ArrayList<ItemHome> = arrayListOf(),
    val isLoading:Boolean = false
)
