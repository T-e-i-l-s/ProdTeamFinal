package com.example.prodteamfinal.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prodteamfinal.R
import com.example.prodteamfinal.domain.model.FormModel
import com.example.prodteamfinal.domain.state.FormType
import java.sql.Time
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetTime
import java.time.format.DateTimeFormatter

@Composable
fun FormView(
    modifier: Modifier,
    item: FormModel
) {
    val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    val localTime = LocalDateTime.parse(item.time, formatter)
    val time = localTime.toLocalTime()
    val date = localTime.toLocalDate()
    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    colorResource(id = R.color.main),
                    RoundedCornerShape(16.dp)
                )
                .padding(10.dp)
        ) {
            Text(
                text = "Заявка на " + when (item.type) {
                    FormType.ИП -> "ИП"
                    else -> "ООО"
                },
                fontSize = 19.sp,
                fontWeight = FontWeight(700),
                color = colorResource(id = R.color.text)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.location_icon),
                    contentDescription = "Локация"
                )
                Text(
                    text = item.location,
                    fontSize = 16.sp,
                    fontWeight = FontWeight(500),
                    color = colorResource(id = R.color.text)
                )
            }
            Text(
                text = "" + "%02d".format(date.dayOfMonth) + "." +
                        "%02d".format(date.monthValue) + "." +
                        date.year + " в " +  time.hour + ":" +  "%02d".format(time.minute),
                fontSize = 16.sp,
                fontWeight = FontWeight(500),
                color = colorResource(id = R.color.text)
            )
            Text(
                text = "" + item.participants.size +
                        when (item.participants.size) {
                            1 -> " участник"
                            2 -> " участника"
                            3 -> " участника"
                            4 -> " участника"
                            else -> " участников"
                        },
                fontSize = 16.sp,
                fontWeight = FontWeight(500),
                color = colorResource(id = R.color.text)
            )
            Text(
                text = "Представитель",
                fontSize = 19.sp,
                fontWeight = FontWeight(700),
                color = colorResource(id = R.color.text)
            )
            ExecutorView(
                modifier = Modifier.padding(top = 10.dp),
                executor = item.executor
            )
        }
    }
}