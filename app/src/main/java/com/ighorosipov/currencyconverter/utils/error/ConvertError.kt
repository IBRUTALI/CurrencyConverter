package com.ighorosipov.currencyconverter.utils.error

/** Класс ошибок конвертации валюты */

enum class ConvertError: Error {
    NO_AMOUNT_VALUE,
    FROM_TO_VALUES_IS_SAME
}