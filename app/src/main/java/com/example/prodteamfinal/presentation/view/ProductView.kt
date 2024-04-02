package com.example.prodteamfinal.presentation.view

import android.content.Intent
import android.net.Uri
import android.webkit.WebView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDeepLink
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.prodteamfinal.R
import com.example.prodteamfinal.domain.model.ProductModel

@Composable
fun ProductView(value: ProductModel) {
    val uriHandler = LocalUriHandler.current
    val route = value.url
    val maxLines = remember {
        mutableIntStateOf(1)
    }

    Column(
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth()
            .background(
                colorResource(id = R.color.ads),
                RoundedCornerShape(16.dp)
            )
            .padding(10.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            ) {
                if (maxLines.intValue == 1) {
                    maxLines.intValue = Int.MAX_VALUE
                } else {
                    maxLines.intValue = 1
                }
            },
    ) {

        Row (
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = value.image,
                contentDescription = "Аватар",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(65.dp)
                    .width(65.dp)
            )
            Column (
                modifier = Modifier.weight(1f).padding(start = 10.dp)
            ) {
                Text(
                    text = value.name,
                    fontSize = 19.sp,
                    fontWeight = FontWeight(700),
                    fontFamily = FontFamily(Font(R.font.roboto))
                )
                Text(
                    text = value.description,
                    fontSize = 19.sp,
                    fontWeight = FontWeight(400),
                    fontFamily = FontFamily(Font(R.font.roboto)),
                    maxLines = maxLines.intValue
                )
                Text(
                    text = "Подробнее...",
                    fontSize = 16.sp,
                    fontWeight = FontWeight(400),
                    fontFamily = FontFamily(Font(R.font.roboto)),
                    color = colorResource(id = R.color.link),
                    modifier = Modifier.clickable {
                        uriHandler.openUri(route)
                    }
                )
            }
        }
    }
}
