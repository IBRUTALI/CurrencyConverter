package com.ighorosipov.currencyconverter.di

import com.ighorosipov.currencyconverter.BuildConfig
import com.ighorosipov.currencyconverter.features.converter.data.dto.CurrencyApi
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
interface NetworkModule {

    companion object {

        @Provides
        @Singleton
        fun provideCurrencyService(okHttpClient: OkHttpClient): CurrencyApi {
            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create()
        }

        @Provides
        @Singleton
        fun provideOkhttpClient(): OkHttpClient {
            val client = OkHttpClient.Builder()
            return client.build()
        }

    }

}