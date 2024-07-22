package com.ighorosipov.currencyconverter.di

import com.ighorosipov.currencyconverter.features.converter.domain.ConvertOptionsValidator
import com.ighorosipov.currencyconverter.features.converter.domain.repository.CurrencyRepository
import com.ighorosipov.currencyconverter.features.converter.domain.use_case.ConvertCurrencyUseCase
import com.ighorosipov.currencyconverter.features.converter.domain.use_case.GetCurrenciesUseCase
import com.ighorosipov.currencyconverter.features.converter.domain.use_case.ValidateAmountValueUseCase
import com.ighorosipov.currencyconverter.features.converter.domain.use_case.ValidateFromToValuesUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
interface UseCaseModule {

    companion object {
        @Provides
        @Singleton
        fun provideGetCurrenciesUseCase(
            repository: CurrencyRepository
        ): GetCurrenciesUseCase = GetCurrenciesUseCase(repository)

        @Provides
        @Singleton
        fun provideConvertCurrencyUseCase(
            repository: CurrencyRepository
        ): ConvertCurrencyUseCase = ConvertCurrencyUseCase(repository)

        @Provides
        @Singleton
        fun provideValidateAmountValueUseCase(
            convertOptionsValidator: ConvertOptionsValidator
        ): ValidateAmountValueUseCase = ValidateAmountValueUseCase(convertOptionsValidator)

        @Provides
        @Singleton
        fun provideValidateFromToValuesUseCase(
            convertOptionsValidator: ConvertOptionsValidator
        ): ValidateFromToValuesUseCase = ValidateFromToValuesUseCase(convertOptionsValidator)

        @Provides
        @Singleton
        fun provideConvertOptionValidator(): ConvertOptionsValidator = ConvertOptionsValidator()
    }


}