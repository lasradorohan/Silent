package com.compultra.silent.ui.requests

import com.compultra.silent.getInitialsForName

sealed class RequestsViewData {
    abstract val name: String
    abstract val address: String
    abstract val timestamp: String
//
//    val timestampFormatted by lazy {
//        timestamp.time.let {
//            when {
//                DateUtils.isToday(it) -> DateFormat.format("hh:mm", it).toString()
//                DateUtils.isToday(it + DateUtils.DAY_IN_MILLIS) -> "Yesterday"
//                else -> DateFormat.format("MMM d", it).toString()
//            }
//        }
//    }
    val initials by lazy { getInitialsForName(name) }


}

data class RequestIncomingViewData(override val name: String, override val address: String, override val timestamp: String): RequestsViewData()
data class RequestOutgoingViewData(override val name: String, override val address: String, override val timestamp: String): RequestsViewData()