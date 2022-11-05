package com.compultra.silent.ui.requests

import android.text.format.DateUtils
import java.util.*

class RequestsDataSource {
    fun loadIncoming() = listOf(
        RequestIncomingDataModel("John Doe",  Date(System.currentTimeMillis() - DateUtils.DAY_IN_MILLIS)),
        RequestIncomingDataModel("Bon Roe", Date(System.currentTimeMillis())),
        RequestIncomingDataModel("Marilyn Monroe",  Date(System.currentTimeMillis()+DateUtils.DAY_IN_MILLIS)),
        RequestIncomingDataModel("John Doe",  Date(System.currentTimeMillis() - DateUtils.DAY_IN_MILLIS)),
        RequestIncomingDataModel("Bon Roe", Date(System.currentTimeMillis())),
        RequestIncomingDataModel("Marilyn Monroe",  Date(System.currentTimeMillis()+DateUtils.DAY_IN_MILLIS)),
        RequestIncomingDataModel("John Doe",  Date(System.currentTimeMillis() - DateUtils.DAY_IN_MILLIS)),
        RequestIncomingDataModel("Bon Roe", Date(System.currentTimeMillis())),
        RequestIncomingDataModel("Marilyn Monroe",  Date(System.currentTimeMillis()+DateUtils.DAY_IN_MILLIS)),
        RequestIncomingDataModel("John Doe",  Date(System.currentTimeMillis() - DateUtils.DAY_IN_MILLIS)),
        RequestIncomingDataModel("Bon Roe", Date(System.currentTimeMillis())),
        RequestIncomingDataModel("Marilyn Monroe",  Date(System.currentTimeMillis()+DateUtils.DAY_IN_MILLIS)),
    )
    fun loadOutgoing() = listOf(
        RequestOutgoingDataModel("John Doe",  Date(System.currentTimeMillis() - DateUtils.DAY_IN_MILLIS)),
        RequestOutgoingDataModel("Bon Roe", Date(System.currentTimeMillis())),
        RequestOutgoingDataModel("Marilyn Monroe",  Date(System.currentTimeMillis()+DateUtils.DAY_IN_MILLIS)),
        RequestOutgoingDataModel("John Doe",  Date(System.currentTimeMillis() - DateUtils.DAY_IN_MILLIS)),
        RequestOutgoingDataModel("Bon Roe", Date(System.currentTimeMillis())),
        RequestOutgoingDataModel("Marilyn Monroe",  Date(System.currentTimeMillis()+DateUtils.DAY_IN_MILLIS)),
        RequestOutgoingDataModel("John Doe",  Date(System.currentTimeMillis() - DateUtils.DAY_IN_MILLIS)),
        RequestOutgoingDataModel("Bon Roe", Date(System.currentTimeMillis())),
        RequestOutgoingDataModel("Marilyn Monroe",  Date(System.currentTimeMillis()+DateUtils.DAY_IN_MILLIS)),
        RequestOutgoingDataModel("John Doe",  Date(System.currentTimeMillis() - DateUtils.DAY_IN_MILLIS)),
        RequestOutgoingDataModel("Bon Roe", Date(System.currentTimeMillis())),
        RequestOutgoingDataModel("Marilyn Monroe",  Date(System.currentTimeMillis()+DateUtils.DAY_IN_MILLIS)),
    )

}