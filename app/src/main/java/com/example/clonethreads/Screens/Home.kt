package com.example.clonethreads.Screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.clonethreads.Itemsview.Threaditems
import com.example.clonethreads.Viewmodel.HomeViewModel


@Composable
fun Home(navHostController: NavHostController) {

    val context = LocalContext.current
    val homeviewmodel = HomeViewModel()
    val threadandusers by homeviewmodel.threadandusers.observeAsState(null)

    LazyColumn {
        items(threadandusers?: emptyList() ){pairs->

            Threaditems(thread=pairs.first, user = pairs.second)


        }

    }
}



//    @Preview(showBackground = true)
//    @Composable
//    fun DefaultPreview() {
//        Home()
//    }

