package com.ighorosipov.currencyconverter.features.converter.data.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency")
data class CurrencyEntity(
   @PrimaryKey
   @ColumnInfo(name = "code")
   val code: String,
   @ColumnInfo(name = "name")
   val name: String
)