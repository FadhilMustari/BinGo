package com.example.capstonefix.repository

import android.content.Context
import android.content.SharedPreferences

object Preference {
    private const val PREF_NAME = "onSignIn"
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
    fun getToken(context: Context): String? {
        val sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPref.getString(TOKEN_KEY, null)
    }

    fun logOut(context: Context) {
        val editor = editorPreference(context, "onSignIn")
        editor.remove("token")
        editor.remove("status")
        editor.apply()
    }
}