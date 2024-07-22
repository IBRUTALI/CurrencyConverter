package com.ighorosipov.currencyconverter.utils.error

/** Запечатанный интерфейс для ошибок, связанных с сетью и БД */
sealed interface DataError: Error {
    enum class Network: DataError {
        REQUEST_TIMEOUT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        PAYLOAD_TO_LARGE,
        SERVER_ERROR,
        SERIALIZATION,
        UNKNOWN
    }
    enum class Local: DataError {
        DISK_FULL,
        UNKNOWN
    }
}