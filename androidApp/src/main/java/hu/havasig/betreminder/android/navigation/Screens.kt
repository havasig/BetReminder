package hu.havasig.betreminder.android.navigation

sealed class Screens(val route: String) {
    object Login: Screens("login_screen")
    object Home: Screens("home_screen")
    object Detail: Screens("detail_screen")
}
