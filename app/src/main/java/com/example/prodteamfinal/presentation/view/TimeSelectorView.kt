package com.example.prodteamfinal.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeSelectorView(modifier: Modifier, state: TimePickerState) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Назначьте время встречи",
            fontSize = 22.sp,
            fontWeight = FontWeight(600),
            fontFamily = FontFamily(Font(R.font.roboto)),
            color = colorResource(id = R.color.text),
            modifier = Modifier.padding(bottom = 10.dp)
        )
        TimePicker(
            state = state,
            layoutType = TimePickerLayoutType.Vertical,
            colors = TimePickerDefaults.colors(
                clockDialColor = colorResource(id = R.color.main),
                selectorColor = colorResource(id = R.color.additional1),
                timeSelectorSelectedContainerColor = colorResource(id = R.color.main),
                periodSelectorSelectedContainerColor = colorResource(id = R.color.main),
                timeSelectorUnselectedContainerColor = colorResource(id = R.color.main),
                timeSelectorSelectedContentColor = colorResource(id = R.color.text),
                timeSelectorUnselectedContentColor = colorResource(id = R.color.text)
            )
        )
    }
}