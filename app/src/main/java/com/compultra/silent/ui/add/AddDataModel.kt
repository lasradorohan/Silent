package com.compultra.silent.ui.add

data class AddDataModel(
    val name: String,
    val number: String,
) {
    val initials by lazy {
        with(name.trim()) {
            val idx = indexOfLast { it == ' ' }
            "${get(0)}${if (idx != -1) get(idx + 1) else ""}"
        }
    }
}
