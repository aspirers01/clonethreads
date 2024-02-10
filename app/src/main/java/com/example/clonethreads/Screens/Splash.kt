package com.example.clonethreads.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.clonethreads.Navigation.Routes
import com.example.clonethreads.R
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.delay

@Composable
fun Splash( navController: NavHostController
) {
      val auth= Firebase.auth

       ConstraintLayout (modifier = Modifier.fillMaxSize()){
           val  (image)= createRefs()

           Image(painter = painterResource(id = R.drawable.threads_app_icon), contentDescription = null, modifier = Modifier.constrainAs(image){
               top.linkTo(parent.top)
               bottom.linkTo(parent.bottom)
               start.linkTo(parent.start)
               end.linkTo(parent.end)
           }.size(120.dp))

       }
//          Text(text ="Splash Screen")
             LaunchedEffect(key1 = true ){
                 delay(2500)
                  if(auth.currentUser == null){
                        navController.navigate(Routes.Login.routes){
                            popUpTo(Routes.Splash.routes){
                                inclusive = true
                            }
                        }
                  }else{
                      navController.navigate(Routes.BottomNav.routes) {
                          popUpTo(Routes.Splash.routes){
                              inclusive = true
                          }
                      }
                  }

             }
         }





