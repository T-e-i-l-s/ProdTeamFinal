package com.example.prodteamfinal.data.repository

import android.content.Context
import com.example.prodteamfinal.data.api.NominatimApi
import com.example.prodteamfinal.domain.model.LocationModel

class NominatimRepositoryImpl {
    fun getValidAddress(
        context: Context,
        request: String,
        onFinish: (result: LocationModel) -> Unit
    ) {
        return NominatimApi().getValidAddress(context, request, onFinish)
    }
}