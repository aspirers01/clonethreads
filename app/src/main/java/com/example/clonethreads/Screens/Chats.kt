package com.example.clonethreads.Screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.clonethreads.Navigation.Routes

@Composable
fun Chats(navController: NavHostController) {
    Column {

        Text(text ="Chats")
        Box(modifier = Modifier.fillMaxWidth().height(400.dp)){
            Tabbar(navController)
        }

        Text(text ="Chats")
    }



}