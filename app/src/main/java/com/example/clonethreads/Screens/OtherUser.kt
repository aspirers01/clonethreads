package com.example.clonethreads.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import com.example.clonethreads.Models.UserModel
import com.example.clonethreads.Navigation.Routes
import com.example.clonethreads.Viewmodel.AuthViewmodel
import com.example.clonethreads.Viewmodel.ProfileViewModel
import com.example.clonethreads.utils.SharedPref

@Composable
fun OtherUser(navController: NavHostController,data:String){

//val uid= SharedPref.getusername(LocalContext.current)
    val profilemodel= ProfileViewModel()
    val threads by profilemodel.threads.observeAsState()
    profilemodel.getThreads(data)
    val context= LocalContext.current
       val user by profilemodel.user.observeAsState()
    profilemodel.getuser(data)




    LazyColumn {
        item{
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            )
            {
                val (profiletext, profileimage, username, userbio, followers, following, logout) = createRefs()

                Text(
                    text = "Profile",
                    style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.ExtraBold),
                    modifier = Modifier.constrainAs(profiletext) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    })

                Text(
                    text = user?.username?:"",
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.ExtraBold),
                    modifier = Modifier.constrainAs(username) {
                        top.linkTo(profiletext.bottom, margin = 16.dp)
                        start.linkTo(parent.start)
                    })

                Image(
                    painter = rememberAsyncImagePainter(model = user?.image?:""),
                    contentDescription = "userimg",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(shape = CircleShape)
                        .constrainAs(profileimage) {
                            top.linkTo(profiletext.bottom)
                            end.linkTo(parent.end)
                        },
                    contentScale = ContentScale.Crop
                )


                Text(
                    text = "Bio",
                    style = TextStyle(fontSize = 16.sp),
                    modifier = Modifier.constrainAs(userbio) {
                        top.linkTo(username.bottom)
                        start.linkTo(parent.start)
                    })
                Text(
                    text = "followers",
                    style = TextStyle(fontSize = 16.sp),
                    modifier = Modifier.constrainAs(followers) {
                        top.linkTo(userbio.bottom, 12.dp)
                        start.linkTo(parent.start)
                    })
                Text(
                    text = "following",
                    style = TextStyle(fontSize = 16.sp),
                    modifier = Modifier.constrainAs(following) {
                        top.linkTo(followers.bottom)
                        start.linkTo(parent.start)
                    })


            }
        }
        items(threads?: emptyList()){thread->
            Threaditems(thread = thread, user = user!!)
        }
    }

}
