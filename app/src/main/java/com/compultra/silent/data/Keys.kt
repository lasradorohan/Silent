package com.compultra.silent.data

data class AcceptedKeys(
    val address: String,
    val key: String
)

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