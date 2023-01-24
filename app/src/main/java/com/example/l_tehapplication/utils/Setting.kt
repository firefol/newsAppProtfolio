package com.example.l_tehapplication.utils

import android.content.Context
import android.content.SharedPreferences
import kotlin.reflect.KProperty

class Setting(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(this.javaClass.simpleName, Context.MODE_PRIVATE)

    var phone: String by SharedPrefStringProperty("")
    var password: String by SharedPrefStringProperty("")
    var checkPhoneMask: Boolean by SharePrefBooleanProperty(false)



    private class SharedPrefStringProperty(private val def: String) {
        operator fun getValue(sharedPrefHandler: Setting, property: KProperty<*>): String {
            return if (sharedPrefHandler.prefs.contains(property.name))
                sharedPrefHandler.prefs.getString(property.name, def) ?: def
            else
                def
        }

        operator fun setValue(sharedPrefHandler: Setting, property: KProperty<*>, s: String) {
            sharedPrefHandler.prefs.edit().putString(property.name, s).apply()
        }
    }

    private class SharePrefBooleanProperty(private val def:Boolean) {
        operator fun getValue(sharedPrefHandler: Setting, property: KProperty<*>): Boolean {
            return if (sharedPrefHandler.prefs.contains(property.name))
                sharedPrefHandler.prefs.getBoolean(property.name, def)
            else
                def
        }

        operator fun setValue(sharedPrefHandler: Setting, property: KProperty<*>, b: Boolean) {
            sharedPrefHandler.prefs.edit().putBoolean(property.name, b).apply()
        }

    }
}