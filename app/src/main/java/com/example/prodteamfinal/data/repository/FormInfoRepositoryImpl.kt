package com.example.prodteamfinal.data.repository

import android.content.Context
import com.example.prodteamfinal.data.api.FormInfoApi
import com.example.prodteamfinal.domain.model.FormModel

class FormInfoRepositoryImpl {
    fun getFormInfo(
        context: Context,
        id: String,
        onFinish: (result: FormModel) -> Unit,
        onError: () -> Unit
    ) {
       FormInfoApi().getFormInfo(context, id, onFinish, onError)
    }
}