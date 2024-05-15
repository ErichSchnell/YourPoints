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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ContentWrapper(navigatonController: NavHostController) {


    NavHost(
        navController = navigatonController,
        startDestination = Home.route
    ) {
        composable(Home.route) {
            HomeScreen(
                navigateToGenerala = {time -> navigatonController.navigate(Generala.createRoute(time)) },
                navigateToGenerico = {time -> navigatonController.navigate(Generico.createRoute(time)) },
                navigateToTruco = {time -> navigatonController.navigate(Truco.createRoute(time)) }
            )
        }
        composable(
            Generico.route,
            arguments = listOf(navArgument("time"){ type = NavType.StringType})
        ) {
            Log.i(TAG, "Generico.route")
        }
        composable(
            Truco.route,
            arguments = listOf(navArgument("time"){ type = NavType.StringType})
        ) {
            TrucoScreen(
                annotatorTime = it.arguments?.getString("time").orEmpty()
            )
        }
        composable(
            Generala.route,
            arguments = listOf(navArgument("time"){ type = NavType.StringType})
        ) {
            Log.i(TAG, "Generala.route")
        }
    }

}

sealed class Routes(val route:String){
    object Home: Routes("home")
    object Generala:Routes("generala/{time}"){
        fun createRoute(time: String): String{
            return "generala/$time"
        }
    }
    object Truco:Routes("truco/{time}"){
        fun createRoute(time: String): String{
            return "truco/$time"
        }
    }
    object Generico:Routes("generico/{time}"){
        fun createRoute(time: String): String{
            return "generico/$time"
        }
    }
}