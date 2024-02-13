package com.example.clonethreads.Itemsview

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.clonethreads.Models.UserModel
import com.example.clonethreads.Navigation.Routes

@Composable
fun UserItems(
    User: UserModel, navHostController: NavHostController
) {
    val context = LocalContext.current

    Column {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp).clickable {

                  val routes=  Routes.OtherUser.routes.replace("{data}",User.uid)

                    navHostController.navigate(routes)
                }
        ) {
            val (userimage, username) = createRefs()
            Image(
                painter = rememberAsyncImagePainter(model = User.image),
                // painterResource(id = R.drawable.man),
                contentDescription = "userimage",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .constrainAs(userimage) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    },
                contentScale = ContentScale.Crop
            )

            Text(text = User.username,
                style = TextStyle(fontSize = 20.sp),
                modifier = Modifier.constrainAs(username) {
                    top.linkTo(userimage.top)
                    start.linkTo(userimage.end, margin = 8.dp)
                    bottom.linkTo(userimage.bottom)
                })


        }

            Divider(color = Color.Black, thickness = 1.dp)
    }

}