package com.taptwotimes.dadaacai.usecase

import com.taptwotimes.dadaacai.data.preferences.UserPrefs
import com.taptwotimes.dadaacai.data.repository.home.HomeRepository
import com.taptwotimes.dadaacai.model.ProductHome
import com.taptwotimes.dadaacai.model.Topping
import javax.inject.Inject

class HomeUseCase @Inject constructor(private val homeRepository: HomeRepository) {

    suspend fun getHome():ArrayList<ProductHome> {
        return homeRepository.getHome()
    }

    suspend fun addCartItem(id:Int, product: ProductHome, toppings:ArrayList<String>) {
        return homeRepository.saveToCart(id, product, toppings)
    }

    suspend fun getToppings(id:String, name:String, category:String):ArrayList<Topping> {
        return homeRepository.getToppings(id, name, category)
    }

    suspend fun getUser(id:String, success:()->Unit){
        val user = homeRepository.getUser(id)
        user.let{
            UserPrefs.setUserId(it.id)
            UserPrefs.setUserName(it.nome)
            UserPrefs.setUserEmail(it.email)
            UserPrefs.setUserCpf(it.cpf)
            UserPrefs.setUserPhone(it.phone)
            UserPrefs.setUserBairro(it.phone)
            success()
        }
    }

    suspend fun getUserAddress(id:String, success:()->Unit){
        val user = homeRepository.getUserAddress(id)
        user.let{
            UserPrefs.setUserRua(it.rua)
            UserPrefs.setUserBairro(it.bairro)
            UserPrefs.setUserNum(it.numero)
            UserPrefs.setUserComp(it.complemento)
            success()
        }
    }

    suspend fun isReviwed(id:String, success:(Boolean) -> Unit){
        homeRepository.isReviwed(id, success)
    }
}