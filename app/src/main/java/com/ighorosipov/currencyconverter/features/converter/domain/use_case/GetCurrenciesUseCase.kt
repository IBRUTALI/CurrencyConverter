package com.ighorosipov.currencyconverter.features.converter.domain.use_case

import com.ighorosipov.currencyconverter.features.converter.domain.repository.CurrencyRepository
import com.ighorosipov.currencyconverter.utils.Resource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCurrenciesUseCase @Inject constructor(
    private val repository: CurrencyRepository,
) {

    suspend operator fun invoke() = flow {
        emit(Resource.Loading())
        emit(repository.getCurrencies())
    }

}