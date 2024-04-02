package com.example.prodteamfinal.presentation.screen

import android.app.AlertDialog
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
import com.example.prodteamfinal.data.api.AgentsApi
import com.example.prodteamfinal.data.api.FormsApi
import com.example.prodteamfinal.data.repository.NominatimRepositoryImpl
import com.example.prodteamfinal.domain.model.FullExecutorModel
import com.example.prodteamfinal.domain.model.LocationModel
import com.example.prodteamfinal.domain.model.ParticipantModel
import com.example.prodteamfinal.domain.state.LoadingState
import com.example.prodteamfinal.navigation.currentScreen
import com.example.prodteamfinal.phoneNumber
import com.example.prodteamfinal.presentation.theme.greenButtonColors
import com.example.prodteamfinal.presentation.view.DateSelectorView
import com.example.prodteamfinal.presentation.view.ExecutorSelectorView
import com.example.prodteamfinal.presentation.view.LocationSelectorView
import com.example.prodteamfinal.presentation.view.ParticipantsSelectorView
import com.example.prodteamfinal.presentation.view.TimeSelectorView
import com.example.prodteamfinal.username
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateFormScreen(context: Context, navController: NavController) {
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
    val date = remember {
        mutableStateOf("01.04.2024")
    }
    val time = rememberTimePickerState(0, 0, true)
    val location = remember {
        mutableStateOf("")
    }
    val fullLocation = remember {
        mutableStateOf(LocationModel("", "", ""))
    }
    val locationIsValid = remember {
        mutableStateOf(true)
    }
    val executorsLoadingStatus = remember {
        mutableStateOf(LoadingState.LOADING)
    }
    val executors = remember {
        mutableStateOf(ArrayList<FullExecutorModel>())
    }
    val executorId = remember {
        mutableIntStateOf(0)
    }
    val screen = remember {
        mutableIntStateOf(0)
    }
    val isChecking = remember {
        mutableStateOf(false)
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
                            if (currentScreen == "create_form_screen") {
                                navController.popBackStack()
                            }
                        } else if (screen.intValue == 5) {
                            screen.intValue-=2
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

                4 -> {
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

                else -> {
                    ExecutorSelectorView(
                        executors = executors,
                        executorId = executorId
                    )
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
                                AgentsApi().getAgents(
                                    context,
                                    it.lat,
                                    it.lon,
                                    dateTime.format(formatter),
                                    { list ->
                                        if (list.isEmpty()) {
                                            val builder = AlertDialog.Builder(context)
                                            builder
                                                .setTitle("Все представители заняты")
                                                .setMessage("Выберите другое время")
                                                .setPositiveButton("Перенести встречу") { _, _ ->
                                                    screen.intValue = 1
                                                    isChecking.value = false
                                                }
                                                .setNegativeButton("На главную") { _, _ ->
                                                    navController.navigate("main_screen")
                                                    isChecking.value = false
                                                }
                                            builder.create()
                                            builder.show()
                                        } else {
                                            screen.intValue++
                                            isChecking.value = false
                                            executors.value = list
                                            executorsLoadingStatus.value = LoadingState.READY
                                        }
                                    },
                                    {
                                        navController.navigate("create_form_result_screen/false")
                                    }
                                )
                            }
                        }
                    } else if (screen.intValue > 4) {
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

                        FormsApi().addForm(
                            context,
                            dateTime.format(formatter),
                            executors.value[executorId.intValue].id,
                            fullLocation.value.name,
                            fullLocation.value.lat,
                            fullLocation.value.lon,
                            participants
                        )

                        navController.navigate("create_form_result_screen/true")
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
