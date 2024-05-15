package com.example.yourpoints.presentation.core

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.yourpoints.presentation.core.Routes.*
import com.example.yourpoints.presentation.ui.home.HomeScreen
import com.example.yourpoints.presentation.ui.truco.TrucoScreen

const val TAG = "Navigator Intern Test"


@Composable
fun ContentWrapper(navigatonController: NavHostController) {


    NavHost(
        navController = navigatonController,
        startDestination = Home.route
    ) {
        composable(Home.route) {
            HomeScreen(
                navigateToGenerala = {navigatonController.navigate(Generala.route) },
                navigateToGenerico = {navigatonController.navigate(Generico.route) },
                navigateToTruco = {navigatonController.navigate(Truco.route) }
            )
        }
        composable(Generico.route) {
            Log.i(TAG, "Generico.route")
        }
        composable(Truco.route) {
            TrucoScreen()
        }
        composable(Generala.route) {
            Log.i(TAG, "Generala.route")
        }
    }

}

sealed class Routes(val route:String){
    object Home: Routes("home")
    object Generala:Routes("generala")
    object Truco:Routes("truco")
    object Generico:Routes("generico")
}