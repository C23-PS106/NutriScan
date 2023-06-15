package com.c23ps160.nutriscan.Login

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("AppKey", 0)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun setLogin(login: Boolean) {
        editor.putBoolean("KEY_LOGIN", login)
        editor.apply()
    }

    fun getLogin(): Boolean {
        return sharedPreferences.getBoolean("KEY_LOGIN", false)
    }

    fun setName(name: String?) {
        editor.putString("NAME", name)
        editor.apply()
    }

    fun getName(): String? {
        return sharedPreferences.getString("NAME", "")
    }

    fun setEmail(email: String?) {
        editor.putString("EMAIL", email)
        editor.apply()
    }

    fun getEmail(): String? {
        return sharedPreferences.getString("EMAIL", "")
    }
}
