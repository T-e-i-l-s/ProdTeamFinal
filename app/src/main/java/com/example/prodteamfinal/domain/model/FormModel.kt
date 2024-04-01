package com.example.prodteamfinal.domain.model

import com.example.prodteamfinal.domain.state.FormState
import com.example.prodteamfinal.domain.state.FormType

data class FormModel(
    val id: String,
    val status: FormState,
    val type: FormType,
    val location: String,
    val executor: ExecutorModel,
    val time: String,
    val documents: ArrayList<String>,
    val participants: ArrayList<ParticipantModel>
)
