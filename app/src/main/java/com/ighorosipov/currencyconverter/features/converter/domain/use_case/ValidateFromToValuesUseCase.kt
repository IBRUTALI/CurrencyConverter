package com.ighorosipov.currencyconverter.features.converter.domain.use_case

import com.ighorosipov.currencyconverter.features.converter.domain.ConvertOptionsValidator
import com.ighorosipov.currencyconverter.features.converter.domain.model.Currency
import com.ighorosipov.currencyconverter.features.converter.domain.model.FromToCurrencies
import com.ighorosipov.currencyconverter.utils.Resource
import com.ighorosipov.currencyconverter.utils.error.ConvertError
import javax.inject.Inject

class ValidateFromToValuesUseCase @Inject constructor(
    private val convertOptionsValidator: ConvertOptionsValidator
) {
    operator fun invoke(fromToCurrencies: FromToCurrencies): Resource<Unit, ConvertError> {
        return convertOptionsValidator.validateFromToValues(fromToCurrencies)
    }
}