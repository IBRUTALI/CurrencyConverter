package com.ighorosipov.currencyconverter.features.converter.presentation

import com.ighorosipov.currencyconverter.utils.error.Error

typealias RootError = Error

sealed interface BaseEvent {
    data class  Error(val error: RootError) : BaseEvent
    data object Loading : BaseEvent
    data object Success : BaseEvent
}