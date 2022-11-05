package com.compultra.silent.data

import androidx.room.Entity

@Entity(tableName = KEYS_TABLE, primaryKeys = ["address"])
data class AcceptedKeys(
    val address: String,
    val key: String
)

@Entity(tableName = CONFIG_TABLE, primaryKeys = ["argument"])
data class MyConfig (
    val argument: String,
    val value: String
) {
    companion object {
        val PRIVATE_KEY = "PRIVATE_KEY"
        val PUBLIC_KEY = "PUBLIC_KEY"
        val LOCAL_ENCRYPTION_KEY = "LOCAL_ENCRYPTION_KEY"
    }
}