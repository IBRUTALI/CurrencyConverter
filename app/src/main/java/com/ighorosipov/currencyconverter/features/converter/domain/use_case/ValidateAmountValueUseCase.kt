package com.ighorosipov.currencyconverter.features.converter.domain.use_case

import com.ighorosipov.currencyconverter.features.converter.domain.ConvertOptionsValidator
import com.ighorosipov.currencyconverter.utils.Resource
import com.ighorosipov.currencyconverter.utils.error.ConvertError
import javax.inject.Inject

class ValidateAmountValueUseCase @Inject constructor(
    private val convertOptionsValidator: ConvertOptionsValidator
) {

    operator fun invoke(amountValue: String): Resource<Unit, ConvertError> {
        return convertOptionsValidator.validateAmountValue(amountValue)
    }

}