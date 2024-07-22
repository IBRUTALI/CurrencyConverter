package com.ighorosipov.currencyconverter.features.converter.domain.model

data class CurrencyConvertDetail(
    val amount: String,
    val baseCurrencyCode: String,
    val baseCurrencyName: String,
    val rates: Rates,
    val updatedDate: String
)
