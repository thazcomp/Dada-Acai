package com.taptwotimes.dadaacai.data.preferences

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import com.google.gson.GsonBuilder

object UserPrefs {
    lateinit var preferences: SharedPreferences
    private val PREFERENCES_FILE_NAME = "USER_PREFERENCES"

    private val USER_ID: String = "user_id"
    private val USER_NAME: String = "user_name"
    private val USER_EMAIL: String = "user_email"
    private val USER_CPF: String = "user_cpf"
    private val USER_PHONE: String = "user_phone"
    private val USER_PHOTO: String = "user_photo"
    private val USER_BAIRRO: String = "user_bairro"
    private val USER_RUA: String = "user_rua"
    private val USER_NUM: String = "user_num"
    private val USER_COMP: String = "user_comp"

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

    fun getUserId():String?{
        return get<String>(USER_ID)
    }

    fun setUserId(id:String){
        put(id, USER_ID)
    }

    fun getUserName():String?{
        return get<String>(USER_NAME)
    }

    fun setUserName(name:String){
        put(name, USER_NAME)
    }

    fun getUserEmail():String?{
        return get<String>(USER_EMAIL)
    }

    fun setUserEmail(email:String){
        put(email, USER_EMAIL)
    }

    fun getUserCpf():String?{
        return get<String>(USER_CPF)
    }

    fun setUserCpf(cpf:String){
        put(cpf, USER_CPF)
    }

    fun getUserPhone():String?{
        return get<String>(USER_PHONE)
    }

    fun setUserPhone(phone:String){
        put(phone, USER_PHONE)
    }

    fun getUserPhoto(): String?{
        return get<String>(USER_PHOTO)
    }

    fun setUserPhoto(uri: String){
        put(uri, USER_PHOTO)
    }

    fun getUserBairro(): String?{
        return get<String>(USER_BAIRRO)
    }

    fun setUserBairro(bairro:String){
        put(bairro, USER_BAIRRO)
    }

    fun getUserRua(): String?{
        return get<String>(USER_RUA)
    }

    fun setUserRua(rua:String){
        put(rua, USER_RUA)
    }

    fun getUserNum(): String?{
        return get<String>(USER_NUM)
    }

    fun setUserNum(num:String){
        put(num, USER_NUM)
    }

    fun getUserComp(): String?{
        return get<String>(USER_COMP)
    }

    fun setUserComp(comp:String){
        put(comp, USER_COMP)
    }
}