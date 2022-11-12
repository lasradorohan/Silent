package com.compultra.silent

import android.content.res.Resources
import android.text.format.DateFormat
import android.text.format.DateUtils
import android.util.TypedValue

inline fun <reified T> Resources.dpToPx(value: Int): T {
    val result = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        value.toFloat(), displayMetrics
    )

    return when (T::class) {
        Float::class -> result as T
        Int::class -> result.toInt() as T
        else -> throw IllegalStateException("Type not supported")
    }
}

const val MYTAG = "WOHOO"

fun getRelativeTimestamp(timestamp: Long): String {
    return when {
        DateUtils.isToday(timestamp) -> DateFormat.format("hh:mm", timestamp).toString()
        DateUtils.isToday(timestamp + DateUtils.DAY_IN_MILLIS) -> "Yesterday"
        else -> DateFormat.format("MMM d", timestamp).toString()
    }
}

fun getInitialsForName(name: String) = with(name.trim()) {
    val idx = indexOfLast { it == ' ' }
    "${get(0)}${if (idx != -1) get(idx + 1) else ""}"
}