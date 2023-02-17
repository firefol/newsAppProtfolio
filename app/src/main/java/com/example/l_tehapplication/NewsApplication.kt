package com.example.l_tehapplication

import android.app.Application
import com.example.l_tehapplication.model.News
import com.example.l_tehapplication.utils.Setting
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsApplication:Application() {
    companion object {
        lateinit var NewsDetail:News
        lateinit var settings: Setting
    }

    override fun onCreate() {
        super.onCreate()
        settings = Setting(this)
    }
}