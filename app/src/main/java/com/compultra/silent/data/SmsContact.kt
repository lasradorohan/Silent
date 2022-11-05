package com.compultra.silent.data

data class Contact(
    val address: String,
    val name: String,
) {
    val initials by lazy {
        with(name.trim()) {
            val idx = indexOfLast { it == ' ' }
            "${get(0)}${if (idx != -1) get(idx + 1) else ""}"
        }
    }
}


