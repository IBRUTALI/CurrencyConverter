package com.ighorosipov.currencyconverter.features.converter.domain.use_case

import com.ighorosipov.currencyconverter.features.converter.domain.model.ConvertOptions
import com.ighorosipov.currencyconverter.features.converter.domain.repository.CurrencyRepository
import com.ighorosipov.currencyconverter.utils.Resource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ConvertCurrencyUseCase @Inject constructor(
    private val repository: CurrencyRepository,
) {

    suspend operator fun invoke(
        convertOptions: ConvertOptions,
    ) = flow {
        emit(Resource.Loading())
        emit(repository.convertCurrency(convertOptions))
    }

}