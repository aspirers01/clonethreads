package com.example.clonethreads.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.rememberNavController
import com.example.clonethreads.Navigation.Routes
import com.example.clonethreads.Viewmodel.AuthViewmodel


@Composable
fun Profile(navController: NavHostController){
    val viewmodel= AuthViewmodel()

    val firebaseUser by viewmodel.firebaseUser.observeAsState(null)
    LaunchedEffect(firebaseUser) {
       if(firebaseUser==null){

          navController.navigate(Routes.Login.routes){
               popUpTo(navController.graph.findStartDestination().id)
               launchSingleTop = true
           }

       }
    }
    Box(modifier = Modifier
        .background(Color.Transparent)
        .fillMaxSize()) {
        Button(onClick = {  viewmodel.signout()},modifier = Modifier.align(Alignment.Center)) {
            Text(text = "log out")
        }
    }
}