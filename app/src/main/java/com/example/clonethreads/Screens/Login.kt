package com.example.clonethreads.Screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState

import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.ExtraBold
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.clonethreads.Navigation.Routes
import com.example.clonethreads.R
import com.example.clonethreads.Viewmodel.AuthViewmodel


@Composable
fun Login( navController: NavHostController) {

// creating viewmodel for register
    val viewmodel = AuthViewmodel()
    val firebaseUser by viewmodel.firebaseUser.observeAsState(null)
    val error by viewmodel.error.observeAsState(null)
    var email: String by remember {
        mutableStateOf("")
    }
    val context= LocalContext.current
    var password: String by remember {
        mutableStateOf("")
    }

    LaunchedEffect(error ){
        if(error!=null)
            Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
    }

    LaunchedEffect(firebaseUser ){
        Log.d("register","launched effect")
        if(firebaseUser!=null){

            navController.navigate(Routes.BottomNav.routes){
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Login", style = TextStyle(fontSize = 24.sp, fontWeight = ExtraBold));
        Box(modifier = Modifier.height(50.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("enter email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("enter password") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        )

        ElevatedButton(onClick = {
              if(email.isNotEmpty() && password.isNotEmpty()){
                    viewmodel.login(email,password,context)
                  }else{
                  Toast.makeText(context, " please fill all details", Toast.LENGTH_SHORT).show()
              }
        }) {
            Text(
                "Login",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp),
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = ExtraBold,
                    textAlign = TextAlign.Center
                ))


        }
        Box(modifier = Modifier.height(20.dp))
           Button(onClick = { /*TODO*/ }) {
                Image(painter = painterResource(id = R.drawable.google), contentDescription = null,)
               Text(
                   "Google",
                   modifier = Modifier
                       .width(180.dp)
                       .padding(start = 24.dp, end = 24.dp),
                   style = TextStyle(
                       fontSize = 24.sp,
                       fontWeight = ExtraBold,
                       textAlign = TextAlign.Center
                   ))
           }
        Box(modifier = Modifier.height(20.dp))
        TextButton(onClick = { gotoregister(navController) }) {
            Text(text ="New user ? ",modifier = Modifier
                .width(180.dp)
                .padding(start = 24.dp),
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = ExtraBold,
                    textAlign = TextAlign.End
                ))
            Text(text ="sign in",modifier = Modifier
                .width(180.dp)
                .padding(end = 24.dp),
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = ExtraBold,
                    textAlign = TextAlign.Start
                ))
        }

    }
}

fun gotoregister(navController: NavHostController) {
navController.navigate(Routes.Register.routes){
    popUpTo(navController.graph.findStartDestination().id)
    launchSingleTop = true
}
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview3() {
//    Login(navController: NavHostController)
}