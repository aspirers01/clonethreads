package com.example.clonethreads.Screens

import android.app.AlertDialog
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.clonethreads.Itemsview.Threaditems
import com.example.clonethreads.Models.UserModel
import com.example.clonethreads.Navigation.Routes
import com.example.clonethreads.Viewmodel.AuthViewmodel
import com.example.clonethreads.Viewmodel.ProfileViewModel
import com.example.clonethreads.utils.SharedPref



@Composable
fun Profile(navController: NavHostController,viewmodel: AuthViewmodel = viewModel()){


    val profilemodel= ProfileViewModel()
    val uid= SharedPref.getusername(LocalContext.current)
    val threads by profilemodel.threads.observeAsState()

    val followerslist by profilemodel.followerlist.observeAsState()
    val followinglist by profilemodel.followinglist.observeAsState()

    profilemodel.getfollower(uid)
    profilemodel.getfollowing(uid)
    profilemodel.getThreads(uid)
val context= LocalContext.current
    val firebaseUser by viewmodel.firebaseUser.observeAsState(null)
    LaunchedEffect(firebaseUser) {
        if (firebaseUser == null) {

            navController.navigate(Routes.Login.routes) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }

        }
    }

    val openDialog = remember { mutableStateOf(false) }

        val user = UserModel(
            username = SharedPref.getname(context),
            image = SharedPref.getimage(context)
        )
     if(openDialog.value){
         alertdialog(
             onDismiss = { // write code for dissmiss dialog
                 openDialog.value = false
             },
             onConfirm = { viewmodel.signout() },
             title ="log out" ,
             message ="are you sure you want to log out?"
         )
     }
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
                            text = SharedPref.getname(context),
                            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.ExtraBold),
                            modifier = Modifier.constrainAs(username) {
                                top.linkTo(profiletext.bottom, margin = 16.dp)
                                start.linkTo(parent.start)
                            })

                        Image(
                            painter = rememberAsyncImagePainter(model = SharedPref.getimage(context)),
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
                            text = " ${followerslist?.size} followers",
                            style = TextStyle(fontSize = 16.sp),
                            modifier = Modifier.constrainAs(followers) {
                                top.linkTo(userbio.bottom, 12.dp)
                                start.linkTo(parent.start)
                            })
                        Text(
                            text = " ${followinglist?.size} following",
                            style = TextStyle(fontSize = 16.sp),
                            modifier = Modifier.constrainAs(following) {
                                top.linkTo(followers.bottom)
                                start.linkTo(parent.start)
                            })

                        Box(modifier = Modifier
                            .background(Color.Transparent)
                            .constrainAs(logout) {
                                top.linkTo(following.bottom, 12.dp)
                                start.linkTo(parent.start)

                            }) {
                            Button(
                                onClick = {
                                    openDialog.value = true

                                    },
                                modifier = Modifier.align(Alignment.Center)
                            ) {
                                Text(text = "log out")
                            }
                        }

                    }
                }
                   items(threads?: emptyList()){thread->
                        Threaditems(thread = thread, user = user)
                    }
            }


    }

@Composable
fun alertdialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    title: String,
    message: String
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = title) },
        text = { Text(text = message) },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm()
                    onDismiss()
                }
            ) {
                Text(text = "Confirm")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss
            ) {
                Text(text = "Dismiss")
            }
        }
    )
}



