package com.example.clonethreads.Screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.clonethreads.Models.UserModel
import com.example.clonethreads.Navigation.Routes
import com.example.clonethreads.Viewmodel.ChatVIewModel
import com.example.clonethreads.utils.SharedPref

@Composable
fun Chats(
    //navController: NavHostController,
    viewmodel: ChatVIewModel = viewModel()
) {
    val context= LocalContext.current
    Column {
        Text(
            text = "Chats",
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.ExtraBold),
                 textAlign =  TextAlign.Start
        )


         val chats= viewmodel.chatdata
         val currentuser= UserModel(
              SharedPref.getname(context),
                SharedPref.getemail(context),
               image = SharedPref.getimage(context),
               uid = SharedPref.getusername(context)

         )

    }


}

@Preview(showBackground = true)
@Composable
fun ChatsPreview() {
    Chats()
}
// Path: Chats.kt