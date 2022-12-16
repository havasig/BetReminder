package hu.havasig.betreminder.android.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import hu.havasig.betreminder.android.details.DetailScreen
import hu.havasig.betreminder.android.home.HomeScreen
import hu.havasig.betreminder.android.login.LoginScreen

@Composable
fun NavGraph (navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = Screens.Login.route)
    {
        composable(route = Screens.Login.route){
            LoginScreen(navController)
        }
        composable(route = Screens.Home.route){
            HomeScreen(navController)
        }
        composable(route = Screens.Detail.route){
            DetailScreen()
        }
    }
}