package com.example.habitualize.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.habitualize.MyApplication

class SharePrefHelper {
    companion object{
        private const val MODE = Context.MODE_PRIVATE
        private var preferences: SharedPreferences? = null

        private fun getPreferences(): SharedPreferences {
            if (preferences == null) {
                preferences = MyApplication.mInstance?.getSharedPreferences("private-prefs", MODE)
            }
            return preferences!!
        }

        private fun getEditor(): SharedPreferences.Editor {
            return getPreferences().edit()
        }

        fun writeInteger(key: String?, value: Int) {
            getEditor().putInt(key, value).apply()
        }

        fun readInteger(key: String?): Int {
            return getPreferences().getInt(key, 0)
        }

        fun writeString(key: String?, value: String?) {
            getEditor().putString(key, value).apply()
        }

        fun readString(key: String?): String? {
            return getPreferences().getString(key, "")
        }

        fun writeBoolean(key: String?, value: Boolean) {
            getPreferences().edit().putBoolean(key, value).apply()
        }

        fun readBoolean(key: String?): Boolean {
            return getPreferences().getBoolean(key, false)
        }

        fun clearAll(selectedTheme: Int, SelectedLanguage: String) {
            getEditor().clear().commit()
            writeInteger(selectedColorIndex, selectedTheme)
            writeString(languageCode, SelectedLanguage)
            writeBoolean(isLanguageScreenShow, false)
        }
    }
}