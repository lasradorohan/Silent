package com.compultra.silent.data

import android.app.PendingIntent
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import android.provider.Telephony
import android.telephony.SmsManager
import android.util.Log
import com.compultra.silent.MYTAG

class PhonebookHelper private constructor(context: Context) {

    val phoneRegex = Regex("[ ()-]")
    fun String.normalizeAddress(): String {
        val normalized = phoneRegex.replace(this, "")
        Log.d(MYTAG, "Replacing $this by $normalized")
        return normalized
    }

//    private val context: Context = context.applicationContext
    private val contentResolver = context.contentResolver
    private val smsManager: SmsManager = context.getSystemService(SmsManager::class.java)

    suspend fun getPlainMessages(
        queryNumber: String? = null,
        queryTimestamp: Long? = null
    ): List<SilentMessage.Unresolved> {
        var selectionQuery: String? = null
        if (queryNumber != null) {
            selectionQuery = "${Telephony.Sms.ADDRESS}=\"$queryNumber\""
        }
        if (queryTimestamp != null) {
            if (selectionQuery != null) selectionQuery += " AND "
            else selectionQuery = ""
            selectionQuery += "${Telephony.Sms.DATE}>$queryTimestamp"
        }
        val c = contentResolver.query(
            Telephony.Sms.CONTENT_URI,
            null,
            selectionQuery,
            null,
            null
        )
        val result = mutableListOf<SilentMessage.Unresolved>()
        c?.use {
            if (c.moveToFirst()) {
                while (!c.isAfterLast) {
                    val smsDate: String = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.DATE))
                    val address: String =
                        c.getString(c.getColumnIndexOrThrow(Telephony.Sms.ADDRESS)).normalizeAddress()
//                    val displayName = getContactNameForAddress(context, address)
                    val message: String = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.BODY))
                    val timestamp = smsDate.toLong()
                    val type =
                        when (c.getString(c.getColumnIndexOrThrow(Telephony.Sms.TYPE)).toInt()) {
                            Telephony.Sms.MESSAGE_TYPE_INBOX -> MessageType.INBOX
                            Telephony.Sms.MESSAGE_TYPE_SENT -> MessageType.SENT
                            Telephony.Sms.MESSAGE_TYPE_OUTBOX -> MessageType.OUTBOX
                            else -> MessageType.UNKNOWN
                        }
                    result.add(SilentMessage.Unresolved(address, timestamp, message, type))
                    c.moveToNext()
                }
            }
        }
        return result
    }


    @Deprecated("no use")
    suspend fun getContactsHavingMessages(): List<Contact> {
        val c: Cursor? = contentResolver.query(
            Telephony.Sms.CONTENT_URI,
            arrayOf("DISTINCT address", "body"),
            "address IS NOT NULL) GROUP BY (address",
            null,
            null
        )
        val result = mutableListOf<Contact>()

        c?.use {
            if (c.moveToFirst()) {
                while (!c.isAfterLast) {
                    val address: String =
                        c.getString(c.getColumnIndexOrThrow(Telephony.Sms.ADDRESS)).normalizeAddress()
                    val displayName = getDisplayNameForAddress(address)
                    result.add(Contact(address, displayName))
                    c.moveToNext()
                }
            }
        }
        return result
    }

    fun getDisplayNameForAddress(address: String): String {
        var contact: String = address
        val uri: Uri = Uri.withAppendedPath(
            ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
            Uri.encode(address)
        )
        val cursor: Cursor? = contentResolver.query(
            uri,
            arrayOf(ContactsContract.PhoneLookup.DISPLAY_NAME),
            ContactsContract.PhoneLookup.NUMBER + "='" + address + "'",
            null,
            null
        )
        cursor?.use {
            if (it.count > 0) {
                it.moveToFirst()
                contact =
                    it.getString(it.getColumnIndexOrThrow(ContactsContract.PhoneLookup.DISPLAY_NAME))
                it.moveToNext()
            }
        }
        return contact
    }

    suspend fun sendSmsMessage(
        destinationAddress: String,
        message: String
    ) {

        val sourceAddress: String? = null
        val sentIntent: PendingIntent? = null
        val deliveryIntent: PendingIntent? = null
        smsManager.sendTextMessage(
            destinationAddress,
            sourceAddress,
            message,
            sentIntent,
            deliveryIntent
        )
    }

    suspend fun sendSmsMessageLong(
        destinationAddress: String,
        message: String
    ) {
        Log.d(MYTAG, "Sending message: $destinationAddress -> $message")
        val parts = smsManager.divideMessage(message)
        val sourceAddress: String? = null
        val sentIntent: ArrayList<PendingIntent>? = null
        val deliveryIntent: ArrayList<PendingIntent>? = null
        smsManager.sendMultipartTextMessage(
            destinationAddress,
            sourceAddress,
            parts,
            sentIntent,
            deliveryIntent
        )
    }


    suspend fun getPhoneBook(): List<Contact> {
        val contactsColumns = arrayOf(
            ContactsContract.Contacts._ID, //0
            ContactsContract.Contacts.DISPLAY_NAME, //1
            ContactsContract.Contacts.HAS_PHONE_NUMBER
        )//2
        val phonesColumns = arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER)

        val cursor = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            contactsColumns,
            null,
            null,
            "display_name"
        )
        val result = mutableListOf<Contact>()
        cursor?.use { c ->
            while (c.moveToNext()) {
                val id = c.getString(0)
                val name = c.getString(1)
//                c.getString(c.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
//            val phoneNumber =
//                c.getString(c.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER))

                val hasPhone = c.getString(2).equals("1")
                if (hasPhone) {
                    val phones = contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        phonesColumns,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                        null,
                        null
                    )
                    phones?.use {
                        while (phones.moveToNext()) {
                            val phoneNumber = phones.getString(0).normalizeAddress()
                            result.add(Contact(phoneNumber, name))
                        }
                    }
                }

            }
        }
        return result
    }

    companion object {
        private lateinit var INSTANCE: PhonebookHelper
        public fun getInstance(context: Context) = synchronized(PhonebookHelper::class.java) {
            if(!::INSTANCE.isInitialized) {
                INSTANCE = PhonebookHelper(context.applicationContext)
            }
            INSTANCE
        }
    }
}