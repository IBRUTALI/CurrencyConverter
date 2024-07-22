package com.ighorosipov.currencyconverter.features.converter.presentation.screens.select_currency

import com.ighorosipov.currencyconverter.features.converter.domain.model.Currency
import com.ighorosipov.currencyconverter.features.converter.domain.model.FromToCurrencies

sealed interface SelectCurrencyFragmentEvent {
    data class SelectCurrencyFrom(val currency: Currency) : SelectCurrencyFragmentEvent
    data class SelectCurrencyTo(val currency: Currency) : SelectCurrencyFragmentEvent
    data object RepeatConnection : SelectCurrencyFragmentEvent
    data class ValidateAmountValue(val amountValue: String) : SelectCurrencyFragmentEvent
    data class ValidateFromToValues(val fromToCurrencies: FromToCurrencies) : SelectCurrencyFragmentEvent
    data class ChangeAmountValue(val value: String) : SelectCurrencyFragmentEvent
}