package com.ighorosipov.currencyconverter.di

import com.ighorosipov.currencyconverter.features.converter.data.repository.CurrencyRepositoryImpl
import com.ighorosipov.currencyconverter.features.converter.domain.repository.CurrencyRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    fun bindCurrencyRepository(
        currencyRepositoryImpl: CurrencyRepositoryImpl,
    ): CurrencyRepository

}