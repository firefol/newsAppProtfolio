package com.example.l_tehapplication.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.l_tehapplication.NewsApplication
import com.example.l_tehapplication.model.News
import com.example.l_tehapplication.retrofit.RetroServiceInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    var liveDataList: MutableLiveData<List<News>?> = MutableLiveData()

    fun getLiveDataObserver(): MutableLiveData<List<News>?> {
        return liveDataList
    }

    fun getPosts() {
        val service = NewsApplication.retrofit.create(RetroServiceInterface::class.java)
        viewModelScope.launch {
            val response = service.getNewsList()
            withContext(Dispatchers.IO) {
                if (response.isSuccessful && response.code() == 200) {
                    liveDataList.postValue(response.body())
                }
            }
        }
    }
}