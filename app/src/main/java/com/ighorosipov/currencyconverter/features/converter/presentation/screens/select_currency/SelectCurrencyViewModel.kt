package com.ighorosipov.currencyconverter.features.converter.presentation.screens.select_currency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ighorosipov.currencyconverter.di.IODispatcher
import com.ighorosipov.currencyconverter.features.converter.domain.model.Currency
import com.ighorosipov.currencyconverter.features.converter.domain.use_case.GetCurrenciesUseCase
import com.ighorosipov.currencyconverter.features.converter.domain.use_case.ValidateAmountValueUseCase
import com.ighorosipov.currencyconverter.features.converter.domain.use_case.ValidateFromToValuesUseCase
import com.ighorosipov.currencyconverter.features.converter.presentation.BaseEvent
import com.ighorosipov.currencyconverter.features.converter.presentation.ValidationEvent
import com.ighorosipov.currencyconverter.utils.Resource
import com.ighorosipov.currencyconverter.utils.error.ConvertError
import com.ighorosipov.currencyconverter.utils.error.DataError
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SelectCurrencyViewModel @AssistedInject constructor(
    private val getCurrenciesUseCase: GetCurrenciesUseCase,
    private val validateAmountValueUseCase: ValidateAmountValueUseCase,
    private val validateFromToValuesUseCase: ValidateFromToValuesUseCase,
    @IODispatcher
    private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val eventChannel = Channel<BaseEvent>(0)
    val events = eventChannel.receiveAsFlow()

    private val _currencies = MutableStateFlow<List<Currency>>(emptyList())
    val currencies: StateFlow<List<Currency>> = _currencies

    private val validationEventChannel = Channel<ValidationEvent>(0)
    val validationEvents = validationEventChannel.receiveAsFlow()

    private val _amountValue = MutableStateFlow("")
    val amountValue: StateFlow<String> = _amountValue

    private val _fromCurrency = MutableStateFlow(Currency(code = "USD", name = ""))
    val fromCurrency: StateFlow<Currency> = _fromCurrency

    private val _toCurrency = MutableStateFlow(Currency(code = "RUB", name = ""))
    val toCurrency: StateFlow<Currency> = _toCurrency

    private val validationErrors = mutableSetOf<ConvertError>()

    init {
        getCurrencies()
    }

    fun onEvent(event: SelectCurrencyFragmentEvent) {
        when (event) {
            is SelectCurrencyFragmentEvent.SelectCurrencyFrom -> {
                _fromCurrency.value = event.currency
            }

            is SelectCurrencyFragmentEvent.SelectCurrencyTo -> {
                _toCurrency.value = event.currency
            }

            is SelectCurrencyFragmentEvent.RepeatConnection -> {
                getCurrencies()
            }

            is SelectCurrencyFragmentEvent.ValidateAmountValue -> {
                when (val resource = validateAmountValueUseCase(event.amountValue)) {
                    is Resource.Error -> {
                        validationErrors.add(resource.error)
                        viewModelScope.launch {
                            eventChannel.send(BaseEvent.Error(resource.error))
                        }
                    }

                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        validationErrors.remove(ConvertError.NO_AMOUNT_VALUE)
                        validateAll()
                    }
                }
            }

            is SelectCurrencyFragmentEvent.ValidateFromToValues -> {
                when (val resource = validateFromToValuesUseCase(event.fromToCurrencies)) {
                    is Resource.Error -> {
                        validationErrors.add(resource.error)
                        viewModelScope.launch {
                            eventChannel.send(BaseEvent.Error(resource.error))
                        }
                    }

                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        validationErrors.remove(ConvertError.FROM_TO_VALUES_IS_SAME)
                        validateAll()
                    }
                }
            }

            is SelectCurrencyFragmentEvent.ChangeAmountValue -> {
                _amountValue.value = event.value
            }
        }
    }

    private fun getCurrencies() {
        viewModelScope.launch(dispatcher) {
            getCurrenciesUseCase().collect { resource ->
                when (resource) {
                    is Resource.Error -> {
                        if (resource.error is DataError.Network) {
                            resource.data?.let { _currencies.emit(it) }
                        }
                        eventChannel.send(BaseEvent.Error(resource.error))
                    }

                    is Resource.Loading -> {
                        eventChannel.send(BaseEvent.Loading)
                    }

                    is Resource.Success -> {
                        eventChannel.send(BaseEvent.Success)
                        _currencies.emit(resource.data)
                    }
                }
            }
        }
    }

    private fun validateAll() {
        if (validationErrors.isEmpty()) {
            viewModelScope.launch {
                validationEventChannel.send(ValidationEvent.Success)
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(): SelectCurrencyViewModel
    }

}