package com.compultra.silent.ui.chats

import android.text.format.DateUtils
import java.util.*

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