package com.ighorosipov.currencyconverter.utils

import com.ighorosipov.currencyconverter.utils.error.Error

typealias RootError = Error

sealed interface Resource<out D, out E : RootError> {
    data class Success<out D, out E : RootError>(val data: D) : Resource<D, E>
    data class Error<out D, out E : RootError>(val data: D? = null, val error: E) : Resource<D, E>
    class Loading<out D, out E : RootError> : Resource<D, E>
}