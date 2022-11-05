package com.compultra.silent.ui.messages

import android.text.format.DateUtils
import com.compultra.silent.data.TextMessage
import java.util.*

class MessagesDataSource {
    fun load() = listOf(
        TextMessage("+919876543210", System.currentTimeMillis() - 6*DateUtils.DAY_IN_MILLIS, "Hey there i'm not creepy", TextMessage.Type.INBOX),
        TextMessage("+919876543210", System.currentTimeMillis()- 6*DateUtils.DAY_IN_MILLIS, "I'm not sure i should be saying lorem ipsum dolor silit", TextMessage.Type.SENT),
        TextMessage("+919876543210", System.currentTimeMillis()-5*DateUtils.DAY_IN_MILLIS, "i should be saying lorem ipsum dolor silit", TextMessage.Type.INBOX),
        TextMessage("+919876543210", System.currentTimeMillis() - 5*DateUtils.DAY_IN_MILLIS, "Hey there i'm not creepy", TextMessage.Type.SENT),
        TextMessage("+919876543210", System.currentTimeMillis() - 4*DateUtils.DAY_IN_MILLIS, "I'm not sure i should be saying lorem ipsum dolor silit", TextMessage.Type.INBOX),
        TextMessage("+919876543210", System.currentTimeMillis()-4*DateUtils.DAY_IN_MILLIS, "i should be saying lorem ipsum dolor silit", TextMessage.Type.SENT),
        TextMessage("+919876543210", System.currentTimeMillis() - 3*DateUtils.DAY_IN_MILLIS, "Hey there i'm not creepy", TextMessage.Type.INBOX),
        TextMessage("+919876543210", System.currentTimeMillis()-3*DateUtils.DAY_IN_MILLIS, "I'm not sure i should be saying lorem ipsum dolor silit", TextMessage.Type.SENT),
        TextMessage("+919876543210", System.currentTimeMillis()-2*DateUtils.DAY_IN_MILLIS, "i should be saying lorem ipsum dolor silit", TextMessage.Type.INBOX),
        TextMessage("+919876543210", System.currentTimeMillis() - 2*DateUtils.DAY_IN_MILLIS, "Hey there i'm not creepy", TextMessage.Type.SENT),
        TextMessage("+919876543210", System.currentTimeMillis()-DateUtils.DAY_IN_MILLIS, "i should be saying lorem ipsum dolor silit", TextMessage.Type.SENT),
        TextMessage("+919876543210", System.currentTimeMillis(), "I'm not sure i should be saying lorem ipsum dolor silit", TextMessage.Type.INBOX),
    )
}