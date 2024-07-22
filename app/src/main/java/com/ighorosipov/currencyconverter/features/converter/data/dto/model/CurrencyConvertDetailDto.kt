package com.ighorosipov.currencyconverter.features.converter.data.dto.model


import com.google.gson.annotations.SerializedName

data class CurrencyConvertDetailDto(
    @SerializedName("amount")
    val amount: String,
    @SerializedName("base_currency_code")
    val baseCurrencyCode: String,
    @SerializedName("base_currency_name")
    val baseCurrencyName: String,
    @SerializedName("rates")
    val rates: HashMap<String, RatesDto>,
    @SerializedName("status")
    val status: String,
    @SerializedName("updated_date")
    val updatedDate: String
)