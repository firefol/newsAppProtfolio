package com.example.l_tehapplication.retrofit

import com.example.l_tehapplication.model.News
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface RetroServiceInterface {

    @GET("/api/v1/posts")
    fun getNewsList():Call<List<News>>

    @GET("/api/v1/phone_masks")
    suspend fun getPhoneMask(): PhoneMaskResponse

    @FormUrlEncoded
    @POST("/api/v1/auth")
    suspend fun authorizathion(@FieldMap params: HashMap<String?, String?>): Response<LoginResponse>
}