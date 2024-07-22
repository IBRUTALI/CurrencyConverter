package com.ighorosipov.currencyconverter.utils.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

/** Класс, созданный специально для фрагментов, чтобы избежать бойлерплейта
 *  Упрощает работу с [ViewBinding] и [ViewModel] во фрагментах
 */

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB : ViewBinding, VM : ViewModel>(
    private val inflate: Inflate<VB>,
) : Fragment() {

    private var _binding: VB? = null
    protected val binding: VB get() = _binding!!

    abstract val viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        inject()
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        subscribeToObservers()
    }

    abstract fun inject()

    abstract fun initViews()

    abstract fun subscribeToObservers()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}