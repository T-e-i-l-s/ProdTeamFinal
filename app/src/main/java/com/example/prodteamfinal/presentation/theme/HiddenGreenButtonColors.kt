package com.example.prodteamfinal.presentation.theme

import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import com.example.prodteamfinal.R

@Composable
fun hiddenGreenButtonColors(): ButtonColors = ButtonDefaults.buttonColors(
    colorResource(id = R.color.additional1).copy(alpha = 0.6f),
    colorResource(id = R.color.background).copy(alpha = 0.6f),
    colorResource(id = R.color.text).copy(alpha = 0.6f),
    colorResource(id = R.color.text).copy(alpha = 0.6f)
)