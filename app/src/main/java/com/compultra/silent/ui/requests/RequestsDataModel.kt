package com.compultra.silent.ui.requests

import android.text.format.DateFormat
import android.text.format.DateUtils
import java.util.Date

sealed class RequestsDataModel {
    abstract val name: String
    abstract val timestamp: Date

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

data class RequestIncomingDataModel(override val name: String, override val timestamp: Date): RequestsDataModel()
data class RequestOutgoingDataModel(override val name: String, override val timestamp: Date): RequestsDataModel()