package com.example.films.di

import com.example.films.data.NetworkClient
import com.example.films.data.network.RetrofitNetworkClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {

    single<NetworkClient> {
        RetrofitNetworkClient(androidContext())
    }





}