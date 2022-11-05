package com.compultra.silent.ui.add

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compultra.silent.data.Repository
import kotlinx.coroutines.launch

class AddViewModel(application: Application) : AndroidViewModel(application) {
    // TODO: Implement the ViewModel
    private val repository = Repository.getInstance(application.applicationContext)

    val contacts = repository.inactiveContacts

    init {
        viewModelScope.launch {
            repository.refreshInactiveContacts()
        }
    }

}