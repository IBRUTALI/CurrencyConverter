package com.ighorosipov.currencyconverter.features.converter.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ighorosipov.currencyconverter.features.converter.data.room.model.CurrencyEntity

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrency(currencyEntity: CurrencyEntity)

    @Query("SELECT * FROM currency")
    suspend fun getCurrencies(): List<CurrencyEntity>

}