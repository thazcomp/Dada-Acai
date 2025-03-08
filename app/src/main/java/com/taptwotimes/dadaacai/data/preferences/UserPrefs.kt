package com.taptwotimes.dadaacai.data.preferences

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
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

    fun setUserPhoto(uri:String){
        put(uri, USER_PHOTO)
    }
}