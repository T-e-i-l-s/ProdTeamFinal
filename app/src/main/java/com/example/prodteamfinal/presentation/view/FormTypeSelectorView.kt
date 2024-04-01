package com.example.prodteamfinal.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prodteamfinal.R
import com.example.prodteamfinal.domain.state.FormType

@Composable
fun FormTypeSelectorView(modifier: Modifier, value: MutableState<FormType>) {
    Column(modifier = modifier) {
        Text(
            text = "Что вы хотите заказать?",
            fontSize = 22.sp,
            fontWeight = FontWeight(600),
            fontFamily = FontFamily(Font(R.font.roboto)),
            color = colorResource(id = R.color.text)
        )
        Row (modifier = Modifier.padding(top = 10.dp)){
            Column(
                modifier = Modifier
                    .background(
                        when (value.value) {
                            FormType.ИП -> colorResource(id = R.color.additional1)
                            else -> colorResource(id = R.color.main)
                        },
                        RoundedCornerShape(
                            topStart = 16.dp,
                            bottomStart = 16.dp
                        )
                    )
                    .weight(0.5f)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                    ) {
                        value.value = FormType.ИП
                    }
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "ИП",
                    fontSize = 22.sp,
                    fontWeight = FontWeight(600),
                    fontFamily = FontFamily(Font(R.font.roboto)),
                    color = when (value.value) {
                        FormType.ИП -> colorResource(id = R.color.background)
                        else -> colorResource(id = R.color.text)
                    }
                )
            }
            Column(
                modifier = Modifier
                    .background(
                        when (value.value) {
                            FormType.ООО -> colorResource(id = R.color.additional1)
                            else -> colorResource(id = R.color.main)
                        },
                        RoundedCornerShape(
                            topEnd = 16.dp,
                            bottomEnd = 16.dp
                        )
                    )
                    .weight(0.5f)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                    ) {
                        value.value = FormType.ООО
                    }
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "ООО",
                    fontSize = 22.sp,
                    fontWeight = FontWeight(600),
                    fontFamily = FontFamily(Font(R.font.roboto)),
                    color = when (value.value) {
                        FormType.ООО -> colorResource(id = R.color.background)
                        else -> colorResource(id = R.color.text)
                    }
                )
            }
        }
    }
}