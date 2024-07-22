package com.ighorosipov.currencyconverter.features.converter.data.dto.model


import com.google.gson.annotations.SerializedName

data class CurrenciesDto(
    @SerializedName("currencies")
    val currencies: HashMap<String, String>
)