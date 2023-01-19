package com.example.l_tehapplication.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.l_tehapplication.ltehApplication
import com.example.l_tehapplication.model.News
import com.example.l_tehapplication.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    var liveDataList: MutableLiveData<List<News>?> = MutableLiveData()

    fun getLiveDataObserver(): MutableLiveData<List<News>?> {
        return liveDataList
    }

    fun makeAPICall() {
        val retroinstance = ltehApplication.retrofit
        val retroService = retroinstance.create(RetroServiceInterface::class.java)
        val call = retroService.getNewsList()
        call.enqueue(object : Callback<List<News>>{
            override fun onResponse(call: Call<List<News>>, response: Response<List<News>>) {
                liveDataList.postValue(response.body())
            }

            override fun onFailure(call: Call<List<News>>, t: Throwable) {
                liveDataList.postValue(null)
            }

        })
    }


}