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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.prodteamfinal.data.api.ProductsApi
import com.example.prodteamfinal.data.repository.NominatimRepositoryImpl
import com.example.prodteamfinal.domain.model.ProductModel
import com.example.prodteamfinal.domain.state.LoadingState
import com.example.prodteamfinal.presentation.theme.greenButtonColors
import com.example.prodteamfinal.presentation.view.ProductView
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun CreateFormResultScreen(context: Context, navController: NavController) {
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
            Success(context)
        } else {
            Box(modifier = Modifier.weight(1f)) {
                Failure()
            }
        }
        Button(
            onClick = {
                navController.navigate("main_screen")
            },
            colors = greenButtonColors(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        ) {
            Text(
                modifier = Modifier
                    .padding(5.dp),
                text = "На главную",
                fontSize = 19.sp,
                fontWeight = FontWeight(500),
                fontFamily = FontFamily(Font(R.font.roboto)),
            )
        }
    }
}

@Composable
fun Success(context: Context) {
    val productsAreReady = remember {
        mutableStateOf(false)
    }
    val products = remember {
        mutableStateOf(ArrayList<ProductModel>())
    }

    LaunchedEffect(true) {
        ProductsApi().getProducts(
            context,
            {
                products.value = it
                productsAreReady.value = true
            },
            {
                productsAreReady.value = false
            }
        )
    }

    if (productsAreReady.value) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.lotti7),
                contentDescription = "Веселый Лотти",
                modifier = Modifier
                    .width(200.dp)
            )
            Text(
                text = "Ваша заявка принята",
                color = colorResource(id = R.color.additional1),
                fontSize = 22.sp,
                fontWeight = FontWeight(500),
                fontFamily = FontFamily(Font(R.font.roboto)),
                modifier = Modifier.padding(top = 10.dp)
            )
            ProductView(value = products.value[0])
        }
    }
}

@Composable
fun Failure() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.lotti4),
            contentDescription = "Грустный Лотти",
            modifier = Modifier
                .width(200.dp)
        )
        Text(
            text = "Ошибка",
            color = colorResource(id = R.color.additional1),
            fontSize = 22.sp,
            fontWeight = FontWeight(500),
            fontFamily = FontFamily(Font(R.font.roboto)),
            modifier = Modifier.padding(top = 10.dp)
        )
    }
}