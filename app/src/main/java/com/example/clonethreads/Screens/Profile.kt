package com.example.clonethreads.Screens

import android.util.Log
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.FinishComposingTextCommand
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.clonethreads.Itemsview.Threaditems
import com.example.clonethreads.Navigation.Routes
import com.example.clonethreads.R
import com.example.clonethreads.Viewmodel.AuthViewmodel
import com.example.clonethreads.Viewmodel.UserThreadViewModel
import com.example.clonethreads.utils.SharedPref
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.auth.User


@Composable
fun Profile(
    navController: NavHostController
    ){
    val viewmodel= AuthViewmodel()
    val usr= UserThreadViewModel()
    val threads by usr.threads.observeAsState(null)
   val userdata by usr.userdata.observeAsState(null)

      val uid= FirebaseAuth.getInstance().currentUser!!.uid
      usr.FetchUserdata(uid)
     usr.FetchThread(uid)
    val context= LocalContext.current
    val firebaseUser by viewmodel.firebaseUser.observeAsState(null)
    LaunchedEffect(firebaseUser) {
       if(firebaseUser==null){

          navController.navigate(Routes.Login.routes){
               popUpTo(navController.graph.findStartDestination().id)
               launchSingleTop = true
           }

       }
    }


    LazyColumn{
        item {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                val (text ,logout ,username ,profileimge,bio,followers ,following ) = createRefs()


                Text(text = "Profile",style = TextStyle(fontWeight = FontWeight.Bold,fontSize = 24.sp),modifier = Modifier.constrainAs(text){
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })

                Text(text = userdata!!.username,style = TextStyle(fontWeight = FontWeight.Bold,fontSize = 24.sp),modifier = Modifier.constrainAs(username){
                    top.linkTo(text.bottom,12.dp)
                    start.linkTo(parent.start,6.dp)

                })
                Image(painter = rememberAsyncImagePainter(SharedPref.getimage(context)),contentDescription = "profile image",modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .constrainAs(profileimge) {
                        top.linkTo(text.bottom, 12.dp)
                        end.linkTo(parent.end, 6.dp)
                    },contentScale = ContentScale.Crop)

                Text(text = SharedPref.getbio(context),style = TextStyle(fontWeight = FontWeight.Bold,fontSize = 24.sp),modifier = Modifier.constrainAs(bio){
                    top.linkTo(text.bottom,12.dp)
                    start.linkTo(parent.start,6.dp)

                })
                Text(text = "followers",style = TextStyle(fontWeight = FontWeight.Bold,fontSize = 16.sp),modifier = Modifier.constrainAs(followers){
                    top.linkTo(bio.bottom,12.dp)
                    start.linkTo(parent.start,6.dp)

                })
                Text(text = "following",style = TextStyle(fontWeight = FontWeight.Bold,fontSize = 16.sp),modifier = Modifier.constrainAs(following){
                    top.linkTo(followers.bottom,12.dp)
                    start.linkTo(parent.start,6.dp)

                })
                Box(modifier = Modifier
                    .background(Color.Transparent)
                    .constrainAs(logout) {
                        top.linkTo(following.bottom, 12.dp)
                        start.linkTo(parent.start, 6.dp)


                    }) {
                    Button(onClick = {  viewmodel.signout()},modifier = Modifier.align(Alignment.Center)) {
                        Text(text = "log out")
                    }
                }



            }
        }
        items(threads?: emptyList() ){

            Threaditems(thread=it, user =userdata!!, navController)


        }


    }





}

