package com.ighorosipov.currencyconverter.features.converter.domain.model

import java.io.Serializable

data class ConvertOptions(
    val fromToCurrencies: FromToCurrencies,
    val amount: Double
): Serializable