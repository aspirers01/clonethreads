package com.example.clonethreads.Screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.clonethreads.Navigation.Routes
import com.example.clonethreads.R
import com.example.clonethreads.Viewmodel.AddThreadViewModel
import com.example.clonethreads.utils.SharedPref
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


@Composable
fun AddThreads(navController: NavHostController,threadModel:AddThreadViewModel= viewModel()) {
    

    val isposted by threadModel.isposted.observeAsState(false)
    val isloading by threadModel.isloading.observeAsState(false)

    var thread: String by remember {
        mutableStateOf("")
    }
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val context = LocalContext.current
    val photopicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let {
                imageUri = it
            }
        }
    )

    LaunchedEffect(isposted ){
        if(isposted!!){
         thread=""
            imageUri=null
            Toast.makeText(context, "thread posted succesfully", Toast.LENGTH_SHORT).show()
      navController.navigate(Routes.Home.routes){
            popUpTo(Routes.AddThreads.routes){
                inclusive=true
            }
          launchSingleTop=true
        }
        }
    }


    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val (crossPic, text, logo, userName, editText, attachMedia, replyText, button, loading) = createRefs()

        Image(painter = painterResource(id = R.drawable.baseline_close_24),
            contentDescription = "cross",

            modifier = Modifier
                .padding(5.dp)
                .constrainAs(crossPic) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }.clickable {
                    navController.navigate(Routes.BottomNav.routes) {
                        popUpTo(Routes.Splash.routes){
                            inclusive = true
                        }
                    }

                }

        )

        Text(text = "Add Thread",
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.ExtraBold),
            modifier = Modifier
                .padding(5.dp)
                .constrainAs(text) {
                    top.linkTo(crossPic.top)
                    start.linkTo(crossPic.end)
                    bottom.linkTo(crossPic.bottom)
                    end.linkTo(parent.end)
                })
        Image(
            painter = rememberAsyncImagePainter(model = SharedPref.getimage(context)),
            //  painterResource(id = R.drawable.man),
            contentDescription = "cross",
            modifier = Modifier
                .constrainAs(logo) {
                    top.linkTo(text.bottom, 16.dp)
                    start.linkTo(text.start)
                    end.linkTo(text.end)
                }
                .size(80.dp)
                .clip(shape = CircleShape), contentScale = ContentScale.Crop
        )
        if(isloading){
            Box(modifier=Modifier.size(50.dp).constrainAs(loading){
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)

            }.zIndex(2.0F), contentAlignment = Alignment.Center)
            {
                CircularProgressIndicator()
            }
        }
        Text(text = SharedPref.getname(context),
            style = TextStyle(fontSize = 24.sp),
            modifier = Modifier
                .padding(5.dp)
                .constrainAs(userName) {
                    top.linkTo(logo.bottom)
                    start.linkTo(crossPic.start)

                })

        basictextfieldwithhint(
            hint = "start a thread ... ",
            value = thread,
            onValueChange = { thread = it },
            modifier = Modifier
                .constrainAs(editText) {
                    top.linkTo(userName.bottom, 16.dp)
                    start.linkTo(crossPic.start)
                    end.linkTo(parent.end)
                }
                .padding(5.dp)
                .fillMaxWidth()
        )

        if (imageUri == null) {
            Image(
                painter = painterResource(id = R.drawable.baseline_attachment_24),
                contentDescription = "cross",
                modifier = Modifier
                    .constrainAs(attachMedia) {
                        top.linkTo(editText.bottom, 16.dp)
                        start.linkTo(crossPic.start)

                    }
                    .clickable {
                        photopicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    }
                    .size(30.dp)
                    .clip(shape = RectangleShape)

            )
        } else {
            Box(modifier = Modifier
                .background(Color.LightGray)
                .padding(2.dp)
                .constrainAs(attachMedia) {
                    top.linkTo(editText.bottom, 16.dp)
                    start.linkTo(crossPic.start)

                }
                .clickable {
                    photopicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }
                .fillMaxWidth()
                .height(200.dp)
                .clip(shape = RectangleShape)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = imageUri),
                    contentDescription = "image",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(shape = RectangleShape)
                )
                Icon(imageVector = Icons.Default.Close, contentDescription = "close",
                    modifier = Modifier
                        .padding(5.dp)
                        .scale(1.0f)
                        .align(Alignment.TopEnd)

                        .clickable {
                            imageUri = null
                        }
                )
            }
        }
        Text(text = "any one can reply ",
            style = TextStyle(fontSize = 20.sp),
            modifier = Modifier
                .padding(5.dp)
                .constrainAs(replyText) {

                    start.linkTo(crossPic.start)
                    bottom.linkTo(parent.bottom, 16.dp)
                })

        TextButton(onClick = {
            if(imageUri==null) {
                if(thread.isEmpty()){
                    Toast.makeText(context, "please enter thread", Toast.LENGTH_SHORT).show()
                }else
                threadModel.savedata(thread,FirebaseAuth.getInstance().currentUser?.uid!!, "")
            }
            else{
                if(thread.isEmpty()){
                    Toast.makeText(context, "please enter thread", Toast.LENGTH_SHORT).show()
                }else
                threadModel.saveimage(thread, FirebaseAuth.getInstance().currentUser?.uid!!, imageUri!!)
            }

        }, modifier = Modifier.constrainAs(button) {
            end.linkTo(parent.end, 5.dp)
            bottom.linkTo(parent.bottom, 16.dp)
        }) {
            Text(
                text = "Post",
                style = TextStyle(fontSize = 20.sp)
            )
        }


    }
}


@Composable
fun basictextfieldwithhint(
    hint: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier
) {
    Box(modifier = modifier) {
        if (value.isEmpty()) {
            Text(text = hint, style = TextStyle(fontSize = 16.sp), color = Color.Gray)
        }
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth()
        )


    }


}

//@Preview(showBackground = true)
//@Composable
//fun AddThreadsPreview() {
//    AddThreads()
//}