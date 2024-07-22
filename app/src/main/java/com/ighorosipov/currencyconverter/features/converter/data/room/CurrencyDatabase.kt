package com.ighorosipov.currencyconverter.features.converter.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ighorosipov.currencyconverter.features.converter.data.room.model.CurrencyEntity

@Database(
    entities = [CurrencyEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CurrencyDatabase: RoomDatabase() {

    abstract val currencyDao: CurrencyDao

    companion object {

        fun getDB(context: Context): CurrencyDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                CurrencyDatabase::class.java,
                DATABASE_NAME
            ).build()
        }

        private const val DATABASE_NAME = "market_db"

    }

}