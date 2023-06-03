package com.example.films.di

import com.example.films.domain.api.ResourceProvider
import com.example.films.domain.impl.ResourceProviderImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val resourceModule = module {

    single<ResourceProvider> {
        ResourceProviderImpl(androidContext())
    }

}