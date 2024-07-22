package com.ighorosipov.currencyconverter

import android.app.Application
import com.ighorosipov.currencyconverter.di.AppComponent
import com.ighorosipov.currencyconverter.di.DaggerAppComponent

class App: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
    }

}