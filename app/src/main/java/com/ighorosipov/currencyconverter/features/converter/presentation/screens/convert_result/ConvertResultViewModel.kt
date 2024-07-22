package com.ighorosipov.currencyconverter.features.converter.presentation.screens.convert_result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ighorosipov.currencyconverter.di.IODispatcher
import com.ighorosipov.currencyconverter.features.converter.domain.model.ConvertOptions
import com.ighorosipov.currencyconverter.features.converter.domain.model.CurrencyConvertDetail
import com.ighorosipov.currencyconverter.features.converter.domain.use_case.ConvertCurrencyUseCase
import com.ighorosipov.currencyconverter.features.converter.presentation.BaseEvent
import com.ighorosipov.currencyconverter.utils.Resource
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ConvertResultViewModel @AssistedInject constructor(
    private val convertCurrencyUseCase: ConvertCurrencyUseCase,
    @Assisted private val convertOptions: ConvertOptions?,
    @IODispatcher
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    init {
        convertOptions?.let {
            convertCurrencies(convertOptions)
        }
    }

    private val eventChannel = Channel<BaseEvent>(0)
    val events = eventChannel.receiveAsFlow()

    private val _convertResult = MutableStateFlow<CurrencyConvertDetail?>(null)
    val convertResult: StateFlow<CurrencyConvertDetail?> = _convertResult

    fun onEvent(event: ConvertResultFragmentEvent) {
        when(event) {
            ConvertResultFragmentEvent.RepeatConnection -> {
                convertOptions?.let {
                    convertCurrencies(convertOptions)
                }
            }
        }
    }

    private fun convertCurrencies(convertOptions: ConvertOptions) {
        viewModelScope.launch(dispatcher) {
            convertCurrencyUseCase(convertOptions).collect { resource ->
                when(resource) {
                    is Resource.Error -> {
                        eventChannel.send(BaseEvent.Error(resource.error))
                    }
                    is Resource.Loading -> {
                        eventChannel.send(BaseEvent.Loading)
                    }
                    is Resource.Success -> {
                        eventChannel.send(BaseEvent.Success)
                        _convertResult.emit(resource.data)
                    }
                }
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(convertOptions: ConvertOptions?): ConvertResultViewModel
    }

}