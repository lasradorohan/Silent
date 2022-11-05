package com.compultra.silent.ui.chats

import android.text.format.DateFormat
import android.text.format.DateUtils
import java.util.Date

data class ChatsDataModel(
    val name: String,
    val address: String,
    val message: String,
    val timestamp: Date
) {
    val timestampFormatted by lazy {
        timestamp.time.let {
            when {
                DateUtils.isToday(it) -> DateFormat.format("hh:mm", it).toString()
                DateUtils.isToday(it + DateUtils.DAY_IN_MILLIS) -> "Yesterday"
                else -> DateFormat.format("MMM d", it).toString()
            }
        }
    }
    val initials by lazy {
        with(name.trim()) {
            val idx = indexOfLast { it == ' ' }
            "${get(0)}${if (idx != -1) get(idx + 1) else ""}"
        }
    }

}
