package com.example.l_tehapplication.retrofit

import com.example.l_tehapplication.model.News
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface RetroServiceInterface {

    @GET("/api/v1/posts")
    suspend fun getNewsList(): Response<List<News>>

    @GET("/api/v1/phone_masks")
    suspend fun getPhoneMask(): Response<PhoneMaskResponse>

    @FormUrlEncoded
    @POST("/api/v1/auth")
    suspend fun authorizathion(@FieldMap params: HashMap<String?, String?>): Response<LoginResponse>

    companion object {
        var retrofitService: RetroServiceInterface? = null
        fun getInstance() : RetroServiceInterface {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("http://dev-exam.l-tech.ru")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetroServiceInterface::class.java)
            }
            return retrofitService!!
        }

    }
}