package com.example.l_tehapplication

import android.app.Application
import com.example.l_tehapplication.model.News
import com.example.l_tehapplication.utils.Setting
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ltehApplication:Application() {
    companion object {
        lateinit var NewsDetail:News
        lateinit var retrofit: Retrofit
        private const val BASE_URL = "http://dev-exam.l-tech.ru"
        lateinit var settings: Setting
    }

    override fun onCreate() {
        super.onCreate()
        settings = Setting(this)
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}