package com.compultra.silent.data

import android.os.Message
import androidx.room.Entity
import androidx.room.Ignore
import java.text.SimpleDateFormat
import java.util.*

enum class MessageType {
    INBOX, SENT, OUTBOX, UNKNOWN
}

@Entity(tableName = MESSAGES_TABLE, primaryKeys = ["address", "timestamp"])
data class TextMessage(
    val address: String,
    val timestamp: Long,
    val message: String,
    val type: MessageType

)

data class PlainMessage(
    val address: String,
    val timestamp: Long,
    val message: String,
    val type: MessageType

) {

}

val TextMessage.formattedTime
    get() = SimpleDateFormat.getDateTimeInstance().format(Date(timestamp))


@Entity(tableName = INCOMING_TABLE, primaryKeys = ["address"])
data class IncomingRequestMessage(
    val address: String,
    val timestamp: Long,
    val content: String
)

@Entity(tableName = OUTGOING_TABLE, primaryKeys = ["address"])
data class OutgoingRequestMessage(
    val address: String,
    val timestamp: Long
)