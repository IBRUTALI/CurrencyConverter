package com.ighorosipov.currencyconverter.features.converter.domain.model

data class Rates(
    val code: String,
    val name: String,
    val rate: String,
    val rateForAmount: Double
)