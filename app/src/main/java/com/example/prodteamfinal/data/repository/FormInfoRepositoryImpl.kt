package com.example.prodteamfinal.data.repository

import android.content.Context
import com.example.prodteamfinal.data.api.FormInfoApi
import com.example.prodteamfinal.domain.model.ExecutorModel
import com.example.prodteamfinal.domain.model.FormModel
import com.example.prodteamfinal.domain.state.FormState
import com.example.prodteamfinal.domain.state.FormType
import kotlinx.coroutines.delay
import java.util.Timer
import java.util.logging.Handler
import kotlin.concurrent.timerTask

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