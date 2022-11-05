package com.compultra.silent.data

import androidx.room.Entity
import java.text.SimpleDateFormat
import java.util.*

enum class MessageType {
    INBOX, SENT, OUTBOX, UNKNOWN
}

sealed class SilentMessage {


    @Entity(tableName = MESSAGES_TABLE, primaryKeys = ["address", "timestamp"])
    data class Text(
        val address: String,
        val timestamp: Long,
        val message: String,
        val type: MessageType

    ) : SilentMessage()

    data class Unresolved(
        val address: String,
        val timestamp: Long,
        val message: String,
        val type: MessageType

    ): SilentMessage() {
        fun resolveType(): SilentMessage {
            val content = message.substring(4)
            return when {
                message.startsWith("%sr:") -> Request.Unresolved(address, timestamp, content)
                message.startsWith("%sc:") -> Connection.Unresolved(address, timestamp, content)
                message.startsWith("%sm:") -> Text(address, timestamp, content, type)
                else -> this
            }
        }

    }




    sealed class Request: SilentMessage() {

        data class Unresolved(
            val address: String,
            val timestamp: Long,
            val content: String
        ): Request()

        @Entity(tableName = INCOMING_TABLE, primaryKeys = ["address"])
        data class Incoming(
            val address: String,
            val timestamp: Long,
            val content: String
        ): Request()

        @Entity(tableName = OUTGOING_TABLE, primaryKeys = ["address"])
        data class Outgoing(
            val address: String,
            val timestamp: Long
        ): Request()
    }

    sealed class Connection : SilentMessage(){
        data class Unresolved(
            val address: String,
            val timestamp: Long,
            val content: String
        ): Connection()


        data class Incoming(
            val address: String,
            val timestamp: Long,
            val content: String
        ): Connection()

        data class Outgoing(
            val address: String,
            val timestamp: Long
        ): Connection()
    }
}

val SilentMessage.Text.formattedTime
    get() = SimpleDateFormat.getDateTimeInstance().format(Date(timestamp))