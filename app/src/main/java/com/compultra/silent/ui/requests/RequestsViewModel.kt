package com.compultra.silent.ui.requests

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compultra.silent.data.Repository
import kotlinx.coroutines.launch

class RequestsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repository.getInstance(application.applicationContext)

    val incoming = repository.incomingRequestsViewData
    val outgoing = repository.outgoingRequestsViewData
    fun refreshMessages() {
        viewModelScope.launch {
            repository.refreshMessages()
        }
    }

    fun sendConnection(address: String) {
        viewModelScope.launch {
            repository.sendConnection(address)
        }
    }
}