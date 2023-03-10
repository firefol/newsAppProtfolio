package com.example.l_tehapplication.ui.authorization

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.l_tehapplication.repository.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AuthorizathionViewModel @Inject constructor (private val networkRepository: NetworkRepository) :
    ViewModel() {


    var liveDataPhoneMask: MutableLiveData<String?> = MutableLiveData()
    var liveDataLogin: MutableLiveData<Boolean> = MutableLiveData()

    fun getLiveDataObserver(): MutableLiveData<String?> {
        return liveDataPhoneMask
    }

    fun getLiveDataObserverLogin(): MutableLiveData<Boolean> {
        return liveDataLogin
    }

    fun getPhoneMask() {
        viewModelScope.launch {
            val response = networkRepository.getPhoneMask()
            withContext(Dispatchers.IO) {
                if (response.isSuccessful)
                liveDataPhoneMask.postValue(response.body()?.phoneMask?.split(" ")?.get(0))
                else {
                    liveDataPhoneMask.postValue("+7")
                }
            }
        }
    }

    fun Login(params: HashMap<String?, String?>) {
        viewModelScope.launch {
            val response = networkRepository.authorizathion(params)

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