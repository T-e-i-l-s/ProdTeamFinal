package com.example.prodteamfinal.presentation.screen

import android.app.AlertDialog
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.prodfinal.presentation.view.SkeletonView
import com.example.prodteamfinal.R
import com.example.prodteamfinal.data.api.FormInfoApi
import com.example.prodteamfinal.data.repository.FormInfoRepositoryImpl
import com.example.prodteamfinal.domain.model.ExecutorModel
import com.example.prodteamfinal.domain.model.FormModel
import com.example.prodteamfinal.domain.state.FormState
import com.example.prodteamfinal.domain.state.FormType
import com.example.prodteamfinal.domain.state.LoadingState
import com.example.prodteamfinal.navigation.currentForm
import com.example.prodteamfinal.navigation.currentScreen
import com.example.prodteamfinal.presentation.theme.blackButtonColors
import com.google.gson.Gson
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@Composable
fun FormInfoScreen(context: Context, navController: NavController) {
    val loadingStatus = remember {
        mutableStateOf(LoadingState.LOADING)
    }
    val time = remember {
        mutableStateOf(LocalTime.NOON)
    }
    val date = remember {
        mutableStateOf(LocalDate.MAX)
    }
    val formInfo = remember {
        mutableStateOf(
            FormModel(
                "",
                FormState.ACTIVE,
                FormType.ИП,
                "",
                ExecutorModel(
                    "",
                    "",
                    "",
                    "",
                ),
                "",
                ArrayList(),
                ArrayList()
            )
        )
    }

    LaunchedEffect(true) {
        loadingStatus.value = LoadingState.LOADING
        val id = navController.currentBackStackEntry
            ?.arguments
            ?.getString("form_id").toString()
        FormInfoRepositoryImpl().getFormInfo(
            context,
            id,
            {
                formInfo.value = it
                val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
                val localTime = LocalDateTime.parse(formInfo.value.time, formatter)
                time.value = localTime.toLocalTime()
                date.value = localTime.toLocalDate()
                loadingStatus.value = LoadingState.READY
            },
            {
                loadingStatus.value = LoadingState.ERROR
            }
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background))
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(id = R.color.additional1))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                        .paint(
                            painterResource(id = R.drawable.lotti1),
                            alignment = Alignment.CenterEnd,
                        ),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.arrow_left_light_icon),
                        contentDescription = "Выйти",
                        modifier = Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                        ) {
                            if (currentScreen == "form_info_screen") {
                                navController.popBackStack()
                            }
                        }
                    )
                    if (loadingStatus.value == LoadingState.READY) {
                        Text(
                            text = "Заявка на " + when (formInfo.value.type) {
                                FormType.ИП -> "ИП"
                                else -> "ООО"
                            },
                            fontSize = 25.sp,
                            fontWeight = FontWeight(500),
                            color = colorResource(id = R.color.background),
                            modifier = Modifier
                                .padding(bottom = 10.dp)
                        )
                    }
                }

                when (loadingStatus.value) {
                    LoadingState.READY -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    colorResource(id = R.color.background),
                                    RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                                )
                                .padding(horizontal = 10.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp)
                                    .background(
                                        colorResource(id = R.color.main),
                                        RoundedCornerShape(16.dp)
                                    )
                                    .padding(10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                AsyncImage(
                                    model = formInfo.value.executor.photo,
                                    contentDescription = "Аватар",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .height(90.dp)
                                        .width(90.dp)
                                        .clip(CircleShape)
                                        .border(2.dp, colorResource(id = R.color.text), CircleShape)
                                )
                                Text(
                                    text = formInfo.value.executor.name,
                                    fontSize = 19.sp,
                                    fontWeight = FontWeight(700),
                                    fontFamily = FontFamily(Font(R.font.roboto)),
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = "Ваш представитель",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight(500),
                                    fontFamily = FontFamily(Font(R.font.roboto)),
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = formInfo.value.executor.description,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight(500),
                                    fontFamily = FontFamily(Font(R.font.roboto)),
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = formInfo.value.executor.phoneNum,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight(500),
                                    fontFamily = FontFamily(Font(R.font.roboto)),
                                    textAlign = TextAlign.Center
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp)
                                    .background(
                                        colorResource(id = R.color.main),
                                        RoundedCornerShape(16.dp)
                                    )
                                    .padding(10.dp),
                            ) {
                                Text(
                                    text = "Информация",
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight(700),
                                    fontFamily = FontFamily(Font(R.font.roboto)),
                                )

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.location_icon),
                                        contentDescription = "Локация"
                                    )
                                    Text(
                                        text = formInfo.value.location,
                                        fontSize = 19.sp,
                                        fontWeight = FontWeight(400),
                                        fontFamily = FontFamily(Font(R.font.roboto)),
                                        modifier = Modifier.padding(start = 3.dp)
                                    )
                                }

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.time_icon),
                                        contentDescription = "Время"
                                    )
                                    Text(
                                        text = "" + "%02d".format(date.value.dayOfMonth) + "." +
                                                "%02d".format(date.value.monthValue) + "." +
                                                date.value.year + " в " + time.value.hour + ":" + "%02d".format(
                                            time.value.minute
                                        ),
                                        fontSize = 19.sp,
                                        fontWeight = FontWeight(400),
                                        fontFamily = FontFamily(Font(R.font.roboto)),
                                        modifier = Modifier.padding(start = 3.dp)
                                    )
                                }
                            }

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp)
                                    .background(
                                        colorResource(id = R.color.main),
                                        RoundedCornerShape(16.dp)
                                    )
                                    .padding(10.dp),
                            ) {
                                Text(
                                    text = "Участники встречи",
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight(700),
                                    fontFamily = FontFamily(Font(R.font.roboto)),
                                )

                                formInfo.value.participants.forEachIndexed { index, item ->
                                    Text(
                                        text = "${index + 1}. ${item.name}",
                                        fontSize = 19.sp,
                                        fontWeight = FontWeight(400),
                                        fontFamily = FontFamily(Font(R.font.roboto)),
                                    )
                                }
                            }

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp)
                                    .background(
                                        colorResource(id = R.color.main),
                                        RoundedCornerShape(16.dp)
                                    )
                                    .padding(10.dp),
                            ) {
                                Text(
                                    text = "Необходимые документы",
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight(700),
                                    fontFamily = FontFamily(Font(R.font.roboto)),
                                )

                                formInfo.value.documents.forEachIndexed { index, item ->
                                    Text(
                                        text = "${index + 1}. $item",
                                        fontSize = 19.sp,
                                        fontWeight = FontWeight(400),
                                        fontFamily = FontFamily(Font(R.font.roboto))
                                    )
                                }
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Button(
                                    colors = blackButtonColors(),
                                    onClick = {
                                        currentForm.value = formInfo.value
                                        navController.navigate("edit_form_screen")
                                    },
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        modifier = Modifier
                                            .padding(5.dp),
                                        text = "Изменить заявку",
                                        fontSize = 19.sp,
                                        fontWeight = FontWeight(500),
                                        fontFamily = FontFamily(Font(R.font.roboto)),
                                    )
                                }
                                Image(
                                    painter = painterResource(id = R.drawable.trash_icon),
                                    contentDescription = "Удалить",
                                    modifier = Modifier
                                        .padding(horizontal = 10.dp)
                                        .clickable(
                                            interactionSource = remember { MutableInteractionSource() },
                                            indication = null,
                                        ) {
                                            val builder = AlertDialog.Builder(context)
                                            builder
                                                .setMessage("Может вам удобно перенести встречу?")
                                                .setPositiveButton("Да") { _, _ ->
                                                    currentForm.value = formInfo.value
                                                    navController.navigate("edit_form_screen")
                                                }
                                                .setNegativeButton("Нет") { dialog, _ ->
                                                    dialog.dismiss()
                                                    FormInfoApi().deleteForm(
                                                        context,
                                                        formInfo.value.id
                                                    )
                                                    navController.navigate("main_screen")
                                                }
                                            builder.create()
                                            builder.show()
                                        }
                                )
                            }
                        }
                    }

                    else -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    colorResource(id = R.color.background),
                                    RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                                )
                                .padding(horizontal = 10.dp)
                        ) {
                            SkeletonView(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp)
                                    .height(180.dp)
                                    .clip(RoundedCornerShape(16.dp))
                            )
                            SkeletonView(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp)
                                    .height(140.dp)
                                    .clip(RoundedCornerShape(16.dp))
                            )
                            SkeletonView(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp)
                                    .height(90.dp)
                                    .clip(RoundedCornerShape(16.dp))
                            )
                            SkeletonView(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp)
                                    .height(230.dp)
                                    .clip(RoundedCornerShape(16.dp))
                            )
                            SkeletonView(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 10.dp)
                                    .height(55.dp)
                                    .clip(RoundedCornerShape(50.dp))
                            )
                        }
                    }
                }
            }
        }
    }
}

