package com.compultra.silent.ui.chats

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compultra.silent.data.Repository
import kotlinx.coroutines.launch

class ChatsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repository.getInstance(application.applicationContext)

    val latest = repository.latestMessages

    fun refreshMessages(){
        viewModelScope.launch {
            repository.refreshMessages()
        }
    }
}