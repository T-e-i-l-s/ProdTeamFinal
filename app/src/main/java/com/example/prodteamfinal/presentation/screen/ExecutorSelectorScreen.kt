package com.example.prodteamfinal.presentation.screen

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.prodteamfinal.R
import com.example.prodteamfinal.data.api.AgentsApi
import com.example.prodteamfinal.domain.model.FullExecutorModel
import com.example.prodteamfinal.domain.state.LoadingState

@Composable
fun ExecutorSelectorScreen(context: Context, navController: NavController) {
    val executorsLoadingStatus = remember {
        mutableStateOf(LoadingState.LOADING)
    }
    val executors = remember {
        mutableStateOf(ArrayList<FullExecutorModel>())
    }
    val executorId = remember {
        mutableIntStateOf(0)
    }

    LaunchedEffect(true) {
        val lat = navController.currentBackStackEntry
            ?.arguments
            ?.getString("lat").toString()
        val lon = navController.currentBackStackEntry
            ?.arguments
            ?.getString("lon").toString()
        val time = navController.currentBackStackEntry
            ?.arguments
            ?.getString("time").toString()
//        AgentsApi().getAgents(
//            context,
//            lat,
//            lon,
//            time,
//            {
//                executors.value = it
//                executorsLoadingStatus.value = LoadingState.READY
                executors.value = arrayListOf(
                    FullExecutorModel(
                        "1",
                        "Андрей",
                        "Я не умею думать",
                        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSnwlxL5YCM7AcPtb1MBzvt4StwmKEHKMu1tfWsg1VyJA&s",
                        "+79674654425"
                    ),
                    FullExecutorModel(
                        "2",
                        "Ваня",
                        "Я не умею работать",
                        "https://img.a.transfermarkt.technology/portrait/big/232355-1691996923.jpg?lm=1",
                        "+79674654425"
                    ),
                    FullExecutorModel(
                        "3",
                        "Миша",
                        "Я не умею делать",
                        "https://img.freepik.com/free-vector/cute-lion-cartoon-character_1308-132909.jpg",
                        "+79674654425"
                    )
                )
                executorsLoadingStatus.value = LoadingState.READY
//            },
//            {
//                navController.navigate("create_form_result_screen/false")
//            }
//        )
    }
    
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Укажите участников встречи",
            fontSize = 22.sp,
            fontWeight = FontWeight(600),
            fontFamily = FontFamily(Font(R.font.roboto)),
            color = colorResource(id = R.color.text),
        )
        LazyRow {
            itemsIndexed(executors.value) { index, item ->
                AsyncImage(
                    model = item.photo,
                    contentDescription = "Аватар",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(70.dp)
                        .clip(CircleShape)
                        .border(2.dp, colorResource(id = R.color.text), CircleShape)
                )
            }
        }
    }
}
