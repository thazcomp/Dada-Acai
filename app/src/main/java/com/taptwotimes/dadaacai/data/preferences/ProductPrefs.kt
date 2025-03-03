package com.taptwotimes.dadaacai.data.preferences

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.gson.GsonBuilder
import com.taptwotimes.dadaacai.model.Topping
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

object ProducrPrefs {
    private val ACAI_TOPPING_1: String = "acai_topping_1"
    private val ACAI_TOPPING_2: String = "acai_topping_2"
    private val ACAI_TOPPING_3: String = "acai_topping_3"

    private val CREPE_TOPPING_1: String = "crepe_topping_1"
    private val CREPE_TOPPING_2: String = "crepe_topping_2"

    lateinit var preferences: SharedPreferences
    private val PREFERENCES_FILE_NAME = "PREFERENCES"

    private var acaiSelectionCounter:Int = 0
    private val crepeSelectionCounter:Int = 0

    fun getAcaiTopping1():Topping? {
        return get<Topping>(ACAI_TOPPING_1)
    }

    fun getAcaiTopping2():Topping? {
        return get<Topping>(ACAI_TOPPING_2)
    }

    fun getAcaiTopping3():Topping? {
        return get<Topping>(ACAI_TOPPING_3)
    }

    fun getCrepeTopping1():Topping? {
        return get<Topping>(CREPE_TOPPING_1)
    }

    fun getCrepeTopping2():Topping? {
        return get<Topping>(CREPE_TOPPING_2)
    }

    fun setAcaiTopping1(topping: Topping?) {
        put(topping, ACAI_TOPPING_1)
    }

    fun setAcaiTopping2(topping: Topping?) {
        put(topping, ACAI_TOPPING_2)
    }

    fun setAcaiTopping3(topping: Topping?) {
        put(topping, ACAI_TOPPING_3)
    }

    fun setCrepeTopping1(topping: Topping) {
        put(topping, CREPE_TOPPING_1)
    }

    fun setCrepeTopping2(topping: Topping) {
        put(topping, CREPE_TOPPING_2)
    }

    fun increaseAcaiSelectionCounter(){
        acaiSelectionCounter++
    }

    fun decreaseAcaiSelectionCounter(){
        acaiSelectionCounter--
    }

    fun getAcaiSelectionCounter():Int{
        return acaiSelectionCounter
    }

    fun clear(){
        preferences.edit().clear().apply()
        acaiSelectionCounter = 0
    }

    fun hasAcaiTopping1():Boolean {
        return when(get<Topping>(ACAI_TOPPING_1)){
            null -> { false }
            else -> { true }
        }
    }

    fun hasAcaiTopping2():Boolean {
        return when(get<Topping>(ACAI_TOPPING_2)){
            null -> { false }
            else -> { true }
        }
    }

    fun hasAcaiTopping3():Boolean {
        return when(get<Topping>(ACAI_TOPPING_3)){
            null -> { false }
            else -> { true }
        }
    }

    fun hasCrepeTopping1():Boolean {
        return when(get<Topping>(CREPE_TOPPING_1)){
            null -> { false }
            else -> { true }
        }
    }

    fun hasCrepeTopping2():Boolean {
        return when(get<Topping>(CREPE_TOPPING_2)){
            null -> { false }
            else -> { true }
        }
    }

    fun with(application: Application) {
        preferences = application.getSharedPreferences(
            PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
    }

    fun <T> put(`object`: T, key: String) {
        val jsonString = GsonBuilder().create().toJson(`object`)
        preferences.edit().putString(key, jsonString).apply()
    }

    inline fun <reified T> get(key: String): T? {
        val value = preferences.getString(key, null)
        return GsonBuilder().create().fromJson(value, T::class.java)
    }

}