package com.example.films.data.App

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import com.example.films.di.dataModule
import com.example.films.di.interactorModule
import com.example.films.di.repositoryModule
import com.example.films.di.resourceModule
import com.example.films.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App:Application(){

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@App)
            modules(dataModule,
                repositoryModule,
                interactorModule,
                viewModelModule,
                resourceModule
            )
        }
    }
}