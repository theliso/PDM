package com.g02.yama.repository

import android.content.Context
import android.content.SharedPreferences
import com.g02.yama.repository.dataAccess.dtos.UserLoginDto

class MySharedPreferences(private val ctx: Context) {

    val SHARED_FILE_NAME : String = MySharedPreferences::class.java.`package`.name + "MY_APP"

    fun save(userLogin: UserLoginDto) {
        val sharedPreferences : SharedPreferences = ctx.getSharedPreferences(
                SHARED_FILE_NAME,
                Context.MODE_PRIVATE
        )
        sharedPreferences.edit().putString("user", userLogin.user).apply()
        sharedPreferences.edit().putString("organization", userLogin.organization).apply()
        sharedPreferences.edit().putString("token", userLogin.userToken).apply()
    }

    fun getToken(): String {
        val shared = ctx.getSharedPreferences(SHARED_FILE_NAME, Context.MODE_PRIVATE)
        return shared.getString("token", "not available")!!
    }

    fun getOrganization(): String {
        val shared = ctx.getSharedPreferences(SHARED_FILE_NAME, Context.MODE_PRIVATE)
        return shared.getString("organization", "not available")!!
    }

    fun getLogin() : String {
        val shared = ctx.getSharedPreferences(SHARED_FILE_NAME, Context.MODE_PRIVATE)
        return shared.getString("user", "not available")!!
    }

    fun erase() {
        val shared = ctx.getSharedPreferences(SHARED_FILE_NAME, Context.MODE_PRIVATE)
        shared.edit().clear().apply()
    }

}