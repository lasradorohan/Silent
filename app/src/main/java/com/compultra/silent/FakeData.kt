package com.compultra.silent

import android.text.format.DateUtils
import com.compultra.silent.data.Contact
import com.compultra.silent.data.MessageType
import com.compultra.silent.data.SilentMessage
import com.compultra.silent.ui.chats.ChatsDataModel
import java.util.*

class AddDataSource {
    fun load() = listOf(
        Contact("John Doe", "+919876414132"),
        Contact("Bon Roe", "+919876414132"),
        Contact("Marilyn Monroe", "+919876414132"),
        Contact("John Doe", "+919876414132"),
        Contact("Bon Roe", "+919876414132"),
        Contact("Marilyn Monroe", "+919876414132"),
        Contact("John Doe", "+919876414132"),
        Contact("Bon Roe", "+919876414132"),
        Contact("Marilyn Monroe", "+919876414132"),
        Contact("John Doe", "+919876414132"),
        Contact("Bon Roe", "+919876414132"),
        Contact("Marilyn Monroe", "+919876414132"),
    )

}

class MessagesDataSource {
    fun load() = listOf(
        SilentMessage.Text("+919876543210", System.currentTimeMillis() - 6* DateUtils.DAY_IN_MILLIS, "Hey there i'm not creepy", MessageType.INBOX),
        SilentMessage.Text("+919876543210", System.currentTimeMillis()- 6* DateUtils.DAY_IN_MILLIS, "I'm not sure i should be saying lorem ipsum dolor silit", MessageType.SENT),
        SilentMessage.Text("+919876543210", System.currentTimeMillis()-5* DateUtils.DAY_IN_MILLIS, "i should be saying lorem ipsum dolor silit", MessageType.INBOX),
        SilentMessage.Text("+919876543210", System.currentTimeMillis() - 5* DateUtils.DAY_IN_MILLIS, "Hey there i'm not creepy", MessageType.SENT),
        SilentMessage.Text("+919876543210", System.currentTimeMillis() - 4* DateUtils.DAY_IN_MILLIS, "I'm not sure i should be saying lorem ipsum dolor silit", MessageType.INBOX),
        SilentMessage.Text("+919876543210", System.currentTimeMillis()-4* DateUtils.DAY_IN_MILLIS, "i should be saying lorem ipsum dolor silit", MessageType.SENT),
        SilentMessage.Text("+919876543210", System.currentTimeMillis() - 3* DateUtils.DAY_IN_MILLIS, "Hey there i'm not creepy", MessageType.INBOX),
        SilentMessage.Text("+919876543210", System.currentTimeMillis()-3* DateUtils.DAY_IN_MILLIS, "I'm not sure i should be saying lorem ipsum dolor silit", MessageType.SENT),
        SilentMessage.Text("+919876543210", System.currentTimeMillis()-2* DateUtils.DAY_IN_MILLIS, "i should be saying lorem ipsum dolor silit", MessageType.INBOX),
        SilentMessage.Text("+919876543210", System.currentTimeMillis() - 2* DateUtils.DAY_IN_MILLIS, "Hey there i'm not creepy", MessageType.SENT),
        SilentMessage.Text("+919876543210", System.currentTimeMillis()- DateUtils.DAY_IN_MILLIS, "i should be saying lorem ipsum dolor silit", MessageType.SENT),
        SilentMessage.Text("+919876543210", System.currentTimeMillis(), "I'm not sure i should be saying lorem ipsum dolor silit", MessageType.INBOX),
    )
}

class ChatsDataSource {
    fun load() = listOf(
        ChatsDataModel("John Doe", "+919769686203", "Hey there i'm not creepy", Date(System.currentTimeMillis() - DateUtils.DAY_IN_MILLIS)),
        ChatsDataModel("Bon Roe", "+919769686203", "I'm not sure i should be saying lorem ipsum dolor silit", Date(System.currentTimeMillis())),
        ChatsDataModel("Marilyn Monroe", "+919769686203", "i should be saying lorem ipsum dolor silit", Date(System.currentTimeMillis()+DateUtils.DAY_IN_MILLIS)),
        ChatsDataModel("John Doe", "+919769686203", "Hey there i'm not creepy", Date(System.currentTimeMillis() - DateUtils.DAY_IN_MILLIS)),
        ChatsDataModel("Bon Roe", "+919769686203", "I'm not sure i should be saying lorem ipsum dolor silit", Date(System.currentTimeMillis())),
        ChatsDataModel("Marilyn Monroe", "+919769686203", "i should be saying lorem ipsum dolor silit", Date(System.currentTimeMillis()+DateUtils.DAY_IN_MILLIS)),
        ChatsDataModel("John Doe", "+919769686203", "Hey there i'm not creepy", Date(System.currentTimeMillis() - DateUtils.DAY_IN_MILLIS)),
        ChatsDataModel("Bon Roe", "+919769686203", "I'm not sure i should be saying lorem ipsum dolor silit", Date(System.currentTimeMillis())),
        ChatsDataModel("Marilyn Monroe", "+919769686203", "i should be saying lorem ipsum dolor silit", Date(System.currentTimeMillis()+DateUtils.DAY_IN_MILLIS)),
        ChatsDataModel("John Doe", "+919769686203", "Hey there i'm not creepy", Date(System.currentTimeMillis() - DateUtils.DAY_IN_MILLIS)),
        ChatsDataModel("Bon Roe", "+919769686203", "I'm not sure i should be saying lorem ipsum dolor silit", Date(System.currentTimeMillis())),
        ChatsDataModel("Marilyn Monroe", "+919769686203", "i should be saying lorem ipsum dolor silit", Date(System.currentTimeMillis()+DateUtils.DAY_IN_MILLIS)),
    )

}