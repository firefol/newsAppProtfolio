package com.example.l_tehapplication.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.l_tehapplication.model.News

class DetailsViewModel() : ViewModel() {

    val newsLiveData: MutableLiveData<News> by lazy {
             MutableLiveData<News>()
    }
}