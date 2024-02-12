package com.example.clonethreads.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.input.key.Key.Companion.Tab
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.clonethreads.Models.Bottomnavitems
import com.example.clonethreads.Navigation.Routes

@Composable
fun Tabbar(NavHostController: NavHostController){
    val list= listOf(
       Bottomnavitems (
            title = "Network",
            route = Routes.Home.routes,
            icon = Icons.Rounded.Check
        ), Bottomnavitems(
            title = "Gruop",
            route = Routes.Profile.routes,
            icon = Icons.Rounded.Home
        )
    )
    var selectedTabIndex by remember {
        mutableStateOf(0)
    }
    val navController= rememberNavController()

    Column {

        TabRow(selectedTabIndex = selectedTabIndex) {
            list.forEachIndexed { index, item ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = {
                        selectedTabIndex = index
                        navController.navigate(item.route)

                    },
                    icon = {
                        item.icon
                    },
                    text = {
                        item.title
                    }
                )
            }
        }
        NavHost(navController = navController, startDestination = Routes.Home.routes) {
            composable(Routes.Home.routes) {
                Home(NavHostController)
            }
            composable(Routes.Profile.routes) {
                Profile(NavHostController)
            }
        }
    }
    }
