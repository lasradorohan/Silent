package com.compultra.silent.data

import android.text.format.DateFormat
import android.text.format.DateUtils
import androidx.room.Entity
import androidx.room.Ignore
import com.compultra.silent.getInitialsForName
import com.compultra.silent.getRelativeTimestamp
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

enum class MessageType {
    INBOX, SENT, OUTBOX, UNKNOWN
}

const val REQUEST_PREFIX = "%sr:"
const val CONNECTION_PREFIX = "%sc:"
const val MESSAGE_PREFIX = "%sm:"

sealed class SilentMessage{
    abstract val timestamp: Long

//    @delegate:Ignore
//    val relativeTime by lazy {
//        timestamp.let {
//            when {
//                DateUtils.isToday(it) -> DateFormat.format("hh:mm", it).toString()
//                DateUtils.isToday(it + DateUtils.DAY_IN_MILLIS) -> "Yesterday"
//                else -> DateFormat.format("MMM d", it).toString()
//            }
//        }
//    }
//    val initials by lazy {
//        with(name.trim()) {
//            val idx = indexOfLast { it == ' ' }
//            "${get(0)}${if (idx != -1) get(idx + 1) else ""}"
//        }
//    }


    @Entity(tableName = MESSAGES_TABLE, primaryKeys = ["address", "timestamp"])
    data class Text(
        val address: String,
        override val timestamp: Long,
        val message: String,
        val type: MessageType

    ) : SilentMessage()

    data class Unresolved(
        val address: String,
        override  val timestamp: Long,
        val message: String,
        val type: MessageType

    ): SilentMessage() {
        fun resolveType(): SilentMessage {

            return when {
                type==MessageType.INBOX && message.startsWith(REQUEST_PREFIX) -> Request.Incoming(address, timestamp, message.substring(4))
                (type==MessageType.SENT || type==MessageType.OUTBOX) && message.startsWith(
                    REQUEST_PREFIX) -> Request.Outgoing(address, timestamp)
                type==MessageType.INBOX && message.startsWith(CONNECTION_PREFIX) -> Connection.Incoming(address, timestamp, message.substring(4))
                (type==MessageType.SENT || type==MessageType.OUTBOX) && message.startsWith(
                    CONNECTION_PREFIX) -> Connection.Outgoing(address, timestamp)
                message.startsWith(MESSAGE_PREFIX) -> Text(address, timestamp, message.substring(4), type)
                else -> this
            }
        }

    }




    sealed class Request: SilentMessage() {

        data class Unresolved(
            val address: String,
            override val timestamp: Long,
            val content: String
        ): Request()

        @Entity(tableName = INCOMING_TABLE, primaryKeys = ["address"])
        data class Incoming(
            val address: String,
            override   val timestamp: Long,
            val content: String
        ): Request()

        @Entity(tableName = OUTGOING_TABLE, primaryKeys = ["address"])
        data class Outgoing(
            val address: String,
            override val timestamp: Long
        ): Request()
    }

    sealed class Connection : SilentMessage(){
        data class Unresolved(
            val address: String,
            override    val timestamp: Long,
            val content: String
        ): Connection()


        data class Incoming(
            val address: String,
            override  val timestamp: Long,
            val content: String
        ): Connection()

        data class Outgoing(
            val address: String,
            override val timestamp: Long
        ): Connection()
    }
}

val SilentMessage.Text.formattedTime
    get() = SimpleDateFormat.getDateTimeInstance().format(Date(timestamp))

@Entity(tableName = LATEST_TABLE, primaryKeys = ["address"])
data class LatestTextMessage(
    val address: String,
    val timestamp: Long,
    val message: String
){
    @Ignore
    var name = " "

    @get:Ignore
    val timestampFormated get() = getRelativeTimestamp(timestamp)

    @get:Ignore
    val initials get() = getInitialsForName(name)
}