package com.compultra.silent.ui.messages

import android.app.Application
import androidx.lifecycle.*
import com.compultra.silent.data.Repository
import com.compultra.silent.data.SilentMessage
import com.compultra.silent.getInitialsForName
import kotlinx.coroutines.launch

class MessagesViewModel(application: Application, savedStateHandle: SavedStateHandle) :
    AndroidViewModel(application) {
    var initials = ""
        private set
    var address = ""
        private set
    var name: String
        private set
    private val repository = Repository.getInstance(application.applicationContext)

    val messages: LiveData<List<SilentMessage.Text>>

    init {
        savedStateHandle.get<String>(MessagesFragment.ARG_ADDRESS)?.let { address = it }
        name = repository.getDisplayNameForAddress(address)
        messages = repository.getTextMessagesForAddress(address)
        initials = getInitialsForName(name)
    }

    private var message = ""
    private var _sendEnabled = MutableLiveData(false)
    val sendEnabled: LiveData<Boolean> = _sendEnabled
    fun setMessage(m: String){
        message = m
        _sendEnabled.value = message.isNotEmpty()
    }

    fun send() {
        if (message.isNotEmpty()){
            viewModelScope.launch {
                repository.sendText(address, message)
            }
        }
        message = ""
    }

}