package com.compultra.silent.ui.messages

import android.text.format.DateUtils
import com.compultra.silent.data.MessageType
import com.compultra.silent.data.SilentMessage
import java.util.*

class MessagesDataSource {
    fun load() = listOf(
        SilentMessage.Text("+919876543210", System.currentTimeMillis() - 6*DateUtils.DAY_IN_MILLIS, "Hey there i'm not creepy", MessageType.INBOX),
        SilentMessage.Text("+919876543210", System.currentTimeMillis()- 6*DateUtils.DAY_IN_MILLIS, "I'm not sure i should be saying lorem ipsum dolor silit", MessageType.SENT),
        SilentMessage.Text("+919876543210", System.currentTimeMillis()-5*DateUtils.DAY_IN_MILLIS, "i should be saying lorem ipsum dolor silit", MessageType.INBOX),
        SilentMessage.Text("+919876543210", System.currentTimeMillis() - 5*DateUtils.DAY_IN_MILLIS, "Hey there i'm not creepy", MessageType.SENT),
        SilentMessage.Text("+919876543210", System.currentTimeMillis() - 4*DateUtils.DAY_IN_MILLIS, "I'm not sure i should be saying lorem ipsum dolor silit", MessageType.INBOX),
        SilentMessage.Text("+919876543210", System.currentTimeMillis()-4*DateUtils.DAY_IN_MILLIS, "i should be saying lorem ipsum dolor silit", MessageType.SENT),
        SilentMessage.Text("+919876543210", System.currentTimeMillis() - 3*DateUtils.DAY_IN_MILLIS, "Hey there i'm not creepy", MessageType.INBOX),
        SilentMessage.Text("+919876543210", System.currentTimeMillis()-3*DateUtils.DAY_IN_MILLIS, "I'm not sure i should be saying lorem ipsum dolor silit", MessageType.SENT),
        SilentMessage.Text("+919876543210", System.currentTimeMillis()-2*DateUtils.DAY_IN_MILLIS, "i should be saying lorem ipsum dolor silit", MessageType.INBOX),
        SilentMessage.Text("+919876543210", System.currentTimeMillis() - 2*DateUtils.DAY_IN_MILLIS, "Hey there i'm not creepy", MessageType.SENT),
        SilentMessage.Text("+919876543210", System.currentTimeMillis()-DateUtils.DAY_IN_MILLIS, "i should be saying lorem ipsum dolor silit", MessageType.SENT),
        SilentMessage.Text("+919876543210", System.currentTimeMillis(), "I'm not sure i should be saying lorem ipsum dolor silit", MessageType.INBOX),
    )
}