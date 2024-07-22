package com.ighorosipov.currencyconverter.features.converter.presentation

sealed interface ValidationEvent {
    data object Success: ValidationEvent
}