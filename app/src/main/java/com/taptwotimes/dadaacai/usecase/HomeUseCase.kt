package com.taptwotimes.dadaacai.usecase

import com.taptwotimes.dadaacai.data.repository.home.HomeRepository
import com.taptwotimes.dadaacai.model.ProductHome
import com.taptwotimes.dadaacai.model.Topping
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class HomeUseCase @Inject constructor(private val homeRepository: HomeRepository, private val viewModelScope: CoroutineScope) {

    suspend fun getHome():ArrayList<ProductHome> {
        return homeRepository.getHome()
    }

    suspend fun getToppings(id:String):ArrayList<Topping> {
        return homeRepository.getToppings(id)
    }
}