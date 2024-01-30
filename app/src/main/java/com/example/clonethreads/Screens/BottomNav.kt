package com.example.clonethreads.Screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.clonethreads.Models.Bottomnavitems
import com.example.clonethreads.Navigation.Routes

@Composable
fun BottomNav(navController: NavHostController){

    val navController1= rememberNavController()
    Scaffold (
        bottomBar = {
           MyAppbar(navController = navController1)
        }){innerpadding->
         NavHost(navController = navController1, startDestination = Routes.Home.routes, modifier = Modifier.padding(innerpadding)) {
             composable(Routes.Home.routes) {
                 Home(navController1)
             }
             composable(Routes.Search.routes) {
                 Search()
             }
             composable(Routes.Profile.routes) {
                 Profile(navController1)
             }
             composable(Routes.Notifications.routes) {
                 Notification()
             }
             composable(Routes.AddThreads.routes) {
                 AddThreads(navController1)
             }

         }

    }
}

@Composable
fun MyAppbar(navController: NavHostController) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    val list= listOf(
        Bottomnavitems(
            title = "Home",
            route = Routes.Home.routes,
            icon = Icons.Rounded.Home
        ), Bottomnavitems(
            title = "Search",
            route = Routes.Search.routes,
            icon = Icons.Rounded.Search
        ),Bottomnavitems(
            title = "Add",
            route = Routes.AddThreads.routes,
            icon = Icons.Rounded.Add
        ),Bottomnavitems(
            title = "Notification",
            route = Routes.Notifications.routes,
            icon = Icons.Rounded.Notifications
        ),Bottomnavitems(
            title = "Profile",
            route = Routes.Profile.routes,
            icon = Icons.Rounded.Person
        )
    )
    BottomAppBar {
         list.forEach { item ->
             val selected = item.route == backStackEntry.value?.destination?.route
                NavigationBarItem( selected = selected, onClick = {
                    navController.navigate(item.route){
                       popUpTo(navController.graph.findStartDestination().id){
                           saveState = true
                       }
                        launchSingleTop = true
                        restoreState = true
                    }
                }, icon = {
                  Icon(imageVector =item.icon ,null )
                }, label = {
                    item.title
                })

         }}



}
