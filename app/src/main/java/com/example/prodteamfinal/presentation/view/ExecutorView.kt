package com.example.prodteamfinal.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import coil.compose.AsyncImage
import com.example.prodteamfinal.R
import com.example.prodteamfinal.domain.model.ExecutorModel

// Базовый скелетон

@Composable
fun ExecutorView(modifier: Modifier, executor: ExecutorModel) {
    Column(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    colorResource(id = R.color.main),
                    RoundedCornerShape(16.dp)
                )
                .border(
                    2.dp,
                    colorResource(id = R.color.gray),
                    RoundedCornerShape(16.dp)
                )
                .padding(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                AsyncImage(
                    model = executor.photo,
                    contentDescription = "Аватар",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(50.dp)
                        .width(50.dp)
                        .clip(CircleShape)
                        .border(2.dp, colorResource(id = R.color.text), CircleShape)
                )
                Column(
                    modifier = Modifier.padding(start = 10.dp)
                ) {
                    Text(
                        text = executor.name,
                        fontSize = 19.sp,
                        fontWeight = FontWeight(500),
                        fontFamily = FontFamily(Font(R.font.roboto))
                    )
                    Text(
                        text = executor.phoneNum,
                        fontSize = 16.sp,
                        fontWeight = FontWeight(500),
                        fontFamily = FontFamily(Font(R.font.roboto)),
                    )
                }
            }
        }
    }
}