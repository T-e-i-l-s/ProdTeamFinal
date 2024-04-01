package com.example.prodteamfinal.presentation.theme

import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import com.example.prodteamfinal.R

@Composable
fun blackButtonColors(): ButtonColors = ButtonDefaults.buttonColors(
    colorResource(id = R.color.text),
    colorResource(id = R.color.background),
    colorResource(id = R.color.background),
    colorResource(id = R.color.background)
)