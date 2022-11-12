package com.compultra.silent.data

import android.content.Context
import android.text.format.DateFormat
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.compultra.silent.MYTAG
import com.compultra.silent.getRelativeTimestamp
import com.compultra.silent.security.EncryptionManager
import com.compultra.silent.ui.requests.RequestIncomingViewData
import com.compultra.silent.ui.requests.RequestOutgoingViewData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.max

class Repository(
    private val database: MessagesDatabase,
    private val phonebookHelper: PhonebookHelper
) {

    //handle encryption here
    //handles message sorting, database insertion here
    // handles get livedata calls

    private val _inactiveContacts = MutableLiveData<List<Contact>>()
    val inactiveContacts: LiveData<List<Contact>> = _inactiveContacts
//    val incomingRequests =
//    val outgoingRequests =

    init {
        EncryptionManager.initInternalPrivateKey()
    }

    fun getDisplayNameForAddress(address: String) =
        phonebookHelper.getDisplayNameForAddress(address)


    private fun insertLatestMessage(address: String, timestamp: Long = 0, message: String = "") {
        database.messagesDao.insertLatestMessage(LatestTextMessage(address, timestamp, message))
    }

    val incomingRequestsViewData =
        Transformations.map(database.messagesDao.getIncomingRequests()) { silentMessages ->
            silentMessages.map {
                val name = phonebookHelper.getDisplayNameForAddress(it.address)
                RequestIncomingViewData(name, it.address, getRelativeTimestamp(it.timestamp))
            }
        }
    val outgoingRequestsViewData =
        Transformations.map(database.messagesDao.getOutgoingRequests()) { silentMessages ->
            silentMessages.map {
                val name = phonebookHelper.getDisplayNameForAddress(it.address)
                RequestOutgoingViewData(name, it.address, getRelativeTimestamp(it.timestamp))
            }
        }
    val latestMessages = Transformations.map(database.messagesDao.getLatestMessages()) { messages ->
        messages.forEach {
            it.name = phonebookHelper.getDisplayNameForAddress(it.address)
        }
        messages
    }

    //    val activeContacts: Set<String> = setOf()
    suspend fun refreshInactiveContacts() {
        withContext(Dispatchers.IO) {
            val activeContacts = database.messagesDao.getAllActiveContacts().toSet()
            Log.d(MYTAG, "Active contacts: $activeContacts")
            val allContacts = phonebookHelper.getPhoneBook()
            val inactiveContacts =
                allContacts.filter { contact -> (contact.address !in activeContacts) }
            Log.d(MYTAG, "Inactive contacts: $inactiveContacts")
            _inactiveContacts.postValue(inactiveContacts)
        }
    }

    fun getTextMessagesForAddress(address: String) =
        database.messagesDao.getTextMessagesForAddress(address)

    suspend fun refreshMessageForAddress(address: String) {
        withContext(Dispatchers.IO) {

            val timestamp = database.messagesDao.getLatestProcessedTimestamp()?.toLong()
            val messages =
                phonebookHelper.getPlainMessages(queryTimestamp = timestamp, queryNumber = address)
                    .map { it.resolveType() }
            messages.forEach { message ->
                if (message is SilentMessage.Text) {
                    //TODO: handle decryption with key from database
                    // if key not found, or decryption fails: add failed decryption request again message to database
                    //TODO: handle database encryption
                    val key = database.messagesDao.getKey(message.address)
                    if (key == null) {
                        Log.d(
                            MYTAG,
                            "Key not found for text message from contact(${message.address})"
                        )
                    } else {
                        if (message.type != MessageType.SENT && message.type != MessageType.OUTBOX) { //handled when sending
                            val decrypted = EncryptionManager.decryptStringSelf(message.message)
                            database.messagesDao.insertTextMessage(message.copy(message = decrypted))
                            insertLatestMessage(message.address, message.timestamp, decrypted)
                        }
                    }
                }
            }
        }

    }


    suspend fun refreshMessages() {
        withContext(Dispatchers.IO) {

            val timestamp = database.messagesDao.getLatestProcessedTimestamp()?.toLong()
            val messages = phonebookHelper.getPlainMessages(queryTimestamp = timestamp)
                .map { it.resolveType() }
            var newTimestamp = timestamp ?: 0
            messages.forEach { message ->
                newTimestamp = max(message.timestamp, newTimestamp)
                when (message) {
                    is SilentMessage.Request.Incoming -> {
                        database.messagesDao.insertIncomingRequest(message)
                        database.messagesDao.insertKey(AcceptedKey(message.address, message.content))
                    }
                    is SilentMessage.Request.Outgoing -> {
                        database.messagesDao.insertOutgoingRequest(message)
                    }
                    is SilentMessage.Connection.Incoming -> {
                        //process: check if outgoing message exists: if so add incoming key to database, delete outgoing request
                        if (database.messagesDao.existsOutgoingRequest(message.address) > 0) {
                            //TODO: encrypt this key
                            database.messagesDao.insertKey(
                                AcceptedKey(
                                    message.address,
                                    message.content
                                )
                            )
                            insertLatestMessage(message.address, message.timestamp)
                            database.messagesDao.deleteOutgoingRequest(message.address)
                        }
                    }
                    is SilentMessage.Connection.Outgoing -> {
                        //process: remove incoming request from database
                        insertLatestMessage(message.address, message.timestamp)
                        database.messagesDao.deleteIncomingRequest(message.address)
                    }
                    is SilentMessage.Text -> {
                        //TODO: handle decryption with key from database
                        // if key not found, or decryption fails: add failed decryption request again message to database
                        //TODO: handle database encryption
                        val key = database.messagesDao.getKey(message.address)
                        if (key == null) {
                            Log.d(
                                MYTAG,
                                "Key not found for text message from contact(${message.address})"
                            )
                        } else {
                            if (message.type != MessageType.SENT && message.type != MessageType.OUTBOX) { //handled when sending
                                val decrypted = EncryptionManager.decryptStringSelf(message.message)
                                database.messagesDao.insertTextMessage(message.copy(message = decrypted))
                                insertLatestMessage(message.address, message.timestamp, decrypted)
                            }
                        }
                    }

                    else -> {
                        Log.d(MYTAG, "Don't know how to process this message $message")
                    }
                }

            }
            database.messagesDao.setLatestProcessedTimestamp(newTimestamp.toString())
        }
    }

    //perform request, connection, text messages (store in database at the same time)
//to handle timestamp discrepancy dont add sent messages in sorting
    suspend fun sendRequest(address: String) {
        withContext(Dispatchers.IO) {
            val key = EncryptionManager.getPublicKeyEncoded() //get own public key
            val message = REQUEST_PREFIX + key
            phonebookHelper.sendSmsMessageLong(address, message)
            Log.d(MYTAG, "Sending Message: $message")
        }
    }

    suspend fun sendConnection(address: String) {
        withContext(Dispatchers.IO) {
            val key = EncryptionManager.getPublicKeyEncoded() //get own public key
            val message = CONNECTION_PREFIX + key
            phonebookHelper.sendSmsMessageLong(address, message)
            Log.d(MYTAG, "Sending Message: $message")
        }
    }

    suspend fun sendText(address: String, message: String) {
        withContext(Dispatchers.IO) {
            //get receiver public key and encrypt
            val key = database.messagesDao.getKey(address)
            if (key == null) {
                Log.d(MYTAG, "no key for contact($address)")
                return@withContext
            }
            EncryptionManager.initExternalPublicKey(key)
            val encryptedMessage = EncryptionManager.encryptStringExternal(message)
            //TODO: verify if time is milliseconds time
            val encryptedPrefixedMessage = MESSAGE_PREFIX + encryptedMessage
            phonebookHelper.sendSmsMessageLong(address, encryptedPrefixedMessage)
            Log.d(MYTAG, "Sending Message: $encryptedPrefixedMessage")
            //TODO: differentiate SENT and OUTBOX
            val timestamp = System.currentTimeMillis()
            database.messagesDao.insertTextMessage(
                SilentMessage.Text(
                    address,
                    timestamp,
                    message,
                    type = MessageType.SENT
                )
            )
            insertLatestMessage(address, timestamp, message)
        }

    }

//    fun test() {
//        val em = EncryptionManager
//        val publicKey = em.getPublicKeyEncoded()
//        Log.d(MYTAG, "publicKey = $publicKey")
//        val message = "hello form the other siiiide hello form the other siiiidehello form the other siiiidehello form the other siiiide hello form the other siiiidehello form the other siiiide hello form the other siiiide"
//        Log.d(MYTAG, "message = $message")
//        em.initExternalPublicKey(publicKey)
//        val encrypted = em.encryptStringExternal(message)
//        Log.d(MYTAG, "encrypted = $encrypted")
//        em.initInternalPrivateKey()
//        val decrypted = em.decryptStringSelf(encrypted)
//        Log.d(MYTAG, "decrypted = $decrypted")
//    }

    companion object {
        private lateinit var INSTANCE: Repository
        fun getInstance(context: Context) = synchronized(Repository::class.java) {
            if (!::INSTANCE.isInitialized) {
                INSTANCE = Repository(
                    MessagesDatabase.getInstance(context),
                    PhonebookHelper.getInstance(context)
                )
            }
            INSTANCE
        }
    }

}