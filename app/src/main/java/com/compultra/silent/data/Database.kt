package com.compultra.silent.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

const val MESSAGES_TABLE = "messages"
const val INCOMING_TABLE = "incoming"
const val OUTGOING_TABLE = "outgoing"

const val KEYS_TABLE = "keys"
const val CONFIG_TABLE = "config"

const val DATABASE_NAME = "messages"

@Database(
    entities = [SilentMessage.Text::class, SilentMessage.Request.Incoming::class, SilentMessage.Request.Outgoing::class, AcceptedKeys::class, MyConfig::class],
    version = 1
)
abstract class MessagesDatabase : RoomDatabase() {
    //create daos
    abstract val messagesDao: MessagesDao

    companion object {
        //instantiate
        private lateinit var INSTANCE: MessagesDatabase
        fun getInstance(context: Context) = synchronized(MessagesDatabase::class.java) {
            if (!::INSTANCE.isInitialized) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    MessagesDatabase::class.java,
                    DATABASE_NAME
                ).build()
            }
            INSTANCE
        }
    }
}