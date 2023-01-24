package com.example.l_tehapplication.ui.authorization

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.l_tehapplication.NewsApplication
import com.example.l_tehapplication.retrofit.RetroServiceInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
        val service = NewsApplication.retrofit.create(RetroServiceInterface::class.java)
        viewModelScope.launch {
            val response = service.getPhoneMask()

            withContext(Dispatchers.IO) {
                liveDataPhoneMask.postValue(response.phoneMask.split(" ")[0])
            }
        }
    }

    fun Login(params: HashMap<String?, String?>) {
        val service = NewsApplication.retrofit.create(RetroServiceInterface::class.java)
        viewModelScope.launch {
            val response = service.authorizathion(params)

            withContext(Dispatchers.IO) {
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