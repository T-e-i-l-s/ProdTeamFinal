package com.example.prodteamfinal.data.repository

import android.content.Context
import com.example.prodteamfinal.data.api.FormsApi
import com.example.prodteamfinal.domain.model.FormModel

class FormsRepositoryImpl {
    fun getForms(
        context: Context,
        onFinish: (result: ArrayList<FormModel>) -> Unit,
        onError: () -> Unit
    ) {
        FormsApi().getForms(context, onFinish, onError)
    }
}