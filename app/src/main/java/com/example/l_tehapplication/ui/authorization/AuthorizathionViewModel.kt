package com.example.l_tehapplication.ui.authorization

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.l_tehapplication.R
import com.example.l_tehapplication.ltehApplication
import com.example.l_tehapplication.model.News
import com.example.l_tehapplication.retrofit.RetroServiceInterface
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthorizathionViewModel : ViewModel() {


    var liveDataPhoneMask: MutableLiveData<String?> = MutableLiveData()
    var liveDataLogin: MutableLiveData<Boolean> = MutableLiveData()

    fun getLiveDataObserver(): MutableLiveData<String?> {
        return liveDataPhoneMask
    }

    fun getLiveDataObserverLogin(): MutableLiveData<Boolean> {
        return liveDataLogin
    }

    fun getPhoneMask() {
        val service = ltehApplication.retrofit.create(RetroServiceInterface::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getPhoneMask()

            withContext(Dispatchers.Main) {
                liveDataPhoneMask.postValue(response.phoneMask.split(" ")[0])
            }
        }
    }

    fun Login(params: HashMap<String?, String?>) {
        val service = ltehApplication.retrofit.create(RetroServiceInterface::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.authorizathion(params)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful && response.code() == 200) {
                    liveDataLogin.postValue(response.body()?.success)
                } else {
                    if (response.code() == 403)
                        liveDataLogin.postValue(response.body()?.success)
                }
            }
        }
    }

}