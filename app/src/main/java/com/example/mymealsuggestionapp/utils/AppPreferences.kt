package com.example.mymealsuggestionapp.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE

class AppPreferences(context: Context) {

    companion object {
        private const val PREFS_NAME = "app_prefs"
        private const val INFLUENCE = "influence"
        private const val ITEM_POSITION = "item_position"
    }

    private val prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)

    fun saveNationalityOfInfluence(value: String) {
        prefs.edit().putString(INFLUENCE, value).apply()
    }

    fun getNationalityOfInfluence(): String? {
        return prefs.getString(INFLUENCE, null)
    }

    fun saveNationalityOfInfluenceItemPosition(position: Int) {
        prefs.edit().putInt(ITEM_POSITION, position).apply()
    }

    fun getNationalityOfInfluenceItemPosition(): Int {
        return prefs.getInt(ITEM_POSITION, 0)
    }
}