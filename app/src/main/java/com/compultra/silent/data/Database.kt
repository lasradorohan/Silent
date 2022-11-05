package com.compultra.silent.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

const val MESSAGES_TABLE = "messages"
const val INCOMING_TABLE = "incoming"
const val OUTGOING_TABLE = "outgoing"
const val DATABASE_NAME = "messages"

@Database(
    entities = [TextMessage::class, IncomingRequestMessage::class, OutgoingRequestMessage::class],
    version = 1
)
abstract class MessagesDatabase : RoomDatabase() {
    //create daos
    abstract val messagesDao: MessagesDao

    companion object {
        //instantiate
        private lateinit var INSTANCE: MessagesDatabase
        fun getDatabase(context: Context) = synchronized(MessagesDatabase::class.java) {
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