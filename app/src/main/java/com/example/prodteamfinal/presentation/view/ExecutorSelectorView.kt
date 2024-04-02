package com.example.prodteamfinal.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.prodteamfinal.R
import com.example.prodteamfinal.domain.model.FullExecutorModel

@Composable
fun ExecutorSelectorView(
    executors: MutableState<ArrayList<FullExecutorModel>>,
    executorId: MutableIntState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Выберите представителя",
            fontSize = 22.sp,
            fontWeight = FontWeight(600),
            fontFamily = FontFamily(Font(R.font.roboto)),
            color = colorResource(id = R.color.text),
        )
        LazyColumn(
            modifier = Modifier.padding(top = 10.dp),
        ) {
            itemsIndexed(executors.value) { index, item ->
                Row (
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .fillMaxWidth()
                        .background(
                            colorResource(id = R.color.main),
                            RoundedCornerShape(16.dp)
                        )
                        .border(
                            when (index == executorId.intValue) {
                                true -> 4.dp
                                false -> 0.dp
                            },
                            when (index == executorId.intValue) {
                                true -> colorResource(id = R.color.additional1)
                                false -> Color.Transparent
                            },
                            RoundedCornerShape(16.dp)
                        )
                        .clickable {
                            executorId.intValue = index
                        },
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    AsyncImage(
                        model = item.photo,
                        contentDescription = "Аватар",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(90.dp)
                            .clip(RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp))
                    )
                    Column (
                        modifier = Modifier
                            .weight(1f)
                            .padding(10.dp),
                    ) {
                        Text(
                            text = item.name,
                            fontSize = 16.sp,
                            fontWeight = FontWeight(700),
                            fontFamily = FontFamily(Font(R.font.roboto)),
                            color = colorResource(id = R.color.text),
                        )
                        Text(
                            text = item.description,
                            fontSize = 16.sp,
                            fontWeight = FontWeight(500),
                            fontFamily = FontFamily(Font(R.font.roboto)),
                            color = colorResource(id = R.color.text)
                        )
                    }
                }
            }
        }
    }
}
