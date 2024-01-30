package com.example.clonethreads.Screens

import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.clonethreads.Itemsview.Threaditems
import com.example.clonethreads.Itemsview.UserItems
import com.example.clonethreads.Viewmodel.HomeViewModel
import com.example.clonethreads.Viewmodel.SearchViewModel

@Composable
fun Search(navHostController: NavHostController) {

var searchtext by remember {
    mutableStateOf("")
}


    val searchviewmodel = SearchViewModel()
    val userlist by searchviewmodel.searchresult.observeAsState(null)

    Column {
        Text(
            text = "Search",
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.ExtraBold),
            modifier = Modifier
                .padding(top = 6.dp, bottom = 6.dp)
                .align(Alignment.CenterHorizontally),

        )

        OutlinedTextField(
            value = searchtext,
            onValueChange = { searchtext = it },
            label = { Text("search users") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp,bottom = 24.dp),
            leadingIcon = {
                Icon(imageVector =Icons.Default.Search, contentDescription = "searchicon")
            }
        )

        LazyColumn {
            if(userlist!=null && userlist!!.isNotEmpty()) {
                val filterItems = userlist!!.filter {
                    it.username!!.contains(searchtext, ignoreCase = false)
                }
                items(filterItems ?: emptyList()) { it ->

                    UserItems(User = it, navHostController = navHostController)


                }
            }
        }
    }


}