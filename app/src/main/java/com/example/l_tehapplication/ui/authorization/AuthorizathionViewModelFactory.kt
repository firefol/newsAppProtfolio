package com.example.l_tehapplication.ui.authorization

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.l_tehapplication.repository.NetworkRepository

class AuthorizathionViewModelFactory constructor(private val repository: NetworkRepository):
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AuthorizathionViewModel::class.java)) {
            AuthorizathionViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}