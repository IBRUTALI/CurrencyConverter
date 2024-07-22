package com.ighorosipov.currencyconverter.features.converter.presentation.screens.select_currency

import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.ighorosipov.currencyconverter.R
import com.ighorosipov.currencyconverter.databinding.FragmentSelectCurrencyBinding
import com.ighorosipov.currencyconverter.features.converter.domain.model.ConvertOptions
import com.ighorosipov.currencyconverter.features.converter.domain.model.Currency
import com.ighorosipov.currencyconverter.features.converter.domain.model.FromToCurrencies
import com.ighorosipov.currencyconverter.features.converter.presentation.BaseEvent
import com.ighorosipov.currencyconverter.features.converter.presentation.ValidationEvent
import com.ighorosipov.currencyconverter.features.converter.presentation.screens.select_currency.adapter.CurrencyAdapter
import com.ighorosipov.currencyconverter.utils.base.BaseFragment
import com.ighorosipov.currencyconverter.utils.base.showToast
import com.ighorosipov.currencyconverter.utils.di.appComponent
import com.ighorosipov.currencyconverter.utils.di.lazyViewModel
import com.ighorosipov.currencyconverter.utils.error.ConvertError
import com.ighorosipov.currencyconverter.utils.error.DataError
import com.ighorosipov.currencyconverter.utils.error.Error
import kotlinx.coroutines.launch

class SelectCurrencyFragment : BaseFragment<FragmentSelectCurrencyBinding, SelectCurrencyViewModel>(
    FragmentSelectCurrencyBinding::inflate
) {

    private lateinit var startCurrencyAdapter: CurrencyAdapter
    private lateinit var endCurrencyAdapter: CurrencyAdapter

    override val viewModel: SelectCurrencyViewModel by lazyViewModel {
        requireContext().appComponent().selectCurrencyViewModel().create()
    }

    override fun inject() {
        requireContext().appComponent().inject(this)
    }

    override fun initViews() {
        initAdapters()
        onCurrencySpinnerClick()
        retryConnectionBtn()
        onCalculateButtonClick()
        onChangeTextFieldValue()
    }

    override fun subscribeToObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.events.collect { event ->
                    changeUIVisibility(event)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.currencies.collect { currencies ->
                    setListInAdapters(items = currencies)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.fromCurrency.collect { currency ->
                    binding.startCurrencySpinner.setSelection(
                        startCurrencyAdapter.getPosition(
                            currency
                        )
                    )
                    viewModel.onEvent(
                        SelectCurrencyFragmentEvent.ValidateFromToValues(
                            fromToCurrencies = FromToCurrencies(
                                from = currency,
                                to = viewModel.toCurrency.value
                            )
                        )
                    )
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.toCurrency.collect { currency ->
                    binding.endCurrencySpinner.setSelection(endCurrencyAdapter.getPosition(currency))
                    viewModel.onEvent(
                        SelectCurrencyFragmentEvent.ValidateFromToValues(
                            fromToCurrencies = FromToCurrencies(
                                from = viewModel.fromCurrency.value,
                                to = currency
                            )
                        )
                    )
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.validationEvents.collect { event ->
                    when (event) {
                        ValidationEvent.Success -> {
                            binding.calculateAmount.isEnabled = true
                        }
                    }
                }
            }
        }

    }

    private fun setupErrorEvent(error: Error) {
        when (error) {
            DataError.Local.DISK_FULL -> {
                showToast(getString(R.string.not_enough_disk_space))
            }

            DataError.Local.UNKNOWN -> {
                showToast(getString(R.string.an_unknown_error_has_occurred))
            }

            DataError.Network.REQUEST_TIMEOUT -> {
                showToast(getString(R.string.waiting_limit_exceeded))
            }

            DataError.Network.TOO_MANY_REQUESTS -> {
                showToast(getString(R.string.request_limit_exceeded))
            }

            DataError.Network.NO_INTERNET -> {
                showToast(getString(R.string.no_internet))
            }

            DataError.Network.PAYLOAD_TO_LARGE -> {
                showToast(getString(R.string.the_request_object_exceeds_the_limits_defined_by_the_server))
            }

            DataError.Network.SERVER_ERROR -> {
                showToast(getString(R.string.server_error))
            }

            DataError.Network.SERIALIZATION -> {
                showToast(getString(R.string.serialization_error))
            }

            DataError.Network.UNKNOWN -> {
                showToast(getString(R.string.an_unknown_error_has_occurred))
            }

            ConvertError.NO_AMOUNT_VALUE -> {}
            ConvertError.FROM_TO_VALUES_IS_SAME -> {}
        }
    }

    private fun setListInAdapters(items: List<Currency>) {
        startCurrencyAdapter.addAll(items)
        endCurrencyAdapter.addAll(items)
    }

    private fun changeUIVisibility(event: BaseEvent) {
        when (event) {
            is BaseEvent.Error -> {
                when (event.error) {
                    is DataError.Network -> {
                        binding.apply {
                            baseGroup.visibility = View.VISIBLE
                            retryBtn.visibility = View.GONE
                            loadingBar.visibility = View.GONE
                        }
                    }

                    is DataError.Local -> {
                        binding.apply {
                            baseGroup.visibility = View.GONE
                            retryBtn.visibility = View.VISIBLE
                            loadingBar.visibility = View.GONE
                        }
                    }

                    ConvertError.NO_AMOUNT_VALUE -> {
                        binding.calculateAmount.isEnabled = false
                    }

                    ConvertError.FROM_TO_VALUES_IS_SAME -> {
                        binding.calculateAmount.isEnabled = false
                    }
                }
                setupErrorEvent(event.error)
            }

            is BaseEvent.Loading -> {
                binding.apply {
                    baseGroup.visibility = View.GONE
                    retryBtn.visibility = View.GONE
                    loadingBar.visibility = View.VISIBLE
                }
            }

            is BaseEvent.Success -> {
                binding.apply {
                    baseGroup.visibility = View.VISIBLE
                    retryBtn.visibility = View.GONE
                    loadingBar.visibility = View.GONE
                }
            }
        }
    }

    private fun onCalculateButtonClick() {
        binding.calculateAmount.setOnClickListener {
            val bundle = bundleOf()
            bundle.putSerializable(
                KEY_CONVERT_OPTIONS, ConvertOptions(
                    fromToCurrencies = FromToCurrencies(
                        from = viewModel.fromCurrency.value,
                        to = viewModel.toCurrency.value,
                    ), amount = viewModel.amountValue.value.toDouble()
                )
            )
            findNavController().navigate(
                R.id.action_selectCurrencyFragment_to_convertResultFragment,
                bundle
            )
        }
    }

    private fun onChangeTextFieldValue() {
        binding.titleInputLayout.editText?.doAfterTextChanged {
            viewModel.onEvent(SelectCurrencyFragmentEvent.ValidateAmountValue(it.toString()))
            viewModel.onEvent(SelectCurrencyFragmentEvent.ChangeAmountValue(it.toString()))
        }
    }

    private fun onCurrencySpinnerClick() {
        binding.startCurrencySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    val currency = parent?.getItemAtPosition(position) as Currency
                    viewModel.onEvent(SelectCurrencyFragmentEvent.SelectCurrencyFrom(currency))
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

        binding.endCurrencySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    val currency = parent?.getItemAtPosition(position) as Currency
                    viewModel.onEvent(SelectCurrencyFragmentEvent.SelectCurrencyTo(currency))
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}

            }
    }

    private fun retryConnectionBtn() {
        binding.retryBtn.setOnClickListener {
            viewModel.onEvent(SelectCurrencyFragmentEvent.RepeatConnection)
        }
    }

    private fun initAdapters() {
        startCurrencyAdapter =
            CurrencyAdapter(requireContext(), R.layout.item_spinner, mutableListOf())
        endCurrencyAdapter =
            CurrencyAdapter(requireContext(), R.layout.item_spinner, mutableListOf())
        startCurrencyAdapter.setDropDownViewResource(R.layout.item_spinner)
        endCurrencyAdapter.setDropDownViewResource(R.layout.item_spinner)
        binding.startCurrencySpinner.adapter = startCurrencyAdapter
        binding.endCurrencySpinner.adapter = startCurrencyAdapter
    }

    override fun onResume() {
        super.onResume()
        binding.titleInputLayout.editText?.setText(
            viewModel.amountValue.value,
            TextView.BufferType.EDITABLE
        )
    }

    companion object {
        const val KEY_CONVERT_OPTIONS = "KEY_CONVERT_OPTIONS"
    }

}