package com.ighorosipov.currencyconverter.di

import android.app.Application
import com.ighorosipov.currencyconverter.MainActivity
import com.ighorosipov.currencyconverter.features.converter.presentation.screens.convert_result.ConvertResultFragment
import com.ighorosipov.currencyconverter.features.converter.presentation.screens.convert_result.ConvertResultViewModel
import com.ighorosipov.currencyconverter.features.converter.presentation.screens.select_currency.SelectCurrencyFragment
import com.ighorosipov.currencyconverter.features.converter.presentation.screens.select_currency.SelectCurrencyViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        DataModule::class,
        DispatcherModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        UseCaseModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent

    }


    fun inject(mainActivity: MainActivity)
    fun inject(selectCurrencyFragment: SelectCurrencyFragment)
    fun inject(convertResultFragment: ConvertResultFragment)

    fun selectCurrencyViewModel(): SelectCurrencyViewModel.Factory
    fun convertResultViewModel(): ConvertResultViewModel.Factory

}