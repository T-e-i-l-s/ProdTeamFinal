package com.example.prodteamfinal.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.prodteamfinal.R
import com.example.prodteamfinal.domain.model.ExecutorModel
import com.example.prodteamfinal.domain.model.FormModel
import com.example.prodteamfinal.domain.state.FormState
import com.example.prodteamfinal.domain.state.FormType
import com.example.prodteamfinal.presentation.screen.CreateFormResultScreen
import com.example.prodteamfinal.presentation.screen.CreateFormScreen
import com.example.prodteamfinal.presentation.screen.EditFormScreen
import com.example.prodteamfinal.presentation.screen.FormInfoScreen
import com.example.prodteamfinal.presentation.screen.MainScreen

// Граф Stack навигации(между графом с Tab навигацией и вспомогательными экранами)

var currentScreen = "main_screen"
var currentForm = mutableStateOf( // Для edit form screen
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

@Composable
fun StackNavigator () {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "main_screen"
    ) {
        // Главный экран
        composable(
            "main_screen"
        ) {
            currentScreen = "main_screen"
            ChangeStatusBarColor(color = colorResource(id = R.color.background))
            MainScreen(LocalContext.current, navController)
        }
        // Экран информации о заявке
        composable(
            "form_info_screen/{form_id}"
        ) {
            currentScreen = "form_info_screen"
            ChangeStatusBarColor(color = colorResource(id = R.color.additional1))
            FormInfoScreen(LocalContext.current, navController)
        }
        // Экран создания заявки
        composable(
            "create_form_screen" // /{mode}/{json}
        ) {
            currentScreen = "create_form_screen"
            ChangeStatusBarColor(color = colorResource(id = R.color.background))
            CreateFormScreen(LocalContext.current, navController)
        }
        // Экран создания заявки
        composable(
            "edit_form_screen"
        ) {
            currentScreen = "edit_form_screen"
            ChangeStatusBarColor(color = colorResource(id = R.color.background))
            EditFormScreen(LocalContext.current, navController)
        }
        // Экран с результатом заявки
        composable(
            "create_form_result_screen/{result}"
        ) {
            currentScreen = "create_form_result_screen"
            ChangeStatusBarColor(color = colorResource(id = R.color.background))
            CreateFormResultScreen(LocalContext.current, navController)
        }
    }
}