package com.example.films.domain.impl

import android.content.Context
import com.example.films.domain.api.ResourceProvider

class ResourceProviderImpl(private val context: Context): ResourceProvider {
    override fun getString(strRres: Int): String {
        return context.getString(strRres)
    }

}