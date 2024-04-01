package com.example.prodteamfinal.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.prodfinal.navigation.ChangeStatusBarColor
import com.example.prodteamfinal.R
import com.example.prodteamfinal.presentation.screen.CreateFormResultScreen
import com.example.prodteamfinal.presentation.screen.CreateFormScreen
import com.example.prodteamfinal.presentation.screen.ExecutorSelectorScreen
import com.example.prodteamfinal.presentation.screen.FormInfoScreen
import com.example.prodteamfinal.presentation.screen.MainScreen

// Граф Stack навигации(между графом с Tab навигацией и вспомогательными экранами)

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
            ChangeStatusBarColor(color = colorResource(id = R.color.background))
            MainScreen(LocalContext.current, navController)
        }
        // Экран информации о заявке
        composable(
            "form_info_screen/{form_id}"
        ) {
            ChangeStatusBarColor(color = colorResource(id = R.color.additional1))
            FormInfoScreen(LocalContext.current, navController)
        }
        // Экран создания заявки
        composable(
            "create_form_screen" // /{mode}/{json}
        ) {
            ChangeStatusBarColor(color = colorResource(id = R.color.background))
            CreateFormScreen(LocalContext.current, navController)
        }
        // Экран создания заявки
        composable(
            "executor_selector_screen/{lat}/{lon}/{time}"
        ) {
            ChangeStatusBarColor(color = colorResource(id = R.color.background))
            ExecutorSelectorScreen(LocalContext.current, navController)
        }
        // Экран с результатом заявки
        composable(
            "create_form_result_screen/{result}"
        ) {
            ChangeStatusBarColor(color = colorResource(id = R.color.background))
            CreateFormResultScreen(navController)
        }
    }
}