package com.example.clonethreads.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController


@Composable

fun SingleChatScreen(navHostController: NavHostController, chatId: String) {

    Column {
        Text("SingleChatScreen")
         Text(text =chatId)
    }

}
