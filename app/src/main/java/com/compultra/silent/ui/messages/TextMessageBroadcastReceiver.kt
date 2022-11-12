package com.compultra.silent.ui.messages

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.provider.Telephony
import android.util.Log
import com.compultra.silent.MYTAG
import com.compultra.silent.data.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object TextMessageBroadcastReceiver : BroadcastReceiver() {
        val intentFilter = IntentFilter().apply {
            priority = 2147483647
            addAction(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)
        }
     private var address = ""

    fun setAddress(a: String){
        address = a
    }

    override fun onReceive(context: Context, intent: Intent?) {
        val repository = Repository.getInstance(context)
        Log.d(MYTAG, "TextMessageBroadcastReceiver.onReceive called")
        if(address.isNotEmpty()){
            GlobalScope.launch(Dispatchers.IO) {
                repository.refreshMessageForAddress(address)
            }
        }
    }


}