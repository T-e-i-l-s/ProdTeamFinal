package com.example.prodteamfinal.presentation.theme

import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.example.prodteamfinal.R

@Composable
fun textFieldColors() : TextFieldColors =
    TextFieldDefaults.colors(
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        focusedContainerColor = colorResource(id = R.color.main),
        unfocusedContainerColor = colorResource(id = R.color.main),
        cursorColor = colorResource(id = R.color.additional1),
        focusedLabelColor = colorResource(id = R.color.text),
        focusedTextColor = colorResource(id = R.color.text),
    )