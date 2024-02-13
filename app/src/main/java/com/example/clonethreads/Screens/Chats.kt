package com.example.clonethreads.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.clonethreads.Models.UserModel
import com.example.clonethreads.Navigation.Routes
import com.example.clonethreads.Viewmodel.Chatsviewmodel
import com.example.clonethreads.utils.SharedPref

@Composable
fun Chats(
    navController: NavHostController,
    viewmodel: Chatsviewmodel = viewModel()
) {
    val chats = viewmodel.chats.value
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
         val currentuser= UserModel(
              SharedPref.getname(context),
                SharedPref.getemail(context),
               image = SharedPref.getimage(context),
               uid = SharedPref.getusername(context)

         )
        if (chats.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "No Chats")
            }
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(chats) { chat ->
                    val chatUser = if (chat.user1.userId == currentuser?.uid) {
                        chat.user2
                    } else {
                        chat.user1
                    }
                    CommonRow(imageUrl = chatUser.imageUrl, name = chatUser.name) {
                        chat.chatId?.let {
                            val routes=  Routes.SingleChatScreen.routes.replace("{data}",it)
                            //navigateTo(navController, Routes.ChatScreen.route)
                             navController.navigate(routes)


                        }
                    }
                }
            }
        }


    }


}


@Composable
fun CommonImage(
    data: String?,
    modifier: Modifier = Modifier.wrapContentSize(),
    contentScale: ContentScale = ContentScale.Crop,
) {
    val painter = rememberImagePainter(data = data)
    Image(
        painter = painter,
        contentDescription = null,
        modifier = modifier,
        contentScale = contentScale
    )
}

@Composable
fun CommonRow(
    imageUrl: String?,
    name: String?,
    onItemClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(75.dp)
            .clickable { onItemClick.invoke() },
        verticalAlignment = Alignment.CenterVertically
    )
    {
        CommonImage(
            data = imageUrl, modifier = Modifier.padding(8.dp).size(50.dp).clip(CircleShape)
                .background(Color.Red)
        )
        Text(text = name?:"---", fontWeight = FontWeight.Bold,modifier = Modifier.padding(8.dp))

    }

}
@Preview(showBackground = true)
@Composable
fun ChatsPreview() {
    //Chats()
}
// Path: Chats.kt