package com.example.clonethreads.Itemsview

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import com.example.clonethreads.R
import com.example.clonethreads.utils.SharedPref


@Composable
fun Threaditems() {


    val context = LocalContext.current

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        val (userimage, username, date, time, title, image) = createRefs()
        Image(painter = painterResource(id = R.drawable.man),
            contentDescription = "logo",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .constrainAs(userimage) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
            contentScale = ContentScale.Crop
        )

        Text(text = "Username", style = TextStyle(fontSize = 20.sp), modifier = Modifier
            .constrainAs(username) {
                top.linkTo(userimage.top)
                start.linkTo(userimage.end, margin = 8.dp)
                bottom.linkTo(userimage.bottom)
            })


        Text(text = "this is title", style = TextStyle(fontSize = 16.sp), modifier = Modifier
            .constrainAs(title) {
                top.linkTo(username.bottom)
                start.linkTo(username.start)

            })

        ElevatedCard( elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),modifier = Modifier
            .constrainAs(image) {
                top.linkTo(title.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        ) {

            Image(
                painter = painterResource(id = R.drawable.man),
                //rememberAsyncImagePainter(model = imageUri),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentDescription = "image",
                contentScale = ContentScale.Fit
            )

        }


    }


}

@Preview(showBackground = true)
@Composable
fun ThreaditemsPreview() {
    Threaditems()
}