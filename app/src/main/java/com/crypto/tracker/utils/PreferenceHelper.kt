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

    fun isServiceRunnable(): Boolean {
        return sharedPref.getBoolean(KEY_SERVICE, false)
    }

    fun setServiceRunnable(isOn: Boolean) {
        val editor: SharedPreferences.Editor? = sharedPref.edit()
        editor?.putBoolean(KEY_SERVICE, isOn)
        editor?.apply()
    }
}