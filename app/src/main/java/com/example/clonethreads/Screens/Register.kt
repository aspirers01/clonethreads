package com.example.clonethreads.Screens


import android.Manifest
import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column



import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.clonethreads.Navigation.Routes


import com.example.clonethreads.R
import com.example.clonethreads.Viewmodel.AuthViewmodel

@Composable
fun Register(navController: NavHostController,viewmodel: AuthViewmodel = viewModel()){

    val firebaseUser by viewmodel.firebaseUser.observeAsState(null)
   val isloading by viewmodel.isloading.observeAsState(false)
    val error by viewmodel.error.observeAsState(null)
    var username: String by remember {
        mutableStateOf("")
    }
    var registeremail: String by remember {
        mutableStateOf("")
    }
    var registerpassword: String by remember {
        mutableStateOf("")
    }
    var confirmpassword: String by remember {
        mutableStateOf("")
    }
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    if(isloading){
        Box(modifier=Modifier.size(50.dp), contentAlignment = Alignment.Center)
        {
            CircularProgressIndicator()
        }
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

    LaunchedEffect(error) {
        if (error != null)
            Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
    }

    LaunchedEffect(firebaseUser) {
        Log.d("register", "launched effect")
        if (firebaseUser != null) {

            navController.navigate(Routes.BottomNav.routes) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    }

    //Todo implement loading screen


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Register",
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
        );
        Box(modifier = Modifier.height(20.dp))
        Image(
            painter = if(imageUri==null) painterResource(id = R.drawable.man)else rememberAsyncImagePainter(
                model =imageUri
            ),
            contentDescription = null,
            modifier = Modifier
                .size(94.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
                .clickable {
                    photopicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

                },
            contentScale = ContentScale.Crop
        )
        Box(modifier = Modifier.height(30.dp))
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("enter username") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp)
        )
        OutlinedTextField(
            value = registeremail,
            onValueChange = { registeremail = it },
            label = { Text("enter email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, top = 24.dp)
        )

        OutlinedTextField(
            value = registerpassword,
            onValueChange = { registerpassword = it },
            label = { Text("enter password") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, top = 24.dp)
        )

        OutlinedTextField(
            value = confirmpassword,
            onValueChange = { confirmpassword = it },
            label = { Text("confirm password") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        )


        ElevatedButton(onClick = {
               if(username.isNotEmpty()&&registeremail.isNotEmpty()&&registerpassword.isNotEmpty()&&confirmpassword.isNotEmpty()) {
                    if(imageUri==null){
                        Toast.makeText(context, "please select image", Toast.LENGTH_SHORT).show()

                    }else {
                        viewmodel.Register(
                            registeremail.toString(),
                            registerpassword.toString(),
                            username.toString(),
                            confirmpassword.toString(),
                            imageUri!!, context
                        )
                        }

               }else {
                   Toast.makeText(context, "please fill all details", Toast.LENGTH_SHORT).show()
               }
        }) {
            Text(
                "Register",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp),
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center
                )
            )


        }

        Box(modifier = Modifier.height(20.dp))
        TextButton(onClick = {
            gotologin(navController)
        }) {
            Text(
                text = "already user ? ", modifier = Modifier
                    .width(230.dp)
                    .padding(start = 24.dp),
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.End
                )
            )
            Text(
                text = "login ", modifier = Modifier
                    .width(130.dp)
                    .padding(end = 20.dp),
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Start
                )
            )
        }


    }
}




fun gotologin(navController: NavHostController) {
    navController.navigate("login") {
        popUpTo(navController.graph.findStartDestination().id)
        popUpTo(navController.graph.findStartDestination().id)
        launchSingleTop = true
    }
}




@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
//    Register()
}
