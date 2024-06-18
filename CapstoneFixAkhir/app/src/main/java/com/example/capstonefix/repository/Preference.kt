package com.example.capstonefix.repository

import android.content.Context
import android.content.SharedPreferences

object Preference {
    private const val PREF_NAME = "onSignIn"
    private const val PREF_INFO = "info"

    private const val TOKEN_KEY = "token"

    fun initPref(context: Context, name: String): SharedPreferences {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE)
    }

    private fun editorPreference(context: Context, name: String): SharedPreferences.Editor {
        val sharedPref = context.getSharedPreferences(name, Context.MODE_PRIVATE)
        return sharedPref.edit()
    }

    fun saveToken(token: String, context: Context) {
        val editor = editorPreference(context, "onSignIn")
        editor.putString("token", token)
        editor.apply()
    }
    fun saveInfo(username: String, email: String,point : Int, context: Context) {
        val sharedPref = context.getSharedPreferences(PREF_INFO, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("username", username)
        editor.putString("email", email)
        editor.putInt("point", point)
        editor.apply()
    }
    fun getToken(context: Context): String? {
        val sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPref.getString(TOKEN_KEY, null)
    }
    fun getInfo(context: Context): Triple<String?, String?, Int> {
        val sharedPref = context.getSharedPreferences(PREF_INFO, Context.MODE_PRIVATE)
        val username = sharedPref.getString("username", null)
        val email = sharedPref.getString("email", null)
        val point = sharedPref.getInt("point", 0)

        return Triple(username, email, point)
    }


    fun logOut(context: Context) {
        val editor = editorPreference(context, "onSignIn")
        editor.remove("token")
        editor.remove("status")
        editor.apply()
    }
}