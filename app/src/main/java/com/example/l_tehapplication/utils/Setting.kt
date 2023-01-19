package com.example.l_tehapplication.utils

import android.content.Context
import android.content.SharedPreferences
import kotlin.reflect.KProperty

class Setting(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(this.javaClass.simpleName, Context.MODE_PRIVATE)

    var phone: String by SharedPrefStringProperty("")
    var password: String by SharedPrefStringProperty("")



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
}