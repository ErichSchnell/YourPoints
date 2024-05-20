package com.example.yourpoints.presentation.core

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.yourpoints.presentation.core.Routes.*
import com.example.yourpoints.presentation.ui.home.HomeScreen
import com.example.yourpoints.presentation.ui.truco.TrucoScreen
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "Navigator Intern Test"


@Composable
fun ContentWrapper(navigatonController: NavHostController) {

    NavHost(
        navController = navigatonController, startDestination = Home.route
    ) {
        composable(Home.route) {
            HomeScreen(
                navigateToGenerala = {  gameId -> navigatonController.navigate(Generala.createRoute(gameId)) },
                navigateToGenerico = {  gameId -> navigatonController.navigate(Generico.createRoute(gameId)) },
                navigateToTruco = {     gameId -> navigatonController.navigate(Truco.createRoute(gameId)) }
            )
        }
        composable(
            Generico.route, arguments = listOf(navArgument("gameId") { type = NavType.IntType })
        ) {
            Log.i(TAG, "Generico.route")
        }
        composable(
            Truco.route, arguments = listOf(navArgument("gameId") { type = NavType.IntType })
        ) {
            TrucoScreen(
                gameId = it.arguments?.getInt("gameId") ?: 0
            )
        }
        composable(
            Generala.route, arguments = listOf(navArgument("gameId") { type = NavType.IntType })
        ) {
            Log.i(TAG, "Generala.route")
        }
    }

}

sealed class Routes(val route: String) {
    object Home : Routes("home")
    object Generala : Routes("generala/{gameId}") {
        fun createRoute(gameId: Int): String {
            return "generala/$gameId"
        }
    }

    object Truco : Routes("truco/{gameId}") {
        fun createRoute(gameId: Int): String {
            return "truco/$gameId"
        }
    }

    object Generico : Routes("generico/{gameId}") {
        fun createRoute(gameId: Int): String {
            return "generico/$gameId"
        }
    }
}