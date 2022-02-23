package com.crypto.tracker.utils

import android.content.Context
import android.content.SharedPreferences
import com.crypto.tracker.appContext

class PreferenceHelper {

    val context = appContext

    private val sharedPref: SharedPreferences = context!!.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private fun clearUserData() {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.clear()
        editor.apply()
    }


    fun isIntroPassed(): Boolean {
        return sharedPref.getBoolean(KEY_INTRO, false)
    }


    fun setIntroPassed() {
        val editor: SharedPreferences.Editor? = sharedPref.edit()
        editor?.putBoolean(KEY_INTRO, true)
        editor?.apply()
    }
}