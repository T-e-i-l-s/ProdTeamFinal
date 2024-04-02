package com.example.prodteamfinal.presentation.screen

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.prodteamfinal.presentation.view.SkeletonView
import com.example.prodteamfinal.R
import com.example.prodteamfinal.data.repository.FormsRepositoryImpl
import com.example.prodteamfinal.domain.model.FormModel
import com.example.prodteamfinal.domain.state.LoadingState
import com.example.prodteamfinal.presentation.theme.greenButtonColors
import com.example.prodteamfinal.presentation.view.EmptyView
import com.example.prodteamfinal.presentation.view.ErrorView
import com.example.prodteamfinal.presentation.view.FormView

private var loadingStatus = mutableStateOf(LoadingState.LOADING)
private val forms = mutableStateOf(ArrayList<FormModel>())

@Composable
fun MainScreen(context: Context, navController: NavController) {
    LaunchedEffect(true) {
        loadingStatus.value = LoadingState.LOADING
        FormsRepositoryImpl().getForms(
            context,
            {
                forms.value = it
                loadingStatus.value = LoadingState.READY
            },
            {
                loadingStatus.value = LoadingState.ERROR
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background))
            .padding(horizontal = 10.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth(),
            text = "Заявки",
            fontSize = 25.sp,
            fontWeight = FontWeight(600),
            textAlign = TextAlign.Left,
            fontFamily = FontFamily(Font(R.font.roboto)),
        )

        if (loadingStatus.value == LoadingState.READY) {
            if (forms.value.isEmpty()) {
                EmptyView(modifier = Modifier.weight(1f))
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items(forms.value) {
                        FormView(
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null,
                                ) {
                                    navController.navigate("form_info_screen/${it.id}")
                                },
                            item = it
                        )
                    }
                }
            }
        } else if (loadingStatus.value == LoadingState.ERROR) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ErrorView(Modifier)
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(3) {
                    SkeletonView(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                            .height(200.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )
                }
            }
        }

        if (loadingStatus.value == LoadingState.READY) {
            Button(
                onClick = {
                    navController.navigate("create_form_screen")
                },
                colors = greenButtonColors(),
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier
                        .padding(5.dp),
                    text = "Создать заявку",
                    fontSize = 19.sp,
                    fontWeight = FontWeight(500),
                    fontFamily = FontFamily(Font(R.font.roboto)),
                )
            }
        } else if (loadingStatus.value == LoadingState.ERROR) {
            Button(
                onClick = {
                    loadingStatus.value = LoadingState.LOADING
                    FormsRepositoryImpl().getForms(
                        context,
                        {
                            forms.value = it
                            loadingStatus.value = LoadingState.READY
                        },
                        {
                            loadingStatus.value = LoadingState.ERROR
                        }
                    )
                },
                colors = greenButtonColors(),
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier
                        .padding(5.dp),
                    text = "Попробовать еще раз",
                    fontSize = 19.sp,
                    fontWeight = FontWeight(500),
                    fontFamily = FontFamily(Font(R.font.roboto)),
                )
            }
        }
    }
}