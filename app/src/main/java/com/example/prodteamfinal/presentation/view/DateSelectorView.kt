package com.example.prodteamfinal.presentation.view

import android.widget.CalendarView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.prodteamfinal.R
import java.util.Calendar

@Composable
fun DateSelectorView(modifier: Modifier, value: MutableState<String>) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Назначьте дату встречи",
            fontSize = 22.sp,
            fontWeight = FontWeight(600),
            fontFamily = FontFamily(Font(R.font.roboto)),
            color = colorResource(id = R.color.text),
            modifier = Modifier.padding(bottom = 10.dp)
        )
        AndroidView(
            {
                CalendarView(it).apply {
                    val date = value.value.split('.')
                    val calendar = Calendar.getInstance()
                    calendar.set(Calendar.YEAR, date[2].toInt())
                    calendar.set(Calendar.MONTH, 3)
                    calendar.set(Calendar.DAY_OF_MONTH, date[0].toInt())
                    setDate(calendar.timeInMillis)
                    val min = Calendar.getInstance()
                    val max = Calendar.getInstance()
                    max.set(Calendar.YEAR, 2024)
                    max.set(Calendar.MONTH, 3)
                    max.set(Calendar.DAY_OF_MONTH, 17)
                    minDate = min.timeInMillis
                    maxDate = max.timeInMillis
                }
            },
            modifier = Modifier.wrapContentWidth(),
            update = { views ->
                views.setOnDateChangeListener { _, year, month, dayOfMonth ->
                    value.value = "${String.format("%02d", dayOfMonth)}." +
                            "${String.format("%02d", month)}." +
                            "$year"
                }
            },
        )
    }
}