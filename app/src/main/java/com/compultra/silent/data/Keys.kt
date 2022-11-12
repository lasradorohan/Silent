package com.compultra.silent.data

import androidx.room.Entity

@Entity(tableName = KEYS_TABLE, primaryKeys = ["address"])
data class AcceptedKey(
    val address: String,
    val publicKey: String
)

@Entity(tableName = CONFIG_TABLE, primaryKeys = ["argument"])
data class MyConfig (
    val argument: String,
    val value: String
) {
    companion object {
        const val PRIVATE_KEY = "PRIVATE_KEY"
        const val PUBLIC_KEY = "PUBLIC_KEY"
        const val LOCAL_ENCRYPTION_KEY = "LOCAL_ENCRYPTION_KEY"
        const val LATEST_TIMESTAMP = "LATEST_TIMESTAMP"
    }
}