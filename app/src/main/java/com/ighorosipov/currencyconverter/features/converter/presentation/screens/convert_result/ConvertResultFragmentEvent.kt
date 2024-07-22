package com.ighorosipov.currencyconverter.features.converter.presentation.screens.convert_result

sealed interface ConvertResultFragmentEvent {

    data object RepeatConnection : ConvertResultFragmentEvent

}