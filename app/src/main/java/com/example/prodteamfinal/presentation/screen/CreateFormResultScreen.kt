package com.example.prodteamfinal.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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

@Composable
fun CreateFormResultScreen(navController: NavController) {
    val isSuccessful = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(true) {
        val isAllowed = navController.currentBackStackEntry
            ?.arguments
            ?.getString("result") ?: "false"
        isSuccessful.value = isAllowed == "true"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background))
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (isSuccessful.value) {
            Success(navController)
        } else {
            Failure(navController)
        }
    }
}

@Composable
fun Success(navController: NavController) {

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier
                .padding(bottom = 10.dp)
                .background(
                    colorResource(id = R.color.additional1),
                    RoundedCornerShape(16.dp)
                )
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.lotti3),
                contentDescription = "Веселый Лотти",
                modifier = Modifier
                    .width(170.dp)
                    .height(170.dp)
            )
            Text(
                text = "Успешно",
                color = colorResource(id = R.color.background),
                fontSize = 25.sp,
                fontWeight = FontWeight(600),
                fontFamily = FontFamily(Font(R.font.roboto)),
                modifier = Modifier.padding(top = 10.dp)
            )
        }

        Image(
            painter = painterResource(id = R.drawable.xmark_icon),
            contentDescription = "Закрыть",
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                ) {
                    navController.navigate("main_screen")
                }
        )
    }
}

@Composable
fun Failure(navController: NavController) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier
                .padding(bottom = 10.dp)
                .background(
                    colorResource(id = R.color.error),
                    RoundedCornerShape(16.dp)
                )
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.lotti4),
                contentDescription = "Обиженный Лотти",
                modifier = Modifier
                    .width(170.dp)
                    .height(170.dp)
            )
            Text(
                text = "Ошибка",
                color = colorResource(id = R.color.background),
                fontSize = 25.sp,
                fontWeight = FontWeight(600),
                fontFamily = FontFamily(Font(R.font.roboto)),
                modifier = Modifier.padding(top = 10.dp)
            )
        }

        Image(
            painter = painterResource(id = R.drawable.xmark_icon),
            contentDescription = "Закрыть",
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                ) {
                    navController.navigate("main_screen")
                }
        )
    }
}