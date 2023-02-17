package com.example.l_tehapplication.repository

import com.example.l_tehapplication.retrofit.RetroServiceInterface
import retrofit2.http.FieldMap


class NetworkRepository(private val retrofitService: RetroServiceInterface) {

    suspend fun getNewsList() = retrofitService.getNewsList()

    suspend fun getPhoneMask() = retrofitService.getPhoneMask()

    suspend fun authorizathion(@FieldMap params: HashMap<String?, String?>) =
        retrofitService.authorizathion(params)
}