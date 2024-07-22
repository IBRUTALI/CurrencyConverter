package com.ighorosipov.currencyconverter.features.converter.presentation.screens.convert_result

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ighorosipov.currencyconverter.R
import com.ighorosipov.currencyconverter.databinding.FragmentConvertResultBinding
import com.ighorosipov.currencyconverter.features.converter.domain.model.ConvertOptions
import com.ighorosipov.currencyconverter.features.converter.domain.model.CurrencyConvertDetail
import com.ighorosipov.currencyconverter.features.converter.presentation.BaseEvent
import com.ighorosipov.currencyconverter.features.converter.presentation.screens.select_currency.SelectCurrencyFragment.Companion.KEY_CONVERT_OPTIONS
import com.ighorosipov.currencyconverter.utils.base.BaseFragment
import com.ighorosipov.currencyconverter.utils.base.showToast
import com.ighorosipov.currencyconverter.utils.di.appComponent
import com.ighorosipov.currencyconverter.utils.di.lazyViewModel
import com.ighorosipov.currencyconverter.utils.error.DataError
import kotlinx.coroutines.launch

class ConvertResultFragment : BaseFragment<FragmentConvertResultBinding, ConvertResultViewModel>(
    FragmentConvertResultBinding::inflate
) {
    override val viewModel: ConvertResultViewModel by lazyViewModel {
        requireContext().appComponent().convertResultViewModel().create(getBundle())
    }

    override fun inject() {
        requireContext().appComponent().inject(this)
    }

    override fun initViews() {
        retryConnectionBtn()
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
                viewModel.convertResult.collect { result ->
                    result?.let { updateUI(result) }
                }
            }
        }
    }

    private fun changeUIVisibility(event: BaseEvent) {
        when (event) {
            is BaseEvent.Error -> {
                when (event.error) {
                    is DataError.Network -> {
                        binding.apply {
                            baseGroup.visibility = View.GONE
                            retryBtn.visibility = View.VISIBLE
                            loadingBar.visibility = View.GONE
                        }
                        setupErrorEvent(event.error)
                    }
                    else -> {}
                }
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

    private fun setupErrorEvent(error: DataError.Network) {
        when (error) {
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
        }
    }

    private fun updateUI(convertDetail: CurrencyConvertDetail) {
        binding.apply {
            "${convertDetail.amount} ${convertDetail.baseCurrencyCode}".apply {
                amountFrom.text = this
            }
            "${convertDetail.rates.rateForAmount} ${convertDetail.rates.code}".apply {
                amountTo.text = this
            }
        }
    }

    private fun retryConnectionBtn() {
        binding.retryBtn.setOnClickListener {
            viewModel.onEvent(ConvertResultFragmentEvent.RepeatConnection)
        }
    }

    private fun getBundle(): ConvertOptions? {
        return arguments?.getSerializable(KEY_CONVERT_OPTIONS) as ConvertOptions?
    }
}