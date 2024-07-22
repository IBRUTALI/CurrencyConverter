package com.ighorosipov.currencyconverter.features.converter.data.repository

import android.database.sqlite.SQLiteDiskIOException
import com.google.gson.JsonParseException
import com.ighorosipov.currencyconverter.features.converter.data.dto.CurrencyApi
import com.ighorosipov.currencyconverter.features.converter.data.dto.model.CurrenciesDto
import com.ighorosipov.currencyconverter.features.converter.data.mapper.CurrencyMapper
import com.ighorosipov.currencyconverter.features.converter.data.room.CurrencyDao
import com.ighorosipov.currencyconverter.features.converter.domain.model.ConvertOptions
import com.ighorosipov.currencyconverter.features.converter.domain.model.Currency
import com.ighorosipov.currencyconverter.features.converter.domain.model.CurrencyConvertDetail
import com.ighorosipov.currencyconverter.features.converter.domain.repository.CurrencyRepository
import com.ighorosipov.currencyconverter.utils.Resource
import com.ighorosipov.currencyconverter.utils.error.DataError
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val api: CurrencyApi,
    private val dao: CurrencyDao,
) : CurrencyRepository {

    override suspend fun getCurrencies(): Resource<List<Currency>, DataError> {
        return try {
            val currenciesDto = api.getCurrencies()
            insertCurrenciesToDB(currenciesDto = currenciesDto)
            Resource.Success(
                data = getCurrenciesFromDB()
            )
        } catch (e: HttpException) {
            val currenciesCache = getCurrenciesFromDB()
            when (e.code()) {
                408 -> Resource.Error(
                    data = currenciesCache,
                    error = DataError.Network.REQUEST_TIMEOUT
                )

                413 -> Resource.Error(
                    data = currenciesCache,
                    error = DataError.Network.PAYLOAD_TO_LARGE
                )

                429 -> Resource.Error(
                    data = currenciesCache,
                    error = DataError.Network.TOO_MANY_REQUESTS
                )

                in 500..599 -> {
                    Resource.Error(
                        data = currenciesCache,
                        error = DataError.Network.SERVER_ERROR
                    )
                }

                else -> Resource.Error(data = currenciesCache, error = DataError.Network.UNKNOWN)
            }
        } catch (e: IOException) {
            Resource.Error(data = getCurrenciesFromDB(), error = DataError.Network.NO_INTERNET)
        } catch (e: JsonParseException) {
            Resource.Error(data = getCurrenciesFromDB(), error = DataError.Network.SERIALIZATION)
        } catch (e: SQLiteDiskIOException) {
            Resource.Error(error = DataError.Local.DISK_FULL)
        } catch (e: Exception) {
            Resource.Error(error = DataError.Network.UNKNOWN)
        }
    }

    override suspend fun convertCurrency(
        convertOptions: ConvertOptions,
    ): Resource<CurrencyConvertDetail, DataError.Network> {
        return try {
            val currencyDetailDto = api.convertCurrency(
                from = convertOptions.fromToCurrencies.from.code,
                to = convertOptions.fromToCurrencies.to.code,
                amount = convertOptions.amount
            )
            Resource.Success(
                data = CurrencyMapper().currencyConvertDetailDtoToDomain(currencyDetailDto)
            )
        } catch (e: HttpException) {
            when (e.code()) {
                408 -> Resource.Error(
                    error = DataError.Network.REQUEST_TIMEOUT
                )

                413 -> Resource.Error(
                    error = DataError.Network.PAYLOAD_TO_LARGE
                )

                429 -> Resource.Error(
                    error = DataError.Network.TOO_MANY_REQUESTS
                )

                in 500..599 -> {
                    Resource.Error(
                        error = DataError.Network.SERVER_ERROR
                    )
                }

                else -> Resource.Error(error = DataError.Network.UNKNOWN)
            }
        } catch (e: IOException) {
            Resource.Error(error = DataError.Network.NO_INTERNET)
        } catch (e: JsonParseException) {
            Resource.Error(error = DataError.Network.SERIALIZATION)
        } catch (e: Exception) {
            Resource.Error(error = DataError.Network.UNKNOWN)
        }
    }

    private suspend fun insertCurrenciesToDB(
        currenciesDto: CurrenciesDto,
    ) {
        val currenciesEntity =
            CurrencyMapper().currenciesDtoToEntity(currenciesDto = currenciesDto)
        currenciesEntity.forEach { currencyEntity ->
            dao.insertCurrency(currencyEntity = currencyEntity)
        }
    }

    private suspend fun getCurrenciesFromDB(): List<Currency> {
        return CurrencyMapper().currenciesEntityToDomain(
            currenciesEntity = dao.getCurrencies()
        )
    }
}