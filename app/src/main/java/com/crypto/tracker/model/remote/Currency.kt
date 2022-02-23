package com.crypto.tracker.model.remote

enum class Currency {
    USD, EUR, JPY;

    override fun toString(): String {
        return name.lowercase()
    }

}
