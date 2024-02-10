package com.example.clonethreads.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.clonethreads.Screens.AddThreads
import com.example.clonethreads.Screens.BottomNav
import com.example.clonethreads.Screens.Chats
import com.example.clonethreads.Screens.Home
import com.example.clonethreads.Screens.Login
import com.example.clonethreads.Screens.OtherUser
import com.example.clonethreads.Screens.Profile
import com.example.clonethreads.Screens.Register
import com.example.clonethreads.Screens.Search
import com.example.clonethreads.Screens.Splash


@Composable
fun NavGraph(navHostController: NavHostController
) {
    NavHost(navController = navHostController, startDestination = Routes.Splash.routes) {

       composable(Routes.Splash.routes) {
           Splash(navHostController)
       }
        composable(Routes.Home.routes) {
            Home(navHostController)
        }
        composable(Routes.Search.routes) {
            Search(navHostController)
        }
        composable(Routes.Profile.routes) {
           Profile(navHostController)
        }
        composable(Routes.Chats.routes) {
            Chats()
        }
        composable(Routes.AddThreads.routes) {
            AddThreads(navHostController)
        }
        composable(Routes.BottomNav.routes){
            BottomNav(navController = navHostController )
        }
        composable(Routes.Login.routes){
            Login(navHostController)
        }
        composable(Routes.Register.routes){
           Register(navHostController)
        }
        composable(Routes.OtherUser.routes){
            val data=it.arguments?.getString("data")
            OtherUser(navHostController,data!!)
        }
    }
}