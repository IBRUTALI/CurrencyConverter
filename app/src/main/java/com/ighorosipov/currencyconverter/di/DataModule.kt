package com.ighorosipov.currencyconverter.di

import android.content.Context
import com.ighorosipov.currencyconverter.features.converter.data.room.CurrencyDao
import com.ighorosipov.currencyconverter.features.converter.data.room.CurrencyDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
interface DataModule {

    companion object {

        @Singleton
        @Provides
        fun provideCurrencyDatabase(context: Context): CurrencyDatabase {
            return CurrencyDatabase.getDB(context)
        }

        @Singleton
        @Provides
        fun provideCurrencyDao(currencyDatabase: CurrencyDatabase): CurrencyDao {
            return currencyDatabase.currencyDao
        }

    }

}