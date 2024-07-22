package com.ighorosipov.currencyconverter.features.converter.domain

import com.ighorosipov.currencyconverter.features.converter.domain.model.FromToCurrencies
import com.ighorosipov.currencyconverter.utils.Resource
import com.ighorosipov.currencyconverter.utils.error.ConvertError

class ConvertOptionsValidator {

    fun validateAmountValue(amountValue: String): Resource<Unit, ConvertError> {
        if (amountValue.isEmpty()) {
            return Resource.Error(error = ConvertError.NO_AMOUNT_VALUE)
        }
        return Resource.Success(Unit)
    }

    fun validateFromToValues(fromToCurrencies: FromToCurrencies): Resource<Unit, ConvertError> {
        if (fromToCurrencies.from == fromToCurrencies.to) {
            return Resource.Error(error = ConvertError.FROM_TO_VALUES_IS_SAME)
        }
        return Resource.Success(Unit)
    }



}