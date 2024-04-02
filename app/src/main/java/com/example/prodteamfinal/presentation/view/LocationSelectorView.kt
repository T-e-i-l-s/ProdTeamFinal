package com.example.prodteamfinal.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prodteamfinal.R
import com.example.prodteamfinal.presentation.theme.textFieldColors

@Composable
fun LocationSelectorView(
    modifier: Modifier,
    locationString: MutableState<String>,
    isValid: MutableState<Boolean>
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Назначьте место встречи",
            fontSize = 22.sp,
            fontWeight = FontWeight(600),
            fontFamily = FontFamily(Font(R.font.roboto)),
            color = colorResource(id = R.color.text),
        )
        if (!isValid.value) {
            Text(
                text = "Неверные данные",
                fontSize = 19.sp,
                fontWeight = FontWeight(500),
                fontFamily = FontFamily(Font(R.font.roboto)),
                color = colorResource(id = R.color.error),
                modifier = Modifier.padding(top = 10.dp).fillMaxWidth(),
                textAlign = TextAlign.Left
            )
        }
        TextField(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth(),
            value = locationString.value,
            onValueChange = {
                locationString.value = it
                isValid.value = true
            },
            label = {
                Text(
                    text = "Место встречи",
                    fontFamily = FontFamily(Font(R.font.roboto))
                )
            },
            colors = textFieldColors(),
            shape = RoundedCornerShape(16.dp),
        )
    }
}