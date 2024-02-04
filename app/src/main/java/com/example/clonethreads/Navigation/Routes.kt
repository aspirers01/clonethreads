package com.example.clonethreads.Navigation

sealed class Routes(val routes: String) {


    object Home : Routes("home")

    object Search : Routes("search")

    object Profile : Routes("profile")

    object Notifications : Routes("notifications")

    object Splash : Routes("splash")

    object AddThreads : Routes("add_threads")

    object BottomNav : Routes("bottom_nav")
    object Login : Routes("login")
    object Register : Routes("register")

   object OtherUser : Routes("otheruser/{data}")


}