package com.ighorosipov.currencyconverter.features.converter.data.dto.model


import com.google.gson.annotations.SerializedName

data class RatesDto(
    @SerializedName("currency_name")
    val name: String,
    @SerializedName("rate")
    val rate: String,
    @SerializedName("rate_for_amount")
    val rateForAmount: Double
)