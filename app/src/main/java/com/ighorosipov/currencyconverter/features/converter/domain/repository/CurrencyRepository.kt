package com.ighorosipov.currencyconverter.features.converter.domain.repository

import com.ighorosipov.currencyconverter.features.converter.domain.model.ConvertOptions
import com.ighorosipov.currencyconverter.features.converter.domain.model.Currency
import com.ighorosipov.currencyconverter.features.converter.domain.model.CurrencyConvertDetail
import com.ighorosipov.currencyconverter.utils.Resource
import com.ighorosipov.currencyconverter.utils.error.DataError

interface CurrencyRepository {

    suspend fun getCurrencies(): Resource<List<Currency>, DataError>

    suspend fun convertCurrency(
        convertOptions: ConvertOptions
    ):  Resource<CurrencyConvertDetail, DataError.Network>

}