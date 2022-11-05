package com.compultra.silent.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.compultra.silent.MYTAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(private val database: MessagesDatabase, private val phonebookHelper: PhonebookHelper) {

    //TODO: handle encryption here
    //TODO: handle message sorting, database insertion here
    //TODO: handle get livedata calls

    private val _inactiveContacts = MutableLiveData<List<Contact>>()
    val inactiveContacts: LiveData<List<Contact>> = _inactiveContacts

//    val activeContacts: Set<String> = setOf()
    suspend fun refreshInactiveContacts(){
        withContext(Dispatchers.IO) {
            val activeContacts = database.messagesDao.getAllActiveContacts().toSet()
            val allContacts = phonebookHelper.getPhoneBook()
            val inactiveContacts = allContacts.filter { contact -> (contact.address !in activeContacts) }
            Log.d(MYTAG, "Inactive contacts: " + inactiveContacts.toString())
            _inactiveContacts.postValue(inactiveContacts)
        }
    }

    companion object {
        private lateinit var INSTANCE: Repository
        fun getInstance(context: Context) = synchronized(Repository::class.java) {
            if(!::INSTANCE.isInitialized) {
                INSTANCE = Repository(MessagesDatabase.getInstance(context), PhonebookHelper.getInstance(context))
            }
            INSTANCE
        }
    }

}