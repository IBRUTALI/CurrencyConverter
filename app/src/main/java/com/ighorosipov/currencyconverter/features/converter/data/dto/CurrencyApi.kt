package com.ighorosipov.currencyconverter.features.converter.data.dto

import com.ighorosipov.currencyconverter.features.converter.data.dto.model.CurrenciesDto
import com.ighorosipov.currencyconverter.features.converter.data.dto.model.CurrencyConvertDetailDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {

    @GET("$CURRENCY_LIST_URL?api_key=$API_KEY")
    suspend fun getCurrencies(): CurrenciesDto

    @GET("$CURRENCY_CONVERT_URL?api_key=$API_KEY")
    suspend fun convertCurrency(
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("amount") amount: Double
    ): CurrencyConvertDetailDto

    companion object {
        const val API_KEY = "906f827e45c314f3c10eba91d9ce2b307ad6c6ed"
        const val CURRENCY_LIST_URL = "currency/list"
        const val CURRENCY_CONVERT_URL = "currency/convert"
    }
}