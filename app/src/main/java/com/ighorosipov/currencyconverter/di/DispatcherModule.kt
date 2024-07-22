package com.ighorosipov.currencyconverter.di

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
interface DispatcherModule {

    companion object {

        @DefaultDispatcher
        @Provides
        @Singleton
        fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

        @IODispatcher
        @Provides
        @Singleton
        fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

        @MainDispatcher
        @Provides
        @Singleton
        fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    }

}