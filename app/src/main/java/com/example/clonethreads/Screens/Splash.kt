package com.example.clonethreads.Screens

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.clonethreads.Navigation.Routes
import kotlinx.coroutines.delay

@Composable
fun Splash( navController: NavHostController
) {
      Text(text ="Splash")
       LaunchedEffect(key1 = true ){
              delay(2500)
                navController.navigate(Routes.BottomNav.routes)
       }
}
