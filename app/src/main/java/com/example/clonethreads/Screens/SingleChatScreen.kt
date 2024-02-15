package com.example.clonethreads.Screens

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column



import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.clonethreads.Models.Message
import com.example.clonethreads.Models.UserModel
import com.example.clonethreads.Viewmodel.Chatsviewmodel

import com.example.clonethreads.utils.SharedPref


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable

fun SingleChatScreen(
    navHostController: NavHostController,
    chatId: String,
    vm: Chatsviewmodel = viewModel()
) {
    var reply by rememberSaveable {
        mutableStateOf("")
    }
    val context = LocalContext.current
    val currentChat = vm.chats.value?.find { it.chatId == chatId }
    val currentuser = UserModel(
        SharedPref.getname(context),
        SharedPref.getemail(context),
        image = SharedPref.getimage(context),
        uid = SharedPref.getusername(context)

    )
    val chatUser =
        if (currentuser?.uid == currentChat?.user1?.userId) currentChat?.user2 else currentChat?.user1

    val onSendReply = {
        if(reply.isNotEmpty())
        vm.onSendReply(chatId, reply)
        reply = ""

    }
    LaunchedEffect(key1 = Unit ){
        vm.populateMessages(chatId)

    }
    BackHandler {
        vm.depopulateMessages()
    }
    Column {

                if (chatUser != null) {
                    ChatHeader(name = chatUser.name?:"",imageUrl = chatUser.imageUrl?:"",onBackClicked = {
                        navHostController.popBackStack()
                        vm.depopulateMessages()
                    })
                }

//
//                      Text(text ="hello",modifier = Modifier.fillMaxWidth())
                MessageBox(modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),chatMessages = vm.chatMessages.value,currentUserId = currentuser?.uid?:"")

                ReplyBox(reply = reply, onReplyChange = { reply=it }, onSendReply = onSendReply)


    }

}


@Composable
fun ReplyBox(
    reply: String,
    onReplyChange: (String) -> Unit,
    onSendReply: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.End

    ) {
        CommonDivider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextField(value = reply, onValueChange = onReplyChange, maxLines = 3)
            Button(onClick = onSendReply) {
                Text(text = "Send")

            }
        }

    }


}

@Composable
fun ChatHeader(
    name: String, imageUrl: String,
    onBackClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Rounded.ArrowBack, contentDescription = null, modifier =
            Modifier
                .clickable { onBackClicked.invoke() }
                .padding(8.dp))
        CommonImage(
            data = imageUrl, modifier = Modifier
                .padding(8.dp)
                .size(50.dp)
                .clip(CircleShape)
        )
        Text(
            text = name, fontWeight = FontWeight.Bold, modifier = Modifier
                .padding(start = 4.dp)
                .fillMaxWidth()
        )
    }

}


@Preview(showBackground = true)
@Composable
fun show() {
    ReplyBox(reply = "", onReplyChange = { }, onSendReply = { })
}


@Composable
fun CommonDivider() {
    Divider(
        color = Color.LightGray,
        thickness = 1.dp,
        modifier = Modifier
            .alpha(0.3f)
            .padding(top = 8.dp, bottom = 8.dp)
    )
}

@Composable
fun MessageBox(modifier: Modifier, chatMessages: List<Message>?, currentUserId: String) {
    LazyColumn(
        modifier = modifier
    ) {
        items(chatMessages ?: emptyList()) { msg ->
            val alignment = if (msg.sendBy == currentUserId) Alignment.End else Alignment.Start
            val color = if (msg.sendBy == currentUserId) Color.DarkGray else Color.Gray
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp), horizontalAlignment = alignment){
               Box(modifier =Modifier.clip(RoundedCornerShape(16.dp)).padding(16.dp).background(color)){
                   Text(
                       text = msg.message ?: "", modifier = Modifier
                           .clip(RoundedCornerShape(8.dp))
                           .padding(10.dp)
                           .background(color),
                       color = Color.White,
                       fontWeight = FontWeight.Bold
                   )
               }
            }
        }
    }
}
