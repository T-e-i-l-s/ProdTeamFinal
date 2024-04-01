package com.example.prodteamfinal.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prodteamfinal.R
import com.example.prodteamfinal.domain.model.ParticipantModel
import com.example.prodteamfinal.presentation.theme.textFieldColors

@Composable
fun ParticipantsSelectorView(
    modifier: Modifier,
    participants: MutableState<ArrayList<ParticipantModel>>
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Укажите участников встречи",
            fontSize = 22.sp,
            fontWeight = FontWeight(600),
            fontFamily = FontFamily(Font(R.font.roboto)),
            color = colorResource(id = R.color.text),
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            itemsIndexed(participants.value) { index, item ->
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Участник ${index + 1}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight(500),
                            fontFamily = FontFamily(Font(R.font.roboto)),
                            color = colorResource(id = R.color.text),
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Left
                        )
                        if (index > 0) {
                            Image(
                                painter = painterResource(id = R.drawable.xmark_icon),
                                contentDescription = "Удалить",
                                modifier = Modifier
                                    .width(28.dp)
                                    .height(28.dp)
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null,
                                    ) {
                                        val arr = ArrayList<ParticipantModel>()
                                        arr.addAll(participants.value)
                                        arr.removeAt(index)
                                        participants.value = arr
                                    }
                            )
                        }
                    }
                    Divider(color = colorResource(id = R.color.gray))
                    TextField(
                        modifier = Modifier
                            .padding(vertical = 10.dp)
                            .fillMaxWidth(),
                        value = item.name,
                        onValueChange = {
                            val arr = ArrayList<ParticipantModel>()
                            arr.addAll(participants.value)
                            arr[index] = ParticipantModel(
                                it,
                                item.phoneNum
                            )
                            participants.value = arr
                        },
                        label = {
                            Text(text = "Имя", fontFamily = FontFamily(Font(R.font.roboto)))
                        },
                        colors = textFieldColors(),
                        shape = RoundedCornerShape(16.dp),
                    )
                    TextField(
                        modifier = Modifier
                            .padding(bottom = 10.dp)
                            .fillMaxWidth(),
                        value = item.phoneNum,
                        onValueChange = {
                            val arr = ArrayList<ParticipantModel>()
                            arr.addAll(participants.value)
                            arr[index] = ParticipantModel(
                                item.name,
                                it
                            )
                            participants.value = arr
                        },
                        label = {
                            Text(
                                text = "Номер телефона",
                                fontFamily = FontFamily(Font(R.font.roboto))
                            )
                        },
                        colors = textFieldColors(),
                        shape = RoundedCornerShape(16.dp),
                    )

                    Divider(color = colorResource(id = R.color.gray))

                }
            }
            if (participants.value.size < 5) {
                item {
                    Image(
                        painter = painterResource(id = R.drawable.plus_icon),
                        contentDescription = "Закрыть",
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                            ) {
                                val arr = ArrayList<ParticipantModel>()
                                arr.addAll(participants.value)
                                arr.add(
                                    ParticipantModel(
                                        "",
                                        ""
                                    )
                                )
                                participants.value = arr
                            }
                    )
                }
            }
        }
    }
}