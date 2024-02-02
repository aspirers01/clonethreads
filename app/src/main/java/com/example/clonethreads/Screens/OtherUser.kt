package com.example.clonethreads.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.clonethreads.Itemsview.Threaditems
import com.example.clonethreads.Navigation.Routes
import com.example.clonethreads.Viewmodel.AuthViewmodel
import com.example.clonethreads.Viewmodel.UserThreadViewModel
import com.example.clonethreads.utils.SharedPref
import com.google.firebase.auth.FirebaseAuth

@Composable
fun OtherUser(navHostController: NavHostController, userid: String) {

    val usr= UserThreadViewModel()
    val threads by usr.threads.observeAsState(null)
    val userdata by usr.userdata.observeAsState(null)

     usr.FetchUserdata(userid)
    usr.FetchThread(userid)



    LazyColumn {
        item {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                val (text, logout, username, profileimge, bio, followers, following) = createRefs()


                Text(
                    text = "Profile",
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp),
                    modifier = Modifier.constrainAs(text) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    })

                Text(
                    text = userdata!!.username,
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp),
                    modifier = Modifier.constrainAs(username) {
                        top.linkTo(text.bottom, 12.dp)
                        start.linkTo(parent.start, 6.dp)

                    })
                Image(painter = rememberAsyncImagePainter(userdata!!.image),
                    contentDescription = "profile image",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .constrainAs(profileimge) {
                            top.linkTo(text.bottom, 12.dp)
                            end.linkTo(parent.end, 6.dp)
                        },
                    contentScale = ContentScale.Crop
                )

                Text(
                    text = userdata!!.bio,
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp),
                    modifier = Modifier.constrainAs(bio) {
                        top.linkTo(text.bottom, 12.dp)
                        start.linkTo(parent.start, 6.dp)

                    })
                Text(
                    text = "followers",
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp),
                    modifier = Modifier.constrainAs(followers) {
                        top.linkTo(bio.bottom, 12.dp)
                        start.linkTo(parent.start, 6.dp)

                    })
                Text(
                    text = "following",
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp),
                    modifier = Modifier.constrainAs(following) {
                        top.linkTo(followers.bottom, 12.dp)
                        start.linkTo(parent.start, 6.dp)

                    })


            }
        }
        if (threads != null && threads!!.isNotEmpty() && userdata != null) {
            items(threads ?: emptyList()) {

                Threaditems(thread = it, user = userdata!!, navHostController)


            }


        }

    }



}