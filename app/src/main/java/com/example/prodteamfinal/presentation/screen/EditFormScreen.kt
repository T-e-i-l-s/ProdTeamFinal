package com.example.prodteamfinal.presentation.screen

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.prodteamfinal.R
import com.example.prodteamfinal.data.api.FormInfoApi
import com.example.prodteamfinal.data.repository.NominatimRepositoryImpl
import com.example.prodteamfinal.domain.model.LocationModel
import com.example.prodteamfinal.domain.model.ParticipantModel
import com.example.prodteamfinal.navigation.currentForm
import com.example.prodteamfinal.navigation.currentScreen
import com.example.prodteamfinal.phoneNumber
import com.example.prodteamfinal.presentation.theme.greenButtonColors
import com.example.prodteamfinal.presentation.view.DateSelectorView
import com.example.prodteamfinal.presentation.view.LocationSelectorView
import com.example.prodteamfinal.presentation.view.ParticipantsSelectorView
import com.example.prodteamfinal.presentation.view.TimeSelectorView
import com.example.prodteamfinal.username
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditFormScreen(context: Context, navController: NavController) {
    val participants = remember {
        mutableStateOf(
            arrayListOf(
                ParticipantModel(
                    username,
                    phoneNumber
                )
            )
        )
    }
    val id = remember {
        mutableStateOf("")
    }
    val time = rememberTimePickerState(0, 0, true)
    val date = remember {
        mutableStateOf("03.04.2024")
    }
    val location = remember {
        mutableStateOf("")
    }
    val fullLocation = remember {
        mutableStateOf(LocationModel("", "", ""))
    }
    val locationIsValid = remember {
        mutableStateOf(true)
    }
    val screen = remember {
        mutableIntStateOf(0)
    }
    val isChecking = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(true) {
        val formInfo = currentForm.value
        participants.value = formInfo.participants
        val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        val local = LocalDateTime.parse(formInfo.time, formatter)
        val localDate = local.toLocalDate()
        id.value = formInfo.id
        date.value = "${localDate.dayOfMonth}.${localDate.monthValue}.${localDate.year}"
        location.value = formInfo.location
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background))
            .padding(horizontal = 10.dp)
    ) {
        if (!isChecking.value) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Start)
                    .padding(vertical = 10.dp),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.arrow_left_dark_icon),
                    contentDescription = "Выйти",
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                    ) {
                        if (screen.intValue == 0) {
                            if (currentScreen == "edit_form_screen") {
                                navController.popBackStack()
                            }
                        } else if (screen.intValue == 5) {
                            screen.intValue -= 2
                        } else {
                            screen.intValue--
                        }
                    }
                )
            }
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            when (screen.intValue) {
                0 -> {
                    ParticipantsSelectorView(
                        modifier = Modifier.fillMaxWidth(),
                        participants = participants
                    )
                }

                1 -> {
                    DateSelectorView(
                        modifier = Modifier.fillMaxWidth(),
                        date
                    )
                }

                2 -> {
                    TimeSelectorView(
                        modifier = Modifier.fillMaxWidth(),
                        time
                    )
                }

                3 -> {
                    LocationSelectorView(
                        modifier = Modifier.fillMaxWidth(),
                        location,
                        locationIsValid
                    )
                }

                else -> {
                    isChecking.value = true
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .width(40.dp)
                                .height(40.dp),
                            color = colorResource(id = R.color.additional1),
                            trackColor = colorResource(id = R.color.background),
                            strokeWidth = 4.dp
                        )
                        Text(
                            modifier = Modifier.padding(top = 10.dp),
                            text = "Проверяем данные",
                            fontSize = 19.sp,
                            fontFamily = FontFamily(Font(R.font.roboto)),
                        )
                    }
                }
            }

        }

        if (!isChecking.value) {
            Button(
                onClick = {
                    screen.intValue++
                    if (screen.intValue == 4) {
                        isChecking.value = true
                        NominatimRepositoryImpl().getValidAddress(
                            context,
                            location.value
                        ) {
                            if (it.name.isEmpty() || it.lat.isEmpty() || it.lon.isEmpty()) {
                                locationIsValid.value = false
                                isChecking.value = false
                                screen.intValue--
                            } else {
                                fullLocation.value = it
                                val splitedDate = date.value.split('.')
                                val dateTime = LocalDateTime.of(
                                    splitedDate[2].toInt(),
                                    splitedDate[1].toInt() + 1,
                                    splitedDate[0].toInt(),
                                    time.hour,
                                    time.minute,
                                    0,
                                )
                                val formatter = DateTimeFormatter.ISO_DATE_TIME
                                FormInfoApi().patchFormInfo(
                                    context,
                                    id.value,
                                    dateTime.format(formatter),
                                    fullLocation.value.name,
                                    fullLocation.value.lat,
                                    fullLocation.value.lon,
                                    participants
                                )

                                navController.navigate("create_form_result_screen/true")
                            }
                        }
                    }
                },
                colors = greenButtonColors(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
                    .padding(start = 5.dp)
            ) {
                Text(
                    modifier = Modifier
                        .padding(5.dp),
                    text = "Далее",
                    fontSize = 19.sp,
                    fontWeight = FontWeight(500),
                    fontFamily = FontFamily(Font(R.font.roboto)),
                )
            }
        }
    }
}
