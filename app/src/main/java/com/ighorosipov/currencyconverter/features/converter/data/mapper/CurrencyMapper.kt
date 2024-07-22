package com.ighorosipov.currencyconverter.features.converter.data.mapper

import com.ighorosipov.currencyconverter.features.converter.data.dto.model.CurrenciesDto
import com.ighorosipov.currencyconverter.features.converter.data.dto.model.CurrencyConvertDetailDto
import com.ighorosipov.currencyconverter.features.converter.data.room.model.CurrencyEntity
import com.ighorosipov.currencyconverter.features.converter.domain.model.Currency
import com.ighorosipov.currencyconverter.features.converter.domain.model.CurrencyConvertDetail
import com.ighorosipov.currencyconverter.features.converter.domain.model.Rates

class CurrencyMapper {

    fun currenciesDtoToEntity(currenciesDto: CurrenciesDto): List<CurrencyEntity> {
        return currenciesDto.currencies.map {
            CurrencyEntity(
                code = it.key,
                name = it.value
            )
        }
    }

    fun currenciesEntityToDomain(currenciesEntity: List<CurrencyEntity>): List<Currency> {
        return currenciesEntity.map { currencyEntity ->
            Currency(
                code = currencyEntity.code,
                name = currencyEntity.name
            )
        }
    }

    fun currencyEntityToDomain(currencyEntity: CurrencyEntity): Currency {
        return Currency(
            code = currencyEntity.code,
            name = currencyEntity.name
        )
    }


    fun currencyConvertDetailDtoToDomain(
        currencyConvertDetailDto: CurrencyConvertDetailDto,
    ): CurrencyConvertDetail {
        return CurrencyConvertDetail(
            amount = currencyConvertDetailDto.amount,
            baseCurrencyCode = currencyConvertDetailDto.baseCurrencyCode,
            baseCurrencyName = currencyConvertDetailDto.baseCurrencyName,
            rates = currencyConvertDetailDto.rates.firstNotNullOf {
                Rates(
                    code = it.key,
                    name = it.value.name,
                    rate = it.value.rate,
                    rateForAmount = it.value.rateForAmount
                )
            },
            updatedDate = currencyConvertDetailDto.updatedDate
        )
    }

}