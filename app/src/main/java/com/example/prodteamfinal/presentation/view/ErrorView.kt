package com.example.prodteamfinal.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.prodteamfinal.R

@Composable
fun ErrorView (modifier: Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.lotti6),
            contentDescription = "Злой лотти"
        )
        Text(
            text = "Не удалось получить список заявок",
            fontSize = 19.sp,
            fontWeight = FontWeight(600),
            fontFamily = FontFamily(Font(R.font.roboto)),
            color = colorResource(id = R.color.text),
            textAlign = TextAlign.Center
        )
    }
}