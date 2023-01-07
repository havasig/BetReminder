package hu.havasig.betreminder.android.navigation

import hu.havasig.betreminder.R

sealed class Screens(var title:String, var icon:Int, var route:String) {
    object Login: Screens("Login", 0, "login_screen")
    object Home: Screens("Home", R.drawable.ic_baseline_adb_24,"home_screen")
    object Detail: Screens("Details", 0,"detail_screen")
    object Create: Screens("Create", R.drawable.ic_baseline_adb_24,"create_screen")
}
